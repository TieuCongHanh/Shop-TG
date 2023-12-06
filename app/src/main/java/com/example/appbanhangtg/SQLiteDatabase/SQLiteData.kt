package com.example.appbanhangtg.SQLiteDatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteData(context: Context): SQLiteOpenHelper(context,"DATA",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USER(" +
                "_idUser integer primary key, username TEXT, password TEXT, phone TEXT, role TEXT, email TEXT, image TEXT)")

        db?.execSQL("CREATE TABLE PRODUCT(" +
                "_idProduct integer primary key, nameProduct TEXT, quantityProduct TEXT," +
                " priceProduct TEXT, descriptionProduct TEXT, imageProduct TEXT," +
                " _idUser integer, _idShop integer, _idtypeProduct integer)")

        db?.execSQL("CREATE TABLE SHOP(" +
                "_idShop integer primary key, nameShop TEXT, descriptionShop TEXT," +
                " sloganShop TEXT,imageavtShop TEXT, imageShop TEXT, " +
                " _idUser integer)")

        db?.execSQL("CREATE TABLE TYPEPRODUCT(" +
                "_idtypeProduct integer primary key," +
                " nameTypeProduct TEXT)")

        db?.execSQL("CREATE TABLE VOTESHOP(" +
                "_idVoteShop integer primary key, numberofstart TEXT, content TEXT, date TEXT," +
                " _idUser integer, _idShop integer)")
        db?.execSQL("CREATE TABLE ADDRESS(" +
                "_idAddRess integer primary key, fullname TEXT, phone TEXT, address TEXT, note TEXT," +
                " _idUser integer)")

        // user
        db?.execSQL("Insert into USER(username,password,phone,role,email,image) values ('admin','admin','0123','Admin','admin@gmail.com'," +
                "'https://phunugioi.com/wp-content/uploads/2020/10/avatar-chat-anime.jpg') ")
        db?.execSQL("Insert into USER(username,password,phone,role,email,image) values ('shipper','shipper','0123','Shipper','shipper@gmail.com'," +
                "'https://1.bp.blogspot.com/-K_0zfgKhlbw/XqZl81WsZZI/AAAAAAAAjP8/K5Tp7visL9g7aKNNQYSq5kHGRd2P84E9QCLcBGAsYHQ/s1600/Anh-avatar-dep-cho-con-trai%2B%252840%2529.jpg') ")
        db?.execSQL("Insert into USER(username,password,phone,role,email,image) values ('user','user','0123','User','user@gmail.com'," +
                "'https://upanh123.com/wp-content/uploads/2020/10/anh-dai-dien-ngau5.jpg') ")
        db?.execSQL("Insert into USER(username,password,phone,role,email,image) values ('user1','user1','0123','User','user@gmail.com'," +
                "'https://upanh123.com/wp-content/uploads/2020/10/anh-dai-dien-ngau5.jpg') ")

        // shop
        db?.execSQL("Insert into SHOP(nameShop,descriptionShop,sloganShop,imageavtShop,imageShop,_idUser) values ('Ăn vặt','chỗ nào có tôi mong sẽ có bạn','ăn vặt cho béo'," +
                "'https://luatvanthanh.vn/wp-content/uploads/2019/07/cac-buoc-kinh-doanh-online-hieu-qua-can-biet.png','https://tse1.mm.bing.net/th?id=OIP.qLaDi-aJKdOjWnu9sl0uwQAAAA&pid=Api&P=0&h=180',3) ")
        db?.execSQL("Insert into SHOP(nameShop,descriptionShop,sloganShop,imageavtShop,imageShop,_idUser) values ('Cửa hàng vui tươi','Cứ cười đi nào','Cười như điên'," +
                "'https://png.pngtree.com/png-clipart/20190613/original/pngtree-flat-red-shop-with-road-vector-png-png-image_3547975.jpg','https://danangreviews.vn/wp-content/uploads/2021/05/Yody-Da-Nang.jpg',1) ")
        db?.execSQL("Insert into SHOP(nameShop,descriptionShop,sloganShop,imageavtShop,imageShop,_idUser) values ('Cửa hàng khóc','Rất buồn nếu thiếu bạn','Buồn abc xyz'," +
                "'https://bigbuy.vn/images/companies/1/Cua%20hang%20thuc%20an%20nhanh/mo-cua-hang-thuc-an-nhanh.jpg?1600747212044','https://anviethouse.vn/wp-content/uploads/2022/07/anviethouse_thietke-cua-hang-thuc-pham-sach-Mai-Huong-Quang-Ninh-pa-2-1.jpg',2) ")

        // thể loại
        db?.execSQL("Insert into TYPEPRODUCT(nameTypeProduct) values ('Đồ ăn')")
        db?.execSQL("Insert into TYPEPRODUCT(nameTypeProduct) values ('Đồ dùng dụng cụ')")
        db?.execSQL("Insert into TYPEPRODUCT(nameTypeProduct) values ('Thời trang')")

        // sản phẩm
        db?.execSQL("Insert into PRODUCT(nameProduct,quantityProduct,priceProduct,descriptionProduct,imageProduct,_idUser,_idShop,_idtypeProduct) values ('" +
                "bánh ngọt','12','50000','bánh thơm ngon ngọt','https://toplist.vn/images/800px/quan-banh-ngot-tuyet-voi-nhat-hai-phong-149908.jpg',3,1,1)")
        db?.execSQL("Insert into PRODUCT(nameProduct,quantityProduct,priceProduct,descriptionProduct,imageProduct,_idUser,_idShop,_idtypeProduct) values ('" +
                "bánh pizza','12','100000','Bánh ngon lắm nha','https://useful.vn/wp-content/uploads/2020/04/1541837945555_8122435.jpg',3,1,1)")
        db?.execSQL("Insert into PRODUCT(nameProduct,quantityProduct,priceProduct,descriptionProduct,imageProduct,_idUser,_idShop,_idtypeProduct) values ('" +
                "Laptop','12','20000000','Laptop mới','https://tse2.mm.bing.net/th?id=OIP.puzai_D4808Fi7vlZTj3dQHaFh&pid=Api&P=0&h=180',1,2,2)")
        db?.execSQL("Insert into PRODUCT(nameProduct,quantityProduct,priceProduct,descriptionProduct,imageProduct,_idUser,_idShop,_idtypeProduct) values ('" +
                "Laptop','12','20000000','Laptop mới','https://tse2.mm.bing.net/th?id=OIP.puzai_D4808Fi7vlZTj3dQHaFh&pid=Api&P=0&h=180',1,2,2)")
        db?.execSQL("Insert into PRODUCT(nameProduct,quantityProduct,priceProduct,descriptionProduct,imageProduct,_idUser,_idShop,_idtypeProduct) values ('" +
                "Laptop','12','20000000','Laptop mới','https://tse2.mm.bing.net/th?id=OIP.puzai_D4808Fi7vlZTj3dQHaFh&pid=Api&P=0&h=180',1,2,2)")
        db?.execSQL("Insert into PRODUCT(nameProduct,quantityProduct,priceProduct,descriptionProduct,imageProduct,_idUser,_idShop,_idtypeProduct) values ('" +
                "Laptop','12','20000000','Laptop mới','https://tse2.mm.bing.net/th?id=OIP.puzai_D4808Fi7vlZTj3dQHaFh&pid=Api&P=0&h=180',1,2,2)")

        // VOTESHOP
        db?.execSQL("Insert into VOTESHOP(numberofstart,content,date,_idUser,_idShop) values ('5'," +
                "'Shop làm ăn rất uy tín mọi người hãy ủng hộ shop nhiều nhiều lên nha chứ không shop mà nản l anh em mình khôncoscos '," +
                "'20-11-2023',4,1)")
        db?.execSQL("Insert into VOTESHOP(numberofstart,content,date,_idUser,_idShop) values ('2'," +
                "'Shop làm ăn rất cẩu thả nhìn là biets méo uy tín rồi anh em cẩn tận shop này lừa đảo nha anh em tôi mua 2 mà ship có 3 à vão ò lừa đảo nha '," +
                "'20-11-2023',3,1)")
        db?.execSQL("Insert into VOTESHOP(numberofstart,content,date,_idUser,_idShop) values ('3'," +
                "'Shop làm ăn rất uy tín mọi người hãy ủng hộ shop nhiều nhiều lên nha chứ không shop mà nản l anh em mình khôncoscos '," +
                "'20-11-2023',2,1)")
        db?.execSQL("Insert into VOTESHOP(numberofstart,content,date,_idUser,_idShop) values ('1'," +
                "'Shop làm ăn rất uy tín mọi người hãy ủng hộ shop nhiều nhiều lên nha chứ không shop mà nản l anh em mình khôncoscos '," +
                "'20-11-2023',2,1)")
        db?.execSQL("Insert into VOTESHOP(numberofstart,content,date,_idUser,_idShop) values ('4'," +
                "'Shop làm ăn rất uy tín mọi người hãy ủng hộ shop nhiều nhiều lên nha chứ không shop mà nản l anh em mình khôncoscos '," +
                "'20-11-2023',2,1)")

        // address
        db?.execSQL("Insert into ADDRESS(fullname,phone,address,note,_iduser) values ('Tiêu Công Ha'," +
                "'0336119531','Liên Mạc, Thanh Hà, Hải Dương','Nhận hàng ở thôn tiêu xá', 1 )")
        db?.execSQL("Insert into ADDRESS(fullname,phone,address,note,_iduser) values ('Tiêu Công Hạnh'," +
                "'0336119531','Liên Mạc, Thanh Hà, Hải Dương','Nhận hàng ở thôn tiêu xá', 1 )")
        db?.execSQL("Insert into ADDRESS(fullname,phone,address,note,_iduser) values ('Tiêu Công Ha'," +
                "'0336119531','Liên Mạc, Thanh Hà, Hải Dương','Nhận hàng ở thôn tiêu xá', 2 )")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}