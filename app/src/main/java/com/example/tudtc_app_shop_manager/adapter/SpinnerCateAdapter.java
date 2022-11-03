package com.example.tudtc_app_shop_manager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.model.Category;

import java.util.List;

public class SpinnerCateAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Category> categoryList;

    public SpinnerCateAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.custom_spinner_cate_adapter, null);
        Category category = categoryList.get(position);
        TextView tvName = convertView.findViewById(R.id.tv_item_spinner_cate);
        tvName.setText(category.getNameCategory());
        ImageView imageView = convertView.findViewById(R.id.img_item_spinner_cate);
        Bitmap bitmap = BitmapFactory.decodeByteArray(category.getImage(), 0, category.getImage().length);
        imageView.setImageBitmap(bitmap);
        return convertView;
    }

    public void setData(List<Category> listCategory) {
        this.categoryList = listCategory;
        notifyDataSetChanged();
    }
}
