package com.bancodelpacifico.searchviewbdp.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.Utils
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import com.bancodelpacifico.searchviewbdp.interfaces.OnListenerButton
import kotlinx.android.synthetic.main.search_content_list_item_item.view.*
import kotlinx.android.synthetic.main.search_content_list_item_item.view.additional1
import kotlinx.android.synthetic.main.search_content_list_item_item.view.description
import kotlinx.android.synthetic.main.search_content_list_item_item.view.title
import kotlinx.android.synthetic.main.search_content_list_item_second.view.*

class ListHolderItemsSecondOption(v: View, private var onListenerButton: OnListenerButton): RecyclerView.ViewHolder(v), View.OnClickListener {
    private var view: View = v
    private var items: ItemsModel? = null

    init {
        v.setOnClickListener(this)
    }
    fun bindItems(item : ItemsModel?, nItems:Boolean){
        items = item
        view.title.text = item?.tittle
        view.description.text = Utils.toTitleCase(item?.description!!)
        view.additional1.text = item.additional1
        view.additional2.text = item.additional2

        if(nItems)
            view.line.visibility = View.INVISIBLE

    }
    override fun onClick(p0: View?) {
        onListenerButton.onClickItemSelection(items!!)
    }
    companion object {
        private val ITEMS_KEY = "ITEMS_KEY"
    }
}