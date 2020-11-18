package com.tsivileva.nata.exchange.ask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tsivileva.nata.core.model.Exchange
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.EVEN_RECYCLER_ITEM
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.ODD_RECYCLER_ITEM
import com.tsivileva.nata.exchange.databinding.ItemAskLayoutBinding
import com.tsivileva.nata.exchange.databinding.ItemAskOddLayoutBinding

class AskRecyclerAdapter : RecyclerView.Adapter<AskRecyclerAdapter.AskEvenViewHolder>() {

    private val list = mutableListOf<Exchange.Data>()

    fun addToList(item: Exchange) {
        list.addAll(item.ordersData)
        notifyItemRangeChanged(
            list.lastIndex + 1 - item.ordersData.count(),
            item.exchangeSymbol.count()
        )
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            EVEN_RECYCLER_ITEM
        } else {
            ODD_RECYCLER_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskEvenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == EVEN_RECYCLER_ITEM) {
            ItemAskLayoutBinding.inflate(inflater, parent, false)
        } else {
            ItemAskOddLayoutBinding.inflate(inflater, parent, false)
        }
        return AskEvenViewHolder(binding)
    }

    override fun onBindViewHolder(holderEven: AskEvenViewHolder, position: Int) {
        holderEven.bind(list[position])
    }

    override fun getItemCount() = list.count()


    class AskEvenViewHolder(
        private var binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Exchange.Data) {
            (binding as? ItemAskLayoutBinding)?.let {
                it.amountTV.text = data.amount.toString()
                it.priceTV.text = data.price.toString()
                it.totalTV.text = data.getTotal().toString()
            }
            (binding as? ItemAskOddLayoutBinding)?.let {
                it.amountTV.text = data.amount.toString()
                it.priceTV.text = data.price.toString()
                it.totalTV.text = data.getTotal().toString()
            }
        }
    }

}