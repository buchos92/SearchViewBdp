package com.bancodelpacifico.searchviewbdp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.adapter.AdpaterMultipleCategory
import com.bancodelpacifico.searchviewbdp.interfaces.ListSearchableAbs


class ViewSearchCategory : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView:RecyclerView
    private lateinit var adpaterMultipleCategory: AdpaterMultipleCategory
    private val ITEMS = "listOfItems"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_search_static_list, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)

        setRecyclerViewOfList(arguments?.getParcelableArrayList(ITEMS)!!)
        return rootView
    }

    private fun setRecyclerViewOfList(items: ArrayList<out ListSearchableAbs>){
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        adpaterMultipleCategory =
            AdpaterMultipleCategory(
                items
            )
        recyclerView.adapter = adpaterMultipleCategory
    }
}

