package com.tsivileva.nata.websockets.viewPager

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import com.tsivileva.nata.exchange.ask.AskFragment
import com.tsivileva.nata.exchange.bid.BidFragment
import com.tsivileva.nata.exchange.statistic.StatisticFragment
import com.tsivileva.nata.websockets.R
import com.tsivileva.nata.websockets.databinding.FragmentMainViewPagerBinding


class MainViewPagerFragment : Fragment() {
    private var _binding: FragmentMainViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = listOf(
            BidFragment().apply {
                lifecycle.addObserver(PagerLifecycleLogger(this))
            },
            AskFragment().apply {
                lifecycle.addObserver(PagerLifecycleLogger(this))
            },
            StatisticFragment().apply {
                lifecycle.addObserver(PagerLifecycleLogger(this))
            },
        )
        binding.pager.adapter =  MainPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuItemBid -> runOnTimer { binding.pager.currentItem = 0 }
                R.id.menuItemAsk -> runOnTimer { binding.pager.currentItem = 1 }
                R.id.menuItemDiff -> runOnTimer { binding.pager.currentItem = 2 }
            }
            true
        }

        binding.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == SCROLL_STATE_IDLE) {
                        when (binding.pager.currentItem) {
                            0 -> binding.bottomNavView.selectedItemId = R.id.menuItemBid
                            1 -> binding.bottomNavView.selectedItemId = R.id.menuItemAsk
                            2 -> binding.bottomNavView.selectedItemId = R.id.menuItemDiff
                        }
                        binding.bottomNavView.isSelected = true
                    }
                }
            }
        )
    }

    private fun runOnTimer(callback: () -> Unit) {
        Handler().postAtTime({
            callback()
        }, 500)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}