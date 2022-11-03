package com.example.tudtc_app_shop_manager.DTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.tudtc_app_shop_manager.model.User;
import com.example.tudtc_app_shop_manager.database.DatabaseHelper;


public class UserDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "User";
    public static final String SQL_USER ="CREATE TABLE User (username text primary key, " +
            "password text, phone numberic(10), fullname text,gender byte, block bit);";
    public static final String TAG = "UserDAO";
    public UserDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int inserUser(User nd){
        ContentValues values = new ContentValues();
        values.put("username",nd.getUserName());
        values.put("password",nd.getPassword());
        values.put("phone",nd.getPhone());
        values.put("fullname",nd.getFullName());
        values.put("gender",nd.getGender());
        values.put("block","0");
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
    public List<User> getAllUser(){
        String sql = "select * from User";
        List<User> list = getData(sql);
        return list;
    }

    //update
    public int updateNguoiDung(User nd){
        ContentValues values = new ContentValues();
        values.put("username",nd.getUserName());
        values.put("password",nd.getPassword());
        values.put("phone",nd.getPhone());
        values.put("fullname",nd.getFullName());
        values.put("gender",nd.getGender());
        int result = db.update(TABLE_NAME,values,"username=?", new String[]{nd.getUserName()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int changePasswordUser(User nd){
        ContentValues values = new ContentValues();
        values.put("username",nd.getUserName());
        values.put("password",nd.getPassword());
        int result = db.update(TABLE_NAME,values,"username=?", new String[]{nd.getUserName()});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public int updateInfoNguoiDung(String username,String phone, String name, byte gender){
        ContentValues values = new ContentValues();
        values.put("phone",phone);
        values.put("fullname",name);
        values.put("gender", gender);
        int result = db.update(TABLE_NAME,values,"username=?", new String[]{username});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //delete
    public int upBlackList(String username, int block){
        ContentValues values = new ContentValues();
        values.put("block", block);
        int result = db.update(TABLE_NAME,values,"username=?", new String[]{username});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //check login
    public User checkLogin(String username, String pass){
        String sql = "select * from User where username = ? and password=?";
        List<User> list = getData(sql, username, pass);
        if (list.size() != 0)
            return list.get(0);
        else
            return null;
    }

    public List<User> getBlackList(){
        String sql = "select * from User where block = 1";
        List<User> list = getData(sql);
        return list;
    }

    public List<User> getData(String sql, String...strings){
        List<User> dsNguoiDung = new ArrayList<>();
        Cursor c = db.rawQuery(sql, strings);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            User ee = new User();
            ee.setUserName(c.getString(0));
            ee.setPassword(c.getString(1));
            ee.setPhone(c.getString(2));
            ee.setFullName(c.getString(3));
            ee.setGender(c.getExtras().getByte(String.valueOf(4)));
            if (c.getString(5).equals("1")){
                ee.setBlock(true);
            } else {
                ee.setBlock(false);
            }
            dsNguoiDung.add(ee);
            Log.d("//====================",ee.toString());
            c.moveToNext();
        }
        c.close();
        return dsNguoiDung;
    }

}
