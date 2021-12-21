package com.example.marketplace.fragments

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
import com.example.marketplace.R
import com.example.marketplace.adapters.ProductImageAdapter
import com.example.marketplace.model.Image
import com.example.marketplace.model.SharedViewModel
import java.sql.Date
import java.sql.Timestamp

class ProductDetailsByOwnerFragment : Fragment() {
    private lateinit var vpImages: ViewPager2
    private lateinit var ivProfile: ImageView
    private lateinit var tvSellerName: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvProductTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvProductState: TextView
    private lateinit var ivIsActive: ImageView
    private lateinit var tvAmount: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTotalAmountType: TextView
    private lateinit var tvPricePerItem: TextView
    private lateinit var tvPriceType: TextView
    private lateinit var tvSoldAmount: TextView
    private lateinit var tvSoldAmountType: TextView
    private lateinit var tvRevenue: TextView
    private lateinit var tvRevenueType: TextView

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_details_by_owner, container, false)
        initializeView(view)
        bindData()
        return view
    }

    private fun bindData() {
        if(sharedViewModel.currentProduct!=null){
            val product = sharedViewModel.currentProduct!!
            if(product.images.isEmpty()){
                vpImages.adapter = ProductImageAdapter(requireContext(), listOf(Image()))
            }else{
                vpImages.adapter = ProductImageAdapter(requireContext(), product.images)
            }
            tvSellerName.text = product.username
            val time = Timestamp(product.creation_time)
            tvDate.text = Date(time.time).toString()
            val title = product.title.replace("\"","",true)
            tvProductTitle.text = title
            val price = product.price_per_unit.replace("\"","",true)
            val priceType = product.price_type.replace("\"","",true)
            val amountType = product.amount_type.replace("\"","",true)
            tvPrice.text = "${price} ${priceType}/${amountType}"
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
            val units = product.units.replace("\"","",true)
            tvAmount.text =units
            val description = product.description.replace("\"","",true)
            tvProductDescription.text = description
            tvTotalAmount.text = "${(units).toDouble()-sharedViewModel.soldItems}"
            tvTotalAmountType.text = amountType
            tvPricePerItem.text = price
            tvPriceType.text = priceType
            tvSoldAmount.text = "${sharedViewModel.soldItems}"
            tvSoldAmountType.text = amountType
            tvRevenue.text = "${sharedViewModel.soldItems*price.toDouble()}"
            tvRevenueType.text = priceType
        }
    }

    private fun initializeView(view: View?) {
        if(view != null){
            vpImages= view.findViewById(R.id.vpImagesOwner)
            ivProfile = view.findViewById(R.id.ivProfileDetailOwner)
            tvSellerName = view.findViewById(R.id.tvSellerNameDeetailOwner)
            tvDate = view.findViewById(R.id.tvDateDetailOwner)
            tvProductTitle =view.findViewById(R.id.tvProductTitleDetailOwner)
            tvPrice = view.findViewById(R.id.tvPriceDetailOwner)
            tvProductState = view.findViewById(R.id.tvProductStateDetailOwner)
            ivIsActive = view.findViewById(R.id.ivIsActiveOwner)
            tvAmount = view.findViewById(R.id.tvAmountDetailOwner)
            tvProductDescription = view.findViewById(R.id.tvProductDescriptionDetailOwner)
            tvTotalAmount = view.findViewById(R.id.tvAmountDetailsByOwner)
            tvTotalAmountType = view.findViewById(R.id.tvAmountTypeDetailByOwner)
            tvPricePerItem = view.findViewById(R.id.tvPriceDetailByOwner)
            tvPriceType = view.findViewById(R.id.tvPriceTypeDetailByOwner)
            tvSoldAmount = view.findViewById(R.id.tvSoldAmountDetailByOwner)
            tvSoldAmountType = view.findViewById(R.id.tvSoldAmountTypeDetailByOwner)
            tvRevenue = view.findViewById(R.id.tvRevenewDetailByOwner)
            tvRevenueType = view.findViewById(R.id.tvRevenewTypeDetailByOwner)
        }
    }
}
