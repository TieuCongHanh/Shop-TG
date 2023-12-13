package com.example.appbanhangtg.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Activity.AccountSetting
import com.example.appbanhangtg.Activity.Bill
import com.example.appbanhangtg.Activity.Bill1
import com.example.appbanhangtg.Activity.Cart
import com.example.appbanhangtg.Activity.Login
import com.example.appbanhangtg.Activity.MyShop
import com.example.appbanhangtg.Activity.VoteProduct
import com.example.appbanhangtg.Adapter.ShopAdapter
import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentHomeBinding
import com.example.appbanhangtg.databinding.FragmentProfileBinding

private lateinit var binding: FragmentProfileBinding
class Profile : Fragment() {
    private val cartDAO: CartDAO by lazy { CartDAO(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        val user = context?.let { SharedPrefsManager.getUser(it) }

        if (user?.username == "" || user?.username == null){
            binding.txtusernameProfile.setText("Bạn chưa đăng nhập")
            binding.txtchucvuProfile.setText("Đăng nhập ngay  >  ")
            binding.txtchucvuProfile.setOnClickListener {
                val intent = Intent(context,Login::class.java)
                startActivity(intent)
            }
        }else{
            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(binding.root.context)
                .load(user?.image)
                .apply(requestOptions)
                .placeholder(R.drawable.icon_persion) // Placeholder image while loading
                .into(binding.imgavtProfile)
            binding.txtusernameProfile.text = user?.username

        }


        binding.txtshowaccountProfile.setOnClickListener {
            val intent = Intent(context,AccountSetting::class.java)
            startActivity(intent)
        }
        binding.imgcartProfile.setOnClickListener {
            val intent = Intent(context, Cart::class.java)
            startActivity(intent)
        }
        binding.txtshoppRofile.setOnClickListener {
            val intent = Intent(context,MyShop::class.java)
            startActivity(intent)
        }
        binding.historibill.setOnClickListener {
            val intent = Intent(context,Bill::class.java)
            startActivity(intent)
        }
        binding.voteproductdetail.setOnClickListener {
            val intent = Intent(context,VoteProduct::class.java)
            startActivity(intent)
        }
        binding.donbancuatoi.setOnClickListener {
            val intent = Intent(context,Bill1::class.java)
            startActivity(intent)
        }

        val userId = user?._idUser

        val cartCount = userId?.let {
            cartDAO.getCartCountByUserId(it)
        }

        // Hiển thị số lượng sản phẩm lên TextView
        cartCount?.let {
          binding.numcart.text = "$it"
        }

        return binding.root
    }
}