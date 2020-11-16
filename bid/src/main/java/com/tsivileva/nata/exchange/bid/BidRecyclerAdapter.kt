package com.tsivileva.nata.exchange.bid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tsivileva.nata.core.entity.Exchange
import com.tsivileva.nata.exchange.databinding.ItemBidLayoutBinding
import com.tsivileva.nata.exchange.databinding.ItemBidOddLayoutBinding


private const val EVEN_ITEM = 0
private const val ODD_ITEM = 1

class BidRecyclerAdapter : RecyclerView.Adapter<BidRecyclerAdapter.BidEvenViewHolder>() {

    private val list = mutableListOf<Exchange.Data>()

    fun addToList(item: Exchange) {
        list.addAll(item.ordersData)
        notifyItemRangeChanged(
            list.lastIndex+1 - item.ordersData.count(),
            item.exchangeSymbol.count()
        )
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            EVEN_ITEM
        } else {
            ODD_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidEvenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == EVEN_ITEM) {
            ItemBidLayoutBinding.inflate(inflater, parent, false)
        } else {
            ItemBidOddLayoutBinding.inflate(inflater, parent, false)
        }
        return BidEvenViewHolder(binding)
    }

    override fun getItemCount() = list.count()


    class BidEvenViewHolder(
        private var binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Exchange.Data) {
            (binding as? ItemBidLayoutBinding)?.let {
                it.amountTV.text = data.amount.toString()
                it.priceTV.text = data.price.toString()
                it.totalTV.text = data.getTotal().toString()
            }
            (binding as? ItemBidOddLayoutBinding)?.let {
                it.amountTV.text = data.amount.toString()
                it.priceTV.text = data.price.toString()
                it.totalTV.text = data.getTotal().toString()
            }
        }
    }

    override fun onBindViewHolder(holder: BidEvenViewHolder, position: Int) {
        holder.bind(list[position])
    }

}
