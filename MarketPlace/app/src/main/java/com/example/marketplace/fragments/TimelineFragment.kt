package com.example.marketplace.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import com.example.marketplace.R


class TimelineFragment : Fragment() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var swLatest:Switch
    private lateinit var spOrder:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)
        initialize(view)
        return view
    }

    private fun initialize(view: View?) {
        if(view != null){
            swLatest = view.findViewById(R.id.swLatest)
            spOrder = view.findViewById(R.id.spOrder)
        }
        val orders = resources.getStringArray(R.array.Order)
        val adapter = ArrayAdapter(requireContext(),R.layout.spinner_list, orders)
        adapter.setDropDownViewResource(R.layout.spinner_list)
        spOrder.adapter = adapter
    }
}