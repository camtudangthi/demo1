package com.example.tudtc_app_shop_manager.DTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.database.DatabaseHelper;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.HoaDon;
import com.example.tudtc_app_shop_manager.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 6/15/18.
 */
public class HoaDonDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "HoaDon";
    public static final String SQL_HOA_DON ="CREATE TABLE HoaDon (" +
            " id INTEGER primary key AUTOINCREMENT," +
            " ngaymua date," +
            " username varchar(30)," +
            " paid bit," +
            " status byte)";
    public static final String TAG = "HoaDonDAO";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public HoaDonDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<HoaDon> getData(String sql, String...strings) throws ParseException {
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, strings);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            HoaDon ee = new HoaDon();
            ee.setId(c.getInt(0));
            ee.setDatePurchase(sdf.parse(c.getString(1)));
            ee.setUsername(c.getString(2));
            if (c.getInt(3) == 1){
                ee.setPaid(true);
            } else {
                ee.setPaid(false);
            }
            ee.setStatus(c.getInt(4));
            list.add(ee);
            Log.d("//=====",ee.toString());
            c.moveToNext();
        }
        c.close();
        return list;
    }

    //insert
    public int inserHoaDon(HoaDon hd){
        ContentValues values = new ContentValues();
//        values.put("id",hd.getId());
        values.put("ngaymua",sdf.format(hd.getDatePurchase()));
        values.put("username",hd.getUsername());
        values.put("paid",hd.isPaid());
        values.put("status",String.valueOf(hd.getStatus()));
        try {
            if(db.insert(TABLE_NAME,null,values)== -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }

    //get theo id người dùng
    public List<HoaDon> getIDUsername(String id) throws ParseException {
        String sql = "select * from Hoadon where username=?";
        List<HoaDon> list = getData(sql, id);
        return list;
    }

    //lấy theo trạng thái đơn hàng
    public List<HoaDon> getStatus(int status) throws ParseException {
        String sql = "select * from Hoadon where status=?";
        List<HoaDon> list = getData(sql, String.valueOf(status));
        return list;
    }

    public int getIdHoaDon(String id) throws ParseException {
        String sql = "SELECT * FROM Hoadon WHERE username=? ORDER BY id DESC";
        List<HoaDon> list = getData(sql, id);
        if (list.size() != 0)
            return list.get(0).getId();
        else
            return 0;
    }


    //getAll
    public List<HoaDon> getAllHoaDon() throws ParseException {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            HoaDon ee = new HoaDon();
            ee.setId(c.getInt(0));
            ee.setDatePurchase(sdf.parse(c.getString(1)));
            ee.setUsername(c.getString(2));
            if (c.getInt(3) == 1){
                ee.setPaid(true);
            } else {
                ee.setPaid(false);
            }
            ee.setStatus(c.getInt(4));
            dsHoaDon.add(ee);
            Log.d("//=====",ee.toString());
            c.moveToNext();
        }
        c.close();
        return dsHoaDon;
    }
    //update
    public int updateHoaDon(HoaDon hd){
        ContentValues values = new ContentValues();
        values.put("id",hd.getId());
        values.put("ngaymua",sdf.format(hd.getDatePurchase()));
        values.put("username",hd.getUsername());
        values.put("paid",hd.isPaid());
        values.put("status",String.valueOf(hd.getStatus()));
        int result = db.update(TABLE_NAME,values,"id=?", new String[]{String.valueOf(hd.getId())});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int updateStatus(HoaDon hd){
        ContentValues values = new ContentValues();
        values.put("id",hd.getId());
        Log.i("tag ============", hd.getId() +" " + hd.getStatus());
        values.put("status",hd.getStatus());
        int result = db.update(TABLE_NAME,values,"id=?", new String[]{String.valueOf(hd.getId())});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteHoaDonByID(String mahoadon){
        int result = db.delete(TABLE_NAME,"id=?",new String[]{mahoadon});
        if (result == 0)
            return -1;
        return 1;
    }

    public int getSoLuongDon(int id){
        String sql = "SELECT count(id) FROM HoaDon WHERE strftime('%m',HoaDon.ngayMua) = strftime('%m','now') AND status = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});
        int count = 0;
        c.moveToFirst();
        while (c.isAfterLast()==false){
            count = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        return count;
    }
}
