package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {
    companion object{
        const val TAG = "LOGIN_FRAGMENT"
    }
    private lateinit var etEmail:TextInputEditText
    private lateinit var etPassword:TextInputEditText
    private lateinit var btnLogin:Button
    private lateinit var tvClickHere:TextView
    private lateinit var btnSingUp:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        initializeView(view)
        registerListeners()
        return view
    }

    private fun registerListeners() {
        btnLogin.setOnClickListener {
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

    private fun login() {
        Log.d(TAG,"Log in")
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