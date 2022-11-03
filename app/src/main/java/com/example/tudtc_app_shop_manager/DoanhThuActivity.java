package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.DTO.HoaDonDAO;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;

public class DoanhThuActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4;
    HoaDonChiTietDAO donChiTietDAO;
    HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        tv1 = findViewById(R.id.tv_da_nhan);
        tv2 = findViewById(R.id.tv_xuly);
        tv3 = findViewById(R.id.tv_cho);
        tv4 = findViewById(R.id.tv_huy);
        donChiTietDAO = new HoaDonChiTietDAO(this);
        hoaDonDAO = new HoaDonDAO(this);

        String text = "Đơn đã hủy:\nSố lượng: " + hoaDonDAO.getSoLuongDon(3)
                + " - " + "Tổng tiền: "+ ChuyenDoi.SoString(donChiTietDAO.getDoanhThu(3)) + " vnd";
        tv4.setText(text);

        text = "Đơn đã nhận:\nSố lượng: " + hoaDonDAO.getSoLuongDon(2)
                + " - " + "Tổng tiền: "+ ChuyenDoi.SoString(donChiTietDAO.getDoanhThu(2)) + " vnd";
        tv3.setText(text);

        text = "Đơn đang xử lý:\nSố lượng: " + hoaDonDAO.getSoLuongDon(1)
                + " - " + "Tổng tiền: "+ ChuyenDoi.SoString(donChiTietDAO.getDoanhThu(1)) + " vnd";
        tv2.setText(text);

        text = "Đơn chờ xác nhận:\nSố lượng: " + hoaDonDAO.getSoLuongDon(0);
        tv1.setText(text);
    }

    public void closeDoanhThu(View view) {
        onBackPressed();
    }
}