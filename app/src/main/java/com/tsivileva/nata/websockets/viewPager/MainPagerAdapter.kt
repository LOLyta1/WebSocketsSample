package com.tsivileva.nata.websockets.viewPager

import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(
    private val fragments: List<Fragment>,
    manager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount() = fragments.count()

    override fun createFragment(position: Int) = fragments[position]

}