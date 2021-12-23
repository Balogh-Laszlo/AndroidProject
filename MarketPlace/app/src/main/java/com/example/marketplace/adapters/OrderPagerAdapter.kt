package com.example.marketplace.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.marketplace.Orders

class OrderPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = Orders()
        return fragment
    }

}
