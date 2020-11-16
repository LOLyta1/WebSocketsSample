package com.tsivileva.nata.ask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tsivileva.nata.core.entity.Exchange
import com.tsivileva.nata.ask.databinding.ItemAskLayoutBinding
import com.tsivileva.nata.ask.databinding.ItemAskOddLayoutBinding

private const val EVEN_ITEM = 0
private const val ODD_ITEM = 1

class AskRecyclerAdapter : RecyclerView.Adapter<AskRecyclerAdapter.AskEvenViewHolder>() {

    private val list = mutableListOf<Exchange.Data>()

    fun addToList(item: Exchange) {
        list.addAll(item.ordersData)
        notifyDataSetChanged()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AskEvenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == EVEN_ITEM) {
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