package com.bancodelpacifico.searchviewbdp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.adapter.AdpaterMultipleCategory
import com.bancodelpacifico.searchviewbdp.interfaces.ItemsModel
import com.bancodelpacifico.searchviewbdp.interfaces.SearchListenerOn


class ViewSearchCategory : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView:RecyclerView
    private var adpaterMultipleCategory = AdpaterMultipleCategory()
    private lateinit var searchListenerOn: SearchListenerOn
    private val ITEMS = "listOfItems"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_search_static_list, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)

        setRecyclerViewOfList()
        return rootView
    }
    private fun setRecyclerViewOfList(){
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adpaterMultipleCategory
    }
    fun addNewItems(items: MutableList<ItemsModel>){
        adpaterMultipleCategory.apply {
            addNewItems(items)
        }
    }
}

