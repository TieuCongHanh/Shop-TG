package com.example.appbanhangtg.Fragment.StarShop

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Activity.ProductDetail
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.Adapter.VoteShopAdapter
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Fragment.Product_Shop
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentStarShopBinding

private lateinit var binding: FragmentStarShopBinding

class StarShop : Fragment() {

    private var shopModel: ShopModel? = null
    private lateinit var voteshopDAO: VoteShopDAO
    private lateinit var userDao: UserDAO
    override fun onResume() {
        super.onResume()
        listStar1()
    }
    companion object {
        fun newInstance(shopWrapper: ShopWrapper): StarShop {
            val fragment = StarShop()
            val args = Bundle()
            args.putSerializable("SHOP_EXTRA", shopWrapper)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStarShopBinding.inflate(inflater, container, false)

        listStar1()

        return binding.root
    }

    private fun displayStarList(votes: List<VoteShopModel>) {
        val recyclerView = binding.recyclerview

        recyclerView.layoutManager = GridLayoutManager(
            context,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val reversedVotes = votes.reversed() // Đảo ngược danh sách

        val voteShopAdapter = VoteShopAdapter(reversedVotes, userDao) { clickedVoteShop ->
            Toast.makeText(context, "${clickedVoteShop._idVoteShop}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = voteShopAdapter
    }

    private fun listStar1() {
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel
        val shopId = shopModel?._idShop

        voteshopDAO = VoteShopDAO(requireContext())
        userDao = UserDAO(requireContext())

        // Lấy danh sách vote theo ID của cửa hàng
        val voteShopList = shopId?.let {
            voteshopDAO.getByVoteShopId(it)
        }
        val voteCount = shopId?.let {
            voteshopDAO.getStarCountById(it)
        }

        voteShopList?.let { displayStarList(it) }
        voteCount?.let {
            binding.txttotu.text = "Tổng đánh giá của 1 sao là : $it"
        }
    }

}