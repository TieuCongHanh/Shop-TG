package com.example.appbanhangtg.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.VoteShopDAO
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentIntroduceShopBinding

private lateinit var binding: FragmentIntroduceShopBinding
class Introduce_Shop : Fragment() {

    private var shopModel: ShopModel? = null
    private lateinit var productDAO: ProductDAO
    private lateinit var voteShopDAO: VoteShopDAO

    companion object {
        fun newInstance(shopWrapper: ShopWrapper): Introduce_Shop {
            val fragment = Introduce_Shop()
            val args = Bundle()
            args.putSerializable("SHOP_EXTRA", shopWrapper)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onResume() {
        super.onResume()
        lisst() // Tải lại sản phẩm
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroduceShopBinding.inflate(inflater, container, false)
        lisst()

        return binding.root
    }
    private fun lisst(){
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel

        val shopId = shopModel?._idShop

        productDAO = ProductDAO(requireContext())
        voteShopDAO = VoteShopDAO(requireContext())

        val productCount = shopId?.let {
            productDAO.getProductCountByShopId(it)
        }
        val voteShopCount = shopId?.let {
            voteShopDAO.getVoteShopCountById(it)
        }

        // Hiển thị số lượng sản phẩm lên TextView
        productCount?.let {
            binding.totunum.text = "$it"
        }
        voteShopCount?.let {
            binding.totuvote.text = "$it"
        }


        // Hiển thị thông tin cửa hàng
        shopModel?.let {
            binding.txtslogan.text = "Với slogan của Shop là ${it.sloganShop} chúng tôi sẽ cho bạn những trải nghiệm thật thú vị"
            binding.txtdesc.text = "Miêu tả: ${it.descriptionShop}"
        }
    }

}