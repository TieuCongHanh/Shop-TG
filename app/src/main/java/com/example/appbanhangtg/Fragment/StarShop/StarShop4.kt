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
import com.example.appbanhangtg.databinding.FragmentStarShop3Binding
import com.example.appbanhangtg.databinding.FragmentStarShop4Binding

private lateinit var binding: FragmentStarShop4Binding

class StarShop4 : Fragment() {
    private var shopModel: ShopModel? = null
    private lateinit var voteshopDAO: VoteShopDAO
    private lateinit var userDAO: UserDAO
    override fun onResume() {
        super.onResume()
        list()
    }
    companion object {
        fun newInstance(shopWrapper: ShopWrapper): StarShop4 {
            val fragment = StarShop4()
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
        binding = FragmentStarShop4Binding.inflate(inflater, container, false)
        list()

        return binding.root
    }

    private fun displayStarList(votes: List<VoteShopModel>) {
        val recyclerView = binding.recyclerview4

        recyclerView.layoutManager = GridLayoutManager(
            context,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val voteShopAdapter = VoteShopAdapter(votes, userDAO) { clickedVoteShop ->
            Toast.makeText(context, "${clickedVoteShop._idVoteShop}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = voteShopAdapter
    }
    private fun list(){
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel
        val shopId = shopModel?._idShop

        voteshopDAO = VoteShopDAO(requireContext())
        userDAO = UserDAO(requireContext())

        // Lấy danh sách vote theo ID của cửa hàng
        val voteShopList = shopId?.let {
            voteshopDAO.getByVote4ShopId(it)
        }
        val voteCount = shopId?.let {
            voteshopDAO.getStarCoun4tById(it)
        }

        voteShopList?.let { displayStarList(it) }
        voteCount?.let {
            binding.txttotu.text = "Tổng đánh giá của 5 sao là : $it"
        }
    }

}