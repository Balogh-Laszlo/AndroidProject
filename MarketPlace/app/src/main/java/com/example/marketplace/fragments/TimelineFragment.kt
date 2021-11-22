package com.example.marketplace.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.ProductListAdapter
import com.example.marketplace.R
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.ListViewModelFactory
import kotlinx.coroutines.launch


class TimelineFragment : Fragment(), ProductListAdapter.OnItemClickListener {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swLatest:Switch
    private lateinit var spOrder:Spinner
    lateinit var listViewModel:ListViewModel
    private lateinit var rvAdapter: ProductListAdapter
    private lateinit var rvList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(this,factory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)
        initialize(view)
        lifecycleScope.launch {
            listViewModel.getProducts()
        }
        rvAdapter = ProductListAdapter(requireContext(),ArrayList<Product>(),this)
        rvList.adapter = rvAdapter
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        listViewModel.products.observe(viewLifecycleOwner){
            rvAdapter.setData(listViewModel.products.value as ArrayList<Product>)
            rvAdapter.notifyDataSetChanged()
        }
        return view
    }

    private fun initialize(view: View?) {
        if(view != null){
            swLatest = view.findViewById(R.id.swLatest)
            spOrder = view.findViewById(R.id.spOrder)
            rvList = view.findViewById(R.id.rvProductList)
        }
        val orders = resources.getStringArray(R.array.Order)
        val adapter = ArrayAdapter(requireContext(),R.layout.spinner_list, orders)
        adapter.setDropDownViewResource(R.layout.spinner_list)
        spOrder.adapter = adapter

    }
    override fun onItemClick(position:Int){

    }
}