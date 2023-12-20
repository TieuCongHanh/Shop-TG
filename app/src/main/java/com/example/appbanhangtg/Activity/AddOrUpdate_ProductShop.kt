package com.example.appbanhangtg.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.TypeProductDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.TypeProductModel

import com.example.appbanhangtg.databinding.ActivityAddOrUpdateProductShopBinding
import java.text.DecimalFormat

private lateinit var binding: ActivityAddOrUpdateProductShopBinding

class AddOrUpdate_ProductShop : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private val typeProductDAO: TypeProductDAO by lazy { TypeProductDAO(this) }
    private val productDAO: ProductDAO by lazy { ProductDAO(this) }
    private lateinit var typeList: MutableList<TypeProductModel>
    private var selectedTypeId: Int = 0
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
            val requestOptions = RequestOptions().transform(CircleCrop())
            Glide.with(this)
                .load(imageUri)
                .apply(requestOptions)
                .into(binding.imgavtproduct)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrUpdateProductShopBinding.inflate(layoutInflater)
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


        binding.imgavtproduct.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, pickImage)

        }


        SpinerCoBan()

        binding.imgbackaddProductshop.setOnClickListener { finish() }

        val shopModel = intent.getSerializableExtra("SHOP_EXTRA") as? ShopModel
        val shopWrapper = ShopWrapper(shopModel)
        val user = this?.let { SharedPrefsManager.getUser(it) }
        val productId = intent.getIntExtra("_idProduct", 0)


        if (productId == 0) {
            binding.txtaddProduct.setText("Thêm sản phẩm")
            binding.btnaddProduct.setText("Add Product")
            binding.btnaddProduct.setOnClickListener {

                val imageUriString = imageUri.toString()

                val nameProduct = binding.edtNameProduct.text.toString()
                val quantityProduct = binding.edtQuantityProduct.text.toString()
                val priceProduct = binding.edtPriceProduct.text.toString()
                val descriptionProduct = binding.edtDescriptionProduct.text.toString()
                val idshop = shopModel?._idShop
                val iduser = user?._idUser
                val idtype = selectedTypeId
                val imges = binding.imgavtproduct.toString()

                val quantity = quantityProduct.toIntOrNull()
                val price = priceProduct.toDoubleOrNull()

                if (nameProduct.isEmpty() || quantityProduct.isEmpty() || priceProduct.isEmpty() || descriptionProduct.isEmpty()) {
                    Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                } else if (imageUri == null) {
                    Toast.makeText(this, "Hình ảnh phải có cho sản phẩm", Toast.LENGTH_SHORT).show()
                }else if (quantity == null || price == null ){
                    Toast.makeText(this, "Số lượng hoặc giá tiền phải là số", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (iduser != null && idshop != null) {
                        hamAdd(
                            nameProduct,
                            quantity,
                            price,
                            descriptionProduct,
                            imageUriString,
                            iduser,
                            idshop,
                            idtype
                        )
                    }
                }
            }
        } else {
            binding.txtaddProduct.setText("Cập nhập sản phẩm")
            binding.btnaddProduct.setText("Update Product")

            val currentProduct =
                productDAO.getAllProduct().find { it._idProduct == productId }
            if (currentProduct != null) {
                displayProductInfo(currentProduct)
                binding.btnaddProduct.setOnClickListener {
                    val imageUriString = imageUri.toString()

                    val nameProduct = binding.edtNameProduct.text.toString()
                    val quantityProduct = binding.edtQuantityProduct.text.toString()
                    val priceProduct = binding.edtPriceProduct.text.toString()
                    val descriptionProduct = binding.edtDescriptionProduct.text.toString()
                    val idshop = shopModel?._idShop
                    val iduser = user?._idUser
                    val idtype = selectedTypeId
                    val imges = binding.imgavtproduct.toString()

                    val quantity = quantityProduct.toIntOrNull()
                    val price = priceProduct.toDoubleOrNull()

                    if (nameProduct.isEmpty() || quantityProduct.isEmpty() || priceProduct.isEmpty() || descriptionProduct.isEmpty()) {
                        Toast.makeText(this, "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show()
                    } else if (imageUri == null) {
                        Toast.makeText(this, "Hình ảnh phải có cho sản phẩm", Toast.LENGTH_SHORT).show()
                    }else if (quantity == null || price == null ){
                        Toast.makeText(this, "Số lượng hoặc giá tiền phải là số", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (iduser != null && idshop != null) {
                            hamUpdate(
                                productId,
                                nameProduct,
                                quantity,
                                price,
                                descriptionProduct,
                                imageUriString,
                                iduser,
                                idshop,
                                idtype
                            )
                        }
                    }
                }
            }
        }

    }

    private fun SpinerCoBan() {
        typeList = mutableListOf()
        typeList.addAll(typeProductDAO.getAllTypeProducts())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            typeList.map { it.nameTypeProduct }
        )
        binding.sproleaddorupdate.adapter = adapter

        binding.sproleaddorupdate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedTypeId = typeList[position]._idtypeProduct ?: 0
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

    }

    private fun hamAdd(
        nameProduct: String,
        quantityProduct: Int,
        priceProduct: Double,
        descriptionProduct: String,
        imageProduct: String,
        iduser: Int,
        idshop: Int,
        idtype: Int

    ) {
        val newProduct = ProductModel(
            0,
            nameProduct,
            quantityProduct,
            priceProduct,
            descriptionProduct,
            imageProduct,
            iduser,
            idshop,
            idtype
        )
        val typeId = productDAO.addProduct(newProduct)

        if (typeId > -1) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)

            finish()
        } else {
            Toast.makeText(this, "thêm thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hamUpdate(
        productId: Int,
        nameProduct: String,
        quantityProduct: Int,
        priceProduct: Double,
        descriptionProduct: String,
        imageProduct: String,
        iduser: Int,
        idshop: Int,
        idtype: Int
    ) {
        val updatedProduct = ProductModel(
            productId,
            nameProduct,
            quantityProduct,
            priceProduct,
            descriptionProduct,
            imageProduct,
            iduser,
            idshop,
            idtype
        )
        val rowsAffected = productDAO.updateProduct(updatedProduct)

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProductInfo(product: ProductModel) {
        binding.edtNameProduct.setText(product.nameProduct)
        binding.edtQuantityProduct.setText(product.quantityProduct.toString())
        binding.edtPriceProduct.setText(formatPrice(product.priceProduct))
        binding.edtDescriptionProduct.setText(product.descriptionProduct)
        val requestOptions = RequestOptions().transform(CircleCrop())

        Glide.with(binding.root.context)
            .load(product?.imageProduct)
            .apply(requestOptions)
            .into(binding.imgavtproduct)

        // Đặt giá trị cho Spinner
        val typeIndex = typeList.indexOfFirst { it._idtypeProduct == product._idtypeProduct }
        if (typeIndex != -1) {
            binding.sproleaddorupdate.setSelection(typeIndex)
        }
    }
    private fun formatPrice(price: Double) : String {
        val formatter = DecimalFormat("#.###")
        return formatter.format(price)
    }
}