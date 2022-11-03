package com.example.tudtc_app_shop_manager.DTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tudtc_app_shop_manager.database.DatabaseHelper;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.Product;

import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "Product";
    public static final String SQL_PRODUCT ="CREATE TABLE Product (" +
            " id varchar(20) primary key, " +
            " idCategory smallint," +
            " name nvarchar(50)," +
            " description nvarchar(100)," +
            " price int," +
            " amount smallint," +
            " image BLOG);";
    public static final String TAG = "ProductDAO";
    public ProductDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("id",product.getId());
        values.put("idCategory",product.getIdCategory());
        values.put("name",product.getName());
        values.put("description",product.getDescription());
        values.put("price",product.getPrice());
        values.put("amount",product.getAmount());
        values.put("image",product.getImage());
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
    public List<Product> getAllProduct(){
        String sql = "select * from Product";
        List<Product> list = getData(sql);
        return list;
    }

    //update
    public int updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("id",product.getId());
        values.put("idCategory",product.getIdCategory());
        values.put("name",product.getName());
        values.put("description",product.getDescription());
        values.put("price",product.getPrice());
        values.put("amount",product.getAmount());
        values.put("image",product.getImage());
        int result = db.update(TABLE_NAME,values,"id=?", new String[]{String.valueOf(product.getId())});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteProductByID(String id){
        int result = db.delete(TABLE_NAME,"id=?",new String[]{id});
        if (result == 0)
            return -1;
        return 1;
    }

    public Product checkID(String id){
        String sql = "select * from Product where id=?";
        List<Product> list = getData(sql, id);
        if (list.size() != 0)
            return list.get(0);
        else
            return null;
    }

    public List<Product> getTop(String id){
        String sql = "SELECT * FROM Product INNER JOIN HoaDonChiTiet on HoaDonChiTiet.idProduct = Product.id \n" +
                "INNER JOIN HoaDon ON HoaDon.id = HoaDonChiTiet.idHD WHERE strftime('%m',HoaDon.ngaymua) = ?\n" +
                "GROUP BY idProduct ORDER BY sum(HoaDonChiTiet.soluong) DESC LIMIT 10";
        List<Product> list = getData(sql, id);
        return list;
    }

    public List<Product> getData(String sql, String...strings){
        List<Product> listProduct= new ArrayList<>();
        Cursor c = db.rawQuery(sql, strings);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            Product ee = new Product();
            ee.setId(c.getString(0));
            ee.setIdCategory(c.getString(1));
            ee.setName(c.getString(2));
            ee.setDescription(c.getString(3));
            ee.setPrice(c.getInt(4));
            ee.setAmount(c.getInt(5));
            ee.setImage(c.getBlob(6));
            listProduct.add(ee);
            Log.d("//====================",ee.toString());
            c.moveToNext();
        }
        c.close();
        return listProduct;
    }
}
