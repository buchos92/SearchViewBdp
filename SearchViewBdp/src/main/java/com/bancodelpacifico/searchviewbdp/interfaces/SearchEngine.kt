package com.bancodelpacifico.searchviewbdp.interfaces

import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import kotlin.collections.ArrayList

class SearchEngine {
    var items : List<ItemsModel>? = null

    fun setItemsModel(value: ArrayList<ItemsModel>?){
        val itemsModel = value ?: throw IllegalArgumentException("Items Mode required")
        items = itemsModel

        if(isDuplicateCategory())
            throw IllegalArgumentException("Categories are repeated")
    }
    fun searchItem(){
        val listCategory = filterFor(items!!,CATEGORY)
        val listItems = filterFor(items!!,CATEGORY)

    }

    fun isDuplicateCategory():Boolean{
        val categories = filterFor(items!!, CATEGORY)
        val listIdCategories = getCategoryId(categories)

        return hasDuplicates(listIdCategories)
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