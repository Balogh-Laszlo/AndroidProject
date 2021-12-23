package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.adapters.OrderPagerAdapter
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.GetOrdersViewModel
import com.example.marketplace.viewmodels.GetOrdersViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class MyFaresFragment : Fragment() {
    private lateinit var pagerAdapter: OrderPagerAdapter
    private lateinit var vpPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var getOrdersViewModel: GetOrdersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = GetOrdersViewModelFactory(Repository())
        getOrdersViewModel = ViewModelProvider(this,factory)[GetOrdersViewModel::class.java]

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_fares, container, false)
        getOrdersViewModel.filter.value = Pair("username", MyApplication.username)
        lifecycleScope.launch {
            getOrdersViewModel.getOrders()
        }
        getOrdersViewModel.filter.value = Pair("owner_username", MyApplication.username)
        lifecycleScope.launch {
            getOrdersViewModel.getOrders()
        }

        getOrdersViewModel.response.observe(viewLifecycleOwner){
            if(it.orders.isNotEmpty()) {
                var isBuyer = true
                it.orders.forEach { if(it.username != MyApplication.username){ isBuyer = false } }
                if (isBuyer) {
                    Log.d("xxx", "My orders" +it.toString())
                    sharedViewModel.myOrders.value = it.orders
                }
            }
            if (it.orders.isNotEmpty()){
                var isOwner = true
                it.orders.forEach { if(it.owner_username != MyApplication.username){ isOwner = false } }
                if(isOwner){
                    Log.d("xxx", "My sales"+ it.toString())
                    sharedViewModel.mySales.value = it.orders
                }
            }
        }
        vpPager = view.findViewById(R.id.vpMyFares)
        pagerAdapter = OrderPagerAdapter(this)
        vpPager.adapter = pagerAdapter



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout,vpPager){ tab, position ->
            if(position == 0){
                tab.setText("Ongoing Sales")
                tab.text = "Ongoing Sales"
            }
            else{
                tab.text = "Ongoing Orders"
            }
        }.attach()
    }

}