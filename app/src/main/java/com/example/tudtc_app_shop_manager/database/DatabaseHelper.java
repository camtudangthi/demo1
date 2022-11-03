package com.example.tudtc_app_shop_manager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tudtc_app_shop_manager.DTO.CartDAO;
import com.example.tudtc_app_shop_manager.DTO.CategoryDAO;
import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.DTO.HoaDonDAO;
import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.DTO.UserDAO;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.Product;

/**
 * Created by admin on 6/7/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbShopManager11";
    public static final int VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.SQL_USER);
        db.execSQL(CategoryDAO.SQL_CATEGORY);
        db.execSQL(ProductDAO.SQL_PRODUCT);
        db.execSQL(CartDAO.SQL_CART);
        db.execSQL(HoaDonDAO.SQL_HOA_DON);
        db.execSQL(HoaDonChiTietDAO.SQL_HOA_DON_CHI_TIET);
        db.execSQL("INSERT INTO User VALUES (\"admin\", \"admin\", \"0328645153\", \"Bách hóa An Nhiên\", 1, 0)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+UserDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+CategoryDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+ProductDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+CartDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+HoaDonDAO.TABLE_NAME);
        db.execSQL("Drop table if exists "+HoaDonChiTietDAO.TABLE_NAME);

        onCreate(db);
    }

}
