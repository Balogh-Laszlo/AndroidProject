package com.example.marketplace.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.adapters.ProductListAdapter
import com.example.marketplace.model.DeleteRequest
import com.example.marketplace.model.Product
import com.example.marketplace.model.Screen
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.DeleteProductViewModel
import com.example.marketplace.viewmodels.DeleteProductViewModelFactory
import com.example.marketplace.viewmodels.RegisterViewModel
import com.example.marketplace.viewmodels.RegisterViewModelFactory
import kotlinx.coroutines.launch


class MyMarketFragment : Fragment(), ProductListAdapter.OnItemClickListener,
    ProductListAdapter.OnDeleteClickListener {
    private lateinit var rvItemList: RecyclerView
    private lateinit var btnAddProduct: ImageButton

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val list: MutableList<Product> = mutableListOf()
    private val list1:MutableList<Product> = mutableListOf()
    private lateinit var adapter: ProductListAdapter

    private lateinit var deleteProductViewModel: DeleteProductViewModel
    private var selectedPosition = -1

    companion object{
        const val TAG = "MYMARKET"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = DeleteProductViewModelFactory (Repository())
        deleteProductViewModel = ViewModelProvider(this, factory)[DeleteProductViewModel::class.java]
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
        setupSearch()
        deleteProductViewModel.deleteProductResponse.observe(viewLifecycleOwner){
            showDeleteSuccessfulDialog()
        }
        return view
    }

    private fun showDeleteSuccessfulDialog() {
        Log.d(TAG,deleteProductViewModel.deleteProductResponse.value.toString())
        if(selectedPosition != -1){
            list.removeAt(selectedPosition)
            adapter.setData(list)
            adapter.notifyDataSetChanged()
            selectedPosition = -1
        }
        val dialog = Dialog(requireContext(),R.style.DialogStyle)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.bg_dialog)
        dialog.setContentView(R.layout.delete_successful_dialog_layout)
        val btnClose = dialog.findViewById<ImageButton>(R.id.btnCloseDialog)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun registerListeners() {
        btnAddProduct.setOnClickListener {
            findNavController().navigate(R.id.addNewProductFragment)
        }
    }

    private fun setAdapters() {
        if (sharedViewModel.productList != null){
            sharedViewModel.productList!!.forEach {
                if(it.username.compareTo(MyApplication.username) == 0){
                    Log.d("xxx",it.username)
                    Log.d("xxx",it.toString())
                    list.add(it)
                }
            }
        }
        sharedViewModel.productList = list
        adapter = ProductListAdapter(requireContext(),list,this,Screen.MyMarket,this,null)
        rvItemList.adapter = adapter
        rvItemList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initializeView(view: View?) {
            if(view != null){
                rvItemList = view.findViewById(R.id.rvItemListMyMArket)
                btnAddProduct = view.findViewById(R.id.btnAddProductMyMarket)
            }
    }

    override fun onItemClick(position: Int) {

        sharedViewModel.currentProduct = sharedViewModel.productList!![position]
        findNavController().navigate(R.id.productDetailsByOwnerFragment)
    }
    private fun setupSearch() {
        if(MyApplication.searchView != null) {
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
                    if (MyApplication.searchView != null) {
                        list.forEach {
                            if (it.title.contains(s)) {
                                list1.add(it)
                            }
                        }
                    }
                    adapter.setData(list1)
                    adapter.notifyDataSetChanged()

                    return false
                }
            })
        }
    }

    override fun onDeleteClick(position: Int) {
        val dialog = Dialog(requireContext(),R.style.DialogStyle)
        dialog.setContentView(R.layout.delete_dialog_layout)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.bg_dialog)
        val tvTitle = dialog.findViewById<TextView>(R.id.tvProductTitleDeleteDialog)
        tvTitle.text = sharedViewModel.productList!![position].title.replace("\"","",true)
        val btnNo = dialog.findViewById<Button>(R.id.btnNoDeleteDialog)
        val btnYes = dialog.findViewById<Button>(R.id.btnYesDeleteDialog)
        dialog.show()
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        btnYes.setOnClickListener {
            btnYes.isEnabled = false
            deleteProductViewModel.deleteRequest.value = DeleteRequest(list[position].product_id,MyApplication.token)
            Log.d("xxx",deleteProductViewModel.deleteRequest.value.toString())
            lifecycleScope.launch {
                deleteProductViewModel.deleteProduct()
            }
            dialog.dismiss()
            selectedPosition = position
        }


    }
}