package com.bancodelpacifico.searchviewexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import kotlinx.android.synthetic.main.search_static_list_item.view.*


class SearchListDefault : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView:RecyclerView
    private lateinit var adpater: RecyclerAdpater
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

    private fun setRecyclerViewOfList(items: ArrayList<out SearchViewBdp.ListSearchableAbs >){
        Log.v("items",items.toString())
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        adpater = RecyclerAdpater(items)
        recyclerView.adapter = adpater
    }
}

class RecyclerAdpater( var items : ArrayList<out SearchViewBdp.ListSearchableAbs>) :
    RecyclerView.Adapter<RecyclerAdpater.ListHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val items = items[position]
        holder.bindItems(items)
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflatedView = parent.inflate(R.layout.search_static_list_item, false)
        return ListHolder(inflatedView)
    }

    class ListHolder(v:View): RecyclerView.ViewHolder(v),View.OnClickListener {
        private var view:View = v
        private var items: SearchViewBdp.ListSearchableAbs? = null

        init {
            v.setOnClickListener(this)
        }
        fun bindItems(item : SearchViewBdp.ListSearchableAbs?){
            view.card_title.text = item?.tittle
        }
        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
        companion object{
            private val ITEMS_KEY = "ITEMS_KEY"
        }
    }
    /* //private val context: Context?
    private val values: List<String> = objects

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.search_static_list_item, parent, false)
        val textView = rowView.findViewById(R.id.card_details) as TextView
        textView.text = values[position]
        return rowView
    }*/

}