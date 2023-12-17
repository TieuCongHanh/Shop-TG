package com.example.appbanhangtg.Fragment.StarProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Adapter.VoteProductAdapter
import com.example.appbanhangtg.Adapter.VoteShopAdapter
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.DAO.VoteProductDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Model.ProductWrapper
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.Model.VoteProductModel
import com.example.appbanhangtg.Model.VoteShopModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentStarProduct1Binding

private lateinit var binding: FragmentStarProduct1Binding

class StarProduct1 : Fragment() {
    private lateinit var voteProductDAO: VoteProductDAO
    private lateinit var productDAO: ProductDAO
    private lateinit var userDao: UserDAO
    override fun onResume() {
        super.onResume()
        liststarshop2()
    }
    companion object {
        fun newInstance(productWrapper: ProductWrapper): StarProduct1 {
            val fragment = StarProduct1()
            val args = Bundle()
            args.putSerializable("PRODUCT_EXTRA", productWrapper)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStarProduct1Binding.inflate(inflater, container, false)
        liststarshop2()
        // Inflate the layout for this fragment
        return binding.root
    }


    private fun displayStarList(votes: List<VoteProductModel>) {
        val recyclerView = binding.recyclerview1

        recyclerView.layoutManager = GridLayoutManager(
            context,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        val voteShopAdapter = VoteProductAdapter(votes, userDao) { clickedVoteShop ->
            Toast.makeText(context, "${clickedVoteShop._idProduct}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = voteShopAdapter
    }

    private fun liststarshop2() {
        val ProductWrapper = arguments?.getSerializable("PRODUCT_EXTRA") as? ProductWrapper
        val shopModel = ProductWrapper?.productModel
        val shopId = shopModel?._idProduct

        voteProductDAO = VoteProductDAO(requireContext())
        userDao = UserDAO(requireContext())

        // Lấy danh sách vote theo ID của cửa hàng
        val voteShopList = shopId?.let {
            voteProductDAO.getByVote1ShopId(it)
        }
        val voteCount = shopId?.let {
            voteProductDAO.getStarCoun1tById(it)
        }

        voteShopList?.let { displayStarList(it) }
        voteCount?.let {
           binding.txttotu.text =
                "Tổng đánh giá của 2 sao là : $it"
        }

    }

}