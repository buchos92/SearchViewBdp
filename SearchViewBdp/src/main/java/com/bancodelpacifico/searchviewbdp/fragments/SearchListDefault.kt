package com.bancodelpacifico.searchviewbdp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import kotlinx.android.synthetic.main.search_content_list_item_category.view.title
import kotlinx.android.synthetic.main.search_content_list_item_item.view.*


class SearchListDefault : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView:RecyclerView
    private lateinit var adpater: RecyclerAdpater
    private val ITEMS = "listOfItems"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_search_static_list, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)

        setRecyclerViewOfList(arguments?.getParcelableArrayList(ITEMS)!!)
        return rootView
    }

    private fun setRecyclerViewOfList(items: ArrayList<out SearchViewBdp.ListSearchableAbs >){
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        adpater =
            RecyclerAdpater(items)
        recyclerView.adapter = adpater
    }
}

class RecyclerAdpater( var items : ArrayList<out SearchViewBdp.ListSearchableAbs>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val CATEGORY = 0
    private val ITEM = 1

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if(item.type == CATEGORY)
            (holder as ListHolderCategory).bindItems(item)
        else
            (holder as ListHolderItems).bindItems(item,theLastItemIsCategory(items, position))
    }

    private fun theLastItemIsCategory(items: ArrayList<out SearchViewBdp.ListSearchableAbs>, position: Int):Boolean{
        if(items.isNotEmpty() && position > (items.size - 1)){
            return items[position - 1].type == CATEGORY
        }
        return false
    }
    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    override fun getItemViewType(position: Int): Int {
        return if (items[position].type == CATEGORY)
            SearchViewBdp.CATEGORY
        else
            SearchViewBdp.ITEM
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view: View
        return if (viewType === CATEGORY) { // for call layout
            view = parent.inflate(R.layout.search_content_list_item_category, false)
            ListHolderCategory(
                view
            )
        } else { // for email layout
            view = parent.inflate(R.layout.search_content_list_item_item, false)
            ListHolderItems(
                view
            )
        }
    }

    class ListHolderCategory(v:View): RecyclerView.ViewHolder(v),View.OnClickListener {
        private var view:View = v
        private var items: SearchViewBdp.ListSearchableAbs? = null

        init {
            v.setOnClickListener(this)
        }
        fun bindItems(item : SearchViewBdp.ListSearchableAbs?){
            view.title.text = item?.tittle
        }
        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
        companion object{
            private val ITEMS_KEY = "ITEMS_KEY"
        }
    }

    class ListHolderItems(v:View): RecyclerView.ViewHolder(v),View.OnClickListener {
        private var view:View = v
        private var items: SearchViewBdp.ListSearchableAbs? = null

        init {
            v.setOnClickListener(this)
        }
        fun bindItems(item : SearchViewBdp.ListSearchableAbs?, nItems:Boolean){
            view.title.text = item?.tittle
            view.description.text = item?.description
            view.additional.text = item?.tittle

            if(nItems)
                view.line.visibility = View.INVISIBLE
        }
        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
        companion object{
            private val ITEMS_KEY = "ITEMS_KEY"
        }
    }
}