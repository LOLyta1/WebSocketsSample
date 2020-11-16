package com.tsivileva.nata.ask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.tsivileva.nata.core.R
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsivileva.nata.core.databinding.FragmentExchangeBinding
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.webSocket.entity.ConnectionStatus
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class AskFragment : Fragment() {
    private val viewModel by viewModels<AskViewModel>()
    private var _binding: FragmentExchangeBinding? = null
    private val binding get() = _binding!!
    private val askAdapter: AskRecyclerAdapter? = AskRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        initSpinner()
        binding.stopBTN.setOnClickListener {
            viewModel.unsubscribe()
        }
    }

    private fun initRecyclerView() {
        binding.currenceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = askAdapter
        }
    }

    private fun initObservers() {
        viewModel.subscribeOnConnectionStatus(viewLifecycleOwner).observe(viewLifecycleOwner) {
            when (it) {
                ConnectionStatus.Opened -> {
                    Timber.d("Connection was opened")
                    connect(binding.currencySpinner.getCurrencyPair())
                    subscribeOnOrders()
                }

                ConnectionStatus.Closed -> {
                    Timber.d("Connection was closed")
                }

                is ConnectionStatus.Failed -> {
                    viewModel.unsubscribe()
                }

                is ConnectionStatus.Loading -> {
                    setLoadingState()
                }
            }
        }
    }

    private fun connect(pair: Pair<Currency, Currency>) {
        viewModel.setCurrenciesAndConnect(pair.first, pair.second)
    }

    private fun subscribeOnOrders() {
        try {
            setLoadingState()
            viewModel.getOrders(viewLifecycleOwner).observe(viewLifecycleOwner) {
                askAdapter?.addToList(it)
                binding.currenceRecyclerView.smoothScrollToPosition(askAdapter?.itemCount ?: 0)
            }
        } catch (e: Exception) {
            Timber.d("$e")
            e.printStackTrace()
        }
    }


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
                 //   viewModel.release()
                    askAdapter?.clear()
                    connect(getCurrencyPair())
                 //   subscribeOnOrders()
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}