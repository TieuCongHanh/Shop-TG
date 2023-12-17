package com.example.appbanhangtg.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appbanhangtg.Activity.AddOrUpdate_ProductShop
import com.example.appbanhangtg.Activity.ProductDetail
import com.example.appbanhangtg.Adapter.ProductAdapter
import com.example.appbanhangtg.DAO.ProductDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.ShopModel
import com.example.appbanhangtg.Model.ShopWrapper
import com.example.appbanhangtg.databinding.FragmentProductShopBinding

private lateinit var binding: FragmentProductShopBinding

class Product_Shop : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<ProductModel>
    private val productDAO: ProductDAO by lazy { ProductDAO(requireContext()) }
    private var shopModel: ShopModel? = null

    companion object {
        fun newInstance(shopWrapper: ShopWrapper): Product_Shop {
            val fragment = Product_Shop()
            val args = Bundle()
            args.putSerializable("SHOP_EXTRA", shopWrapper)
            fragment.arguments = args
            return fragment
        }

        const val ADD_OR_UPDATE_REQUEST = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_OR_UPDATE_REQUEST) {
            if (resultCode == AppCompatActivity.RESULT_OK) {

                listProductShop() // load sp khi co data thay doi
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductShopBinding.inflate(layoutInflater)
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel
        val user = context?.let { SharedPrefsManager.getUser(it) }
        val userId = user?._idUser

        if (userId != shopModel?._idUser) {
            binding.addproductShop.visibility = View.GONE
        } else {
            binding.addproductShop.setOnClickListener {
                val intent = Intent(context, AddOrUpdate_ProductShop::class.java)
                intent.putExtra("SHOP_EXTRA", shopModel)
                intent.putExtra("_idProduct", 0)
                startActivityForResult(intent, ADD_OR_UPDATE_REQUEST)
            }
        }

        listProductShop() // hieenr thij sanr pham


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
        productAdapter = ProductAdapter(products) { clickedProduct ->
            val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
            val shopModel = shopWrapper?.shopModel
            val user = context?.let { SharedPrefsManager.getUser(it) }
            val userId = user?._idUser

            if (userId == shopModel?._idUser) {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setMessage("Bạn muốn làm gì?")

                    .setCancelable(true)
                    .setPositiveButton("Delete") { dialog, _ ->
                        showDeleteProductDialog(clickedProduct)
                    }
                    .setNegativeButton("Update") { dialog, _ ->
                        val intent = Intent(context, AddOrUpdate_ProductShop::class.java)
                        intent.putExtra("PRODUCT_EXTRA", clickedProduct)
                        intent.putExtra("SHOP_EXTRA", shopModel)
                        intent.putExtra("_idProduct", clickedProduct._idProduct)
                        startActivityForResult(intent, ADD_OR_UPDATE_REQUEST)
                    }
                    .setNeutralButton("Detail") { dialog, _ ->
                        val intent = Intent(context, ProductDetail::class.java)
                        intent.putExtra("PRODUCT_EXTRA", clickedProduct)
                        startActivity(intent)
                    }


                val alert = dialogBuilder.create()
                alert.show()
            } else {

                val intent = Intent(context, ProductDetail::class.java)
                intent.putExtra("PRODUCT_EXTRA", clickedProduct)
                startActivity(intent)
            }
        }

        recyclerView.adapter = productAdapter
    }

    private fun listProductShop() {
        val shopWrapper = arguments?.getSerializable("SHOP_EXTRA") as? ShopWrapper
        val shopModel = shopWrapper?.shopModel

        val shopId = shopModel?._idShop
        productList = mutableListOf()

        // Lấy danh sách sản phẩm theo ID của cửa hàng
        val productList = shopId?.let {
            productDAO.getByShopIdProduct(it)
        }

        productList?.let { displayProductList(it) }
    }
    private fun showDeleteProductDialog(product: ProductModel) {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Xóa sản phẩm")
        builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?")
        builder.setPositiveButton("Xóa") { dialog, which -> // Gọi phương thức để xóa bình luận
            productDAO.deleteProduct(product._idProduct)
            Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show()
            listProductShop()
        }
        builder.setNegativeButton(
            "Hủy"
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }


}