package com.tsivileva.nata.ask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        binding.stopBTN.setOnClickListener {
            viewModel.disconnect()
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
                    connectToStreamAndGetOrders()
                }

                ConnectionStatus.Closed -> {
                    Timber.d("Connection was closed")

                }

                is ConnectionStatus.Failed -> {
                    Timber.d("Connection is FAILED ${it.error}")
                    viewModel.disconnect()
                }

                is ConnectionStatus.Loading -> {
                    Timber.d("Connection is Loading")
                    setLoadingState()
                }
            }
        }
    }

    private fun connectToStreamAndGetOrders() {
        try {
            setLoadingState()
            viewModel.setCurrenciesAndConnect(Currency.Bitcoin, Currency.Tether)
            viewModel.getOrders(viewLifecycleOwner).observe(viewLifecycleOwner) {
                askAdapter?.addToList(it)
                Timber.d("was recieved order: $it")
            }
        } catch (e: Exception) {
            Timber.d("$e")
            e.printStackTrace()
        }
    }

    private fun setLoadingState() {

    }

    override fun onResume() {
        super.onResume()
       /* if (!viewModel.isConnected()) {
            connectToStreamAndGetOrders()
        }*/
    }

    override fun onPause() {
        super.onPause()
       // viewModel.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}