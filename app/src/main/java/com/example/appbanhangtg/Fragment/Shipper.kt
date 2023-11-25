package com.example.appbanhangtg.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentProfileBinding
import com.example.appbanhangtg.databinding.FragmentShipperBinding

private lateinit var binding: FragmentShipperBinding
class Shipper : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShipperBinding.inflate(inflater,container,false)

        return binding.root
    }
}