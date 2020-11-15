package com.tsivileva.nata.core.model

import android.content.Context
import com.tsivileva.nata.core.R

sealed class Currency(private val nameResourceId: Int) {

    fun getName(context: Context): String {
        return context.resources.getString(nameResourceId)
    }

    object Bitcoin : Currency(R.string.bitCoin)
    object BinanceCoin : Currency(R.string.binanceCoin)
    object Ethereum : Currency(R.string.ethereum)
    object Tether : Currency(R.string.tether)
}