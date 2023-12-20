package com.example.appbanhangtg.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.R
import java.text.DecimalFormat

class ProductAdapter (
    private val context: Context,
    private val list: List<ProductModel>,
    private val clickRecyclerView: (ProductModel) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    private val billDAO: BillDAO by lazy { BillDAO(context) }
    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avt: ImageView = itemView.findViewById(R.id.itemimgavt_product)
        val name: TextView = itemView.findViewById(R.id.itemtxtname_product)
        val price: TextView = itemView.findViewById(R.id.itemtxtprice_product)
        val slban: TextView = itemView.findViewById(R.id.itemtxtsl_product)
        val hethang: TextView = itemView.findViewById(R.id.hethang)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductHolder, position: Int) {
        val currentProduct = list[position]


        // Gán dữ liệu vào các view trong ViewHolder
        holder.apply {
            val nameProductLimited = currentProduct.nameProduct.limitTo(30)
            name.text = nameProductLimited
            price.text = formatPrice(currentProduct.priceProduct)

            // Load ảnh
            val radiusInPixels = itemView.context.resources.displayMetrics.density * 10 // Chuyển đổi dp sang pixel
            Glide.with(itemView.context)
                .load(currentProduct.imageProduct)
                .placeholder(R.drawable.icon_person) // Placeholder image while loading
                .transform(RoundedCorners(radiusInPixels.toInt()))
                .into(avt)
            // hiển thị soos lượng bán
            hethang.visibility = View.GONE
            if (currentProduct.quantityProduct <=0){
                hethang.visibility = View.VISIBLE
            }

            val totalQuantitySold = billDAO.getTotalQuantityByProductId(currentProduct._idProduct)
            slban.text = "Đã bán $totalQuantitySold"
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
    private fun formatPrice(price: Double) : String {
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