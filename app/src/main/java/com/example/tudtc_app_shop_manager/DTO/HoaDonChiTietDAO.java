package com.example.tudtc_app_shop_manager.DTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tudtc_app_shop_manager.database.DatabaseHelper;
import com.example.tudtc_app_shop_manager.model.ChiTietHoaDon;
import com.example.tudtc_app_shop_manager.model.HoaDon;
import com.example.tudtc_app_shop_manager.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 8/12/18.
 */
public class HoaDonChiTietDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "HoaDonChiTiet";
    public static final String SQL_HOA_DON_CHI_TIET ="CREATE TABLE HoaDonChiTiet( " +
            " idHD int NOT NULL," +
            " idProduct varchar(20) NOT NULL," +
            " soLuong smallint," +
            " price int)";
    public static final String TAG = "HoaDonChiTiet";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public HoaDonChiTietDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserHoaDonChiTiet(ChiTietHoaDon don){
        ContentValues values = new ContentValues();
        values.put("idHD",don.getHoaDon());
        values.put("idProduct",don.getProduct().getId());
        values.put("soLuong",don.getSoLuongMua());
        values.put("price",don.getProduct().getPrice());
        try {
            if(db.insert(TABLE_NAME,null,values)== -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }

    public List<ChiTietHoaDon> getTheoIdHD(String id){
        String sql = "SELECT idHD, Product.id, Product.name, Product.image, HoaDonChiTiet.price, soLuong \n" +
                "FROM HoaDonChiTiet INNER JOIN Product on HoaDonChiTiet.idProduct = Product.id \n" +
                "WHERE idHD = ?";
        List<ChiTietHoaDon> list = getData(sql, id);
        return list;
    }

    public int getSumMoneyId(String id){
        String sql = "SELECT sum(soLuong*price) FROM HoaDonChiTiet WHERE idHD = ?";
        Cursor c = db.rawQuery(sql, new String[]{id});
        int money = 0;
        c.moveToFirst();
        while (c.isAfterLast()==false){
            money = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        return money;
    }

    public List<ChiTietHoaDon> getData(String sql, String...strings){
        List<ChiTietHoaDon> listProduct= new ArrayList<>();
        Cursor c = db.rawQuery(sql, strings);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            ChiTietHoaDon ee = new ChiTietHoaDon();
            ee.setHoaDon(c.getInt(0));
            Product product = new Product();
            product.setId(c.getString(1));
            product.setName(c.getString(2));
            product.setImage(c.getBlob(3));
            product.setPrice(c.getInt(4));
            ee.setProduct(product);
            ee.setSoLuongMua(c.getInt(5));
            listProduct.add(ee);
            Log.d("//====================",ee.toString());
            c.moveToNext();
        }
        c.close();
        return listProduct;
    }


//    //update
//    public int updateHoaDonChiTiet(HoaDonChiTiet hd){
//        ContentValues values = new ContentValues();
//        values.put("maHDCT",hd.getMaHDCT());
//        values.put("mahoadon",hd.getHoaDon().getMaHoaDon());
//        values.put("maSach",hd.getSach().getMaSach());
//        values.put("soLuong",hd.getSoLuongMua());
//        int result = db.update(TABLE_NAME,values,"maHDCT=?", new String[]{String.valueOf(hd.getMaHDCT())});
//        if (result == 0){
//            return -1;
//        }
//        return 1;
//    }

    //delete
    public int deleteHoaDonChiTietByID(String id){
        int result = db.delete(TABLE_NAME,"idHD=?",new String[]{String.valueOf(id)});
        if (result == 0)
            return -1;
        return 1;
    }

    public double getDoanhThu(int sta){
        double doanhThu = 0;
        String sql ="SELECT SUM(soluong*price) FROM HoaDon INNER JOIN HoaDonChiTiet\n" +
                "on HoaDon.id = HoaDonChiTiet.idHD\n" +
                "WHERE strftime('%m',HoaDon.ngayMua) = strftime('%m','now') AND status = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(sta)});
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
}
