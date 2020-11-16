package com.tsivileva.nata.websockets.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tsivileva.nata.ask.AskFragment
import com.tsivileva.nata.exchange.bid.BidFragment

class MainPagerAdapter(
    private val fragments: List<Fragment>,
    manager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(manager,lifecycle) {

    override fun getItemCount()=fragments.count()

    override fun createFragment(position: Int)=fragments[position]

}