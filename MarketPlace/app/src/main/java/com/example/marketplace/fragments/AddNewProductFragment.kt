package com.example.marketplace.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.MainActivity
import com.example.marketplace.R
import com.example.marketplace.activityResult.PickPhoto
import com.example.marketplace.adapters.ImageAdapterAddProduct
import com.example.marketplace.model.AddProductData
import com.example.marketplace.model.Product
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.AddProductViewModel
import com.example.marketplace.viewmodels.AddProductViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch


class AddNewProductFragment : Fragment() {
    companion object{
        const val TAG = "ADDNEWPRODUCT"
    }
    private lateinit var ivUploadImage: ImageView
    private lateinit var rvImageList: RecyclerView

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swIsActive: Switch
    private lateinit var tvIsActive: TextView

    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etPrice: TextInputEditText
    private lateinit var spPriceType: Spinner
    private lateinit var etAmount: TextInputEditText
    private lateinit var spAmount: Spinner
    private lateinit var etSellerName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var btnPreview: Button
    private lateinit var btnLaunch: Button

    private lateinit var tiTitle: TextInputLayout
    private lateinit var tiDescription: TextInputLayout
    private lateinit var tiPrice: TextInputLayout
    private lateinit var tiAmount: TextInputLayout
    private lateinit var tiName: TextInputLayout
    private lateinit var tiEmail: TextInputLayout
    private lateinit var tiPhoneNumber: TextInputLayout

    private val selectedUris = mutableListOf<Uri>()
    private lateinit var  imageAdapter: ImageAdapterAddProduct

    private lateinit var addProductViewModel: AddProductViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val newProduct = AddProductData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = AddProductViewModelFactory(Repository())
        addProductViewModel = ViewModelProvider(this,factory)[AddProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_new_product, container, false)
        initializeView(view)
        registerListeners()
        imageAdapter = ImageAdapterAddProduct(requireContext(),selectedUris)
        rvImageList.adapter = imageAdapter
        rvImageList.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        addProductViewModel.productResult.observe(viewLifecycleOwner){
            if(it != null){
                sharedViewModel.currentProduct = it
                findNavController().navigate(R.id.productDetailsByOwnerFragment)
            }
        }

        return view
    }
    private fun initializeView(view: View?) {
        if(view != null){
            ivUploadImage = view.findViewById(R.id.ivUploadImagesAddProduct)
            rvImageList = view.findViewById(R.id.rvImageListAddProduct)
            swIsActive = view.findViewById(R.id.swIsActiveAddProduct)
            tvIsActive = view.findViewById(R.id.tvIsActiveAddProduct)
            etTitle = view.findViewById(R.id.etTitleAddProduct)
            etDescription = view.findViewById(R.id.etDescriptionAddProduct)
            etPrice = view.findViewById(R.id.etPriceAddProduct)
            spPriceType= view.findViewById(R.id.spPriceTypeAddProduct)
            etAmount = view.findViewById(R.id.etAmountAddProduct)
            spAmount = view.findViewById(R.id.spAmountTypeAddProduct)
            etSellerName = view.findViewById(R.id.etSellerNameAddProduct)
            etEmail = view.findViewById(R.id.etEmailAddProduct)
            etPhoneNumber = view.findViewById(R.id.etPhoneNumberAddProduct)
            btnPreview = view.findViewById(R.id.btnPreviewMyFare)
            btnLaunch = view.findViewById(R.id.btnLaunchMyFare)
            tiAmount = view.findViewById(R.id.tiAmount)
            tiName = view.findViewById(R.id.tiName)
            tiDescription = view.findViewById(R.id.tiDescription)
            tiEmail = view.findViewById(R.id.tiEmail)
            tiPhoneNumber = view.findViewById(R.id.tiPhoneNumber)
            tiPrice = view.findViewById(R.id.tiPrice)
            tiTitle = view.findViewById(R.id.tiTitle)

            tvIsActive.text = "Active"
            tvIsActive.setTextColor(Color.parseColor("#00B5C0"))

            val priceTypes = resources.getStringArray(R.array.PriceType)
            var adapter = ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item, priceTypes)
            spPriceType.adapter = adapter

            val amountTypes = resources.getStringArray(R.array.AmountType)
            adapter = ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,amountTypes)
            spAmount.adapter =adapter
        }
    }

    private fun registerListeners() {
        ivUploadImage.setOnClickListener {
            launchIntentForPhotos()
        }
        swIsActive.setOnClickListener {
            if(swIsActive.isChecked){
                tvIsActive.text = "Active"
                tvIsActive.setTextColor(Color.parseColor("#00B5C0"))
            }
            else{
                tvIsActive.text = "Inactive"
                tvIsActive.setTextColor(Color.parseColor("#606060"))
            }
        }
        btnLaunch.setOnClickListener {
            btnLaunch.isEnabled = false
            val isDataOk = validateData()
            if(isDataOk){
                newProduct.let {
                    it.title = etTitle.text.toString()
                    it.amount = etAmount.text.toString()
                    it.amount_type = spAmount.selectedItem.toString()
                    it.description = etDescription.text.toString()
                    it.phoneNumber = etPhoneNumber.text.toString()
                    it.price = etPrice.text.toString()
                    it.price_type = spPriceType.selectedItem.toString()
                    it.sellerEmail = etEmail.text.toString()
                    it.sellerName = etSellerName.text.toString()
                }
                Log.d(TAG, newProduct.toString())
                addProduct()
            }


        }
        btnPreview.setOnClickListener {

        }
    }

    private fun addProduct() {
        if(addProductViewModel.product.value != null) {
            addProductViewModel.product.value!!.let {
                it.amount_type = newProduct.amount_type
                it.price_type = newProduct.price_type
                it.price_per_unit = newProduct.price
                it.title = newProduct.title
                it.username= newProduct.sellerName
                it.units = newProduct.amount
                it.description = newProduct.description
            }
            lifecycleScope.launch {
                addProductViewModel.addProduct()
            }
        }
    }

    private fun validateData():Boolean {
        if(etTitle.text!!.length<2){
            tiTitle.error = tiTitle.errorContentDescription
            return false
        }
        else{
            tiTitle.error = null
            tiTitle.isErrorEnabled =false
        }
        if (etPrice.text!!.isEmpty()){
            tiPrice.error = tiPrice.errorContentDescription
            return false
        }
        else{
            tiPrice.error = null
            tiPrice.isErrorEnabled = false
        }
        if(etAmount.text!!.isEmpty()){
            tiAmount.error = tiAmount.errorContentDescription
            return false
        }
        else{
            tiAmount.error = null
            tiAmount.isErrorEnabled = false
        }
        if(etDescription.text!!.isEmpty()){
            tiDescription.error = tiDescription.errorContentDescription
            return false
        }
        else{
            tiDescription.error = null
            tiDescription.isErrorEnabled = false
        }
        if(etEmail.text!!.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text!!.toString()).matches()) {
            tiEmail.error = tiDescription.errorContentDescription
            return false
        }
        else{
            tiEmail.error = null
            tiEmail.isErrorEnabled = false
        }
        if(etPhoneNumber.text!!.isEmpty() || etPhoneNumber.text!!.length>10){
            tiPhoneNumber.error = tiPhoneNumber.errorContentDescription
            return false
        }
        else{
            tiPhoneNumber.error = null
            tiPhoneNumber.isErrorEnabled = false
        }
        if(etSellerName.text!!.isEmpty()){
            tiName.error = tiName.errorContentDescription
            return false
        }
        else{
            tiName.error = null
            tiName.isErrorEnabled = false
        }
        return true
    }

    private fun launchIntentForPhotos() {
        if (isReadExternalPermissionGranted()) {
            getPhoto.launch(0)
        } else {
            requestReadExternalPermission()
        }
    }
    private val getPhoto = registerForActivityResult(PickPhoto()) { result ->
        if(result.clipData != null) {
            for ( i in 0 until result.clipData.itemCount) {
                selectedUris.add(result.clipData.getItemAt(i).uri)
            }
        }
        else if (result.uri != null){
            selectedUris.add(result.uri)
        }
        imageAdapter.notifyDataSetChanged()
    }

    private fun isReadExternalPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadExternalPermission() {
        val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(requireActivity(), permission,
            MainActivity.EXTERNAL_PERMISSION_CODE
        )
    }





}