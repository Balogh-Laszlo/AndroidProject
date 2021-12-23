package com.example.marketplace.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.marketplace.R
import com.example.marketplace.model.SharedViewModel
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.GetUserInfoViewModel
import com.example.marketplace.viewmodels.GetUserInfoViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var getUserInfoViewModel: GetUserInfoViewModel

    private lateinit var tvEmail: TextView
    private lateinit var tvUsername:TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var tvUsername2 :TextView
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory2 = GetUserInfoViewModelFactory(Repository())
        getUserInfoViewModel = ViewModelProvider(this,factory2)[GetUserInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        lifecycleScope.launch {
            getUserInfoViewModel.getUserInfo(sharedViewModel.selectedUsername)
        }
        getUserInfoViewModel.response.observe(viewLifecycleOwner){
            tvEmail.text = it.data[0].email
            tvUsername2.text = it.data[0].username
            tvUsername.text = it.data[0].username
            tvPhoneNumber.text = it.data[0].phone_number
        }
        tvEmail = view.findViewById(R.id.tvEmailProfile)
        tvPhoneNumber = view.findViewById(R.id.tvPhonenumberProfile)
        tvUsername = view.findViewById(R.id.tvUsernameProfile)
        tvUsername2 = view.findViewById(R.id.tvUsernameProfile2)
        return view
    }

}