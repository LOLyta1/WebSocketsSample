package com.tsivileva.nata.exchange.ask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsivileva.nata.core.databinding.FragmentExchangeBinding
import com.tsivileva.nata.core.model.NetworkResponse
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.getCurrencyPair
import com.tsivileva.nata.exchange.com.tsivileva.nata.exchange.setOnCurrencySelectListener
import dagger.hilt.android.AndroidEntryPoint

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

        binding.spinner.setSelection(-1)

        binding.spinner.setOnCurrencySelectListener {
            viewModel.unsubscribe()
            viewModel.load(it)
        }

        viewModel.orders.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Loading -> {
                    setLoadingState()
                }

                is NetworkResponse.Successful -> {
                    removeLoadingState()
                    askAdapter?.addToList(it.data)
                    binding.currencyRv.smoothScrollToPosition(
                        askAdapter?.itemCount ?: 0
                    )
                }

                is NetworkResponse.Error -> {
                    removeLoadingState()
                    Toast.makeText(context, "Error:${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.currencyRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = askAdapter
        }
    }

    private fun setLoadingState() {
        binding.loader.visibility = View.VISIBLE
    }

    private fun removeLoadingState() {
        binding.loader.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        viewModel.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(
            binding.spinner.getCurrencyPair()
        )
    }
}