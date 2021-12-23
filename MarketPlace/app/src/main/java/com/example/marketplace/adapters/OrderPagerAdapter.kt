package com.example.marketplace.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.marketplace.Orders
import com.example.marketplace.fragments.Sales

class OrderPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        return if(position == 0){
            Sales()
        }else{
            Orders()
        }
    }

}
