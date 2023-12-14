package com.example.appbanhangtg.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.appbanhangtg.DAO.AddRessDAO
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityCarttingBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar

private lateinit var binding: ActivityCarttingBinding

class Cartting : AppCompatActivity() {
    private var numberquantity : Int = 1
    private var totalSum : Double = 0.0
    private var totalMonyship : Double = 0.0
    private var ptship : String = ""
    private var ngaynhanhang : String = ""
    private lateinit var productDAO: ProductDAO
    private lateinit var addRessDAO: AddRessDAO
    private lateinit var billDAO: BillDAO

    companion object {
        private const val REQUEST_ADDRESS = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarttingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NutTangGiamSL()
        updatePriceUI()
        updateTotalSum()
        ptgiaohang()
        ptship()

        val user = this?.let { SharedPrefsManager.getUser(it) }
        val productModel = intent.getSerializableExtra("PRODUCT_EXTRA") as? ProductModel
        productDAO = ProductDAO(this)
        billDAO = BillDAO(this)

        // Lấy danh sách địa chỉ của người dùng
        addRessDAO = AddRessDAO(this)
        val userId = user?._idUser
        val addressList = userId?.let {
            addRessDAO.getByAddressIdUser(it)
        }

        // Kiểm tra danh sách địa chỉ và hiển thị địa chỉ đầu tiên nếu có
        if (addressList?.isNotEmpty() == true) {
            val firstAddress = addressList[0]
            binding.fullnamePhone.text = "${firstAddress.fullname} | ${firstAddress.phone}"
            binding.address.text = firstAddress.address
            binding.note.text = firstAddress.note
        }

        productModel?.let {

            val shopDAO = ShopDAO(this)
            val shopList = shopDAO.getByProductIdShop(it._idShop)

            if (shopList.isNotEmpty()) {
                val shop = shopList[0] // Lấy cửa hàng đầu tiên
                // Hiển thị thông tin cửa hàng
                binding.txtnameShop.text = shop.nameShop
            }
            // Hiển thị thông tin sản phẩm
            val tronavt = RequestOptions().transform(CircleCrop())
            Glide.with(binding.root.context)
                .load(it.imageProduct)
                .apply(tronavt)
                .placeholder(R.drawable.shop1)
                .into(binding.imgproduct)

            binding.txtpriceproduct.text = formatPrice(it.priceProduct)
            binding.sumtienhang.text = formatPrice(it.priceProduct)
            binding.sumone.text = formatPrice(it.priceProduct)
            binding.txtnameProduct.text = it.nameProduct


            // nút đặt hàng
            binding.txtaddbill.setOnClickListener {
                // Cập nhật các biến dựa trên thông tin hiện tại
                val quantitybill = numberquantity
                val sumpricebill = totalSum
                val ptthanhtoan = ptship
                val phiship = totalMonyship
                val currentDate = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val datedathang = dateFormat.format(currentDate.time)
                val datenhanhang = ngaynhanhang
                val _idUser = userId
                val _idProduct = productModel?._idProduct
                val _idAddRess = addressList?.get(0)?._idAddRess
                val _idShop = shopList.get(0)._idShop

                if (addressList?.isNotEmpty() == false){
                    Toast.makeText(this, "Không có địa chỉ", Toast.LENGTH_SHORT).show()
                }
                else if (_idUser != null && _idProduct != null && _idAddRess != null && _idShop != null) {
                    hamadd(
                        quantitybill, sumpricebill, ptthanhtoan, phiship, datedathang, datenhanhang,
                        "false", "false", "false", "false", "false", "false",
                        _idUser, _idProduct, _idAddRess, _idShop,""
                    )
                } else {
                    Toast.makeText(this, "Thông tin không đầy đủ", Toast.LENGTH_SHORT).show()
                }
            }
        }


        // nút back
        binding.imgbackproductDetail.setOnClickListener {
            finish()
        }

        // nút địa chỉ
        binding.relativeAdress.setOnClickListener {
            val intent = Intent(this, Address::class.java)
            startActivityForResult(intent, REQUEST_ADDRESS)
        }
        // nút đialog phương thức giao hàng
        binding.moneyshipper.setOnClickListener {
            moneyship()
        }
        binding.ptthanhtoan.setOnClickListener {
            ptthanhtoan()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ADDRESS && resultCode == Activity.RESULT_OK) {
            val selectedAddress = data?.getSerializableExtra("EXTRA_ADDRESS") as? AddressModel
            if (selectedAddress != null) {
                binding.fullnamePhone.text =
                    "${selectedAddress.fullname} | ${selectedAddress.phone}"
                binding.address.text = selectedAddress.address
                binding.note.text = selectedAddress.note
            }
        }
    }

    private fun NutTangGiamSL() {
        binding.imgcong.setOnClickListener {
            numberquantity++
            updateQuantityUI(numberquantity)
            updatePriceUI()
        }

        binding.imgtru.setOnClickListener {
            if (numberquantity > 1) {
                numberquantity--
                updateQuantityUI(numberquantity)
                updatePriceUI()
            } else {
                Toast.makeText(this, "Số lượng không được thấp hơn 1", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun formatPrice(price: Double): String {
        val formatter = DecimalFormat("#,### VNĐ")
        return formatter.format(price)
    }

    private fun updateQuantityUI(quantity: Int) {
        binding.txtquantity.text = quantity.toString()
        binding.txtquantity1.text = quantity.toString()
    }

    private fun updatePriceUI() {
        val productModel = intent.getSerializableExtra("PRODUCT_EXTRA") as? ProductModel
        productModel?.let {
            val price = it.priceProduct * numberquantity
            binding.sumone.text = formatPrice(price)
            binding.sumtienhang.text = formatPrice(price)

            // Cập nhật tổng tiền hàng ở đây
            updateTotalSum()
        }
    }

    private fun moneyship() {
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_moneyship, null)
        dialog.setContentView(dialogView)

        val option1 = dialogView.findViewById<RadioButton>(R.id.option1)
        val option2 = dialogView.findViewById<RadioButton>(R.id.option2)
        val option3 = dialogView.findViewById<RadioButton>(R.id.option3)

        dialogView.post {
            val parent = dialogView.parent as View
            val layoutParams = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior

            if (behavior is BottomSheetBehavior<*>) {
                behavior.peekHeight = dialogView.measuredHeight
            }
        }

        option1.setOnClickListener {
            ptgiaohang()
        }

        option2.setOnClickListener {
            val currentDate5 = Calendar.getInstance()
            val currentDate6 = Calendar.getInstance()
            currentDate5.add(Calendar.DAY_OF_MONTH, 5) // Thêm 3 ngày
            currentDate6.add(Calendar.DAY_OF_MONTH, 6) // Thêm 4 ngày
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val resultDate5 = dateFormat.format(currentDate5.time)
            val resultDate6 = dateFormat.format(currentDate6.time)

            binding.ptvanchuyen.text = "Bình thường"
            totalMonyship = 20000.0
            ngaynhanhang =  "Nhận hàng vào : " + resultDate5 + " - " + resultDate6
            binding.sumtienship.text = formatPrice(totalMonyship)
            binding.moneysship.text = formatPrice(totalMonyship)
            binding.date.text = ngaynhanhang
            updateTotalSum()

        }

        option3.setOnClickListener {
            val currentDate7 = Calendar.getInstance()
            currentDate7.add(Calendar.DAY_OF_MONTH, 7) // Thêm 3 ngày
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val resultDate7 = dateFormat.format(currentDate7.time)

            binding.ptvanchuyen.text = "Chậm"
            totalMonyship = 10000.0
            ngaynhanhang = "Nhận hàng trên : " + resultDate7
            binding.sumtienship.text = formatPrice(totalMonyship)
            binding.moneysship.text = formatPrice(totalMonyship)
            binding.date.text = ngaynhanhang
            updateTotalSum()

        }

        dialog.show()
    }

    private fun ptthanhtoan() {
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_ptthanhtoan, null)
        dialog.setContentView(dialogView)

        val option1 = dialogView.findViewById<TextView>(R.id.ptttoff)


        dialogView.post {
            val parent = dialogView.parent as View
            val layoutParams = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior

            if (behavior is BottomSheetBehavior<*>) {
                behavior.peekHeight = dialogView.measuredHeight
            }
        }

        option1.setOnClickListener {
            ptship()
        }

        dialog.show()
    }
    private fun ptship(){
        ptship = "Thanh toán khi nhận hàng"
        binding.ptthanhtoanshow.text = ptship
    }

    private fun updateTotalSum() {
        val sumtienship =
            binding.sumtienship.text.toString().replace("[^0-9]".toRegex(), "").toDoubleOrNull()
                ?: 0.0
        val sumtienhang =
            binding.sumtienhang.text.toString().replace("[^0-9]".toRegex(), "").toDoubleOrNull()
                ?: 0.0
        val sumtiengiamgia =
            binding.sumtiengiamgia.text.toString().replace("[^0-9]".toRegex(), "").toDoubleOrNull()
                ?: 0.0

        totalSum = sumtienship + sumtienhang + sumtiengiamgia
        binding.sumtienthanhtoan.text = formatPrice(totalSum)
        binding.sumtienthanhtoan1.text = formatPrice(totalSum)
    }

    private fun ptgiaohang() {
        val currentDate3 = Calendar.getInstance()
        val currentDate4 = Calendar.getInstance()
        currentDate3.add(Calendar.DAY_OF_MONTH, 3) // Thêm 3 ngày
        currentDate4.add(Calendar.DAY_OF_MONTH, 4) // Thêm 4 ngày
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val resultDate3 = dateFormat.format(currentDate3.time)
        val resultDate4 = dateFormat.format(currentDate4.time)

        binding.ptvanchuyen.text = "Nhanh"
        totalMonyship = 30000.0
        ngaynhanhang = "Nhận hàng vào: " + resultDate3 + " - " + resultDate4
        binding.moneysship.text = formatPrice(totalMonyship)
        binding.sumtienship.text = formatPrice(totalMonyship)
        binding.date.text = ngaynhanhang
        updateTotalSum()  // gọi tới để cập nhập tổng tiền shop cho tổng tiền thanh toán
    }
    private fun hamadd(
        quantitybill: Int,
        sumpricebill: Double,
        ptthanhtoan: String,
        phiship: Double,
        datedathang: String,
        datenhanhang: String,
        TTXacNhan: String,
        TTLayhang: String,
        TTGiaoHang: String,
        TTHuy: String,
        TTDaGiao: String,
        TTVote: String,
        _idUser: Int,
        _idProduct: Int,
        _idAddRess: Int,
        _idShop: Int,
        username : String

    ) {
        val newbill = BillModel(
            0,
            quantitybill,
            sumpricebill,
            ptthanhtoan,
            phiship,
            datedathang,
            datenhanhang,
            TTXacNhan,
            TTLayhang,
            TTGiaoHang,
            TTHuy,
            TTDaGiao,
            TTVote,
            _idUser,
            _idProduct,
            _idAddRess,
            _idShop,
            username
        )
        val typeId = billDAO.addBill(newbill)

        if (typeId > -1) {
            Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show()
        }
    }

}