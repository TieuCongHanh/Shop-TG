package com.example.appbanhangtg.Adapter

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.DAO.VoteProductDAO
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.R
import java.lang.Integer.min
import java.text.DecimalFormat

class TopSellingAdapter(
    private val context: Context,
    private val list: List<ProductModel>,
    private val clickItem: (ProductModel) -> Unit
) : RecyclerView.Adapter<TopSellingAdapter.ProductHolder>() {

    private val voteProductDAO: VoteProductDAO by lazy { VoteProductDAO(context) }
    private val billDAO:BillDAO by lazy { BillDAO(context) }

    inner class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val avt: ImageView = view.findViewById(R.id.itemimgavt_product)
        private val name: TextView = view.findViewById(R.id.itemtxtname_product)
        private val tien: TextView = view.findViewById(R.id.item_tien)
        private val tien1: TextView = view.findViewById(R.id.item_tien1)
        private val daban: TextView = view.findViewById(R.id.item_daban)
        private val danhgia: TextView = view.findViewById(R.id.item_danhgia)
        // Thêm các TextView khác nếu cần

        fun bind(product: ProductModel) {
            Glide.with(context)
                .load(product.imageProduct)
                .transform(RoundedCorners(10))
                .into(avt)

            name.text = product.nameProduct.limitTo(30)
            tien1.text = formatPrice(product.priceProduct)
            val tientang10 =product.priceProduct + product.priceProduct * 10/100
            tien.text = applyStrikethroughSpan(formatPrice(tientang10))

            // hiển thị trung bình cộng đánh giá sản phẩm
            val averageRating = product._idProduct?.let { voteProductDAO.calculateAverageRatingByShopId(it) }
            danhgia.text = "$averageRating"

            // hiển thị soos lượng bán được
            val totalQuantitySold = billDAO.getTotalQuantityByProductId(product._idProduct)
            daban.text = "Đã bán $totalQuantitySold"




            itemView.setOnClickListener {
                clickItem(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_topselling, parent, false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = min(list.size, 5) // Giới hạn số lượng item là 5 hoặc ít hơn

    private fun String.limitTo(length: Int): String {
        return if (this.length > length) this.substring(0, length) + "..." else this
    }
    private fun formatPrice(price: Double): String {
        val formatter = DecimalFormat("#,### VNĐ")
        return formatter.format(price)
    }
    fun applyStrikethroughSpan(text: String): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(StrikethroughSpan(), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}
