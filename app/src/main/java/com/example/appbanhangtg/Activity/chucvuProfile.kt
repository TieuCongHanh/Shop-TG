package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityChucvuProfileBinding
import java.text.DecimalFormat

private lateinit var binding:ActivityChucvuProfileBinding
class chucvuProfile : AppCompatActivity() {

    private val billDAO: BillDAO by lazy { BillDAO(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChucvuProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val userId = user?._idUser
        val username = user?.username

        binding.imgbackaddProductshop.setOnClickListener { finish() }
        binding.username.text = username

        val sumtienbill = userId?.let { billDAO.getTotalSumPriceByUserId(it) }
        binding.tienchi.text = formatPrice(sumtienbill!!.toDouble() ?: 0.0)

        if (sumtienbill!! <= 2000000.0){
            val tien = 2000000.0 - sumtienbill
         binding.tiennagcap.text = formatPrice(tien)
        }else if(sumtienbill!! <= 10000000.0){
            val tien = 10000000.0 - sumtienbill
          binding.tiennagcap.text = formatPrice(tien)
        }else if(sumtienbill!! < 100000000.0){
            val tien = 100000000.0 - sumtienbill
          binding.tiennagcap.text = formatPrice(tien)
        }
        else {
            binding.tiennagcap.setText("0 VNĐ")
        }
        val billlidt = billDAO.getBillCountByUserId(userId)
        billlidt?.let {
            binding.numbill.text = "$it"
        }
    }
    private fun formatPrice(price: Double) : String {
        val formatter = DecimalFormat("#,### VNĐ")
        return formatter.format(price)
    }
}