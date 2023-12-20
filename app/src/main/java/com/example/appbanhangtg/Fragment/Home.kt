package com.example.appbanhangtg.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbanhangtg.Activity.AddOrUpdate_User
import com.example.appbanhangtg.Activity.Cart
import com.example.appbanhangtg.Activity.Login
import com.example.appbanhangtg.Activity.ProductDetail
import com.example.appbanhangtg.Activity.Shop
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.Adapter.ShopAdapter
import com.example.appbanhangtg.Adapter.TopSellingAdapter
import com.example.appbanhangtg.Adapter.UserAdapter
import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.FragmentHomeBinding
import com.example.appbanhangtg.databinding.FragmentQLUserBinding
import java.text.DecimalFormat

private lateinit var binding:FragmentHomeBinding
class Home : Fragment() {
    private lateinit var topSellingAdapter: TopSellingAdapter

    private lateinit var shopAdapter: ShopAdapter
    private lateinit var shopList: MutableList<ShopModel>
    private val shopDAO: ShopDAO by lazy { ShopDAO(requireContext()) }

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<ProductModel>
    private val productDAO: ProductDAO by lazy { ProductDAO(requireContext()) }

    private val cartDAO: CartDAO by lazy { CartDAO(requireContext()) }
    override fun onResume() {
        super.onResume()
        loadProduct() // Tải lại sản phẩm
        loadnumcart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)



        // sản phẩm
        productList = mutableListOf()
        productAdapter = ProductAdapter(requireContext(),productList) { clickedProduct ->
            val intent = Intent(context, ProductDetail::class.java)
            intent.putExtra("PRODUCT_EXTRA", clickedProduct)
            startActivity(intent)
        }
        binding.recyclerviewdish.adapter = productAdapter
        binding.recyclerviewdish.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false)
        loadnumcart()
        loadProduct()

        binding.carthome.setOnClickListener {
            val user = context?.let { SharedPrefsManager.getUser(it) }
            if (user == null){
                showDoaLogLogin()
            }else{
                val intent = Intent(context,Cart::class.java)
                startActivity(intent)
            }
        }

        // quảng cáo
        topSellingAdapter = TopSellingAdapter(requireContext(),productList){ clickitem ->
            val intent = Intent(context, ProductDetail::class.java)
            intent.putExtra("PRODUCT_EXTRA", clickitem)
            startActivity(intent)
        }
        binding.viewPagerTopSelling.adapter = topSellingAdapter

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            var count = 0
            override fun run() {
                if (count < topSellingAdapter.itemCount) {
                    binding.viewPagerTopSelling.currentItem = count++
                } else {
                    count = 0
                }
                handler.postDelayed(this, 3000) // 3000 milliseconds = 3 seconds
            }
        }
        handler.postDelayed(runnable, 3000)

        return binding.root
    }


    private fun loadProduct() {
        productList.clear()
        val allProducts = productDAO.getAllProduct()
        val filteredProducts = allProducts.filter { it.quantityProduct >= 1 }

        productList.addAll(filteredProducts)
        productAdapter.notifyDataSetChanged()
    }
    private fun loadnumcart(){
        val user = context?.let { SharedPrefsManager.getUser(it) }
        val userId = user?._idUser

        val cartCount = userId?.let {
            cartDAO.getCartCountByUserId(it)
        }

        // Hiển thị số lượng sản phẩm lên TextView
        cartCount?.let {
            binding.numcart.text = "$it"
        }
    }
    private fun showDoaLogLogin() {
        val user = context?.let { SharedPrefsManager.getUser(it) }
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Thông Báo Shop TG")
        builder.setMessage("Bạn có muốn đăng nhập lúc này?")
        builder.setPositiveButton("Đăng nhập") { dialog, which ->
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton(
            "Hủy"
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }


}