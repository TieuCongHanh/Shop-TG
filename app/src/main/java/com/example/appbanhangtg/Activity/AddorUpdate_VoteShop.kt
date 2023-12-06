package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Fragment.StarShop.StarShop
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddorUpdateVoteShopBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var binding: ActivityAddorUpdateVoteShopBinding

class AddorUpdate_VoteShop : AppCompatActivity() {
    private lateinit var voteShopDAO: VoteShopDAO
    private var numberofstart: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddorUpdateVoteShopBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imgbackaddVoteshop.setOnClickListener { finish() }

        val shopModel = intent.getSerializableExtra("SHOP_EXTRA") as? ShopModel
        val shopWrapper = ShopWrapper(shopModel)
        val user = this?.let { SharedPrefsManager.getUser(it) }
        voteShopDAO = VoteShopDAO(this)

        btnstar()


        binding.btnaddVoteshop.setOnClickListener {
            if (user?._idUser == null || user?._idUser <= 0) {
                Toast.makeText(this, "Bạn cần đăng nhập mới có thể ", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            } else {
                val content = binding.edtContent.text.toString()
                val userId = user?._idUser
                val shopID = shopModel?._idShop ?: -1
                val date = getCurrentDate()
                val stringNumber = numberofstart

                if (content.isEmpty() || stringNumber == null) {
                    Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                } else {
                    hamAdd(stringNumber, content, date, userId, shopID)
                }

            }
        }

    }

    private fun hamAdd(
        numberofstart: String,
        content: String,
        date: String,
        _idUser: Int,
        _idShop: Int
    ) {
        val newVoteShop = VoteShopModel(0, numberofstart, content, date, _idUser, _idShop)
        val userId = voteShopDAO.addVoteShop(newVoteShop)

        if (userId > -1) {
            Toast.makeText(this, "Đã thành công viết bình luận", Toast.LENGTH_SHORT).show()
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