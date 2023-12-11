package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Adapter.CartAdapter
import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.CartModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.databinding.ActivityCartBinding

private lateinit var binding: ActivityCartBinding

class Cart : AppCompatActivity() {
    private lateinit var cartDAO: CartDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productList = intent.getSerializableExtra("PRODUCT_EXTRA") as? ArrayList<ProductModel>
        val user = this?.let { SharedPrefsManager.getUser(it) }

        cartDAO = CartDAO(this)
        val userId = user?._idUser

        // Lấy danh sách address theo ID của user
        val cartList = userId?.let {
            cartDAO.getByCartIdUser(it)
        }

        cartList?.let { displayCartList(it) }

        binding.imgbackAddress.setOnClickListener {
            finish()
        }

    }

    private fun displayCartList(cart: List<CartModel>) {
        val recyclerView = binding.recyclerviewAddress

        recyclerView.layoutManager = GridLayoutManager(
            this,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val cartAdapter = CartAdapter(this, cart, cartDAO) { clickedCart ->
            val productId = clickedCart._idProduct

            val productModel = cartDAO.getProductByIdProduct(productId)

            val intent = Intent(this, ProductDetail::class.java)
            intent.putExtra("PRODUCT_EXTRA", productModel)
            startActivity(intent)
        }
        recyclerView.adapter = cartAdapter
    }






}