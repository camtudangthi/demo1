package com.example.tudtc_app_shop_manager.DTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tudtc_app_shop_manager.database.DatabaseHelper;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.User;

import java.util.ArrayList;
import java.util.List;


public class CategoryDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "Category";
    public static final String SQL_CATEGORY ="CREATE TABLE Category (" +
            " id INTEGER primary key AUTOINCREMENT, " +
            "name nvarchar(50)," +
            " description nvarchar(100)," +
            " image BLOG);";
    public static final String TAG = "CategoryDAO";
    public CategoryDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("id",category.getIdCategory());
        values.put("name",category.getNameCategory());
        values.put("description",category.getDescriptionCategory());
        values.put("image",category.getImage());
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
    public List<Category> getAllCategory(){
        String sql = "select * from Category";
        List<Category> list = getData(sql);
        return list;
    }

    //update
    public int updateCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("id",category.getIdCategory());
        values.put("name",category.getNameCategory());
        values.put("description",category.getDescriptionCategory());
        values.put("image",category.getImage());
        int result = db.update(TABLE_NAME,values,"id=?", new String[]{String.valueOf(category.getIdCategory())});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //getID
    public Category getIDCategory(String id){
        String sql = "select * from Category where id=?";
        List<Category> list = getData(sql, id);
        if (list.size() != 0)
            return list.get(0);
        else
            return null;
    }

    //delete
    public int deleteCategoryByID(String id){
        int result = db.delete(TABLE_NAME,"id=?",new String[]{id});
        if (result == 0)
            return -1;
        return 1;
    }

    public List<Category> getData(String sql, String...strings){
        List<Category> listCategory = new ArrayList<>();
        Cursor c = db.rawQuery(sql, strings);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            Category ee = new Category();
            ee.setIdCategory(c.getString(0));
            ee.setNameCategory(c.getString(1));
            ee.setDescriptionCategory(c.getString(2));
            ee.setImage(c.getBlob(3));
            listCategory.add(ee);
            Log.d("//====================",ee.toString());
            c.moveToNext();
        }
        c.close();
        return listCategory;
    }

}
