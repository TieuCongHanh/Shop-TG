package com.example.appbanhangtg.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.appbanhangtg.Adapter.UserAdapter
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddOrUpdateUserBinding

private lateinit var binding:ActivityAddOrUpdateUserBinding
class AddOrUpdate_User : AppCompatActivity() {

    private val userDAO: UserDAO by lazy { UserDAO(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SpinerCoBan()

        val userId = intent.getIntExtra("_idUser", 0)
        Toast.makeText(this, "id $userId", Toast.LENGTH_SHORT).show()

        if (userId == 0) {
            binding.txtaddorupdate.setText("Thêm người dùng")
            binding.btnaddorupdate.setText("Add User")
            binding.btnaddorupdate.setOnClickListener {
                val username = binding.edtusernameaddorupdate.text.toString()
                val password = binding.edtpasswordaddorupdate.text.toString()
                val image = binding.edtimgaddorupdate.text.toString()
                val phone = binding.edtphoneaddorupdate.text.toString()
                val email = binding.edtemailaddorupdate.text.toString()
                val role = binding.sproleaddorupdate.selectedItem.toString()

                if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                } else {
                    hamAdd(username, password, phone, role, email, image)
                }
            }
        } else {
            binding.txtaddorupdate.setText("Cập nhật người dùng")
            binding.btnaddorupdate.setText("Update User")

            val currentUser = userDAO.getAllUsers().find { it._idUser == userId }
            if (currentUser != null) {
                displayUserInfo(currentUser)

                binding.btnaddorupdate.setOnClickListener {
                    val username = binding.edtusernameaddorupdate.text.toString()
                    val password = binding.edtpasswordaddorupdate.text.toString()
                    val image = binding.edtimgaddorupdate.text.toString()
                    val phone = binding.edtphoneaddorupdate.text.toString()
                    val email = binding.edtemailaddorupdate.text.toString()
                    val role = binding.sproleaddorupdate.selectedItem.toString()

                    if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                        Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                    } else {
                        hamUpdate(userId, username, password, phone, email, role, image)
                    }
                }
            }
        }
    }

    private fun SpinerCoBan() {
        // tạo 1 list ngôn ngữ
        val  list = resources.getStringArray(R.array.role)
        // khai báo bộ điều hướng
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,list)
        binding.sproleaddorupdate.adapter = adapter

        binding.sproleaddorupdate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
    private fun hamAdd(username: String, password: String,phone: String,email: String,role: String,image: String) {
        val newUser = UserModel(0, username, password, phone, role, email, image)
        val userId = userDAO.addUser(newUser)

        if (userId > -1) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
        }
    }
    private fun hamUpdate(userId: Int, username: String, password: String, phone: String, email: String, role: String, image: String) {
        val updatedUser = UserModel(userId, username, password, phone, role, email, image)
        val rowsAffected = userDAO.updateUser(updatedUser)

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }
    private fun displayUserInfo(user: UserModel) {
        binding.edtusernameaddorupdate.setText(user.username)
        binding.edtpasswordaddorupdate.setText(user.password)
        binding.edtimgaddorupdate.setText(user.image)
        binding.edtphoneaddorupdate.setText(user.phone)
        binding.edtemailaddorupdate.setText(user.email)

        // Set giá trị cho Spinner
        val roles = resources.getStringArray(R.array.role)
        val roleIndex = roles.indexOf(user.role)
        binding.sproleaddorupdate.setSelection(roleIndex)
    }

}