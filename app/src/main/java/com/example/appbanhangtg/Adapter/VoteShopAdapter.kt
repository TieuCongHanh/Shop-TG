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
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.R

class VoteShopAdapter (private val list: List<VoteShopModel>, private val clickRecyclerView: (VoteShopModel) -> Unit) :
    RecyclerView.Adapter<VoteShopAdapter.VoteShopHolder>() {

    inner class VoteShopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.txtcontent_voteShop)
        val date: TextView = itemView.findViewById(R.id.txtdate_voteShop)

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VoteShopAdapter.VoteShopHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vote_shop, parent, false)
        return VoteShopHolder(view)
    }

    override fun onBindViewHolder(holder: VoteShopAdapter.VoteShopHolder, position: Int) {

        holder.apply {
            content.text = list[position].content
            date.text = list[position].date

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}