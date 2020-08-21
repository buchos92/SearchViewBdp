package com.bancodelpacifico.searchviewbdp.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import com.bancodelpacifico.searchviewbdp.interfaces.OnListenerButton
import kotlinx.android.synthetic.main.search_content_list_item_item.view.*

class ListHolderItems(v: View,var onListenerButton:OnListenerButton): RecyclerView.ViewHolder(v),
    View.OnClickListener {
    private var view: View = v
    private var items: ItemsModel? = null

    init {
        v.setOnClickListener(this)
    }
    fun bindItems(item : ItemsModel?, nItems:Boolean){
        items = item
        view.title.text = item?.tittle
        view.description.text = item?.description
        view.additional.text = item?.additional

        // icons
        val iconItem = item?.iconItem ?: R.drawable.ic_baseline_search_24
        view.iconItem.setImageResource(iconItem)

        if(nItems)
            view.line.visibility = View.INVISIBLE

        //view.content.setOnClickListener { onListenerButton.onClickItemSelection(item!!) }
    }
    override fun onClick(p0: View?) {
       onListenerButton.onClickItemSelection(items!!)
    }
    companion object{
        private val ITEMS_KEY = "ITEMS_KEY"
    }
}