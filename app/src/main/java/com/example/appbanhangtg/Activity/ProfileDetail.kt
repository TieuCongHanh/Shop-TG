package com.example.appbanhangtg.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityProfileDetailBinding

private lateinit var binding:ActivityProfileDetailBinding
class ProfileDetail : AppCompatActivity() {

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
                .into(binding.imgprofileDetail)
            // Sử dụng URI với Glide để tải ảnh, hoặc xử lý tùy theo nhu cầu của bạn
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbackprofileDetail.setOnClickListener {
            finish()
        }
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val requestOptions = RequestOptions().transform(CircleCrop())

        Glide.with(binding.root.context)
            .load(user?.image)
            .apply(requestOptions)
            .placeholder(R.drawable.icon_person) // Placeholder image while loading
            .into(binding.imgprofileDetail)

       binding.edtusernameprofileDetail.setText(" " +user?.username)
       binding.edtemailprofileDetail.setText(" " +user?.email)
       binding.edtphoneprofileDetail.setText(" " +user?.phone)

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


        binding.imgprofileDetail.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, pickImage)
        }

        binding.btnupdateProfile.setOnClickListener {
            val imageUriString = imageUri.toString()

            val phone = binding.edtphoneprofileDetail.text.toString()
            val email = binding.edtemailprofileDetail.text.toString()
            val userId = user?._idUser
            if ( phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
            } else {
                if (userId != null) {
                    hamUpdate(userId,phone,email,imageUriString)
                }
            }
        }
    }
    private fun hamUpdate(userId: Int, email: String, phone: String, imageUriString: String) {
        val rowsAffected = userDAO.updateUserDetails(userId,phone, email, imageUriString)

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()

            val user = this?.let { SharedPrefsManager.getUser(it) }
            val updatedUser = UserModel(userId,user?.username.toString(),user?.password.toString(), phone,user?.role.toString(), email, imageUriString)
            SharedPrefsManager.saveUser(this, updatedUser)

            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }


}