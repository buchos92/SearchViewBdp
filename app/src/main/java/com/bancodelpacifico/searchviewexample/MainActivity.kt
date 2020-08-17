package com.bancodelpacifico.searchviewexample

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // first step is create of object that extend of ListSearchableInterface
        val listSearchable = arrayListOf(
            Items("CATEGORY","Description","",CATEGORY),
            Items("Test One","Description","",ITEM),
            Items("Test One","Description","",ITEM),
            Items("Test One","Description","",ITEM),
            Items("Test One","Description","",ITEM),
            Items("CATEGORY2","Description","",CATEGORY),
            Items("Test One","Description","",ITEM),
            Items("Test One","Description","",ITEM),
            Items("Test One","Description","",ITEM)
        )

        // set Up searchView bdp
        val searchViewLayout: SearchViewBdp = findViewById(R.id.search_view_container)
        searchViewLayout.setExpandedContentSupportFragment(this)

        searchViewLayout.setCollapsedHint("Welcome to Bdp")
        searchViewLayout.setExpandedHint("Buscar...")
        searchViewLayout.setListToSearch(listSearchable)

        searchViewLayout.setSearchListener(object:SearchViewBdp.SearchListener{
            override fun onFinished(searchKeyword: String?) {

            }
        })
        searchViewLayout.setSearchBoxListener(object : SearchViewBdp.SearchBoxListener{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}