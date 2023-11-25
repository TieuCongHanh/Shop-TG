package com.example.appbanhangtg.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.databinding.ActivityLoginBinding

private lateinit var binding: ActivityLoginBinding

class Login : AppCompatActivity() {
    private val userDAO: UserDAO by lazy { UserDAO(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnlogin.setOnClickListener {
            val username = binding.edtusername.text.toString()
            val password = binding.edtpassword.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
            } else {
                hamLogin(username, password)
            }
        }
        binding.btnloguplg.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun hamLogin(username: String, password: String) {
        val checklogin = userDAO.login(username, password, this)
        if (checklogin != null) {
            if (checklogin.role == "Admin") {
                val intent = Intent(this, HomeAdmin::class.java)
                startActivity(intent)
                Toast.makeText(this, "Chào mừng "+ checklogin.username+" đến với TG", Toast.LENGTH_SHORT).show()
            }else if(checklogin.role == "Shipper"){
                val intent = Intent(this, HomeShip::class.java)
                startActivity(intent)
                Toast.makeText(this, "Chào mừng "+checklogin.username+" đến với TG", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, HomeUser::class.java)
                startActivity(intent)
                Toast.makeText(this, "Chào mừng "+checklogin.username+" đến với TG", Toast.LENGTH_SHORT).show()
            }


        } else {
            Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT)
                .show()
        }
    }
}