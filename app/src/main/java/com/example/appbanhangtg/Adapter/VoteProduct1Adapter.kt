package com.example.appbanhangtg.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Model.VoteProductModel
import com.example.appbanhangtg.R

class VoteProduct1Adapter(
    private val list: List<VoteProductModel>,
    private val userDao: UserDAO,
    private val clickRecyclerView: (VoteProductModel) -> Unit
) :
    RecyclerView.Adapter<VoteProduct1Adapter.VoteProductHolder>() {

    inner class VoteProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.txtcontent_voteShop)
        val date: TextView = itemView.findViewById(R.id.txtdate_voteShop)
        val name: TextView = itemView.findViewById(R.id.txtusername_voteShop)
        val itemavtuser: ImageView = itemView.findViewById(R.id.itemavtuser_voteShop)
        val star1: ImageView = itemView.findViewById(R.id.imgstar_voteShop)
        val star2: ImageView = itemView.findViewById(R.id.imgstar1_voteShop)
        val star3: ImageView = itemView.findViewById(R.id.imgstar2_voteShop)
        val star4: ImageView = itemView.findViewById(R.id.imgstar3_voteShop)
        val star5: ImageView = itemView.findViewById(R.id.imgstar4_voteShop)

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
    ): VoteProduct1Adapter.VoteProductHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vote_shop, parent, false)
        return VoteProductHolder(view)
    }

    override fun onBindViewHolder(holder: VoteProduct1Adapter.VoteProductHolder, position: Int) {

        holder.apply {
            content.text = list[position].content
            date.text = list[position].date

            val userId = list[position]._idUser // Giả sử userId được lưu trong VoteShopModel

            val userInfo = userDao.getUserInfoByUserId(userId)
            val username = userInfo.first ?: "" // Nếu username null, sẽ hiển thị chuỗi rỗng
            val userImageUrl = userInfo.second ?: ""

            name.text = username
            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(itemView.context)
                .load(userImageUrl)
                .apply(requestOptions)
                .into(itemavtuser)

            if (list[position].numberofstart == "1"){
                star2.visibility = View.GONE
                star3.visibility = View.GONE
                star4.visibility = View.GONE
                star5.visibility = View.GONE
            }else if (list[position].numberofstart == "2"){
                star3.visibility = View.GONE
                star4.visibility = View.GONE
                star5.visibility = View.GONE
            }else if (list[position].numberofstart == "3"){
                star4.visibility = View.GONE
                star5.visibility = View.GONE
            }
            else if (list[position].numberofstart == "4"){
                star5.visibility = View.GONE
            }
        }

    }


    override fun getItemCount(): Int {
        return 5
    }
}