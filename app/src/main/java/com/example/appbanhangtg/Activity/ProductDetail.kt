package com.example.appbanhangtg.Activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.CartModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityProductDetailBinding
import java.text.DecimalFormat


private lateinit var binding: ActivityProductDetailBinding

class ProductDetail : AppCompatActivity() {

    private lateinit var productDAO: ProductDAO
    private lateinit var voteShopDAO: VoteShopDAO
    private val cartDAO: CartDAO by lazy { CartDAO(this) }


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

                val numproduct = productDAO.getProductCountByShopId(shop._idShop)
                val tbcvote = voteShopDAO.getVoteShopCountById(shop._idShop)

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
            binding.txtpriceproductDetail.text = formatPrice(it.priceProduct)
            binding.txtnameproductDetail.text = it.nameProduct
            binding.txtdescproductDetail.text = it.descriptionProduct

            val user = this?.let { SharedPrefsManager.getUser(it) }
            val userId = user?._idUser
            val productIdsp = it._idProduct
            val shopIdsp = it._idShop


            // thêm sản phẩm vào giỏ hàng
            binding.imgCartAdd.setOnClickListener {
                if (productIdsp == null || userId == null ){
                    Toast.makeText(this, "Lỗi rùi hahaa", Toast.LENGTH_SHORT).show()
                }else{
                    hamADDCart(userId,productIdsp,shopIdsp)
                }

            }

        }


        binding.txtcartting.setOnClickListener {
            val intent = Intent(this, Cartting::class.java)
            intent.putExtra("PRODUCT_EXTRA", productModel)
            startActivity(intent)
        }

        // tính tổng sản phẩm có trong giỏ hàng
        sumcart()

        binding.imgbackproductDetail.setOnClickListener {
            finish()

        }
        binding.imgcartproductDetail.setOnClickListener {
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }
    }

    private fun formatPrice(price: Double): String {
        val formatter = DecimalFormat("#,### VNĐ")
        return formatter.format(price)
    }
    private fun hamADDCart(
        iduser: Int,
        idproduct: Int,
        shopIdsp: Int
    ) {
        val newCart = CartModel(
            0,
            iduser,
            idproduct,
            shopIdsp
        )
        val cartId = cartDAO.addCart(newCart)

        if (cartId > -1) {
            Toast.makeText(this, "Thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show()
            sumcart()
        } else {
            Toast.makeText(this, "thêm thất bại", Toast.LENGTH_SHORT).show()
        }
    }
    private fun sumcart(){
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val userId = user?._idUser

        val cartCount = userId?.let {
            cartDAO.getCartCountByUserId(it)
        }

        // Hiển thị số lượng sản phẩm lên TextView
        cartCount?.let {
            binding.numcart.text = "$it"
        }
    }
}