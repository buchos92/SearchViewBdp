package com.bancodelpacifico.searchviewbdp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bancodelpacifico.searchviewbdp.R
import com.bancodelpacifico.searchviewbdp.interfaces.SearchListenerOn
import com.bancodelpacifico.searchviewbdp.interfaces.OnListenerButton
import kotlinx.android.synthetic.main.fragment_search_no_match.view.*

class ViewSearchNotMatch :Fragment(){

    private lateinit var onListenerButton: OnListenerButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_search_no_match, container, false)

        rootView.BUTTON_REGISTER_ACCOUNT.setOnClickListener {
            onListenerButton.button1()
        }
        rootView.BUTTON_REGISTER_CELL_PHONE.setOnClickListener{
            onListenerButton.button2()
        }

        return rootView
    }
    fun setSearchListenerOn(onListenerButton: OnListenerButton){
        this.onListenerButton = onListenerButton
    }
}