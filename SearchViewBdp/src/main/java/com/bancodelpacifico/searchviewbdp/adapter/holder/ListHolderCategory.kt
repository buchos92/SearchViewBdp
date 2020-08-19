package com.bancodelpacifico.searchviewbdp.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import kotlinx.android.synthetic.main.search_content_list_item_category.view.*

class ListHolderCategory(v: View): RecyclerView.ViewHolder(v),
    View.OnClickListener {
    private var view: View = v
    private var items: ItemsModel? = null

    init {
        v.setOnClickListener(this)
    }
    fun bindItems(item : ItemsModel?){
        view.title.text = item?.tittle
    }
    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
    companion object{
        private val ITEMS_KEY = "ITEMS_KEY"
    }
}