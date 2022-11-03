package com.example.tudtc_app_shop_manager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.ChiTietHoaDon;

import java.util.List;

public class ChiTietAdapter extends BaseAdapter {
    private List<ChiTietHoaDon> chiTietHoaDons;
    private Context context;
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    public ChiTietAdapter(List<ChiTietHoaDon> hoaDons, Context context) {
        this.chiTietHoaDons = hoaDons;
        this.context = context;
        this.hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return chiTietHoaDons.size();
    }

    @Override
    public Object getItem(int position) {
        return chiTietHoaDons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chiTietHoaDons.get(position).getHoaDon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        ChiTietHoaDon chiTietHoaDon = chiTietHoaDons.get(position);
        if (chiTietHoaDon == null){
            return null;
        }
        ViewHolder holder = new ViewHolder(convertView);

        holder.tvName.setText(chiTietHoaDon.getProduct().getName() + "");
        holder.tvPrice.setText("Giá : " + ChuyenDoi.SoString(chiTietHoaDon.getProduct().getPrice()) + " vnd");
        holder.tvAmount.setText("Số lượng : " + chiTietHoaDon.getSoLuongMua());
        holder.tvMoney.setText("Thành tiền : "+ChuyenDoi.SoString(chiTietHoaDon.getProduct().getPrice()*chiTietHoaDon.getSoLuongMua()) + " vnd");
        return convertView;
    }

    private class ViewHolder {
        private TextView tvName, tvPrice, tvAmount, tvMoney;

        public ViewHolder(View itemView) {
            tvName = itemView.findViewById(R.id.tv_name_1);
            tvPrice = itemView.findViewById(R.id.tv_price1);
            tvAmount = itemView.findViewById(R.id.tv_amoun1);
            tvMoney = itemView.findViewById(R.id.tv_money);
        }
    }

    public void setData(List<ChiTietHoaDon> hoaDons) {
        this.chiTietHoaDons = hoaDons;
        notifyDataSetChanged();
    }
}
