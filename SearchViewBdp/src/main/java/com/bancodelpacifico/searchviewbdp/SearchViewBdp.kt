package com.bancodelpacifico.searchviewbdp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bancodelpacifico.searchviewbdp.view.ViewSearchCategory
import com.bancodelpacifico.searchviewbdp.view.ViewSearchNotMatch
import com.bancodelpacifico.searchviewbdp.interfaces.*
import com.bancodelpacifico.searchviewbdp.view.ViewEmpty


class SearchViewBdp(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs),OnListenerButton{

    private lateinit var mCollapsed:ViewGroup
    private lateinit var mSearchIcon:View
    private lateinit var mCollapsedSearchBox:View
    private lateinit var mCollapsedHintView:TextView

    private lateinit var mExpanded:ViewGroup
    private lateinit var mSearchEditText:EditText
    private lateinit var mBackButtonView:View
    private lateinit var mExpandedSearchIcon:View

    private var mToolbar: Toolbar? = null
    private var listSearchable:ArrayList<ItemsModel > = arrayListOf()

    private var toolbarExpandedHeight = 0
    private var mExpandedHeight = 0
    private var ANIMATION_DURATION = 500

    private var mBackgroundTransition: TransitionDrawable? = null

    private var mIsExpanded = false
    private var mCollapsedHeight:Int? = null
    private var mSearchListener: SearchListener? = null
    private var mSearchBoxListener: SearchBoxListener? = null

    private var mCollapsedDrawable: Drawable? = null
    private var mExpandedDrawable: Drawable? = null

    private var mAnimator: ValueAnimator? = null
    private val mOnToggleAnimationListener: OnToggleAnimationListener? = null

    private val mFragmentManager: FragmentManager? = null
    private val mExpandedContentFragment: Fragment? = null
    private var mSupportFragmentManager: FragmentManager? = null
    private var mExpandedContentCurrentViewSearch : ViewSearchCategory = ViewSearchCategory(this)
    private val mExpandetContentFragmentNoMatch : ViewSearchNotMatch = ViewSearchNotMatch()
    private val mViewEmpty: ViewEmpty = ViewEmpty()
    private var searchEngine:SearchEngine = SearchEngine()

    private lateinit var onListenerButton: OnListenerButton
    private var initFadeOn = true

    companion object{
        // types of items
        const val CATEGORY:Int = 0
        const val ITEM:Int = 1
        const val ITEM_SECOND_OPTION:Int = 2
        const val EMPTY :Int = 21

        // config params of animation
        const val PADDINGANIMATION = 0F

    }

    init {
        mExpandetContentFragmentNoMatch.setSearchListenerOn(this)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.designSearchView)
            val text = typedArray.getText(R.styleable.designSearchView_design_searchview)

            Log.v("text",text.toString())
            typedArray.recycle()
        }
        ANIMATION_DURATION = context.resources.getInteger(R.integer.animation_duration)
    }

    @Override
    override fun onFinishInflate() {
        mCollapsed = findViewById(R.id.search_box_collapsed)
        mSearchIcon = findViewById(R.id.search_magnifying_glass)
        mCollapsedSearchBox = findViewById(R.id.search_box_start_search)
        mCollapsedHintView = findViewById(R.id.search_box_collapsed_hint)


        mExpanded = findViewById(R.id.search_expanded_root)
        mSearchEditText = mExpanded.findViewById(R.id.search_expanded_edit_text)
        mBackButtonView = mExpanded.findViewById(R.id.search_expanded_back_button)
        mExpandedSearchIcon = findViewById(R.id.search_expanded_magnifying_glass)

        mCollapsedSearchBox.setOnLongClickListener {
            mCollapsedSearchBox.performClick()
            mSearchEditText.performLongClick()
            false
        }

        mCollapsed.setOnClickListener(mSearchViewOnClickListener)
        mSearchIcon.setOnClickListener(mSearchViewOnClickListener)
        mCollapsedSearchBox.setOnClickListener(mSearchViewOnClickListener)

        mSearchEditText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Utils.showInputMethod(v)
            } else {
                Utils.hideInputMethod(v)
            }
        }


        mSearchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callSearchListener()
                Utils.hideInputMethod(v!!)
                return@OnEditorActionListener true
            }
            false
        })
        mSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                phrases: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (mSearchEditText.text.isNotEmpty()) {
                    if (mExpandedSearchIcon.visibility != View.VISIBLE) {
                        Utils.fadeIn(
                            mExpandedSearchIcon,
                            ANIMATION_DURATION
                        )
                    }

                } else {
                    Utils.fadeOut(
                        mExpandedSearchIcon,
                        ANIMATION_DURATION
                    )
                }
                // response to interface
                mSearchBoxListener?.onTextChanged(
                    phrases,
                    start,
                    before,
                    count
                )


                // PROCESS FOR SEARCH
                val listResultSearch = searchEngine.searchItem(phrases)

                when {
                    listResultSearch.size > 0 -> {
                        mExpandedContentCurrentViewSearch.addNewItems(listResultSearch)
                        showContextMatchResult()
                    }
                    mSearchEditText.text.isNotEmpty() -> {
                        showContentNotMatchResult()
                        mExpandedContentCurrentViewSearch.addNewItems(listSearchable)
                    }
                    else -> {
                        clearContent()
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                mSearchBoxListener?.beforeTextChanged(
                    s,
                    start,
                    count,
                    after
                )
            }

            override fun afterTextChanged(s: Editable) {
                mSearchBoxListener?.afterTextChanged(s)
            }
        })

        mBackButtonView.setOnClickListener { collapse() }

        mExpandedSearchIcon.setOnClickListener { v ->
            callSearchListener()
            Utils.hideInputMethod(v)
        }

        mCollapsedDrawable = ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent))
        mExpandedDrawable = ColorDrawable(ContextCompat.getColor(context, R.color.default_color_expanded))
        mBackgroundTransition = TransitionDrawable(
            arrayOf(
                mCollapsedDrawable,
                mExpandedDrawable
            )
        )
        mBackgroundTransition!!.isCrossFadeEnabled = true
        setBackgroundCompat()

        // here is established the form to init the look and feel

        //Utils.setPaddingAll(this, PADDINGANIMATION,8f)

        super.onFinishInflate()
    }

    /**
     * Open the search UI when the user clicks on the search box.
     */
    private val mSearchViewOnClickListener = OnClickListener {
            if (!mIsExpanded) {
                expand(true)
            }
    }
    fun onClickSearchView(){
        if (!mIsExpanded) {
            expand(true)
        }
    }
    private fun expand(requestFocus: Boolean) {
        mCollapsedHeight = height
        mBackgroundTransition?.startTransition(ANIMATION_DURATION)
        toggleToolbar(true)
        mIsExpanded = true
        animateStates(true, 1f, 0f)
        Utils.crossFadeViews(mExpanded, mCollapsed, ANIMATION_DURATION)
        if (requestFocus) {
            mSearchEditText.requestFocus()
        }
    }
    private fun expandForFirstTime(requestFocus: Boolean) {
        mCollapsedHeight = height
        mBackgroundTransition?.startTransition(ANIMATION_DURATION)
        toggleToolbar(true)
        mIsExpanded = true
        animateStates(true, 1f, 0f)
        Utils.crossFadeViews(mExpanded, mCollapsed, ANIMATION_DURATION)
        if (requestFocus) {
            mSearchEditText.requestFocus()
        }

    }
    private fun collapse() {
        mBackgroundTransition?.reverseTransition(
            ANIMATION_DURATION
        )
        toggleToolbar(false)
        mSearchEditText.text = null
        mIsExpanded = false
        animateStates(false, 0f, 1f)
        if(initFadeOn)
            Utils.crossFadeViews(mCollapsed, mExpanded, ANIMATION_DURATION)
        else
            Utils.crossFadeViews(mExpanded, mExpanded, ANIMATION_DURATION)
        hideContentFragment()
    }
    private fun setBackgroundCompat() {
        background = mBackgroundTransition
    }
    private fun callSearchListener() {
        val editable = mSearchEditText.text
        if (editable != null && editable.isNotEmpty()) {
            mSearchListener?.onFinished(editable.toString())
        }
    }

    private fun toggleToolbar(expanding: Boolean) {
        if (mToolbar == null) return
        mToolbar!!.clearAnimation()
        if (expanding) {
            toolbarExpandedHeight = mToolbar!!.height
        }
        val toYValue = (if (expanding) toolbarExpandedHeight * -1 else 0).toFloat() // 80 * -1 = -80
        mToolbar!!.animate()
            .y(toYValue)
            .setDuration(ANIMATION_DURATION.toLong())
            .start()
        Utils.animateHeight(
            mToolbar!!,
            if (expanding) toolbarExpandedHeight else 0,
            if (expanding) 0 else toolbarExpandedHeight,
            ANIMATION_DURATION
        )
    }

    /***
     */
    fun setExpandedContentSupportFragment(
        activity: FragmentActivity,
        contentSupportFragment: Fragment? = null
    ) {
        mSupportFragmentManager = activity.supportFragmentManager
        mExpandedHeight = Utils.getSizeOfScreen(activity)!!.y
    }

    private fun animateStates(
        expand: Boolean,
        start: Float,
        end: Float
    ) {
        mAnimator = ValueAnimator.ofFloat(start, end)
        mAnimator!!.cancel()
        mAnimator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (expand) {
                    Utils.setPaddingAll(this@SearchViewBdp, 0F)
                    //showContextMatchResult()
                    //mExpandedContentCurrentViewSearch.addNewItems(listSearchable)
                    val params = layoutParams
                    params.height = mExpandedHeight
                    layoutParams = params
                } else {
                    Utils.setPaddingAll(this@SearchViewBdp, PADDINGANIMATION)
                }
                mOnToggleAnimationListener?.onFinish(expand)
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                if (!expand) {
                    val params = layoutParams
                    params.height = mCollapsedHeight!!
                    layoutParams = params
                }
                mOnToggleAnimationListener?.onStart(expand)
            }
        })
        mAnimator!!.addUpdateListener { animation ->
            var padding = (PADDINGANIMATION * animation.animatedFraction).toInt()
            if (expand) padding = PADDINGANIMATION.toInt() - padding
            Utils.setPaddingAll(this, padding.toFloat())
        }
        mAnimator!!.duration = ANIMATION_DURATION.toLong()
        mAnimator!!.start()
    }

    private fun showContextMatchResult(){
        // replace fragment content
        replaceFragmentContent(mExpandedContentCurrentViewSearch)
    }
    private fun showContentNotMatchResult() {
        // replace fragment content
        replaceFragmentContent(mExpandetContentFragmentNoMatch)
    }

    private fun replaceFragmentContent(fragment: Fragment?){
        // replace fragment content
        if (mFragmentManager != null) {
            val transaction: FragmentTransaction = mFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.animator.fade_in_object_animator,
                R.animator.fade_out_object_animator
            )
            transaction.replace(R.id.search_expanded_content, fragment!!)
            transaction.commit()
        } else if (mSupportFragmentManager != null) {
            val transaction = mSupportFragmentManager!!.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in_anim_set, R.anim.fade_out_anim_set)
            transaction.replace(R.id.search_expanded_content, fragment!!)
            transaction.commit()
        }
    }
    private fun clearContent(){
        // replace fragment content
        if (mFragmentManager != null) {
            val transaction: FragmentTransaction = mFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.animator.fade_in_object_animator,
                R.animator.fade_out_object_animator
            )
            transaction.replace(R.id.search_expanded_content,mViewEmpty)
            transaction.commit()
        } else if (mSupportFragmentManager != null) {
            val transaction = mSupportFragmentManager!!.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in_anim_set, R.anim.fade_out_anim_set)
            transaction.replace(R.id.search_expanded_content, mViewEmpty)
            transaction.commit()
        }
    }

    private fun hideContentFragment() {
        if (mFragmentManager != null) {
            val transaction: FragmentTransaction = mFragmentManager.beginTransaction()
            mExpandedContentFragment?.let { transaction.remove(it).commit() }
        } else if (mSupportFragmentManager != null) {
            val transaction: FragmentTransaction =
                mSupportFragmentManager!!.beginTransaction()
            mExpandedContentCurrentViewSearch.let { transaction.remove(it).commit() }
        } else {
            //
        }
    }
    fun setTransitionDrawables(
        collapsedDrawable: Drawable?,
        expandedDrawable: Drawable?
    ) {
        mCollapsedDrawable = collapsedDrawable
        mExpandedDrawable = expandedDrawable
        mBackgroundTransition = TransitionDrawable(
            arrayOf(
                mCollapsedDrawable,
                mExpandedDrawable
            )
        )
        mBackgroundTransition!!.isCrossFadeEnabled = true
        setBackgroundCompat()
        Utils.setPaddingAll(this@SearchViewBdp, PADDINGANIMATION)
    }

    fun initAnimationFadeOn(value:Boolean){
        this.initFadeOn = value
        if(!value){
            mCollapsed.alpha = 0f
            mCollapsed.visibility = View.INVISIBLE
        }
    }


    fun setCollapsedHint(searchViewHint: String?) {
        if (searchViewHint != null) {
            mCollapsedHintView.hint = searchViewHint
        }
    }
    fun setExpandedHint(searchViewHint: String?) {
        if (searchViewHint != null) {
            mSearchEditText.hint = searchViewHint
        }
    }

    fun handleToolbarAnimation(toolbar: Toolbar?) {
        mToolbar = toolbar
    }
    fun setSearchListener(listener: SearchListener) {
        mSearchListener = listener
    }

    fun setSearchBoxListener(listener: SearchBoxListener) {
        mSearchBoxListener = listener
    }

    fun addListToSearch(listSearchable:MutableList<ItemsModel>){
        searchEngine.setItemsModel(listSearchable)
        this.listSearchable.addAll(listSearchable)
    }
    fun putListToSearch(listSearchable:MutableList<ItemsModel>){
        searchEngine.setItemsModel(listSearchable)
        this.listSearchable.addAll(listSearchable)
    }

    fun setSearchListenerOn(onListenerButton: OnListenerButton){
        this.onListenerButton = onListenerButton
    }
    override fun button1() {
        onListenerButton.button1()
    }

    override fun button2() {
        onListenerButton.button1()
    }

    override fun onClickItemSelection(itemsModel: ItemsModel) {
        onListenerButton.onClickItemSelection(itemsModel)
    }
}