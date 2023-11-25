package com.example.appbanhangtg.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbanhangtg.Activity.AddOrUpdate_User
import com.example.appbanhangtg.Adapter.UserAdapter
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentQLUserBinding


private lateinit var binding: FragmentQLUserBinding
class QLUser : Fragment() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: MutableList<UserModel>
    private val userDAO: UserDAO by lazy { UserDAO(requireContext()) }
    companion object {
        const val ADD_OR_UPDATE_REQUEST = 1
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_OR_UPDATE_REQUEST) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // Thực hiện cập nhật dữ liệu ở đây
                loadUsers()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQLUserBinding.inflate(inflater,container,false)
        userList = mutableListOf()
        userAdapter = UserAdapter(userList) { clickedUser ->
            // Xử lý sự kiện click trên RecyclerView ở đây
            val intent = Intent(context, AddOrUpdate_User::class.java)
            intent.putExtra("_idUser", clickedUser._idUser)
            startActivityForResult(intent, ADD_OR_UPDATE_REQUEST)
        }
        binding.recyclerview.adapter = userAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        loadUsers()

        binding.adduser.setOnClickListener {
            val intent = Intent(context, AddOrUpdate_User::class.java)
            intent.putExtra("_idUser", 0)
            startActivityForResult(intent, ADD_OR_UPDATE_REQUEST)
        }




        return binding.root
    }

    private fun loadUsers() {
        userList.clear()
        userList.addAll(userDAO.getAllUsers())
        userAdapter.notifyDataSetChanged()
    }


}