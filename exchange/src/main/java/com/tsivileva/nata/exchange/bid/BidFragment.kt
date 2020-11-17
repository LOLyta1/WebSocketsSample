package com.tsivileva.nata.exchange.bid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsivileva.nata.core.R
import com.tsivileva.nata.core.databinding.FragmentExchangeBinding
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.model.ConnectionStatus
import com.tsivileva.nata.exchange.databinding.FragmentBidBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class BidFragment : Fragment() {
    private val viewModel by viewModels<BidViewModel>()
    private var _binding: FragmentBidBinding? = null
    private val binding get() = _binding!!
    private val askAdapter: BidRecyclerAdapter? = BidRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBidBinding.inflate(inflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        initSpinner()
        subscribeOnOrders()
    }

    private fun initRecyclerView() {
        binding.currenceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = askAdapter
        }
    }


    @InternalCoroutinesApi
    private fun initObservers() {
        viewModel.subscribeOnEvents().observe(viewLifecycleOwner) {
            when (it) {
                ConnectionStatus.Opened -> {
                    Timber.d("Connection was opened")
                    subscribeOnOrders()
                }

                ConnectionStatus.Closed -> {
                    Timber.d("Connection was closed")
                }

                is ConnectionStatus.Failed -> {
                    viewModel.unsubscribe()
                }
            }
        }
    }

    @InternalCoroutinesApi
    private fun subscribeOnOrders() {
        try {
            setLoadingState()
            val currencies = binding.currencySpinner.getCurrencyPair()
            viewModel.getOrders(currencies).observe(viewLifecycleOwner) {
                Timber.d("RECIEVED BID = ${it.exchangeSymbol}")
                askAdapter?.addToList(it)
                binding.currenceRecyclerView.smoothScrollToPosition(askAdapter?.itemCount ?: 0)
            }
        } catch (e: Exception) {
            Timber.d("$e")
            e.printStackTrace()
        }
    }
    //TODO: вынести в extension

    @InternalCoroutinesApi
    fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.exchange,
            android.R.layout.simple_dropdown_item_1line
        )
        binding.currencySpinner.apply {
            this.adapter = adapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    askAdapter?.clear()
                    viewModel.getOrders(getCurrencyPair())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    private fun Spinner.getCurrencyPair() = when (this.selectedItemPosition) {
        0 -> Pair(Currency.Bitcoin, Currency.Tether)
        1 -> Pair(Currency.BinanceCoin, Currency.Bitcoin)
        2 -> Pair(Currency.Ethereum, Currency.Bitcoin)
        else -> Pair(Currency.Bitcoin, Currency.Tether)
    }


    private fun setLoadingState() {
//TODO: implement this
    }


    override fun onDetach() {
        super.onDetach()
        viewModel.unsubscribe()
    }

}