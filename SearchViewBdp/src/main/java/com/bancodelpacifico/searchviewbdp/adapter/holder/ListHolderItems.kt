package com.bancodelpacifico.searchviewbdp.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import kotlinx.android.synthetic.main.search_content_list_item_item.view.*

class ListHolderItems(v: View): RecyclerView.ViewHolder(v),
    View.OnClickListener {
    private var view: View = v
    private var items: ItemsModel? = null

    init {
        v.setOnClickListener(this)
    }
    fun bindItems(item : ItemsModel?, nItems:Boolean){
        view.title.text = item?.tittle
        view.description.text = item?.description
        view.additional.text = item?.tittle

        // icons
        val iconItem = item?.iconItem ?: R.drawable.ic_baseline_search_24
        view.iconItem.setImageResource(iconItem)

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