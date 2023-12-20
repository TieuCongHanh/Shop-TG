package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivitySetNewPassWordBinding

private lateinit var binding:ActivitySetNewPassWordBinding
class SetNewPassWord : AppCompatActivity() {

    private val userDAO: UserDAO by lazy { UserDAO(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNewPassWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbackppasDetail.setOnClickListener {
            finish()
        }
        val user = this?.let { SharedPrefsManager.getUser(it) }
        binding.btnsetnew.setOnClickListener {
            val oldpass = binding.passold.text.toString()
            val newpass = binding.passnew.text.toString()
            val newpass1 = binding.passnew1.text.toString()
            val userId = user?._idUser
            if ( oldpass.isEmpty() || newpass.isEmpty()|| newpass1.isEmpty()) {
                Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
            }else if (newpass.length < 6 || newpass1.length < 6){
                Toast.makeText(this, "Mật khẩu mới phải lớn hơn 6 kí tự", Toast.LENGTH_SHORT).show()
            }
            else if (!newpass.equals(newpass1)){
                Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show()
            }else if (!user?.password.toString().equals(oldpass)){
                Toast.makeText(this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show()
            }
            else {
                if (userId != null) {
                    hamUpdate(userId,newpass)
                }
            }
        }
    }
    private fun hamUpdate(userId: Int, newpass: String) {
        val rowsAffected = userDAO.updateUserPassword(userId,newpass)

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()

            val user = this?.let { SharedPrefsManager.getUser(it) }
            val updatedUser = UserModel(
                userId,user?.username.toString(),newpass,
                user?.phone.toString(),user?.role.toString(), user?.role.toString(), user?.image.toString())
            SharedPrefsManager.saveUser(this, updatedUser)

            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }
}