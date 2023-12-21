package com.example.appbanhangtg.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.appbanhangtg.Activity.AccountSetting
import com.example.appbanhangtg.Activity.Bill
import com.example.appbanhangtg.Activity.Bill1
import com.example.appbanhangtg.Activity.Bill2
import com.example.appbanhangtg.Activity.Cart
import com.example.appbanhangtg.Activity.HomeAdmin
import com.example.appbanhangtg.Activity.HomeShip
import com.example.appbanhangtg.Activity.HomeUser
import com.example.appbanhangtg.Activity.Login
import com.example.appbanhangtg.Activity.MyShop
import com.example.appbanhangtg.Activity.VoteProduct
import com.example.appbanhangtg.Activity.chucvuProfile
import com.example.appbanhangtg.Adapter.ShopAdapter
import com.example.appbanhangtg.DAO.BillDAO
import com.example.appbanhangtg.DAO.CartDAO
import com.example.appbanhangtg.DAO.ShopDAO
import com.example.appbanhangtg.DAO.UserDAO
import com.example.appbanhangtg.Interface.SharedPrefsManager
import com.example.appbanhangtg.Model.ProductModel
import com.example.appbanhangtg.Model.UserModel
import com.example.appbanhangtg.R
import com.example.appbanhangtg.databinding.ActivityChucvuProfileBinding
import com.example.appbanhangtg.databinding.FragmentHomeBinding
import com.example.appbanhangtg.databinding.FragmentProfileBinding

private lateinit var binding: FragmentProfileBinding

class Profile : Fragment() {
    private val cartDAO: CartDAO by lazy { CartDAO(requireContext()) }
    private val billDAO: BillDAO by lazy { BillDAO(requireContext()) }
    private val shopDAO: ShopDAO by lazy { ShopDAO(requireContext()) }
    private val userDAO: UserDAO by lazy { UserDAO(requireContext()) }
    override fun onResume() {
        super.onResume()
        loadshop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        loadshop()

        return binding.root
    }

    private fun loadshop() {
        val user = context?.let { SharedPrefsManager.getUser(it) }
        val userId = user?._idUser
        val username = user?.username

        if (user?.username == "" || user?.username == null) {
            binding.txtusernameProfile.setText("Bạn chưa đăng nhập")
            binding.txtchucvuProfile.setText("Đăng nhập ngay  >  ")
            binding.txtchucvuProfile.setOnClickListener {
                val intent = Intent(context, Login::class.java)
                startActivity(intent)
            }
        } else {
            val requestOptions = RequestOptions().transform(CircleCrop())

            Glide.with(binding.root.context)
                .load(user?.image)
                .apply(requestOptions)
                .placeholder(R.drawable.icon_persion) // Placeholder image while loading
                .into(binding.imgavtProfile)
            binding.txtusernameProfile.text = user?.username

            val sumtienbill = userId?.let { billDAO.getTotalSumPriceByUserId(it) }

            if (sumtienbill!! <= 2000000.0){
                binding.txtchucvuProfile.setText("Khách hàng đồng >  ")
                binding.txtchucvuProfile.setOnClickListener {
                    val intent = Intent(context, chucvuProfile::class.java)
                    startActivity(intent)
                }
            }else if(sumtienbill!! <= 10000000.0){
                binding.txtchucvuProfile.setText("Khách hàng bạc >  ")
                binding.txtchucvuProfile.setOnClickListener {
                    val intent = Intent(context, chucvuProfile::class.java)
                    startActivity(intent)
                }
            }else if(sumtienbill!! < 100000000.0){
                binding.txtchucvuProfile.setText("Khách hàng vàng >  ")
                binding.txtchucvuProfile.setOnClickListener {
                    val intent = Intent(context, chucvuProfile::class.java)
                    startActivity(intent)
                }
            }
            else {
                binding.txtchucvuProfile.setText("Khách hàng VIP >  ")
                binding.txtchucvuProfile.setOnClickListener {
                    val intent = Intent(context, chucvuProfile::class.java)
                    startActivity(intent)
                }
            }
        }

        btnmua()
        btnban()
        btnship()

        val cartCount = userId?.let {
            cartDAO.getCartCountByUserId(it)
        }
        // Hiển thị số lượng sản phẩm lên TextView
        cartCount?.let {
            binding.numcart.text = "$it"
        }

        // đơn mua
        val billcountTTXacNhan = userId?.let {
            billDAO.getbillCountByTTXacNhanId(it)
        }
        val billcountTTLayHang = userId?.let {
            billDAO.getbillCountByTTLayHangId(it)
        }
        val billcountTTGiaoHang = userId?.let {
            billDAO.getbillCountByTTGiaoHangId(it)
        }
        val billcountTTVote = userId?.let {
            billDAO.getbillCountByTTDanhGiaId(it)
        }
        billcountTTXacNhan?.let {
            binding.nummuaCXN.text = "$it"
        }
        billcountTTLayHang?.let {
            binding.nummuaCLH.text = "$it"
        }
        billcountTTGiaoHang?.let {
            binding.nummuaCLH.text = "$it"
        }
        billcountTTVote?.let {
            binding.nummuaDG.text = "$it"
        }
        // đơn ship
        val billcountTTGiaoHangship = username?.let {
            billDAO.getbillCountByTTGiaoHangShipId(it)
        }
        val billcountTTGiaoHangship1 = username?.let {
            billDAO.getbillCountByTTGiaoHangShip1Id(it)
        }
        billcountTTGiaoHangship?.let {
            binding.numShipDDG.text = "$it"
        }
        billcountTTGiaoHangship1?.let {
            binding.numshipDDHT.text = "$it"
        }

        // đơn bán
        val billcountTTXacNhanBan = userId?.let {
            billDAO.getbillCountByTTXacNhanBanId(it)
        }
        val billcountTTLayHangBan = userId?.let {
            billDAO.getbillCountByTTLayHangBanId(it)
        }
        billcountTTXacNhanBan?.let {
            binding.numbanCXN.text = "$it"
        }
        billcountTTLayHangBan?.let {
            binding.numbanCLH.text = "$it"
        }

    }

    private fun btnmua() {
        val user = context?.let { SharedPrefsManager.getUser(it) }
        if (user == null){
            binding.txtshowaccountProfile.setOnClickListener {
                showDoaLogLogin()
            }
            binding.imgcartProfile.setOnClickListener {
                showDoaLogLogin()
            }
            binding.txtshoppRofile.setOnClickListener {
                showDoaLogLogin()
            }
            // mua
            binding.historibill.setOnClickListener {
                showDoaLogLogin()
            }
            binding.muaCXN.setOnClickListener {
                showDoaLogLogin()
            }
            binding.muaCLH.setOnClickListener {
                showDoaLogLogin()
            }
            binding.muaCGH.setOnClickListener {
                showDoaLogLogin()
            }
            // dánh giá
            binding.voteproductdetail.setOnClickListener {
                showDoaLogLogin()
            }
            // khách hàng thân thiết
            binding.khachhangthantiet.setOnClickListener {
                showDoaLogLogin()
            }
            // suwj kiện
            binding.mainhome.setOnClickListener {
                showDoaLogLogin()
            }
        }else{
            binding.txtshowaccountProfile.setOnClickListener {
                val intent = Intent(context, AccountSetting::class.java)
                startActivity(intent)
            }
            binding.imgcartProfile.setOnClickListener {
                val intent = Intent(context, Cart::class.java)
                startActivity(intent)
            }
            binding.txtshoppRofile.setOnClickListener {
                val intent = Intent(context, MyShop::class.java)
                startActivity(intent)
            }
            // mua
            binding.historibill.setOnClickListener {
                val intent = Intent(context, Bill::class.java)
                intent.putExtra("OPEN_TAB_INDEX", 4) // Mở tab số 2
                startActivity(intent)
            }
            binding.muaCXN.setOnClickListener {
                val intent = Intent(context, Bill::class.java)
                intent.putExtra("OPEN_TAB_INDEX", 0) // Ví dụ, mở tab số 0
                startActivity(intent)
            }
            binding.muaCLH.setOnClickListener {
                val intent = Intent(context, Bill::class.java)
                intent.putExtra("OPEN_TAB_INDEX", 1) // Ví dụ, mở tab số 1
                startActivity(intent)
            }
            binding.muaCGH.setOnClickListener {
                val intent = Intent(context, Bill::class.java)
                intent.putExtra("OPEN_TAB_INDEX", 2) // Mở tab số 2
                startActivity(intent)
            }
            // dánh giá
            binding.voteproductdetail.setOnClickListener {
                val intent = Intent(context, VoteProduct::class.java)
                startActivity(intent)
            }
            // khách hàng thân thiết
            binding.khachhangthantiet.setOnClickListener {
                val intent = Intent(context, chucvuProfile::class.java)
                startActivity(intent)
            }
            // suwj kiện
            binding.mainhome.setOnClickListener {
                val roleuser = user?.role
                if (roleuser == "Admin") {
                    val intent = Intent(context, HomeAdmin::class.java)
                    intent.putExtra("OPEN_TAB_INDEX", 0) // Ví dụ, mở tab số 0
                    startActivity(intent)
                } else if (roleuser == "Shipper") {
                    val intent = Intent(context, HomeShip::class.java)
                    intent.putExtra("OPEN_TAB_INDEX", 0) // Ví dụ, mở tab số 0
                    startActivity(intent)
                } else {
                    val intent = Intent(context, HomeUser::class.java)
                    intent.putExtra("OPEN_TAB_INDEX", 0) // Ví dụ, mở tab số 0
                    startActivity(intent)
                }
            }
        }

    }

    private fun btnban() {
        val user = context?.let { SharedPrefsManager.getUser(it) }
        val userId = user?._idUser
        val shoplist1 = userId?.let { shopDAO.getShopsByUserId(it) }

        if (user == null){
            binding.donbancuatoi.setOnClickListener {
                showDoaLogLogin()
            }
            binding.banCXN.setOnClickListener {
                showDoaLogLogin()
            }
            binding.banCLH.setOnClickListener {
                showDoaLogLogin()
            }
        }else{
            if (shoplist1 == null){

                binding.donbancuatoi.setOnClickListener {
                    Toast.makeText(context, "Bạn chưa có shop", Toast.LENGTH_SHORT).show()
                }
                binding.banCXN.setOnClickListener {
                    Toast.makeText(context, "Bạn chưa có shop", Toast.LENGTH_SHORT).show()
                }
                binding.banCLH.setOnClickListener {
                    Toast.makeText(context, "Bạn chưa có shop", Toast.LENGTH_SHORT).show()
                }
            }else{
                binding.donbancuatoi.setOnClickListener {
                    val intent = Intent(context, Bill1::class.java)
                    intent.putExtra("OPEN_TAB_INDEX", 4) // Mở tab số 2
                    startActivity(intent)
                }
                binding.banCXN.setOnClickListener {
                    val intent = Intent(context, Bill1::class.java)
                    intent.putExtra("OPEN_TAB_INDEX", 0) // Ví dụ, mở tab số 0
                    startActivity(intent)
                }
                binding.banCLH.setOnClickListener {
                    val intent = Intent(context, Bill1::class.java)
                    intent.putExtra("OPEN_TAB_INDEX", 1) // Ví dụ, mở tab số 1
                    startActivity(intent)
                }
            }
        }



    }

    private fun btnship() {
        val user = context?.let { SharedPrefsManager.getUser(it) }
        if (user == null){
            binding.donshipcuatoi.setOnClickListener {
                showDoaLogLogin()
            }
            binding.shipDDG.setOnClickListener {
                showDoaLogLogin()
            }
            binding.shipDDHT.setOnClickListener {
                showDoaLogLogin()
            }
        }
       else if (user?.role != "Shipper") {
            binding.donshipcuatoi.setOnClickListener {
                Toast.makeText(
                    context,
                    "Bạn chưa đăng ký thành người giao hàng",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.shipDDG.setOnClickListener {
                Toast.makeText(
                    context,
                    "Bạn chưa đăng ký thành người giao hàng",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.shipDDHT.setOnClickListener {
                Toast.makeText(
                    context,
                    "Bạn chưa đăng ký thành người giao hàng",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // ship
            binding.donshipcuatoi.setOnClickListener {
                val intent = Intent(context, Bill2::class.java)
                startActivity(intent)
            }
            binding.shipDDG.setOnClickListener {
                val intent = Intent(context, Bill2::class.java)
                startActivity(intent)
            }
            binding.shipDDHT.setOnClickListener {
                val intent = Intent(context, Bill2::class.java)
                startActivity(intent)
            }
        }

    }

    private fun showDoaLogRole() {
        val user = context?.let { SharedPrefsManager.getUser(it) }
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Thay đổi vai trò của tài khoản")
        builder.setMessage("Bạn có chắc muốn đổi vai trò thành người giao hàng?")
        builder.setPositiveButton("Thay đổi") { dialog, which ->
            user?._idUser?.let { userDAO.updateUserRole(it, "Shipper") }
            Toast.makeText(context, "Thay đổi thành công thành công", Toast.LENGTH_SHORT).show()
            loadshop()
        }
        builder.setNegativeButton(
            "Hủy"
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
    private fun showDoaLogLogin() {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Thông Báo Shop TG")
        builder.setMessage("Bạn cần phải đăng nhập. Bạn có muốn đăng nhập lúc này?")
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