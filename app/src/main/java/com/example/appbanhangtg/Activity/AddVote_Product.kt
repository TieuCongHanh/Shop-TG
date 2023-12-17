package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.TypeProductDAO
import com.example.appbanhangtg.DAO.VoteProductDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.VoteProductModel
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddVoteProductBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var binding: ActivityAddVoteProductBinding

class AddVote_Product : AppCompatActivity() {

    private val shopDAO: ShopDAO by lazy { ShopDAO(this) }
    private val billDAO: BillDAO by lazy { BillDAO(this) }
    private val productDAO: ProductDAO by lazy { ProductDAO(this) }
    private val voteProductDAO: VoteProductDAO by lazy { VoteProductDAO(this) }
    private var numberofstart: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVoteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val billmodel = intent.getSerializableExtra("BILL_EXTRA") as? BillModel

        btnstar()

        billmodel?.let {
            // thông tin cửa hàng
            val shoplist = shopDAO.getByProductIdShop(it._idShop)
            binding.nameshopbill.text = shoplist[0].nameShop

            // thông tin sản phẩm
            val productlist = productDAO.getProductById(it._idProduct)
            productlist?.let {
                val requestOptions = RequestOptions().transform(CircleCrop())

                Glide.with(binding.root.context)
                    .load(it.imageProduct)
                    .apply(requestOptions)
                    .placeholder(R.drawable.icon_person) // Placeholder image while loading
                    .into(binding.imgproductbill)
                binding.nameproductbill.text = it.nameProduct.toString()
                binding.priceproductbill.text = it.priceProduct.toString()
            }

            // thông tin bill
            binding.trangthaibill.text = "Đang đánh giá"
            binding.quantityproductbill.text = it.quantitybill.toString()
            binding.sumproduct.text = it.quantitybill.toString()
            binding.sumpricebill.text = it.sumpricebill.toString()

            // nút viết comment
            binding.btnaddVoteshop.setOnClickListener {
                val user = this?.let { SharedPrefsManager.getUser(it) }

                val content = binding.edtContent.text.toString()
                val userId = user?._idUser
                val productId = billmodel?._idProduct ?: -1
                val date = getCurrentDate()
                val stringNumber = numberofstart

                if (content.isEmpty() || stringNumber == null) {
                    Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                } else {
                    if (userId != null) {
                        hamAdd(stringNumber, content, date, userId, productId)
                    }

                }
            }

        }

    }

    private fun hamAdd(
        numberofstart: String,
        content: String,
        date: String,
        _idUser: Int,
        _idProduct: Int
    ) {
        val newVoteProduct = VoteProductModel(0, numberofstart, content, date, _idUser, _idProduct)
        val userId = voteProductDAO.addVoteProduct(newVoteProduct)
        val billmodel = intent.getSerializableExtra("BILL_EXTRA") as? BillModel
        if (userId > -1) {
            Toast.makeText(this, "Đã thành công viết bình luận", Toast.LENGTH_SHORT).show()
            val tthuy = "true"
            val billId = billmodel?._idBill
            billId?.let { billDAO.updateTTVote(it, tthuy) }

            setResult(AppCompatActivity.RESULT_OK)
            finish()

        } else {
            Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        return currentDate
    }


    private fun btnstar() {
        binding.start.setOnClickListener {
            binding.start.setImageResource(R.drawable.icon_star_rate_24)
            binding.start1.setImageResource(R.drawable.icon_star_outline_24)
            binding.start2.setImageResource(R.drawable.icon_star_outline_24)
            binding.start3.setImageResource(R.drawable.icon_star_outline_24)
            binding.start4.setImageResource(R.drawable.icon_star_outline_24)
            numberofstart = "1"
        }
        binding.start1.setOnClickListener {
            binding.start.setImageResource(R.drawable.icon_star_rate_24)
            binding.start1.setImageResource(R.drawable.icon_star_rate_24)
            binding.start2.setImageResource(R.drawable.icon_star_outline_24)
            binding.start3.setImageResource(R.drawable.icon_star_outline_24)
            binding.start4.setImageResource(R.drawable.icon_star_outline_24)
            numberofstart = "2"
        }
        binding.start2.setOnClickListener {
            binding.start.setImageResource(R.drawable.icon_star_rate_24)
            binding.start1.setImageResource(R.drawable.icon_star_rate_24)
            binding.start2.setImageResource(R.drawable.icon_star_rate_24)
            binding.start3.setImageResource(R.drawable.icon_star_outline_24)
            binding.start4.setImageResource(R.drawable.icon_star_outline_24)
            numberofstart = "3"
        }
        binding.start3.setOnClickListener {
            binding.start.setImageResource(R.drawable.icon_star_rate_24)
            binding.start1.setImageResource(R.drawable.icon_star_rate_24)
            binding.start2.setImageResource(R.drawable.icon_star_rate_24)
            binding.start3.setImageResource(R.drawable.icon_star_rate_24)
            binding.start4.setImageResource(R.drawable.icon_star_outline_24)
            numberofstart = "4"
        }
        binding.start4.setOnClickListener {
            binding.start.setImageResource(R.drawable.icon_star_rate_24)
            binding.start1.setImageResource(R.drawable.icon_star_rate_24)
            binding.start2.setImageResource(R.drawable.icon_star_rate_24)
            binding.start3.setImageResource(R.drawable.icon_star_rate_24)
            binding.start4.setImageResource(R.drawable.icon_star_rate_24)
            numberofstart = "5"
        }
    }
}