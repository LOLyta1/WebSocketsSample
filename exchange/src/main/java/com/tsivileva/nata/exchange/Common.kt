package com.tsivileva.nata.exchange.com.tsivileva.nata.exchange

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.tsivileva.nata.core.R
import com.tsivileva.nata.core.model.Currency

const val EVEN_RECYCLER_ITEM = 0
const val ODD_RECYCLER_ITEM = 1

fun Spinner.getCurrencyPair() = when (this.selectedItemPosition) {
    0 -> Pair(Currency.Bitcoin, Currency.Tether)
    1 -> Pair(Currency.BinanceCoin, Currency.Bitcoin)
    2 -> Pair(Currency.Ethereum, Currency.Bitcoin)
    else -> Pair(Currency.Bitcoin, Currency.Tether)
}

fun Spinner.setOnCurrencySelectListener(
    onClick: (pair: Pair<Currency, Currency>) -> Unit
) {
    val adapter = ArrayAdapter.createFromResource(
        this.context,
        R.array.exchange,
        android.R.layout.simple_dropdown_item_1line
    )

    this.adapter = adapter

    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int, id: Long
        ) {
            onClick(getCurrencyPair())
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            /*nothing to do*/
        }
    }
}
