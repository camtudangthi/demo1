package com.example.tudtc_app_shop_manager.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.myInterface.IClickGoToAddCate;

import java.util.List;

public class Category2Adapter extends RecyclerView.Adapter<Category2Adapter.CategoryHolder>{
    private List<Category> listCategory;
    private Context context;

    public Category2Adapter(List<Category> listCategory, Context context) {
        this.listCategory = listCategory;
        this.context = context;
    }

    @NonNull
    @Override
    public Category2Adapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category2, parent, false);
        return new Category2Adapter.CategoryHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = listCategory.get(position);
        if (category == null){
            return;
        }
        holder.tvName.setText(category.getNameCategory());
        Bitmap bitmap = BitmapFactory.decodeByteArray(category.getImage(), 0, category.getImage().length);
        holder.img.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (listCategory != null){
            return listCategory.size();
        }
        return 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private ImageView img;
        private TextView tvName;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_cate2);
            img = itemView.findViewById(R.id.img_cate2);
            tvName = itemView.findViewById(R.id.tv_name_item_cate2);
        }
    }

    public void setData(List<Category> listCategory) {
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }
}
