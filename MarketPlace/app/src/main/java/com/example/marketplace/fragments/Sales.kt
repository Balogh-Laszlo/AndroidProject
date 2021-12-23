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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapters.OrderAdapter
import com.example.marketplace.model.OrderScreen
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.UpdateSaleViewModel
import com.example.marketplace.viewmodels.UpdateSaleViewModelFactory
import kotlinx.coroutines.launch

class Sales : Fragment(), OrderAdapter.OnItemSelected {
    private lateinit var rvSales: RecyclerView
    private lateinit var adapter: OrderAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var updateSaleViewModel: UpdateSaleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UpdateSaleViewModelFactory(Repository())
        updateSaleViewModel = ViewModelProvider(this,factory)[UpdateSaleViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sales, container, false)
        rvSales = view.findViewById(R.id.rvSales)
        adapter = OrderAdapter(requireContext(), listOf(),OrderScreen.OngoingSales,this)
        rvSales.adapter = adapter
        rvSales.layoutManager = LinearLayoutManager(context)
        updateSaleViewModel.response.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
            Log.d("xxx","Update"+it.updated_item.toString())
        }

        sharedViewModel.mySales.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                Log.d("xxx","list changed")
            }
        }

        return view
    }

    override fun onItemSelected(position: Int, status: String) {
        Log.d("xxx","Sales onItemSelected")
        Log.d("xxx","My sales: "+sharedViewModel.mySales.value!![position])
        if(sharedViewModel.mySales.value!![position].status != status){
            Log.d("xxx","Sales onItemSelected status != ")
            lifecycleScope.launch {
                updateSaleViewModel.updateSale(sharedViewModel.mySales.value!![position].order_id,status)
            }
        }
    }

}