package com.example.tudtc_app_shop_manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.HoaDonChiTietActivity;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.HoaDon;

import java.util.List;

public class HoaDonAdapter extends BaseAdapter {
    private List<HoaDon> hoaDons;
    private Context context;
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    public HoaDonAdapter(List<HoaDon> hoaDons, Context context) {
        this.hoaDons = hoaDons;
        this.context = context;
        this.hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return hoaDons.size();
    }

    @Override
    public Object getItem(int position) {
        return hoaDons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return hoaDons.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
        HoaDon don = hoaDons.get(position);
        if (don == null){
            return null;
        }
        ViewHolder holder = new ViewHolder(convertView);

        holder.tvId.setText(don.getId() + " - " + don.getUsername());
        switch (don.getStatus()){
            case 0:
                holder.tvStatus.setTextColor(Color.CYAN);
                holder.tvStatus.setText("Chờ xác nhận");
                break;
            case 1:
                holder.tvStatus.setTextColor(Color.BLUE);
                holder.tvStatus.setText("Đang xử lý");
                break;
            case 2:
                holder.tvStatus.setTextColor(Color.GREEN);
                holder.tvStatus.setText("Đã nhận hàng");
                break;

            case 3:
                holder.tvStatus.setTextColor(Color.RED);
                holder.tvStatus.setText("Đã hủy");
                break;
        }
        holder.tvDate.setText(ChuyenDoi.LayNgayString(don.getDatePurchase()));
        holder.tvMoney.setText(ChuyenDoi.SoString(hoaDonChiTietDAO.getSumMoneyId(String.valueOf(don.getId()))) + "vnd");
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout layout;
        private TextView tvId, tvStatus, tvDate, tvMoney;

        public ViewHolder(View itemView) {
            layout = itemView.findViewById(R.id.layout_item_hoadon);
            tvId = itemView.findViewById(R.id.tv_id_item);
            tvStatus = itemView.findViewById(R.id.tv_status_item);
            tvDate = itemView.findViewById(R.id.tv_date_item);
            tvMoney = itemView.findViewById(R.id.tv_sum_money);
        }
    }

    public void setData(List<HoaDon> hoaDons) {
        this.hoaDons = hoaDons;
        notifyDataSetChanged();
    }
}
