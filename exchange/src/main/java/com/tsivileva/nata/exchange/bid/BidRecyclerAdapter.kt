package com.tsivileva.nata.exchange.bid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tsivileva.nata.core.model.Exchange
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.EVEN_RECYCLER_ITEM
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.ODD_RECYCLER_ITEM
import com.tsivileva.nata.exchange.databinding.ItemBidLayoutBinding
import com.tsivileva.nata.exchange.databinding.ItemBidOddLayoutBinding
import timber.log.Timber


class BidRecyclerAdapter : RecyclerView.Adapter<BidRecyclerAdapter.BidEvenViewHolder>() {

    private val list = mutableListOf<Exchange.Data>()

    fun addToList(item: Exchange) {
        Timber.d("add new values into list, count = ${item.ordersData.count()}")
        list.addAll(item.ordersData)
        val offsetSize = item.ordersData.count()
        notifyItemRangeChanged(
            list.lastIndex + 1 - offsetSize,
            offsetSize
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidEvenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == EVEN_RECYCLER_ITEM) {
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
