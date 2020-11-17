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
import com.tsivileva.nata.core.model.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class BidFragment : Fragment() {
    private val viewModel by viewModels<BidViewModel>()
    private var _binding: FragmentExchangeBinding? = null
    private val binding get() = _binding!!
    private val askAdapter: BidRecyclerAdapter? = BidRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSpinner()
    }


    private fun initRecyclerView() {
        binding.currenceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = askAdapter
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    private fun subscribeOnOrders() {
        setLoadingState()
        val currencies = binding.currencySpinner.getCurrencyPair()
        viewModel.getOrders(currencies).observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Loading -> {
                    // TODO()
                    Timber.d("Loading")
                }

                is NetworkResponse.Successful -> {
                    askAdapter?.addToList(it.data)
                    binding.currenceRecyclerView.smoothScrollToPosition(askAdapter?.itemCount ?: 0)
                    Timber.d("Successful")

                }

                is NetworkResponse.Error -> {
                    Timber.d("Error ${it.message}")
                }
            }

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
                   // askAdapter?.clear()
                    //viewModel.unsubscribe()
                    //viewModel.getOrders(getCurrencyPair())
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

    @InternalCoroutinesApi
    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)/*
        if (!viewModel.isConnected) {
            subscribeOnOrders()
        }*/
    }

    @FlowPreview
    @InternalCoroutinesApi
    override fun onResume() {
        super.onResume()

        subscribeOnOrders()
    }


}