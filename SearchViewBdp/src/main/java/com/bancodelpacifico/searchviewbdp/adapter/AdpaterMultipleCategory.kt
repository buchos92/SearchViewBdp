package com.bancodelpacifico.searchviewbdp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.EMPTY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM_SECOND_OPTION
import com.bancodelpacifico.searchviewbdp.adapter.holder.ListHolderCategory
import com.bancodelpacifico.searchviewbdp.adapter.holder.ListHolderItems
import com.bancodelpacifico.searchviewbdp.adapter.holder.ListHolderItemsSecondOption
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import com.bancodelpacifico.searchviewbdp.interfaces.OnListenerButton

class AdpaterMultipleCategory(private var items : MutableList<ItemsModel>? = mutableListOf(),
                              private var onListenerButton: OnListenerButton) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        val item = items?.get(position)
        when {
            item?.type == CATEGORY && item.isShow!! -> (holder as ListHolderCategory).bindItems(item)
            item?.type == ITEM -> (holder as ListHolderItems).bindItems(item,theLastItemIsCategory(items!!, position))
            item?.type == ITEM_SECOND_OPTION -> (holder as ListHolderItemsSecondOption).bindItems(item,theLastItemIsCategory(items!!, position))
            else -> (holder as EmptyHolder)
        }
    }

    private fun theLastItemIsCategory(items: MutableList<out ItemsModel>, position: Int):Boolean{
        if(items.isNotEmpty() && position > (items.size - 1)){
            return items[position - 1].type == CATEGORY
        }
        return false
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun getItemViewType(position: Int): Int {
        //items?.size ?: return 0
        if(!items?.get(position)?.isShow!!) return EMPTY
        return items!![position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when(viewType){
            CATEGORY -> { // for call layout
                view = parent.inflate(R.layout.search_content_list_item_category, false)
                ListHolderCategory(view)
            }
            ITEM -> {// for call layout
                view = parent.inflate(R.layout.search_content_list_item_item, false)
                ListHolderItems(view, onListenerButton)
            }
            ITEM_SECOND_OPTION -> {// for call layout
                view = parent.inflate(R.layout.search_content_list_item_second ,false)
                ListHolderItemsSecondOption(view, onListenerButton)
            }
            else -> {
                view = parent.inflate(R.layout.search_content_list_item_empty, false)
                EmptyHolder(view)
            }
        }
    }
}

class EmptyHolder(v: View) :  RecyclerView.ViewHolder(v)