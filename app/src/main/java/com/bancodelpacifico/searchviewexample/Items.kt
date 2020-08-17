package com.bancodelpacifico.searchviewexample

import com.bancodelpacifico.searchviewbdp.SearchViewBdp
import kotlinx.android.parcel.Parcelize

@Parcelize
class Items(
    override var tittle: String,
    override var description: String?,
    override var additional: String?,
    override var type: Int
) : SearchViewBdp.ListSearchableAbs()