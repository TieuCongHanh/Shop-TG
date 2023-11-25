package com.example.appbanhangtg.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentProductShopBinding

private lateinit var binding: FragmentProductShopBinding

class Product_Shop : Fragment() {

    private lateinit var productDAO: ProductDAO
    private var shopModel: ShopModel? = null

    companion object {
        fun newInstance(shopWrapper: ShopWrapper): Product_Shop {
            val fragment = Product_Shop()
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
        binding = FragmentProductShopBinding.inflate(layoutInflater)
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel

        val shopId = shopModel?._idShop

        productDAO = ProductDAO(requireContext())

        // Lấy danh sách sản phẩm theo ID của cửa hàng
        val productList = shopId?.let {
            productDAO.getByShopIdProduct(it)
        }

        productList?.let { displayProductList(it) }

        return binding.root
    }

    private fun displayProductList(products: List<ProductModel>) {
        val recyclerView = binding.recyclerviewproductShop

        recyclerView.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        val productAdapter = ProductAdapter(products) { clickedProduct ->
            Toast.makeText(requireContext(), "Clicked: ${clickedProduct.nameProduct}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = productAdapter
    }
}