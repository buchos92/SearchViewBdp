package com.bancodelpacifico.searchviewbdp.interfaces

import android.os.Parcelable
import android.text.Editable
import kotlinx.android.parcel.Parcelize

interface SearchListener {
    fun onFinished(searchKeyword: String?)
}
interface OnToggleAnimationListener {
    fun onStart(expanding: Boolean)
    fun onFinish(expanded: Boolean)
}

@Parcelize
class ItemsModel( var tittle: String,
                  var description: String? = "",
                  var additional: String? = "",
                  var type: Int,
                  var iconItem: Int? = null,
                  var categoryId:Int) : Parcelable
/***
 * Interface for listening to search edit text.
 */
interface SearchBoxListener {
    fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    )

    fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    )

    fun afterTextChanged(s: Editable?)
}