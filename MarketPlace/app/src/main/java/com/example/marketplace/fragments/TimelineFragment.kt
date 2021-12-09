package com.example.marketplace.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.MyApplication
import com.example.marketplace.adapters.ProductListAdapter
import com.example.marketplace.R
import com.example.marketplace.model.Product
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.ListViewModelFactory
import kotlinx.coroutines.launch


class TimelineFragment : Fragment(), ProductListAdapter.OnItemClickListener,
    AdapterView.OnItemSelectedListener {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swLatest:Switch
    private lateinit var spOrder:Spinner
    lateinit var listViewModel:ListViewModel
    private lateinit var rvAdapter: ProductListAdapter
    private lateinit var rvList: RecyclerView
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(requireActivity(),factory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Log.d("xxx",MyApplication.username)
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
            sharedViewModel.productList = listViewModel.products.value
            setupSearch()
        }
        spOrder.onItemSelectedListener = this
        return view
    }

    private fun setupSearch() {
        if(MyApplication.searchView != null) {
            val id = MyApplication.searchView!!.context.resources
                .getIdentifier("android:id/search_src_text", null, null)

            val textView = MyApplication.searchView!!.findViewById<View>(id) as TextView

            textView.setTextColor(Color.WHITE)
            Log.d("xxx","searchItem not null")
            MyApplication.searchView!!.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if(MyApplication.searchMenuItem != null){
                        MyApplication.searchMenuItem!!.collapseActionView()
                    }
                    return false
                }

                override fun onQueryTextChange(s: String): Boolean {
                    Log.d("xxx",s)
                    val list = mutableListOf<Product>()
                    if (MyApplication.searchView != null) {
                        sharedViewModel.productList!!.forEach {
                            if (it.title.contains(s)) {
                                list.add(it)
                            }
                        }
                    }
                    rvAdapter.setData(list)
                    rvAdapter.notifyDataSetChanged()

                    return false
                }
            })
        }
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
        sharedViewModel.currentProduct = listViewModel.products.value!![position]
        findNavController().navigate(R.id.action_timelineFragment_to_productDetailByCostumer)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(listViewModel.products.value!=null) {
            Log.d("TIMELINE",spOrder.selectedItem.toString())
            when (spOrder.selectedItem.toString()) {
                "Seller" -> {
                    listViewModel.products.value!!.sortBy {
                        it.username
                    }
                    rvAdapter.setData(listViewModel.products.value!!)
                    rvAdapter.notifyDataSetChanged()
                }
                "Product" -> {
                    listViewModel.products.value!!.sortBy {
                        it.title
                    }
                    rvAdapter.setData(listViewModel.products.value!!)
                    rvAdapter.notifyDataSetChanged()
                }
                else -> {
                    listViewModel.products.value!!.sortByDescending {
                        it.creation_time
                    }
                    rvAdapter.setData(listViewModel.products.value!!)
                    rvAdapter.notifyDataSetChanged()

                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        listViewModel.products.value!!.sortByDescending {
            it.creation_time
        }
        rvAdapter.setData(listViewModel.products.value!!)
        rvAdapter.notifyDataSetChanged()
    }
}