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
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.TypeProductModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityAddOrUpdateShopBinding

private lateinit var binding:ActivityAddOrUpdateShopBinding
class AddOrUpdate_Shop : AppCompatActivity() {
    private var clickedOnImageavtShop = false
    private var clickedOnImageShop = false
    private val shopDAO: ShopDAO by lazy { ShopDAO(this) }
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

            // Xác định xem bạn bấm vào ảnhavtShop hay imageShop và gán hình ảnh tương ứng
            if (clickedOnImageavtShop) {
                Glide.with(this)
                    .load(imageUri)
                    .into(binding.imageavtShop)
            } else if (clickedOnImageShop) {
                Glide.with(this)
                    .load(imageUri)
                    .into(binding.imageShop)
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateShopBinding.inflate(layoutInflater)
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


        binding.imageavtShop.setOnClickListener {
            clickedOnImageavtShop = true
            clickedOnImageShop = false

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, pickImage)
        }

        binding.imageShop.setOnClickListener {
            clickedOnImageavtShop = false
            clickedOnImageShop = true

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, pickImage)
        }

        binding.imgbackaddShop.setOnClickListener { finish() }

        val shopModel = intent.getSerializableExtra("SHOP_EXTRA") as? ShopModel
        val shopWrapper = ShopWrapper(shopModel)
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val shopId = intent.getIntExtra("_idShop", 0)


        if (shopId == 0) {
            binding.txtaddShop.setText("Tạo mới cửa hàng")
            binding.btnaddShop.setText("Add Shop")
            binding.btnaddShop.setOnClickListener {

                val imageUriString = imageUri.toString()

                val nameShop = binding.edtNameShop.text.toString()
                val sloganShop = binding.edtSloganShop.text.toString()
                val descriptionShop = binding.edtDescriptionShop.text.toString()
                val iduser = user?._idUser

                if (nameShop.isEmpty() || sloganShop.isEmpty() || descriptionShop.isEmpty()) {
                    Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                } else if (imageUriString.isEmpty()) {
                    Toast.makeText(this, "Hình ảnh phải có cho shop", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (iduser != null ) {
                        hamAdd(
                            nameShop,
                            descriptionShop,
                            sloganShop,
                            imageUriString,
                            imageUriString,
                            iduser
                        )
                    }
                }
            }
        } else {
            binding.txtaddShop.setText("Cập nhập cửa hàng")
            binding.btnaddShop.setText("Update Shop")

            val currentShop =
                shopDAO.getAllShop().find { it._idShop == shopId }
            if (currentShop != null) {
                displayProductInfo(currentShop)
                binding.btnaddShop.setOnClickListener {

                    val imageUriString = imageUri.toString()


                    val nameShop = binding.edtNameShop.text.toString()
                    val sloganShop = binding.edtSloganShop.text.toString()
                    val descriptionShop = binding.edtDescriptionShop.text.toString()
                    val iduser = user?._idUser

                    if (nameShop.isEmpty() || sloganShop.isEmpty() || descriptionShop.isEmpty()) {
                        Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                    } else if (imageUriString.isEmpty()) {
                        Toast.makeText(this, "Hình ảnh phải có cho shop", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (iduser != null) {
                            hamUpdate(
                                shopId,
                                nameShop,
                                descriptionShop,
                                sloganShop,
                                imageUriString,
                                imageUriString,
                                iduser
                            )
                        }
                    }
                }
            }
        }

    }
    private fun hamAdd(
        nameShop: String,
        descriptionShop: String,
        sloganShop: String,
        imageUriString: String,
        imageUriString1: String,
        iduser: Int

    ) {
        val newShop = ShopModel(
            0,
            nameShop,
            descriptionShop,
            sloganShop,
            imageUriString,
            imageUriString1,
            iduser
        )
        val typeId = shopDAO.addShop(newShop)

        if (typeId > -1) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)

            finish()
        } else {
            Toast.makeText(this, "thêm thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hamUpdate(
        shopId: Int,
        nameShop: String,
        descriptionShop: String,
        sloganShop: String,
        imageUriString: String,
        imageUriString1: String,
        iduser: Int
    ) {
        val updateShop = ShopModel(
            shopId,
            nameShop,
            descriptionShop,
            sloganShop,
            imageUriString,
            imageUriString1,
            iduser
        )
        val rowsAffected = shopDAO.updateShop(updateShop)

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProductInfo(shop: ShopModel) {
        binding.edtNameShop.setText(shop.nameShop)
        binding.edtDescriptionShop.setText(shop.descriptionShop.toString())
        binding.edtSloganShop.setText(shop.sloganShop.toString())

        val requestOptions = RequestOptions().transform(CircleCrop())

        Glide.with(binding.root.context)
            .load(shop?.imageavtShop)
            .apply(requestOptions)
            .into(binding.imageavtShop)
        Glide.with(binding.root.context)
            .load(shop?.imageShop)
            .apply(requestOptions)
            .into(binding.imageShop)

    }
}