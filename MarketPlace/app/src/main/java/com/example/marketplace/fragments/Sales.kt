package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
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

class Sales : Fragment() {
    private lateinit var rvSales: RecyclerView
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
        val view = inflater.inflate(R.layout.fragment_sales, container, false)
        rvSales = view.findViewById(R.id.rvSales)
        adapter = OrderAdapter(requireContext(), listOf(),OrderScreen.OngoingSales)
        rvSales.adapter = adapter
        rvSales.layoutManager = LinearLayoutManager(context)
        sharedViewModel.mySales.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                Log.d("xxx","list changed")
            }
        }

        return view
    }

}