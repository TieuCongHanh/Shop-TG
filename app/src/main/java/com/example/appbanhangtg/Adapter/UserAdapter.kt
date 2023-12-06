package com.example.appbanhangtg.Adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.bumptech.glide.request.target.Target

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
            val baseUrl = "https://"

            Glide.with(itemView.context)
                .load(currentUser.image) // 'image' là URI hoặc đường dẫn ảnh
                .placeholder(R.drawable.icon_person) // Hình ảnh placeholder khi đang tải
                .error(R.drawable.icon_bill) // Hình ảnh hiển thị khi có lỗi
                .transform(RoundedCorners(radiusInPixels.toInt()))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        e?.logRootCauses("Glide") // Ghi lại nguyên nhân gốc của lỗi
                        return false
                    }


                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(avt) // 'imageView' là ImageView trong layout của adapter


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
