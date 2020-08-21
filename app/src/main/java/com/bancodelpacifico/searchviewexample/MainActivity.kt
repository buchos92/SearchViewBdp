package com.bancodelpacifico.searchviewexample

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import com.bancodelpacifico.searchviewbdp.interfaces.OnListenerButton
import com.bancodelpacifico.searchviewbdp.interfaces.SearchBoxListener
import com.bancodelpacifico.searchviewbdp.interfaces.SearchListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // first step is create of object that extend of ListSearchableInterface
        val listSearchable = arrayListOf(
                ItemsModel("CATEGORY - 1","Description",type = CATEGORY , categoryId = 0),
                ItemsModel("Manuel","Marcos","",ITEM , iconItem = R.drawable.ic_baseline_arrow_back_24, categoryId = 0),
                ItemsModel("Gabriel","Description","",ITEM ,categoryId = 0),
                ItemsModel("Marcos","Gabriel","",ITEM,categoryId = 0)
        )

        // set Up searchView bdp
        val searchViewLayout: SearchViewBdp = findViewById(R.id.search_view_container)
        searchViewLayout.setExpandedContentSupportFragment(this)

        searchViewLayout.setCollapsedHint("Welcome to Bdp")
        searchViewLayout.setExpandedHint("Buscar...")
        searchViewLayout.addListToSearch(listSearchable)

        searchViewLayout.putListToSearch(arrayListOf(
            ItemsModel("CATEGORY - 2","Description",type = CATEGORY,categoryId = 1),
            ItemsModel("Gabriel","Manuel","",ITEM,iconItem = R.drawable.ic_baseline_search_24,categoryId = 0),
            ItemsModel("Manuel One","Description","",ITEM,categoryId = 1),
            ItemsModel("Marcos","Gabriel","",ITEM,categoryId = 1)
        ))
        searchViewLayout.setSearchListener(object:
            SearchListener {
            override fun onFinished(searchKeyword: String?) {

            }
        })
        searchViewLayout.setSearchBoxListener(object : SearchBoxListener {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        searchViewLayout.setSearchListenerOn(object : OnListenerButton{
            override fun button1() {
                Log.v("BUTTON1","BUTTON2")
            }

            override fun button2() {

            }
        })
    }
}