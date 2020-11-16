package com.tsivileva.nata.exchange.statistic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsivileva.nata.core.model.Statistic
import com.tsivileva.nata.exchange.databinding.ItemStatisticBinding

class StatisticRecyclerAdapter :
    RecyclerView.Adapter<StatisticRecyclerAdapter.StatisticViewHolder>() {


    private var list = mutableListOf<Statistic.Data>()

    fun addToList(item: Statistic) {
        list.addAll(item.data)
        val offsetSize = item.data.count()
        notifyItemRangeChanged(
            list.lastIndex+1 - offsetSize,
            offsetSize
        )
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatisticBinding.inflate(inflater, parent, false)
        return StatisticViewHolder(binding)
    }

    override fun getItemCount() = list.count()


    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class StatisticViewHolder(
        private var binding: ItemStatisticBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(statistic: Statistic.Data) {
            binding.bidPriceTV.text = statistic.bidPrice.toString()
            binding.bidDiffTV.text = statistic.getDiff().toString()

            binding.askPriceTV.text = statistic.askPrice.toString()
            binding.askDiffTV.text = statistic.getDiff().toString()
        }
    }
}