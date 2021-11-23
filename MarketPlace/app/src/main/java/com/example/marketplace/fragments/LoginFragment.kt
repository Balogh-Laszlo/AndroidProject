package com.example.marketplace.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.MainActivity
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.LoginViewModel
import com.example.marketplace.viewmodels.LoginViewModelFactory
import com.example.marketplace.viewmodels.RefreshTokenViewModel
import com.example.marketplace.viewmodels.RefreshTokenViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    companion object{
        const val TAG = "LOGIN_FRAGMENT"
    }
    private lateinit var etEmail:TextInputEditText
    private lateinit var etPassword:TextInputEditText
    private lateinit var btnLogin:Button
    private lateinit var tvClickHere:TextView
    private lateinit var btnSingUp:Button

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var refreshTokenViewModel: RefreshTokenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]



    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val preferences = requireContext().getSharedPreferences("token",MODE_PRIVATE)
        var token = preferences.getString("token","")

        if(token != null && token.isNotEmpty()) {
            val factory2 = RefreshTokenViewModelFactory(Repository(), token)
            refreshTokenViewModel = ViewModelProvider(this,factory2)[RefreshTokenViewModel::class.java]
            lifecycleScope.launch {
                refreshTokenViewModel.refreshToken()
            }
            refreshTokenViewModel.token.observe(viewLifecycleOwner){
                MyApplication.token = refreshTokenViewModel.token.value.toString()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
        initializeView(view)
        registerListeners()
        loginViewModel.token.observe(viewLifecycleOwner){
            Log.d("xxx", "navigate to list")
            val preferences = requireContext().getSharedPreferences("token", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("token",loginViewModel.token.value)
            editor.apply()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun registerListeners() {
        btnLogin.setOnClickListener {
            getLoginData()
            login()
        }
        tvClickHere.setOnClickListener {
            Log.d(TAG,"recover password")
            findNavController().navigate(R.id.action_loginFragment_to_recoverPasswordFragment)
        }
        btnSingUp.setOnClickListener {
            Log.d(TAG,"Sign Up")
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun getLoginData() {
        loginViewModel.user.value.let {
            if (it != null) {
                it.username = etEmail.text.toString()
            }
            if (it != null) {
                it.password = etPassword.text.toString()
            }
        }
    }

    private fun login() {
        lifecycleScope.launch {
            loginViewModel.login()
        }
    }

    private fun initializeView(view: View?) {
        if(view != null){
            etEmail = view.findViewById(R.id.etEmailLogin)
            etPassword = view.findViewById(R.id.etPasswordLogin)
            btnLogin = view.findViewById(R.id.btnLogin)
            tvClickHere = view.findViewById(R.id.tvClickHereLogin)
            btnSingUp = view.findViewById(R.id.btnSignUpLogin)
        }
    }

}