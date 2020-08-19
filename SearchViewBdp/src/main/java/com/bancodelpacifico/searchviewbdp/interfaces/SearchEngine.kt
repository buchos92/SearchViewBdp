package com.bancodelpacifico.searchviewbdp.interfaces

import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.CATEGORY
import com.bancodelpacifico.searchviewbdp.SearchViewBdp.Companion.ITEM
import kotlin.collections.ArrayList

class SearchEngine {
    var items : List<ItemsModel>? = null

    fun setItemsModel(value: MutableList<ItemsModel>?){
        val itemsModel = value ?: throw IllegalArgumentException("Items Mode required")
        items = itemsModel

        if(isDuplicateCategory())
            throw IllegalArgumentException("Categories are repeated")
    }
    fun searchItem(inputPhrases : CharSequence):MutableList<ItemsModel>{
        val resultSearch:MutableList<ItemsModel> = mutableListOf()
        if(inputPhrases.isEmpty()) return resultSearch

        val phrases = inputPhrases.toString().toLowerCase()

        val listCategory = filterFor(items!!,CATEGORY)
        val listItems = filterFor(items!!, ITEM)

        val searchItemsMatch = listItems.filter {
            it.tittle.toLowerCase().contains(phrases) || it.description?.toLowerCase()?.contains(phrases)!! || it.additional?.toLowerCase()?.contains(phrases)!!
        }
        listCategory.forEach{ item ->
            searchItemsMatch.filter { it.categoryId ==  item.categoryId}
        }

        listCategory.forEach{ category ->

            val categoryMatch = searchItemsMatch.filter { it.categoryId == category.categoryId }

            if(categoryMatch.isNotEmpty())
                resultSearch.add(category)
                categoryMatch.map {
                    resultSearch.add(it)
                }
        }

        return resultSearch.toMutableList()
    }

    fun isDuplicateCategory():Boolean{
        val categories = filterFor(items!!, CATEGORY)
        val listIdCategories = getCategoryId(categories)

        return hasDuplicates(listIdCategories)
    }
    val filterFor: (List<ItemsModel> ,Int) -> List<ItemsModel>  = { item:List<ItemsModel> ,type:Int ->  item.filter { it.type == type }}

    fun getCategoryId(items: List<ItemsModel>):Array<Int>{
        val listId : MutableList<Int> = mutableListOf()

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