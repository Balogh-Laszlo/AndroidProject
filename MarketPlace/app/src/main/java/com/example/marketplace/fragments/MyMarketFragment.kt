package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.adapters.ProductListAdapter
import com.example.marketplace.model.Product
import com.example.marketplace.model.SharedViewModel


class MyMarketFragment : Fragment(), ProductListAdapter.OnItemClickListener {
    private lateinit var rvItemList: RecyclerView
    private lateinit var btnAddProduct: ImageButton

    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_market, container, false)
        initializeView(view)
        setAdapters()
        registerListeners()
        return view
    }

    private fun registerListeners() {
        btnAddProduct.setOnClickListener {
            findNavController().navigate(R.id.addNewProductFragment)
        }
    }

    private fun setAdapters() {
        val list:MutableList<Product> = mutableListOf()
        if (sharedViewModel.productList != null){
            sharedViewModel.productList!!.forEach {
                if(it.username.compareTo(MyApplication.username) == 0){
                    Log.d("xxx",it.username)
                    Log.d("xxx",it.toString())
                    list.add(it)
                }
            }
        }
        rvItemList.adapter = ProductListAdapter(requireContext(),list,this)
        rvItemList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initializeView(view: View?) {
            if(view != null){
                rvItemList = view.findViewById(R.id.rvItemListMyMArket)
                btnAddProduct = view.findViewById(R.id.btnAddProductMyMarket)
            }
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.productDetailsByOwnerFragment)
    }
}