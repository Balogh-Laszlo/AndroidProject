package com.example.marketplace.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapters.OrderAdapter
import com.example.marketplace.model.OrderScreen
import com.example.marketplace.model.SharedViewModel


class Orders : Fragment() {
    private lateinit var rvOrders:RecyclerView
    private lateinit var adapter: OrderAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        rvOrders = view.findViewById(R.id.rvOrders)
        adapter = OrderAdapter(requireContext(), listOf(),OrderScreen.OngoingOrders,null)
        rvOrders.adapter = adapter
        rvOrders.layoutManager = LinearLayoutManager(context)
        sharedViewModel.myOrders.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            }
        }


        return view
    }

}