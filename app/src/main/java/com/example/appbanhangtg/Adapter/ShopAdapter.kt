package com.example.appbanhangtg.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ItemShopBinding
import com.example.appbanhangtg.databinding.ItemUserBinding


class ShopAdapter(private val list: List<ShopModel>, private val clickRecyclerView: (ShopModel) -> Unit) :
    RecyclerView.Adapter<ShopAdapter.ShopHolder>() {


    inner class ShopHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val avt: ImageView = itemView.findViewById(R.id.itemimgavt_shop)
        val nameShop: TextView = itemView.findViewById(R.id.itemtxtname_shop)
        val descriptionShop: TextView = itemView.findViewById(R.id.itemtxtdesc_shop)
        val sloganShop: TextView = itemView.findViewById(R.id.itemtxtslogans_shop)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.ShopHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return ShopHolder(view)
    }

    override fun onBindViewHolder(holder: ShopAdapter.ShopHolder, position: Int) {
        holder.apply {
            // Load ảnh
            val requestOptions = RequestOptions().transform(CircleCrop())
            Glide.with(itemView.context)
                .load(list[position].imageShop)
                .apply(requestOptions)
                .placeholder(R.drawable.icon_person) // Placeholder image while loading
                .into(avt)
            nameShop.text = list[position].nameShop
            descriptionShop.text = list[position].descriptionShop
            sloganShop.text = list[position].sloganShop


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}