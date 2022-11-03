package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.DTO.HoaDonDAO;
import com.example.tudtc_app_shop_manager.adapter.HoaDonAdapter;
import com.example.tudtc_app_shop_manager.model.HoaDon;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListHoaDonActivity extends AppCompatActivity {
    Spinner spinner;
    ListView lv;
    private List<HoaDon> hoaDons = new ArrayList<>();
    private HoaDonDAO hoaDonDAO;
    private HoaDonChiTietDAO donChiTietDAO;
    public HoaDonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don);

        spinner = findViewById(R.id.spn_status_don);
        lv = findViewById(R.id.lv_hoadon);
        hoaDonDAO = new HoaDonDAO(this);
        donChiTietDAO = new HoaDonChiTietDAO(this);
        try {
            hoaDons = hoaDonDAO.getAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter = new HoaDonAdapter(hoaDons, this);
        lv.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.status_don, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setSelection(4);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 4){
                    try {
                        hoaDons = hoaDonDAO.getAllHoaDon();
                        adapter.setData(hoaDons);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {
                    hoaDons = hoaDonDAO.getStatus(position);
                    adapter.setData(hoaDons);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu menu = new PopupMenu(ListHoaDonActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_hoadon_admin, menu.getMenu());
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.act_detal:
                                gotoDetal(position);
                                break;

                            case R.id.act_stat_0:
                                suatrangthai(position, 0);
                                break;

                            case R.id.act_stat_1:
                                suatrangthai(position, 1);
                                break;
                            case R.id.act_stat_2:
                                suatrangthai(position, 2);
                                break;
                            case R.id.act_stat_3:
                                suatrangthai(position, 3);
                                break;

                            case R.id.act_xoa:
                                xoa(position);
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void xoa(int position) {
        if (donChiTietDAO.deleteHoaDonChiTietByID(String.valueOf(hoaDons.get(position).getId())) == 1){
        }
        if (hoaDonDAO.deleteHoaDonByID(String.valueOf(hoaDons.get(position).getId())) == 1){
            hoaDons.remove(position);
            adapter.setData(hoaDons);
        }
    }

    private void suatrangthai(int position, int i) {
        HoaDon don = new HoaDon();
        don.setId(hoaDons.get(position).getId());
        don.setStatus(i);
        Toast.makeText(this, don.toString(), Toast.LENGTH_SHORT).show();
        if (hoaDonDAO.updateStatus(don) == 1) {
            hoaDons.get(position).setStatus(i);
            adapter.setData(hoaDons);
        }
    }

    private void gotoDetal(int position) {
        Intent intent = new Intent(ListHoaDonActivity.this, HoaDonChiTietActivity.class);
        intent.putExtra("idHoaDon", String.valueOf(hoaDons.get(position).getId()));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            hoaDons = hoaDonDAO.getAllHoaDon();
            adapter.setData(hoaDons);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void closeListHD(View view) {
        onBackPressed();
    }
}