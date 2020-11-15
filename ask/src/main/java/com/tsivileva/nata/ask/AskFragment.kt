package com.tsivileva.nata.ask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tsivileva.nata.statistic.databinding.FragmentAskBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AskFragment : Fragment() {
    val viewModel by viewModels<AskViewModel>()
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
            viewModel.subscribeOnAskOrders().observe(viewLifecycleOwner){
                Timber.d("was recieved order: $it")
            }
        }
    }
    private fun initObservers() {
        viewModel.orders.observe(viewLifecycleOwner) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}