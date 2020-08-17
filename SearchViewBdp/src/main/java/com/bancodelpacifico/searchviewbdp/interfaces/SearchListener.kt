package com.bancodelpacifico.searchviewbdp.interfaces

import android.os.Parcelable
import android.text.Editable

interface SearchListener {
    fun onFinished(searchKeyword: String?)
}
interface OnToggleAnimationListener {
    fun onStart(expanding: Boolean)
    fun onFinish(expanded: Boolean)
}

abstract class ListSearchableAbs : Parcelable {
    open lateinit var tittle      :String
    open var description : String? = null
    open var additional :String? = null
    open var type :Int = 0
}
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