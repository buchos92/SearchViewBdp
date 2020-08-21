package com.bancodelpacifico.searchviewbdp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import com.bancodelpacifico.searchviewbdp.adapter.holder.ListHolderCategory
import com.bancodelpacifico.searchviewbdp.adapter.holder.ListHolderItems
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import com.bancodelpacifico.searchviewbdp.interfaces.OnListenerButton

class AdpaterMultipleCategory(private var items : MutableList<ItemsModel>? = mutableListOf(),var onListenerButton: OnListenerButton) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val CATEGORY = 0
    private val ITEM = 1

    override fun getItemCount(): Int {
        return items?.size!!
    }
    fun addNewItems(itemsList: MutableList<out ItemsModel>) {
        items?.clear()
        items?.addAll(itemsList)
        notifyDataSetChanged()
    }
    fun adItems(itemsList: MutableList<out ItemsModel>) {
        items?.addAll(itemsList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items?.size ?: return
        val item = items?.get(position)
        if(item?.type == CATEGORY)
            (holder as ListHolderCategory).bindItems(item)
        else
            (holder as ListHolderItems).bindItems(item,theLastItemIsCategory(items!!, position))
    }

    private fun theLastItemIsCategory(items: MutableList<out ItemsModel>, position: Int):Boolean{
        if(items.isNotEmpty() && position > (items.size - 1)){
            return items[position - 1].type == CATEGORY
        }
        return false
    }
    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context)
            .inflate(layoutRes, this, attachToRoot)
    }
    override fun getItemViewType(position: Int): Int {
        items?.size ?: return 0
        return if (items!![position].type == CATEGORY)
            SearchViewBdp.CATEGORY
        else
            SearchViewBdp.ITEM
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType === CATEGORY) { // for call layout
            view = parent.inflate(R.layout.search_content_list_item_category, false)
            ListHolderCategory(
                view
            )
        } else { // for email layout
            view = parent.inflate(R.layout.search_content_list_item_item, false)
            ListHolderItems(
                view,
                onListenerButton
            )
        }
    }
}