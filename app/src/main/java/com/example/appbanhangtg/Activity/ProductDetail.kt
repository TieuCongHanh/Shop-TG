package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable.Creator
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ProductWrapper
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityProductDetailBinding

private lateinit var binding: ActivityProductDetailBinding

class ProductDetail : AppCompatActivity() {

    private lateinit var productDAO: ProductDAO
    private lateinit var voteShopDAO: VoteShopDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productModel = intent.getSerializableExtra("PRODUCT_EXTRA") as? ProductModel
        productDAO = ProductDAO(this)
        voteShopDAO = VoteShopDAO(this)

        productModel?.let {

            val shopDAO = ShopDAO(this)
            val shopList = shopDAO.getByProductIdShop(it._idShop)

            if (shopList.isNotEmpty()) {
                val shop = shopList[0] // Lấy cửa hàng đầu tiên

                // Hiển thị thông tin cửa hàng
                binding.txtnameshopproductDetail.text = shop.nameShop

                val requestOptions = RequestOptions().transform(CircleCrop())
                Glide.with(binding.root.context)
                    .load(shop.imageavtShop)
                    .apply(requestOptions)
                    .placeholder(R.drawable.shop1)
                    .into(binding.imgshopproductDetail)

               val numproduct =  productDAO.getProductCountByShopId(shop._idShop)
               val tbcvote =  voteShopDAO.getVoteShopCountById(shop._idShop)

                binding.txtnumsp.text = "$numproduct"
                binding.txttbcvote.text = "$tbcvote"

                binding.txtshowshop.setOnClickListener {
                    val shopIntent = Intent(this, Shop::class.java)
                    shopIntent.putExtra("SHOP_EXTRA", shop)
                    startActivity(shopIntent)
                }
                binding.imgshopproductDetail.setOnClickListener {
                    val shopIntent = Intent(this, Shop::class.java)
                    shopIntent.putExtra("SHOP_EXTRA", shop)
                    startActivity(shopIntent)
                }

            }

            // Hiển thị thông tin sản phẩm
            val tronavt = RequestOptions().transform(CircleCrop())
            Glide.with(binding.root.context)
                .load(it.imageProduct)
                .apply(tronavt)
                .placeholder(R.drawable.shop1)
                .into(binding.avt)
            binding.txtpriceproductDetail.text = " " + it.priceProduct + " VNĐ"
            binding.txtnameproductDetail.text = it.nameProduct
            binding.txtdescproductDetail.text = it.descriptionProduct
        }

        binding.imgCartAdd.setOnClickListener {
            Toast.makeText(this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show()
        }
        binding.txtcartting.setOnClickListener {

        }




        binding.imgbackproductDetail.setOnClickListener {
            finish()
        }
    }
}