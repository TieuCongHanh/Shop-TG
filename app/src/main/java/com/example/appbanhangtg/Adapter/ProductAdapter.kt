package com.example.appbanhangtg.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ItemShopBinding

class ProductAdapter (private val list: List<ProductModel>, private val clickRecyclerView: (ProductModel) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avt: ImageView = itemView.findViewById(R.id.itemimgavt_product)
        val name: TextView = itemView.findViewById(R.id.itemtxtname_product)
        val price: TextView = itemView.findViewById(R.id.itemtxtprice_product)
        val slban: TextView = itemView.findViewById(R.id.itemtxtsl_product)
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
            name.text = currentProduct.nameProduct
            price.text = currentProduct.priceProduct + " VNĐ"

            // Load ảnh
            val radiusInPixels = itemView.context.resources.displayMetrics.density * 10 // Chuyển đổi dp sang pixel
            Glide.with(itemView.context)
                .load(currentProduct.imageProduct)
                .placeholder(R.drawable.icon_person) // Placeholder image while loading
                .transform(RoundedCorners(radiusInPixels.toInt()))
                .into(avt)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}