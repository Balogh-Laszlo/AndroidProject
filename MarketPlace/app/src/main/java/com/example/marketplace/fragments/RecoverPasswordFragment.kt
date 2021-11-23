package com.example.marketplace.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.model.Reset
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ResetPasswordViewModel
import com.example.marketplace.viewmodels.ResetPasswordViewModelFactory


class RecoverPasswordFragment : Fragment() {
    private lateinit var btnEmailMe:Button
    private lateinit var etUsername:EditText
    private lateinit var etEmail:EditText
    private lateinit var resetPasswordViewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ResetPasswordViewModelFactory(requireContext(), Repository())
        resetPasswordViewModel = ViewModelProvider(this,factory)[ResetPasswordViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_recover_password, container, false)
        initializeView(view)

        btnEmailMe.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etUsername.text.toString()
            resetPasswordViewModel.reset.value = Reset(username, email)
            resetPasswordViewModel.resetPassword()

        }
        resetPasswordViewModel.code.observe(viewLifecycleOwner){
            if(it != null &&it == 200){
                Toast.makeText(requireContext(),resetPasswordViewModel.message,Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_recoverPasswordFragment_to_loginFragment)
            }
            else if(it == null){
                Toast.makeText(requireContext(),"Something went wrong. Try again!",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(),resetPasswordViewModel.message,Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun initializeView(view: View?) {
        if (view != null){
            btnEmailMe=view.findViewById(R.id.btnEmailMe)
            etEmail = view.findViewById(R.id.etEmailRecovery)
            etUsername = view.findViewById(R.id.etUsernameRecovery)
        }
    }

}