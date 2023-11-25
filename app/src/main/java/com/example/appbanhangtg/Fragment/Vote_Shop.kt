package com.example.appbanhangtg.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.Adapter.VoteShopAdapter
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentVoteShopBinding

private lateinit var binding: FragmentVoteShopBinding
class Vote_Shop : Fragment() {

    private var shopModel: ShopModel? = null
    private lateinit var voteshopDAO: VoteShopDAO

    companion object {
        fun newInstance(shopWrapper: ShopWrapper): Vote_Shop {
            val fragment = Vote_Shop()
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
        binding = FragmentVoteShopBinding.inflate(inflater,container,false)
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel

        val shopId = shopModel?._idShop
        voteshopDAO = VoteShopDAO(requireContext())
        // Lấy danh sách sản phẩm theo ID của cửa hàng
        val voteShopList = shopId?.let {
            voteshopDAO.getByVoteShopId(it)
        }

        voteShopList?.let { displayVoteShopList(it) }

        // Sử dụng shopModel ở đây để làm điều gì đó trong Fragment
        return binding.root
    }

    private fun displayVoteShopList(Votes: List<VoteShopModel>) {
        val recyclerView = binding.recyclerviewabc

        recyclerView.layoutManager = GridLayoutManager(
            context,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val voteShopAdapter = VoteShopAdapter(Votes) { clickedVoteShop ->
            Toast.makeText(requireContext(), "Clicked: ${clickedVoteShop.numberofstart} Star", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = voteShopAdapter
    }
}