package com.example.appbanhangtg.Fragment.StarShop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Adapter.VoteShopAdapter
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentStarShop1Binding
import com.example.appbanhangtg.databinding.FragmentStarShopBinding

private lateinit var binding: FragmentStarShop1Binding

class StarShop1 : Fragment() {
    private var shopModel: ShopModel? = null
    private lateinit var voteshopDAO: VoteShopDAO
    private lateinit var userDAO: UserDAO

    companion object {
        fun newInstance(shopWrapper: ShopWrapper): StarShop1 {
            val fragment = StarShop1()
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
        binding = FragmentStarShop1Binding.inflate(inflater, container, false)
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel
        val shopId = shopModel?._idShop

        voteshopDAO = VoteShopDAO(requireContext())
        userDAO = UserDAO(requireContext())

        // Lấy danh sách vote theo ID của cửa hàng
        val voteShopList = shopId?.let {
            voteshopDAO.getByVote1ShopId(it)
        }
        val voteCount = shopId?.let {
            voteshopDAO.getStarCoun1tById(it)
        }

        voteShopList?.let { displayStarList(it) }
        voteCount?.let {
            binding.txttotu.text = "Tổng đánh giá của 2 sao là : $it"
        }

        return binding.root
    }

    private fun displayStarList(votes: List<VoteShopModel>) {
        val recyclerView = binding.recyclerview1

        recyclerView.layoutManager = GridLayoutManager(
            context,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val voteShopAdapter = VoteShopAdapter(votes,userDAO) { clickedVoteShop ->
            Toast.makeText(context, "${clickedVoteShop._idVoteShop}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = voteShopAdapter
    }

}