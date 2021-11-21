package com.example.marketplace.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.marketplace.R


class RecoverPasswordFragment : Fragment() {
    private lateinit var btnEmailMe:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_recover_password, container, false)
        btnEmailMe=view.findViewById(R.id.btnEmailMe)

        btnEmailMe.setOnClickListener {
            Toast.makeText(requireContext(),"The email has been sent",Toast.LENGTH_LONG).show()

        }
        return view
    }

}