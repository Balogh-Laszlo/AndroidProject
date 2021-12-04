package com.example.marketplace.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.marketplace.ProductImageAdapter
import com.example.marketplace.R
import com.example.marketplace.model.Image
import com.example.marketplace.model.SharedViewModel
import java.sql.Date
import java.sql.Timestamp


class ProductDetailByCostumer : Fragment() {
    private lateinit var vpImages: ViewPager2
    private lateinit var ivProfile: ImageView
    private lateinit var tvSellerName: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvProductTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvProductState:TextView
    private lateinit var ivIsActive: ImageView
    private lateinit var tvAmount: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var ivMail: ImageView

    val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_detail_by_costumer, container, false)
        initializeView(view)
        bindData()
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun bindData() {
        if (sharedViewModel.currentProduct!=null){
            val product = sharedViewModel.currentProduct!!
            tvSellerName.text = product.username
            val time = Timestamp(product.creation_time)
            tvDate.text = Date(time.time).toString()
            tvProductTitle.text = product.title
            tvPrice.text = "${product.price_per_unit} ${product.price_type}/${product.amount_type}"
            if (product.is_active){
                tvProductState.text = "Active"
                Glide.with(requireContext())
                    .load(R.drawable.active)
                    .into(ivIsActive)
            }else{
                tvProductState.text = "Inactive"
                Glide.with(requireContext())
                    .load(R.drawable.inactive)
                    .into(ivIsActive)
            }
            tvAmount.text = product.units
            tvProductDescription.text = product.description
            if(product.images.isNotEmpty()) {
                vpImages.adapter = ProductImageAdapter(requireContext(), product.images)
            }else{
                vpImages.adapter = ProductImageAdapter(requireContext(), listOf())
            }
        }
    }

    private fun initializeView(view: View?) {
        if(view != null){
            vpImages= view.findViewById(R.id.vpImagesCustomer)
            ivProfile = view.findViewById(R.id.ivProfileDetailCostumer)
            tvSellerName = view.findViewById(R.id.tvSellerNameDeetailCostumer)
            tvDate = view.findViewById(R.id.tvDateDetailCostumer)
            tvProductTitle =view.findViewById(R.id.tvProductTitleDetailCostumer)
            tvPrice = view.findViewById(R.id.tvPriceDetailCostumer)
            tvProductState = view.findViewById(R.id.tvProductStateDetailCostumer)
            ivIsActive = view.findViewById(R.id.ivIsActive)
            tvAmount = view.findViewById(R.id.tvAmountDetailCostumer)
            tvProductDescription = view.findViewById(R.id.tvProductDescriptionDetailCostumer)
            ivMail = view.findViewById(R.id.ivMailDetails)
        }
    }
}