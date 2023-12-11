package com.example.appbanhangtg.Activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Adapter.UserAdapter
import com.example.appbanhangtg.DAO.UserDAO

import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddOrUpdateUserBinding
import android.Manifest

private lateinit var binding:ActivityAddOrUpdateUserBinding
class AddOrUpdate_User : AppCompatActivity() {

    private val userDAO: UserDAO by lazy { UserDAO(this) }
    private var imageUri: Uri? = null
    companion object {
        private const val pickImage = 1001 // Mã yêu cầu tùy ý
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == pickImage) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Quyền đã được cấp, bạn có thể tiếp tục với việc chọn ảnh
            } else {
                Toast.makeText(this, "Quyền truy cập bị từ chối", Toast.LENGTH_SHORT).show()
                // Xử lý trường hợp quyền bị từ chối
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data // URI của tài liệu hoặc tệp tin được chọn

            // Kiểm tra và lấy quyền truy cập lâu dài
            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            imageUri?.let {
                contentResolver.takePersistableUriPermission(imageUri!!, takeFlags)
            }
            Glide.with(this)
                .load(imageUri)
                .into(binding.imgaddorupdate)
            // Sử dụng URI với Glide để tải ảnh, hoặc xử lý tùy theo nhu cầu của bạn
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Nếu không có quyền, yêu cầu quyền từ người dùng
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                pickImage
            )
        }


        binding.edtimgaddorupdate.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, pickImage)
        }

        SpinerCoBan()

        val userId = intent.getIntExtra("_idUser", 0)
        Toast.makeText(this, "id $userId", Toast.LENGTH_SHORT).show()

        if (userId == 0) {
            binding.txtaddorupdate.setText("Thêm người dùng")
            binding.btnaddorupdate.setText("Add User")
            binding.btnaddorupdate.setOnClickListener {

                val imageUriString = imageUri.toString()

                val username = binding.edtusernameaddorupdate.text.toString()
                val password = binding.edtpasswordaddorupdate.text.toString()
                val phone = binding.edtphoneaddorupdate.text.toString()
                val email = binding.edtemailaddorupdate.text.toString()
                val role = binding.sproleaddorupdate.selectedItem.toString()

                if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                } else {
                    hamAdd(username, password, phone, role, email, imageUriString)
                }
            }
        } else {
            binding.txtaddorupdate.setText("Cập nhật người dùng")
            binding.btnaddorupdate.setText("Update User")

            val currentUser = userDAO.getAllUsers().find { it._idUser == userId }
            if (currentUser != null) {
                displayUserInfo(currentUser)

                binding.btnaddorupdate.setOnClickListener {
                    val imageUriString = imageUri.toString()

                    val username = binding.edtusernameaddorupdate.text.toString()
                    val password = binding.edtpasswordaddorupdate.text.toString()
                    val phone = binding.edtphoneaddorupdate.text.toString()
                    val email = binding.edtemailaddorupdate.text.toString()
                    val role = binding.sproleaddorupdate.selectedItem.toString()

                    if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                        Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                    } else {
                        hamUpdate(userId, username, password, phone, email, role, imageUriString)
                    }
                }
            }
        }
        binding.imgbackaddUsser.setOnClickListener { finish() }
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
        if (userDAO.getAllUsers().any { it.username == username || it.email == email }) {
            Toast.makeText(this, "Người dùng đã tồn tại", Toast.LENGTH_SHORT).show()
            return
        }
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
        binding.edtphoneaddorupdate.setText(user.phone)
        binding.edtemailaddorupdate.setText(user.email)

        val requestOptions = RequestOptions().transform(CircleCrop())

        Glide.with(binding.root.context)
            .load(user?.image)
            .apply(requestOptions)
            .placeholder(R.drawable.icon_persion)
            .into(binding.imgaddorupdate)

        // Set giá trị cho Spinner
        val roles = resources.getStringArray(R.array.role)
        val roleIndex = roles.indexOf(user.role)
        binding.sproleaddorupdate.setSelection(roleIndex)
    }

}