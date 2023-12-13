package com.example.appbanhangtg.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.Model.CartModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.R
import java.text.DecimalFormat

class BillAdapter(
    private val context: Context,
    private val list: List<BillModel>,
    private val billDAO: BillDAO,
    private val clickRecyclerView: (BillModel) -> Unit
) :
    RecyclerView.Adapter<BillAdapter.BillHolder>() {

    inner class BillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameproductbill: TextView = itemView.findViewById(R.id.nameproductbill)
        val nameshopbill: TextView = itemView.findViewById(R.id.nameshopbill)
        val priceproductbill: TextView = itemView.findViewById(R.id.priceproductbill)
        val quantityproductbill: TextView = itemView.findViewById(R.id.quantityproductbill)
        val trangthaibill: TextView = itemView.findViewById(R.id.trangthaibill)
        val sumproduct: TextView = itemView.findViewById(R.id.sumproduct)
        val sumpricebill: TextView = itemView.findViewById(R.id.sumpricebill)
        val giaohang: TextView = itemView.findViewById(R.id.giaohang)
        val voteproductbill: TextView = itemView.findViewById(R.id.voteproductbill)
        val show1: TextView = itemView.findViewById(R.id.showborder)
        val linerxacnhan: LinearLayout = itemView.findViewById(R.id.linerxacnhan)
        val imgproductbill: ImageView = itemView.findViewById(R.id.imgproductbill)


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedUser = list[position]
                    clickRecyclerView.invoke(clickedUser)
                }
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAdapter.BillHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bill, parent, false)
        return BillHolder(view)
    }

    override fun onBindViewHolder(holder: BillAdapter.BillHolder, position: Int) {
        holder.apply {
            val productId = list[position]._idProduct

            val productInfo = billDAO.getProductInfoByBillId(productId)
            val nameproduct = productInfo.first ?: "" // vị trí 1
            val priceProduct = productInfo.second ?: 0.0  // vị trí 2
            val productImageUrl = productInfo.third ?: "" // vị trí 3

            val nameProductLimited = nameproduct.limitTo(100)
            nameproductbill.text = nameProductLimited


            priceproductbill.text = formatPrice(priceProduct)
            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(itemView.context)
                .load(productImageUrl)
                .apply(requestOptions)
                .into(imgproductbill)

            val shopDAO = ShopDAO(context)
            val shopList = shopDAO.getByProductIdShop(list[position]._idShop)


            // Hiển thị tên cửa hàng của sản phẩm hiện tại
            if (shopList.isNotEmpty()) {
                val currentShopName = shopList[0].nameShop
                nameshopbill.text = currentShopName


            }

            // thông tin riêng bill
            val soluongsp = list[position].quantitybill ?: 0
            val phoship = list[position].phiship ?: 0.0
            quantityproductbill.text = soluongsp.toString()

            sumproduct.text = soluongsp.toString()
            sumpricebill.text = (soluongsp * priceProduct + phoship).toString()

            if (list[position].TTXacNhan == "false") {
                trangthaibill.text = "Chờ xác nhận"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            } else if (list[position].TTLayhang == "false") {
                trangthaibill.text = "Chờ lấy hàng"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            } else if (list[position].TTGiaoHang == "false") {
                trangthaibill.text = "Chưa giao hàng"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            } else if (list[position].TTGiaoHang == "true") {
                trangthaibill.text = "Đang giao hàng"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            } else if (list[position].TTHuy == "true") {
                trangthaibill.text = "Đã hủy"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            } else if (list[position].TTDaGiao == "true") {
                trangthaibill.text = "Hoàn thành"
            } else if (list[position].TTVote == "false") {
                trangthaibill.text = "Chưa đánh giá"
            }
            else if (list[position].TTVote == "true") {
                trangthaibill.text = "Đã đánh giá"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            }

        }
    }


    override fun getItemCount(): Int {
        return list.size
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