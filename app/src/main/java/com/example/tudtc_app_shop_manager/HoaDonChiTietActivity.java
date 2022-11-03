package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.adapter.ChiTietAdapter;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.ChiTietHoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietActivity extends AppCompatActivity {
    ListView lv;
    TextView tvSum;
    ChiTietAdapter adapter;
    List<ChiTietHoaDon> list = new ArrayList<>();
    HoaDonChiTietDAO donChiTietDAO;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        lv = findViewById(R.id.lv_chitiet);
        tvSum = findViewById(R.id.tv_tong_tien);
        intent = getIntent();
        String id = intent.getStringExtra("idHoaDon");
        donChiTietDAO = new HoaDonChiTietDAO(this);
        list = donChiTietDAO.getTheoIdHD(id);
        adapter = new ChiTietAdapter(list, this);
        lv.setAdapter(adapter);
        String st = "Tổng thanh toán : " + ChuyenDoi.SoString(donChiTietDAO.getSumMoneyId(id)) + " vnd";
        tvSum.setText(st);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setData(list);
    }

    public void closeChiTiet(View view) {
        onBackPressed();
    }
}