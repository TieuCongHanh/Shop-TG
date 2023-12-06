package com.example.appbanhangtg.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbanhangtg.Activity.AddOrUpdate_User
import com.example.appbanhangtg.Activity.ProductDetail
import com.example.appbanhangtg.Activity.Shop
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.Adapter.ShopAdapter
import com.example.appbanhangtg.Adapter.UserAdapter
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentHomeBinding
import com.example.appbanhangtg.databinding.FragmentQLUserBinding

private lateinit var binding:FragmentHomeBinding
class Home : Fragment() {
    private lateinit var shopAdapter: ShopAdapter
    private lateinit var shopList: MutableList<ShopModel>
    private val shopDAO: ShopDAO by lazy { ShopDAO(requireContext()) }

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<ProductModel>
    private val productDAO: ProductDAO by lazy { ProductDAO(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        // shop
        shopList = mutableListOf()
        shopAdapter = ShopAdapter(shopList) { clickedShop ->
            val intent = Intent(context, Shop::class.java)
            intent.putExtra("SHOP_EXTRA", clickedShop)
            startActivity(intent)
        }
        binding.recyclerviewshop.adapter = shopAdapter
        binding.recyclerviewshop.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false)
        loadShop()

        // sản phẩm
        productList = mutableListOf()
        productAdapter = ProductAdapter(productList) { clickedProduct ->
            val intent = Intent(context, ProductDetail::class.java)
            intent.putExtra("PRODUCT_EXTRA", clickedProduct)
            startActivity(intent)
        }
        binding.recyclerviewdish.adapter = productAdapter
        binding.recyclerviewdish.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false)
        loadProduct()


        return binding.root
    }

    private fun loadShop() {
        shopList.clear()
        shopList.addAll(shopDAO.getAllShop())
        shopAdapter.notifyDataSetChanged()
    }
    private fun loadProduct() {
        productList.clear()
        productList.addAll(productDAO.getAllProduct())
        productAdapter.notifyDataSetChanged()
    }
}