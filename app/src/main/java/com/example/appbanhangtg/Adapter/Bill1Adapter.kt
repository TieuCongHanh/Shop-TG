package com.example.appbanhangtg.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.Interface.OnDataChangedListener
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.R
import java.text.DecimalFormat

class Bill1Adapter(
    private val context: Context,
    private val list: List<BillModel>,
    private val billDAO: BillDAO,
    private val clickRecyclerView: (BillModel) -> Unit
) :
    RecyclerView.Adapter<Bill1Adapter.Bill1Holder>() {

    inner class Bill1Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Bill1Adapter.Bill1Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bill, parent, false)
        return Bill1Holder(view)
    }

    override fun onBindViewHolder(holder: Bill1Adapter.Bill1Holder, position: Int) {
        val billId = list[position]._idBill
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
            sumpricebill.text = formatPrice(list[position].sumpricebill)

            if (list[position].TTXacNhan == "false") {
                trangthaibill.text = "Chờ xác nhận"
                voteproductbill.setText("Xác nhận")
                voteproductbill.setOnClickListener {
                    val bill = list[position]
                    showConfirmationDialog(bill, holder.itemView.context, position)
                }
            } else if (list[position].TTLayhang == "false") {
                trangthaibill.text = "Chờ lấy hàng"
                voteproductbill.setText("Lấy hàng")
                voteproductbill.setOnClickListener {
                    val bill = list[position]
                    showConfirmationDialog(bill, holder.itemView.context, position)
                }
            }else if (list[position].TTGiaoHang == "false") {
                trangthaibill.text = "Chưa giao hàng"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            }
            else if (list[position].TTGiaoHang == "true") {
                trangthaibill.text = "Đang giao hàng"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            }
            else if (list[position].TTDaGiao == "true") {
                trangthaibill.text = "Hoàn thành"
                show1.visibility = View.GONE
                linerxacnhan.visibility = View.GONE
            } else if (list[position].TTHuy == "true") {
                trangthaibill.text = "Đã hủy"
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
    private fun showConfirmationDialog(bill: BillModel, context: Context, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Xác Nhận")
        builder.setMessage("Bạn có muốn xác nhận hành động này không?")

        builder.setPositiveButton("Có") { dialog, which ->
            if (list[position].TTXacNhan == "false"){
                val ttxacnhan = "true"
                billDAO.updateTTXacNhan(bill._idBill, ttxacnhan)

                Toast.makeText(context, "Xác nhận thành công", Toast.LENGTH_SHORT).show()
            }else  if (list[position].TTLayhang == "false" && list[position].TTXacNhan == "true"){
                val ttlayhang = "true"
                billDAO.updateTTLayHang(bill._idBill, ttlayhang)

                Toast.makeText(context, "Xác nhận lấy hàng thành công", Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("Không") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}