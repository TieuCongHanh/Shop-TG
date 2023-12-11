package com.example.appbanhangtg.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.Model.BillModel
import com.example.appbanhangtg.Model.CartModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.R
import java.text.DecimalFormat


class CartAdapter(
    private val context: Context,
    private val list: List<CartModel>,
    private val cartDAO: CartDAO,
    private val clickRecyclerView: (CartModel) -> Unit
) :
RecyclerView.Adapter<CartAdapter.BillHolder>() {

    inner class BillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameproductcart : TextView = itemView.findViewById(R.id.nameproductcart)
        val nameshoptcart : TextView = itemView.findViewById(R.id.nameshopcart)
        val priceproductcart : TextView = itemView.findViewById(R.id.priceproductcart)
        val txtquantitycart : TextView = itemView.findViewById(R.id.txtquantitycart)
        val checkboxshop: CheckBox = itemView.findViewById(R.id.boxshopcart)
        val checkboxproduct: CheckBox = itemView.findViewById(R.id.boxproductcart)
        val imgproductcart: ImageView = itemView.findViewById(R.id.imgproductcart)
        val imgtrucart: ImageView = itemView.findViewById(R.id.imgtrucart)
        val imgcongcart: ImageView = itemView.findViewById(R.id.imgcongcart)
        val showname : LinearLayout = itemView.findViewById(R.id.shownameshop)
        val showcah : TextView = itemView.findViewById(R.id.showcach)


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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.BillHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return BillHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.BillHolder, position: Int) {
        holder.apply {
            val productId = list[position]._idProduct

            val productInfo = cartDAO.getProductInfoByCartId(productId)
            val nameproduct = productInfo.first ?: "" // vị trí 1
            val priceProduct = productInfo.second ?: "" // vị trí 2
            val productImageUrl = productInfo.third ?: "" // vị trí 3

            val nameProductLimited = nameproduct.limitTo(100)
            nameproductcart.text = nameProductLimited

            priceproductcart.text = formatPrice(priceProduct as Double)
            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(itemView.context)
                .load(productImageUrl)
                .apply(requestOptions)
                .into(imgproductcart)

            val shopDAO = ShopDAO(context)
            val shopList = shopDAO.getByProductIdShop(productId)

            // Hiển thị tên cửa hàng chỉ một lần
            if (shopList.isNotEmpty()) {
                val currentShopName = shopList[0].nameShop // Lấy tên cửa hàng của sản phẩm hiện tại
                var previousShopName = ""

                // Tìm sản phẩm trước đó để so sánh _idShop
                if (position > 0) {
                    val previousProductId = list[position - 1]._idProduct
                    val previousShopList = shopDAO.getByProductIdShop(previousProductId)
                    if (previousShopList.isNotEmpty()) {
                        previousShopName = previousShopList[0].nameShop ?: ""
                    }
                }

                // Nếu tên cửa hàng của sản phẩm hiện tại khác với sản phẩm trước đó thì hiển thị tên cửa hàng mới
                if (currentShopName != previousShopName) {
                    nameshoptcart.text = currentShopName
                } else {
                    showname.visibility = View.GONE // Ẩn TextView nếu cùng cửa hàng với sản phẩm trước đó
                    showcah.visibility = View.GONE // Ẩn TextView nếu cùng cửa hàng với sản phẩm trước đó
                }
            }
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