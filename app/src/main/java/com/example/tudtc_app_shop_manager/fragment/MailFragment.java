package com.example.tudtc_app_shop_manager.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.DTO.HoaDonDAO;
import com.example.tudtc_app_shop_manager.HoaDonChiTietActivity;
import com.example.tudtc_app_shop_manager.ListHoaDonActivity;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.adapter.HoaDonAdapter;
import com.example.tudtc_app_shop_manager.model.HoaDon;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MailFragment extends Fragment {
    private View view;
    private ListView lv;
    private HoaDonDAO hoaDonDAO;
    private HoaDonChiTietDAO donChiTietDAO;
    private HoaDonAdapter adapter;
    private List<HoaDon> hoaDons = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mail, container, false);
        lv = view.findViewById(R.id.lv_don);
        hoaDonDAO = new HoaDonDAO(getActivity());
        donChiTietDAO = new HoaDonChiTietDAO(getActivity());
        try {
            hoaDons = hoaDonDAO.getIDUsername(getUserName());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new HoaDonAdapter(hoaDons, getActivity());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu menu = new PopupMenu(getActivity(), view);
                menu.getMenuInflater().inflate(R.menu.menu_hoadon_user, menu.getMenu());
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.act_gotodetal:
                                gotoDetal(position);
                                break;

                            case R.id.act_huy:
                                huyDon(position);
                                break;

                            case R.id.act_xoaDon:
                                xoa(position);
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        return view;
    }

    private void gotoDetal(int position) {
        Intent intent = new Intent(getActivity(), HoaDonChiTietActivity.class);
        intent.putExtra("idHoaDon", String.valueOf(hoaDons.get(position).getId()));
        startActivity(intent);
    }

    private void xoa(int position) {
        if (hoaDons.get(position).getId() != 0){
            Toast.makeText(getActivity(), "Chỉ được xóa đơn hàng khi đã hủy", Toast.LENGTH_SHORT).show();
            return;
        }
        if (donChiTietDAO.deleteHoaDonChiTietByID(String.valueOf(hoaDons.get(position).getId())) == 1){
        }
        if (hoaDonDAO.deleteHoaDonByID(String.valueOf(hoaDons.get(position).getId())) == 1){
            hoaDons.remove(position);
            adapter.setData(hoaDons);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            hoaDons = hoaDonDAO.getIDUsername(getUserName());
            adapter.setData(hoaDons);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void huyDon(int position) {
        HoaDon don = hoaDons.get(position);
        if (don.getStatus() != 0){
            Toast.makeText(getActivity(), "Chỉ được hủy đơn hàng khi đơn đang chờ xác nhận", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nhắc nhở")
                .setMessage("Bạn có chắc chắn hủy đơn hàng này ?")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        don.setStatus(3);
                        if (hoaDonDAO.updateHoaDon(don) == 1){
                            hoaDons.get(position).setStatus(3);
                            adapter.setData(hoaDons);
                            Toast.makeText(getActivity(), "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    public String getUserName(){
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE",getActivity().MODE_PRIVATE);
        String username = pref.getString("USERNAME", "");
        return username;
    }
}