package com.example.appbanhangtg.Activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.Adapter.VoteProductAdapter
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.TypeProductDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.DAO.VoteProductDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.CartModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ProductWrapper
import com.example.appbanhangtg.Model.VoteProductModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityProductDetailBinding
import java.text.DecimalFormat


private lateinit var binding: ActivityProductDetailBinding

class ProductDetail : AppCompatActivity() {

    private lateinit var productDAO: ProductDAO
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<ProductModel>

    private lateinit var voteShopDAO: VoteShopDAO
    private val cartDAO: CartDAO by lazy { CartDAO(this) }
    private val voteProductDAO: VoteProductDAO by lazy { VoteProductDAO(this) }
    private val userDao:UserDAO by lazy { UserDAO(this) }
    private val billDAO:BillDAO by lazy { BillDAO(this) }
    private val typeProductDAO:TypeProductDAO by lazy { TypeProductDAO(this) }
//    override fun onResume() {
//        super.onResume()
//        list()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list()

    }
    private fun list(){
        val productModel = intent.getSerializableExtra("PRODUCT_EXTRA") as? ProductModel
        productDAO = ProductDAO(this)
        voteShopDAO = VoteShopDAO(this)
        val user = this?.let { SharedPrefsManager.getUser(it) }

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
            val tien =it.priceProduct + it.priceProduct * 10/100
            binding.tiengiamgia.text = applyStrikethroughSpan(formatPrice(tien ))
            binding.txtnameproductDetail.text = it.nameProduct
            binding.txtdescproductDetail.text = it.descriptionProduct
            binding.numproduct.text = it.quantityProduct.toString()

            val userId = user?._idUser
            val productIdsp = it._idProduct
            val shopIdsp = it._idShop

            // hiển thị trung bình cộng đánh giá sản phẩm
            val averageRating = productIdsp?.let { voteProductDAO.calculateAverageRatingByShopId(it) }
            binding.tbcdanhgia.text = "$averageRating"

            // hiển thị soos lượng bán được
            val totalQuantitySold = billDAO.getTotalQuantityByProductId(it._idProduct)
            binding.SLdaban.text = "Đã bán được $totalQuantitySold"



            // thêm sản phẩm vào giỏ hàng
            binding.imgCartAdd.setOnClickListener {
                if (productIdsp == null || userId == null ){
                    showDoaLogLogin()
                }else{
                    hamADDCart(userId,productIdsp,shopIdsp)
                }

            }
            // Lấy danh sách vote theo ID của cửa hàng
            val voteShopList = productIdsp?.let {
                voteProductDAO.getByVote4ShopId(it)
            }

            voteShopList?.let { displayStarList(it) }

            binding.txtshowallvote.setOnClickListener {
                val intent = Intent(this,VoteProduct1::class.java)
                intent.putExtra("PRODUCT_EXTRA", productModel)
                startActivity(intent)
            }


        }
        // hiển thị sản phẩm cùng loại
        // sản phẩm
        productList = mutableListOf()
        productAdapter = ProductAdapter(this,productList) { clickedProduct ->
            val intent = Intent(this, ProductDetail::class.java)
            intent.putExtra("PRODUCT_EXTRA", clickedProduct)
            startActivity(intent)
        }
        binding.recyclerviewvoteproduct1.adapter = productAdapter
       binding.recyclerviewvoteproduct1.layoutManager = GridLayoutManager(
            this,
            2,
            GridLayoutManager.VERTICAL,
            false)
        loadProduct()

        binding.txtcartting.setOnClickListener {
            if (user == null){
                showDoaLogLogin()
            }else{
                val intent = Intent(this, Cartting::class.java)
                intent.putExtra("PRODUCT_EXTRA", productModel)
                startActivity(intent)
            }
        }

        // tính tổng sản phẩm có trong giỏ hàng
        sumcart()

        binding.imgbackproductDetail.setOnClickListener {
            finish()

        }
        binding.imgcartproductDetail.setOnClickListener {
            if (user == null){
                showDoaLogLogin()
            }else {
                val intent = Intent(this, Cart::class.java)
                startActivity(intent)
            }
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
    private fun displayStarList(votes: List<VoteProductModel>) {
        val recyclerView = binding.recyclerviewvoteproduct

        recyclerView.layoutManager = GridLayoutManager(this,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val voteShopAdapter = VoteProductAdapter(votes, userDao) { clickedVoteShop ->
            Toast.makeText(this, "${clickedVoteShop._idProduct}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = voteShopAdapter
    }
    private fun showDoaLogLogin() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Thông Báo Shop TG")
        builder.setMessage("Bạn cần phải đăng nhập. Bạn có muốn đăng nhập lúc này?")
        builder.setPositiveButton("Đăng nhập") { dialog, which ->
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton(
            "Hủy"
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
    fun applyStrikethroughSpan(text: String): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(StrikethroughSpan(), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
    private fun loadProduct() {
        val productModel = intent.getSerializableExtra("PRODUCT_EXTRA") as? ProductModel
        productList.clear()
        val allProducts = productDAO.getAllProduct()
        val typelist = typeProductDAO.getAllTypeProducts()
        val typeId = typelist?.get(0)!!._idtypeProduct
        val filteredProducts = allProducts.filter { it.quantityProduct >= 1 && it._idtypeProduct == typeId && it._idProduct != productModel?._idProduct }

        productList.addAll(filteredProducts)
        productAdapter.notifyDataSetChanged()
    }

}