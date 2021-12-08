package com.example.marketplace.fragments

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.MainActivity
import com.example.marketplace.R
import com.example.marketplace.activityResult.PickPhoto
import com.example.marketplace.adapters.ImageAdapterAddProduct


class AddNewProductFragment : Fragment() {
    private lateinit var ivUploadImage: ImageView
    private lateinit var rvImageList: RecyclerView

    private val selectedUris = mutableListOf<Uri>()
    private lateinit var  imageAdapter: ImageAdapterAddProduct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        return view
    }

    private fun registerListeners() {
        ivUploadImage.setOnClickListener {
            launchIntentForPhotos()
        }
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


    private fun initializeView(view: View?) {
        if(view != null){
            ivUploadImage = view.findViewById(R.id.ivUploadImagesAddProduct)
            rvImageList = view.findViewById(R.id.rvImageListAddProduct)
        }
    }

}