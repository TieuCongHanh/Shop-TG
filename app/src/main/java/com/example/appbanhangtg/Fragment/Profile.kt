package com.example.appbanhangtg.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Adapter.ShopAdapter
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentHomeBinding
import com.example.appbanhangtg.databinding.FragmentProfileBinding

private lateinit var binding: FragmentProfileBinding
class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        val user = context?.let { SharedPrefsManager.getUser(it) }

            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(binding.root.context)
                .load(user?.image)
                .apply(requestOptions)
                .placeholder(R.drawable.icon_person) // Placeholder image while loading
                .into(binding.imgavtProfile)
          binding.txtusernameProfile.text = user?.username


        return binding.root
    }
}