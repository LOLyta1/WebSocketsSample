package com.tsivileva.nata.ask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tsivileva.nata.core.model.Currency
import com.tsivileva.nata.core.webSocket.entity.ConnectionStatus
import com.tsivileva.nata.statistic.databinding.FragmentAskBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AskFragment : Fragment() {
    private val viewModel by viewModels<AskViewModel>()
    private var _binding: FragmentAskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAskBinding.inflate(inflater,container,false)// Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding.getAskBTN.setOnClickListener {
            viewModel.setCurrenciesAndConnect(Currency.Bitcoin, Currency.Tether)
            viewModel.getOrders(viewLifecycleOwner).observe(viewLifecycleOwner) {
                Timber.d("was recieved order: $it")
            }

        }

        binding.stopBTN.setOnClickListener {
            viewModel.disconnect()
        }
    }

    private fun initObservers() {
        viewModel.subscribeOnConnectionStatus(viewLifecycleOwner).observe(viewLifecycleOwner){
            when(it){
                ConnectionStatus.Opened -> {
                    Timber.d("Connection was opened")

                }

                ConnectionStatus.Closed -> {
                    Timber.d("Connection was closed")
                }

                is ConnectionStatus.Failed -> {
                    Timber.d("Connection is FAILED ${it.error}")
                    viewModel.disconnect()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}