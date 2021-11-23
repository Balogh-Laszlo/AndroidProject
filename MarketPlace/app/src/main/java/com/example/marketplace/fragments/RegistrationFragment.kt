package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.RegisterViewModel
import com.example.marketplace.viewmodels.RegisterViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {
    companion object{
        const val TAG = "REGISTER_FRAGMENT"
    }
    private lateinit var btnRegister: Button
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var etUserName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var tvLogin: TextView

    private lateinit var userName:String
    private lateinit var phoneNumber:String
    private lateinit var email:String
    private lateinit var password:String

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = RegisterViewModelFactory(this.requireContext(), Repository())
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        initializeView(view)
        registerListeners()
        registerViewModel.code.observe(viewLifecycleOwner){
            Log.d(TAG,"registration code: ${registerViewModel.code}")
            if (registerViewModel.code.value == 200){
                Toast.makeText(requireContext(),registerViewModel.message,Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
            else{
                Toast.makeText(requireContext(),registerViewModel.message,Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun registerListeners() {
        tvLogin.setOnClickListener {
            Log.d(TAG,"Navigate to login fragment")
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        btnRegister.setOnClickListener {
            val isOK = validateData()
            if (isOK) {
                registerViewModel.user.value!!.email = email
                registerViewModel.user.value!!.username = userName
                registerViewModel.user.value!!.password = password
                registerViewModel.user.value!!.phone_number = phoneNumber
                register()
            }
        }
    }

    private fun register() {
        lifecycleScope.launch {
            registerViewModel.register()
        }
    }

    private fun validateData() : Boolean{
        userName = etUserName.text.toString()
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        phoneNumber = etPhoneNumber.text.toString()
        if(userName.length<3){
            Toast.makeText(requireContext(),"User name need to be at least 3 character long",Toast.LENGTH_SHORT).show()
            return false
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(requireContext(),"Not a valid email address format",Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Regex("07\\d{8}").matches(phoneNumber)){
            Toast.makeText(requireContext(),"Not a valid phone number format",Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.length<6){
            Toast.makeText(requireContext(),"Password need to be at least 6 character long",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun initializeView(view: View?) {
        if(view != null) {
            btnRegister = view.findViewById(R.id.btnRegister)
            etPhoneNumber = view.findViewById(R.id.etPhoneNumberRegister)
            etUserName = view.findViewById(R.id.etUserNameRegister)
            etEmail = view.findViewById(R.id.etEmailRegister)
            etPassword = view.findViewById(R.id.etEmailRecovery)
            tvLogin = view.findViewById(R.id.tvLoginRegister)
        }
    }
}