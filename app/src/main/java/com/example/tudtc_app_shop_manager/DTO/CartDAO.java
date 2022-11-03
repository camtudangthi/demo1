package com.example.tudtc_app_shop_manager.DTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tudtc_app_shop_manager.database.DatabaseHelper;
import com.example.tudtc_app_shop_manager.model.Product;
import com.example.tudtc_app_shop_manager.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;


public class CartDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "Cart";
    public static final String SQL_CART ="CREATE TABLE Cart (" +
            " idProduct varchar(20)," +
            " username varchar(30)," +
            " amount smallint," +
            " selected bit)";
    public static final String TAG = "CartDAO";
    public CartDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserCart(ShoppingCart cart){
        ContentValues values = new ContentValues();
        values.put("idProduct",cart.getIdProduct());
        values.put("username",cart.getUsername());
        values.put("amount",cart.getAmount());
        values.put("selected","0");
        try {
            if(db.insert(TABLE_NAME,null,values) == -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }

    //getAll
    public List<ShoppingCart> getAllCart(){
        String sql = "select * from Cart";
        List<ShoppingCart> list = getData(sql);
        return list;
    }

//    //update
//    public int updateCart(ShoppingCart cart){
//        ContentValues values = new ContentValues();
//        values.put("idProduct",cart.getIdProduct());
//        values.put("username",cart.getUsername());
//        values.put("amount",cart.getAmount());
//        values.put("select","0");
//        int result = db.update(TABLE_NAME,values,"username=?", new String[]{String.valueOf(cart.getUsername())});
//        if (result == 0){
//            return -1;
//        }
//        return 1;
//    }

        //updateAmount
    public int updateCartAmount(ShoppingCart cart){
        ContentValues values = new ContentValues();
        values.put("idProduct",cart.getIdProduct());
        values.put("username",cart.getUsername());
        values.put("amount",cart.getAmount());
        int result = db.update(TABLE_NAME,values,"username=? and idProduct=?", new String[]{cart.getUsername(), cart.getIdProduct()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteCart(String user, String idProduct){
        int result = db.delete(TABLE_NAME,"username=? and idProduct=?",new String[]{user, idProduct});
        if (result == 0)
            return -1;
        return 1;
    }

    public ShoppingCart checkID(String user, String idProduct){
        String sql = "select * from Cart where username=? and idProduct=?";
        List<ShoppingCart> list = getData(sql, user, idProduct);
        if (list.size() != 0)
            return list.get(0);
        else
            return null;
    }

    public List<ShoppingCart> getTheoUser(String user){
        String sql = "select * from Cart where username=?";
        List<ShoppingCart> list = getData(sql, user);
        return list;
    }

    public List<ShoppingCart> getData(String sql, String...strings){
        List<ShoppingCart> carts= new ArrayList<>();
        Cursor c = db.rawQuery(sql, strings);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            ShoppingCart ee = new ShoppingCart();
            ee.setIdProduct(c.getString(0));
            ee.setUsername(c.getString(1));
            ee.setAmount(c.getInt(2));
            int select = c.getInt(3);
            if (select == 0){
                ee.setSelect(false);
            } else {
                ee.setSelect(true);
            }
            carts.add(ee);
            Log.d("//====================",ee.toString());
            c.moveToNext();
        }
        c.close();
        return carts;
    }
}
