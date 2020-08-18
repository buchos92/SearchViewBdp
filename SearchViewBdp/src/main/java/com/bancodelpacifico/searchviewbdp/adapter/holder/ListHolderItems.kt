package com.bancodelpacifico.searchviewbdp.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.interfaces.ListSearchableAbs
import kotlinx.android.synthetic.main.search_content_list_item_item.view.*

class ListHolderItems(v: View): RecyclerView.ViewHolder(v),
    View.OnClickListener {
    private var view: View = v
    private var items: ListSearchableAbs? = null

    init {
        v.setOnClickListener(this)
    }
    fun bindItems(item : ListSearchableAbs?, nItems:Boolean){
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