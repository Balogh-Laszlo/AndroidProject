package com.example.marketplace.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.model.UpdateUserDataRequest
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch


class OwnersProfileFragment : Fragment() {
    private lateinit var getUserInfoViewModel: GetUserInfoViewModel
    private  lateinit var updateUserDataViewModel: UpdateUserDataViewModel
    private lateinit var tvUsername: TextView
    private lateinit var etUsername:TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory1 = GetUserInfoViewModelFactory(Repository())
        getUserInfoViewModel = ViewModelProvider(this,factory1)[GetUserInfoViewModel::class.java]
        val factory2 = UpdateUserDateViewModelFactory(Repository())
        updateUserDataViewModel = ViewModelProvider(this,factory2)[UpdateUserDataViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_owners_profile, container, false)
        initializeView(view)
        lifecycleScope.launch {
            getUserInfoViewModel.getUserInfo(MyApplication.username)
        }
        getUserInfoViewModel.response.observe(viewLifecycleOwner){
            if(it != null) {
                if(it.code == 200) {
                    tvUsername.text = it.data[0].username
                    etUsername.setText(it.data[0].username)
                    etEmail.setText(it.data[0].email)
                    etPhoneNumber.setText(it.data[0].phone_number)
                }else{
                    Log.d("xxx",it.code.toString())
                }
            }
        }
        btnSave.setOnClickListener {
            if(validateData()){
                val username = etUsername.text.toString()
                val email = etEmail.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()
                lifecycleScope.launch {
                    updateUserDataViewModel.updateUserData(UpdateUserDataRequest(phoneNumber,email,username,
                        listOf()))
                }
            }
        }
        updateUserDataViewModel.response.observe(viewLifecycleOwner){
            if (it.code == 200) {
                Toast.makeText(requireContext(), "Update successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.profileFragment)
            }
            else{
                Toast.makeText(requireContext(), "Update failed", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun validateData():Boolean {
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val phoneNumber = etPhoneNumber.text.toString()
        if(username.isEmpty()){
            return false
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false
        }
        if(phoneNumber.length>10){
            return false
        }
        return true
    }

    private fun initializeView(view: View?) {
        if(view!=null){
            tvUsername = view.findViewById(R.id.tvUserNameOwnerProfile)
            etUsername = view.findViewById(R.id.etUserNameOwnerProfile)
            etEmail = view.findViewById(R.id.etEmailOwnerProfile)
            etPhoneNumber = view.findViewById(R.id.etPhoneNumberOwnerProfile)
            btnSave = view.findViewById(R.id.btnSaveOwnerProfile)

        }
    }

}