package com.tsivileva.nata.exchange.statistic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tsivileva.nata.core.model.Statistic
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.EVEN_RECYCLER_ITEM
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.ODD_RECYCLER_ITEM
import com.tsivileva.nata.exchange.databinding.ItemOddStatisticBinding
import com.tsivileva.nata.exchange.databinding.ItemStatisticBinding

class StatisticRecyclerAdapter :
    RecyclerView.Adapter<StatisticRecyclerAdapter.StatisticViewHolder>() {

    private var list = mutableListOf<Statistic.Data>()

    fun addToList(item: Statistic) {
        list.addAll(item.data)
        val offsetSize = item.data.count()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == EVEN_RECYCLER_ITEM) {
            ItemStatisticBinding.inflate(inflater, parent, false)
        } else {
            ItemOddStatisticBinding.inflate(inflater, parent, false)
        }
        return StatisticViewHolder(binding)
    }

    override fun getItemCount() = list.count()


    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class StatisticViewHolder(
        private var binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(statistic: Statistic.Data) {
            (binding as? ItemStatisticBinding)?.let {
                it.bidPriceTV.text = statistic.bidPrice.toString()
                it.bidDiffTV.text = statistic.getDiff().toString()
                it.askPriceTV.text = statistic.askPrice.toString()
                it.askDiffTV.text = statistic.getDiff().toString()
            }
            (binding as? ItemOddStatisticBinding)?.let {
                it.bidPriceTV.text = statistic.bidPrice.toString()
                it.bidDiffTV.text = statistic.getDiff().toString()
                it.askPriceTV.text = statistic.askPrice.toString()
                it.askDiffTV.text = statistic.getDiff().toString()
            }
        }
    }
}