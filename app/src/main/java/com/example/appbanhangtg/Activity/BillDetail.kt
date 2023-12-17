package com.example.appbanhangtg.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.AddRessDAO
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.VoteProductDAO
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityBillDetailBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

private lateinit var binding: ActivityBillDetailBinding

class BillDetail : AppCompatActivity() {

    private val shopDAO: ShopDAO by lazy { ShopDAO(this) }
    private val billDAO: BillDAO by lazy { BillDAO(this) }
    private val productDAO: ProductDAO by lazy { ProductDAO(this) }
    private val addRessDAO: AddRessDAO by lazy { AddRessDAO(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgbackaddProductshop.setOnClickListener { finish() }

        val billmodel = intent.getSerializableExtra("BILL_EXTRA") as? BillModel
        billmodel?.let {
            val phiship = billmodel?.phiship?.toDouble() ?: 0.0

            binding.phiship.text = formatPrice(phiship)
            if (phiship > 20000.0) {
                binding.nhanhorcham.text = "Nhanh"
            }else if (phiship > 10000.0){
                binding.nhanhorcham.text = "Bình thường"
            }else{
                binding.nhanhorcham.text = "Chậm"
            }
            binding.mavanchuyen.text = "SPX Express - SPXVN" + billmodel?._idBill

            val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())

            if (billmodel?.TTXacNhan == "false" && billmodel?.TTHuy =="false") {
                binding.TTdonhang.text = "Đơn hàng đang chờ xác nhận"
                binding.datedonhang.text = billmodel?.datedathang
            }else if (billmodel?.TTXacNhan == "true" && billmodel?.TTLayhang == "false" && billmodel?.TTHuy == "false"){
                binding.TTdonhang.text = "Đơn hàng đang chờ Lấy hàng"
                binding.datedonhang.text = currentDate
            }else if (billmodel?.TTXacNhan == "true" && billmodel?.TTLayhang == "true"
                && billmodel?.TTGiaoHang == "false" && billmodel?.TTHuy == "false"){
                binding.TTdonhang.text = "Đơn hàng đang chờ xác nhận giao hàng"
                binding.datedonhang.text = currentDate
            }
            else if (billmodel?.TTXacNhan == "true" && billmodel?.TTLayhang == "true"
                && billmodel?.TTGiaoHang == "true" && billmodel?.TTDaGiao=="false" && billmodel?.TTHuy == "false"){
                binding.TTdonhang.text = "Đơn hàng đang giao hàng"
                binding.datedonhang.text = currentDate
            }else if (billmodel?.TTXacNhan == "true" && billmodel?.TTLayhang == "true"
                && billmodel?.TTGiaoHang == "true" && billmodel?.TTDaGiao=="true" && billmodel?.TTHuy == "false"){
                binding.TTdonhang.text = "Đơn hàng Đã giao hàng thành công"
                binding.datedonhang.text = billmodel?.datenhanhang
            }

            // địa chỉ nhận hàng
            val addressId = it?._idAddRess
            val addresslit = addressId?.let { addRessDAO.getAddressById(addressId) }
            binding.fulllname.text = addresslit?.fullname
            binding.phone.text = addresslit?.phone
            binding.address.text = addresslit?.address

            // shop
            val shopId = it?._idShop
            val shoplist = shopId?.let { shopDAO.getByProductIdShop(shopId) }
            binding.nameshopbill.text = shoplist?.get(0)!!.nameShop.limitTo(100)

            // sản phẩm
            val productId = it?._idProduct
            val productlist = productId?.let { productDAO.getProductById(productId) }
            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(binding.root.context)
                .load(productlist?.imageProduct)
                .apply(requestOptions)
                .into(binding.imgproductbill)
            binding.nameproductbill.text = productlist?.nameProduct?.limitTo(100)
            binding.quantityproductbill.text = billmodel?.quantitybill.toString()
            binding.priceproductbill.text = formatPrice(productlist?.priceProduct?.toDouble() ?:0.0)
            binding.sumproduct.text =  billmodel?.quantitybill.toString()
            binding.sumpricebill.text =  formatPrice(billmodel?.sumpricebill?.toDouble() ?: 0.0)

            // phương thức thanh toán
            binding.ptthanhtoan.text = billmodel?.ptthanhtoan

            // bill
            binding.mabill.text = "MDH" + billmodel?._idBill +"TG"
            binding.datedathang.text = billmodel?.datedathang
            binding.datethanhtoan.text = billmodel?.datenhanhang
            if (currentDate <= billmodel?.datenhanhang.toString()) {
                binding.dategiaohang.text = currentDate
            }else if (currentDate >= billmodel?.datenhanhang.toString()){
                binding.datedathang.text == ""
            }
            binding.datehoanthanh.text = billmodel?.datenhanhang
        }
    }

    private fun formatPrice(price: Double): String {
        val formatter = DecimalFormat("#,### VNĐ")
        return formatter.format(price)
    }

    fun String.limitTo(length: Int): String {
        return if (this.length > length) {
            "${this.substring(0, length)}..."
        } else {
            this
        }
    }
}