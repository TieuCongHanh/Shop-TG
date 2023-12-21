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
            val phoneNumberRegex = Regex("^(03|05|07|08|09)\\d{8}$")
            val isValidphone = phoneNumberRegex.matches(phone)
            val email = binding.edtemaillu.text.toString()
            val emailRegex = Regex("[a-zA-Z0-9._-]+@gmail\\.com")
            val isValidemail = emailRegex.matches(email)
            val role = "User"
            val image = ""

            val userlist = userDAO.getAllUsers()
            val isUsernameTaken = userlist.any { user -> user.username == username }

            if (username.isEmpty() || password.isEmpty() || password1.isEmpty() || phone.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
            }else if (isUsernameTaken){
                Toast.makeText(this, "Tài khoản đã có người sử dụng", Toast.LENGTH_SHORT).show()
            }else if (!isValidphone){
                Toast.makeText(this, "Sai định dạng số điện thoại Việt Nam", Toast.LENGTH_SHORT).show()
            }else if (!isValidemail){
                Toast.makeText(this, "Sai định dạng email", Toast.LENGTH_SHORT).show()
            }
            else if (!password1.equals(password)){
                Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show()
            }
            else{
                hamLogup(username,password, phone,role,email,image)
            }
        }
    }

    private fun hamLogup(username: String, password: String,phone: String,role: String,email: String,image: String) {
        val newUser = UserModel(0, username, password, phone, role, email, image)
        val userId = userDAO.addUser(newUser)


        if (userId > -1) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
        }
    }
}