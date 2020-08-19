package com.bancodelpacifico.searchviewbdp.interfaces

import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import kotlin.collections.ArrayList

class SearchEngine(var itemsModel: ArrayList<ItemsModel>? = null) {
    var items : List<ItemsModel>? = null

    fun searchItem(){
        var itemsModel = itemsModel  ?: throw IllegalArgumentException("Items Mode required")
        items = itemsModel

        val listCategory = filterFor(items!!,CATEGORY)
        val listItems = filterFor(items!!,CATEGORY)

    }

    val filterFor: (List<ItemsModel> ,Int) -> List<ItemsModel>  = { item:List<ItemsModel> ,type:Int ->  item.filter { it.type == type }}

    fun getCategoryId(items: List<ItemsModel>):Array<Int>{
        var listId : MutableList<Int> = mutableListOf()

        items.mapIndexed { index, itemsModel ->
            listId.add(itemsModel.categoryId)
        }
        return listId.toTypedArray()
    }

    /**
     *   should be a list just type, example just list Category or Items
     * */
    fun <T> hasDuplicates(arr: Array<T>): Boolean {
        arr.sort()	// sort the array

        var previous: T? = null

        for (e in arr) {
            if (e != null && e == previous) return true
            previous = e
        }
        return false	// no repeated elements
    }
}