package com.tsivileva.nata.websockets.viewPager

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING
import com.tsivileva.nata.ask.AskFragment
import com.tsivileva.nata.core.databinding.FragmentExchangeBinding
import com.tsivileva.nata.exchange.bid.BidFragment
import com.tsivileva.nata.websockets.R
import com.tsivileva.nata.websockets.databinding.FragmentMainViewPagerBinding
import timber.log.Timber


class MainViewPagerFragment : Fragment() {
    private var _binding: FragmentMainViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainViewPagerBinding.inflate(inflater, container, false)
        val fragmentList = listOf(BidFragment(), AskFragment())
        binding.pager.adapter =
            MainPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuItemBird -> runOnTimer { binding.pager.currentItem = 0 }

                R.id.menuItemAsk -> runOnTimer { binding.pager.currentItem = 1 }
            }
            true
        }

        binding.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == SCROLL_STATE_IDLE) {
                        when (binding.pager.currentItem) {
                            0 -> binding.bottomNavView.selectedItemId = R.id.menuItemBird
                            1 -> binding.bottomNavView.selectedItemId = R.id.menuItemAsk
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