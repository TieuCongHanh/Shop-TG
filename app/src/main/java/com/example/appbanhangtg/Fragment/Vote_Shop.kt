package com.example.appbanhangtg.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appbanhangtg.Activity.AddorUpdate_VoteShop
import com.example.appbanhangtg.Activity.Login
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Fragment.StarShop.StarShop
import com.example.appbanhangtg.Fragment.StarShop.StarShop1
import com.example.appbanhangtg.Fragment.StarShop.StarShop2
import com.example.appbanhangtg.Fragment.StarShop.StarShop3
import com.example.appbanhangtg.Fragment.StarShop.StarShop4
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentVoteShopBinding

private lateinit var binding: FragmentVoteShopBinding

class Vote_Shop : Fragment() {

    private var shopModel: ShopModel? = null
    private lateinit var voteshopDAO: VoteShopDAO

    companion object {
        fun newInstance(shopWrapper: ShopWrapper): Vote_Shop {
            val fragment = Vote_Shop()
            val args = Bundle()
            args.putSerializable("SHOP_EXTRA", shopWrapper)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVoteShopBinding.inflate(inflater, container, false)
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel
        val user = context?.let { SharedPrefsManager.getUser(it) }
        binding.txtstarShop.setOnClickListener {
            val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as ShopWrapper
            val tab1 = StarShop.newInstance(shopWrapper)
            childFragmentManager.beginTransaction().apply {
                replace(R.id.tabfragment, tab1)
                commit()
            }
        }

        binding.txtstar1Shop.setOnClickListener {
            val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as ShopWrapper
            val tab2 = StarShop1.newInstance(shopWrapper)
            childFragmentManager.beginTransaction().apply {
                replace(R.id.tabfragment, tab2)
                commit()
            }
        }

        binding.txtstar2Shop.setOnClickListener {
            val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as ShopWrapper
            val tab3 = StarShop2.newInstance(shopWrapper)
            childFragmentManager.beginTransaction().apply {
                replace(R.id.tabfragment, tab3)
                commit()
            }
        }

        binding.txtstar3Shop.setOnClickListener {
            val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as ShopWrapper
            val tab4 = StarShop3.newInstance(shopWrapper)
            childFragmentManager.beginTransaction().apply {
                replace(R.id.tabfragment, tab4)
                commit()
            }
        }

        binding.txtstar4Shop.setOnClickListener {
            val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as ShopWrapper
            val tab5 = StarShop4.newInstance(shopWrapper)
            childFragmentManager.beginTransaction().apply {
                replace(R.id.tabfragment, tab5)
                commit()
            }
        }
        val shopWrapper1 = arguments?.getSerializable("SHOP_EXTRA") as ShopWrapper
        val tab5 = StarShop4.newInstance(shopWrapper1)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.tabfragment, tab5)
            commit()
        }

        binding.addvoteShop.setOnClickListener {
            if (user == null){
                showDoaLogLogin()
            }else{
                val intent = Intent(context, AddorUpdate_VoteShop::class.java)
                intent.putExtra("SHOP_EXTRA", shopModel) // Đính kèm dữ liệu vào Intent
                startActivity(intent)
            }

        }

        return binding.root
    }

    private fun showDoaLogLogin() {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Thông Báo Shop TG")
        builder.setMessage("Bạn cần phải đăng nhập. Bạn có muốn đăng nhập lúc này?")
        builder.setPositiveButton("Đăng nhập") { dialog, which ->
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton(
            "Hủy"
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}
