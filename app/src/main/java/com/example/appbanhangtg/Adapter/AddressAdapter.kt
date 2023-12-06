package com.example.appbanhangtg.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.appbanhangtg.Model.AddressModel
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.R

class AddressAdapter  (private val list: List<AddressModel>, private val clickRecyclerView: (AddressModel) -> Unit) :
    RecyclerView.Adapter<AddressAdapter.AddressHolder>() {

    inner class AddressHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val radioButton:RadioButton = itemView.findViewById(R.id.radioButton)
        val name: TextView = itemView.findViewById(R.id.txtname_address)
        val phone: TextView = itemView.findViewById(R.id.txtphone_address)
        val address: TextView = itemView.findViewById(R.id.address)
        val note: TextView = itemView.findViewById(R.id.note)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.AddressHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return AddressHolder(view)
    }

    override fun onBindViewHolder(holder: AddressAdapter.AddressHolder, position: Int) {
        val currentAddress = list[position]

        // Gán dữ liệu vào các view trong ViewHolder
        holder.apply {

            name.text = currentAddress.fullname
            phone.text = currentAddress.phone
            address.text = currentAddress.address
            note.text = currentAddress.note


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}