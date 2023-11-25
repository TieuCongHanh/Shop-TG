package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityRegisterBinding

private lateinit var binding: ActivityRegisterBinding
class Register : AppCompatActivity() {
    private val userDAO: UserDAO by lazy { UserDAO(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnloginlu.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        binding.btnloguplu.setOnClickListener {
            val username = binding.edtusernamelu.text.toString()
            val password = binding.edtpasswordlu.text.toString()
            val password1 = binding.edtpasswordlu1.text.toString()
            val phone = binding.edtphonelu.text.toString()
            val email = binding.edtemaillu.text.toString()
            val role = "User"
            val image = ""
            if (username.isEmpty() || password.isEmpty() || password1.isEmpty() || phone.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
            }else{
                hamLogup(username,password, phone,role,email,image)
            }
        }
    }

    private fun hamLogup(username: String, password: String,phone: String,email: String,role: String,image: String) {
        val newUser = UserModel(0, username, password, phone, role, email, image)
        val userId = userDAO.addUser(newUser)

        if (userId > -1) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            // Đăng ký thành công, thực hiện hành động tiếp theo (ví dụ: Chuyển đến màn hình đăng nhập)
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
        }
    }
}