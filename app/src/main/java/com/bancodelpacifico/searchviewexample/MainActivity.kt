package com.bancodelpacifico.searchviewexample

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM_SECOND_OPTION
import com.bancodelpacifico.searchviewbdp.interfaces.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // first step is create of object that extend of ListSearchableInterface
        val listSearchable = arrayListOf(
                ItemsModel("CATEGORY - 1","Description",type = CATEGORY , categoryId = 0, isShow = false),
                ItemsModel("Manuel ITEM OPTION","Marcos","Cedula  -  ps0000",type = ITEM  , iconItem = R.drawable.ic_baseline_arrow_back_24, categoryId = 0),
                ItemsModel("Gabriel","Description","Cedula  -  ps0000",type = ITEM ,categoryId = 0),
                ItemsModel("Marcos","Gabriel","Cedula  -  ps0000",type = ITEM,categoryId = 0)
        )

        // set Up searchView bdp
        val searchViewLayout: SearchViewBdp = findViewById(R.id.search_view_container)
        searchViewLayout.setExpandedContentSupportFragment(this)

        searchViewLayout.setCollapsedHint("Buscar")
        searchViewLayout.setExpandedHint("Buscar")
        searchViewLayout.addListToSearch(listSearchable)
        searchViewLayout.handleToolbarAnimation(toolbar)

        val collapsed = ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent))
        val expanded = ColorDrawable(ContextCompat.getColor(this, R.color.BACKGROUND_COLOR_PRIMARY_DARK))
        searchViewLayout.setTransitionDrawables(collapsed,expanded)
        //searchViewLayout.initAnimationFadeOn(false)

        /*searchViewLayout.putListToSearch(arrayListOf(
            ItemsModel("CATEGORY - 2","Description",type = CATEGORY,categoryId = 1),
            ItemsModel("Gabriel","Manuel","",type = ITEM,iconItem = R.drawable.ic_baseline_search_24,categoryId = 0),
            ItemsModel("Manuel One","Description","",type = ITEM,categoryId = 1),
            ItemsModel("Marcos","Gabriel","",type = ITEM_SECOND_OPTION,categoryId = 1)
        ))*/
        searchViewLayout.setSearchListener(object:
            SearchListener {
            override fun onFinished(searchKeyword: String?) {
                Log.v("SearchViewBdp","onFinish")
            }
        })
        searchViewLayout.onListenerToggle(object : OnListenerToggle{
            override fun onCollapsing() {
                Log.v("SearchViewBdp", "Collapsing")
            }
            override fun onExpanding() {
                Log.v("SearchViewBdp", "Excpanding")
            }
        })

        searchViewLayout.setSearchListenerOn(object : OnListenerButton{
            override fun button1() {
                Log.v("SearchViewBdp","button 1")
            }

            override fun button2() {
                Log.v("SearchViewBdp", "button 2")
            }

            override fun onClickItemSelection(itemsModel: ItemsModel) {
                Log.v("SearchViewBdp", "onClick Item")
            }
        })

        val button = findViewById<Button>(R.id.buttonOn)

        button.setOnClickListener{
            searchViewLayout.onClickSearchView()
        }
    }
}