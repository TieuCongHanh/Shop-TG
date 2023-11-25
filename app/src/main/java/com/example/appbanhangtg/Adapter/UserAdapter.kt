package com.example.appbanhangtg.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.appbanhangtg.Interface.ClickRecyclerView
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ItemUserBinding

private lateinit var binding: ItemUserBinding

class UserAdapter(private val list: List<UserModel>, private val clickRecyclerView: (UserModel) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avt: ImageView = itemView.findViewById(R.id.itemimgavt_user)
        val name: TextView = itemView.findViewById(R.id.itemtxtusername_user)
        val phone: TextView = itemView.findViewById(R.id.itemtxtphone_user)
        val email: TextView = itemView.findViewById(R.id.itemtxtemail_user)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserHolder, position: Int) {
        val currentUser = list[position]

        // Gán dữ liệu vào các view trong ViewHolder
        holder.apply {
            name.text = "${currentUser.username} ${currentUser._idUser}"
            phone.text = currentUser.phone
            email.text = currentUser.email

            // Load ảnh
            val radiusInPixels = itemView.context.resources.displayMetrics.density * 100 // Chuyển đổi dp sang pixel
            Glide.with(itemView.context)
                .load(currentUser.image)
                .placeholder(R.drawable.icon_person) // Placeholder image while loading
                .transform(RoundedCorners(radiusInPixels.toInt()))
                .into(avt)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
