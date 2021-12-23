package com.example.marketplace.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.MyApplication
import com.example.marketplace.adapters.ProductListAdapter
import com.example.marketplace.R
import com.example.marketplace.model.OrderRequest
import com.example.marketplace.model.Product
import com.example.marketplace.model.Screen
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


class TimelineFragment : Fragment(), ProductListAdapter.OnItemClickListener,
    AdapterView.OnItemSelectedListener, ProductListAdapter.OnOrderClickListener,
    ProductListAdapter.OnProfileClickListener {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swLatest:Switch
    private lateinit var spOrder:Spinner
    lateinit var listViewModel:ListViewModel
    private lateinit var rvAdapter: ProductListAdapter
    private lateinit var rvList: RecyclerView
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var addOrderViewModel: AddOrderViewModel
    private lateinit var getUserInfoViewModel: GetUserInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(requireActivity(),factory)[ListViewModel::class.java]

        val factory2 = AddOrderViewModelFactory(Repository())
        addOrderViewModel = ViewModelProvider(requireActivity(),factory2)[AddOrderViewModel::class.java]
        val factory3 = GetUserInfoViewModelFactory(Repository())
        getUserInfoViewModel = ViewModelProvider(this,factory3)[GetUserInfoViewModel::class.java]
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
        rvAdapter = ProductListAdapter(requireContext(),ArrayList<Product>(),this,Screen.TimeLine,null,this,this)
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

        addOrderViewModel.response.observe(viewLifecycleOwner){
            Log.d("xxx",it.toString())
            showOrderSuccessDialog()
        }
        return view
    }

    private fun showOrderSuccessDialog() {
        val dialog = Dialog(requireContext(),R.style.DialogStyle)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.bg_dialog_white)
        dialog.setContentView(R.layout.order_complet_dialog_layout)
        dialog.show()
        val btnClose = dialog.findViewById<ImageButton>(R.id.btnCloseOrderCompleteDialog)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancelOrderCompleteDialog)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        val tvOngoingOrders = dialog.findViewById<TextView>(R.id.tvOngoingOrdersOrderCompleteDialog)
        tvOngoingOrders.setOnClickListener {
            findNavController().navigate(R.id.action_timelineFragment_to_myFaresFragment)
        }
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
        if(sharedViewModel.currentProduct!!.username != MyApplication.username) {
            findNavController().navigate(R.id.action_timelineFragment_to_productDetailByCostumer)
        }
        else{
            findNavController().navigate(R.id.productDetailsByOwnerFragment)
        }
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

    override fun onOrderClick(position: Int) {
        val currentProduct = sharedViewModel.productList!![position]
        val dialog = Dialog(requireContext(),R.style.DialogStyle)
        dialog.window!!.setBackgroundDrawableResource(R.drawable.bg_dialog_white)
        dialog.setContentView(R.layout.order_now_dialog_layout)
        dialog.show()
        initializeDialog(dialog,currentProduct)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun initializeDialog(dialog: Dialog, currentProduct: Product) {
        val btnClose = dialog.findViewById<ImageButton>(R.id.btnCloseOrderDialog)
        val tvUserName = dialog.findViewById<TextView>(R.id.tvUserNameOrder)
        val tvPrice = dialog.findViewById<TextView>(R.id.tvPriceOrderDialog)
        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitleOrderDialog)
        val tvIsActive = dialog.findViewById<TextView>(R.id.tvIsActiveOrderDialog)
        val ivIsActive = dialog.findViewById<ImageView>(R.id.ivIsActiveOrderDialog)
        val tvCreation = dialog.findViewById<TextView>(R.id.tvCreationTimeOrderDialog)
        val tvAmountType = dialog.findViewById<TextView>(R.id.tvAmountTypeOrderDialog)
        val etAmount = dialog.findViewById<TextInputEditText>(R.id.etAmountOrderDialog)
        val etComments = dialog.findViewById<TextInputEditText>(R.id.etCommentsOrderDialog)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancelOrderDialog)
        val btnSend = dialog.findViewById<Button>(R.id.btnSendMyOrderOrderDialog)
        val tiAmount = dialog.findViewById<TextInputLayout>(R.id.tiAmountOrderDialog)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        tvUserName.text = currentProduct.username.replace("\"","")
        tvPrice.text = currentProduct.price_per_unit.replace("\"","") +
                " " +
                currentProduct.price_type.replace("\"","") +
                "/"+
                currentProduct.amount_type.replace("\"","")
        tvTitle.text = currentProduct.title.replace("\"","")
        val date = Date(Timestamp(currentProduct.creation_time).time)
        tvCreation.text = date.toLocaleString()
        tvAmountType.text = currentProduct.amount_type.replace("\"","")
        if (currentProduct.is_active){
            tvIsActive.text = "Active"
            tvIsActive.setTextColor(R.color.loginTextColor1)
            Glide.with(requireContext())
                .load(R.drawable.active)
                .into(ivIsActive)
        }else{
            tvIsActive.text = "Inactive"
            tvIsActive.setTextColor(R.color.hintColor)
            Glide.with(requireContext())
                .load(R.drawable.inactive)
                .into(ivIsActive)
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnSend.setOnClickListener {
            if(validateData(etAmount)){
                val request = OrderRequest(currentProduct.title.replace("\"",""),
                    etComments.text.toString(),
                    currentProduct.price_per_unit.replace("\"",""),
                    etAmount.text.toString(),
                    currentProduct.username.replace("\"","")
                    )
                dialog.dismiss()
                order(request)
            }
            else{
                tiAmount.error = tiAmount.errorContentDescription
            }
        }

    }

    private fun order(request: OrderRequest) {
        addOrderViewModel.request = request
        lifecycleScope.launch {
            addOrderViewModel.order()
        }
    }

    private fun validateData(etAmount: TextInputEditText?): Boolean {
        if(etAmount!=null){
            if(etAmount.text!!.isEmpty()){
                return false
            }
            return true
        }
        return false
    }

    override fun onProfileClick(username:String) {
        findNavController().navigate(R.id.profileFragment)
        sharedViewModel.selectedUsername = username
    }
}