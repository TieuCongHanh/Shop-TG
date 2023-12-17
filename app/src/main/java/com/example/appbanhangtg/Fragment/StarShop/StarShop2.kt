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
import com.example.appbanhangtg.databinding.FragmentStarShop2Binding

private lateinit var binding: FragmentStarShop2Binding

class StarShop2 : Fragment() {

    private var shopModel: ShopModel? = null
    private lateinit var voteshopDAO: VoteShopDAO
    private lateinit var userDAO: UserDAO
    override fun onResume() {
        super.onResume()
        list()
    }
    companion object {
        fun newInstance(shopWrapper: ShopWrapper): StarShop2 {
            val fragment = StarShop2()
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
        binding = FragmentStarShop2Binding.inflate(inflater, container, false)
        list()
        return binding.root
    }

    private fun displayStarList(votes: List<VoteShopModel>) {
        val recyclerView = binding.recyclerview2

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
private fun list(){
    val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
    val shopModel = shopWrapper?.shopModel
    val shopId = shopModel?._idShop

    voteshopDAO = VoteShopDAO(requireContext())
    userDAO = UserDAO(requireContext())

    // Lấy danh sách vote theo ID của cửa hàng
    val voteShopList = shopId?.let {
        voteshopDAO.getByVote2ShopId(it)
    }
    val voteCount = shopId?.let {
        voteshopDAO.getStarCoun2tById(it)
    }

    voteShopList?.let { displayStarList(it) }
    voteCount?.let {
        binding.txttotu.text = "Tổng đánh giá của 3 sao là : $it"
    }

}
}