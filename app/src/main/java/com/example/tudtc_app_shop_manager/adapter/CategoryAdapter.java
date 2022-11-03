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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.DTO.CategoryDAO;
import com.example.tudtc_app_shop_manager.ListCategoryActivity;
import com.example.tudtc_app_shop_manager.ProductActivity;
import com.example.tudtc_app_shop_manager.myInterface.IClickGoToAddCate;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{
    private List<Category> listCategory;
    private Context context;
    private CategoryDAO categoryDAO;
    private IClickGoToAddCate clickGoToAddCate;

    public CategoryAdapter(List<Category> listCategory, Context context, IClickGoToAddCate clickGoToAddCate) {
        this.listCategory = listCategory;
        this.context = context;
        this.clickGoToAddCate = clickGoToAddCate;
        this.categoryDAO = new CategoryDAO(context);
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryAdapter.CategoryHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = listCategory.get(position);
        if (category == null){
            return;
        }
        holder.tvName.setText(category.getNameCategory());
        holder.tvDescription.setText(category.getDescriptionCategory());
        Bitmap bitmap = BitmapFactory.decodeByteArray(category.getImage(), 0, category.getImage().length);
        holder.imgHinh.setImageBitmap(bitmap);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_context_del, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_del_item:
                                delItem(position);
                                return true;
                            case R.id.action_info_item:
                                clickGoToAddCate.onClickGoToAddCate(listCategory.get(position));
                                return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void gotoCate(int i) {

    }

    private void delItem(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo")
                .setMessage("Xác nhận xóa ?")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = categoryDAO.deleteCategoryByID(listCategory.get(i).getIdCategory());
                        if (check > 0){
                            Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                            listCategory.remove(i);
                            setData(listCategory);
                        }
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
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
        private ImageView img, imgHinh;
        private TextView tvName, tvDescription;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_cate);
            img = itemView.findViewById(R.id.img_menu_del);
            imgHinh = itemView.findViewById(R.id.img_item_cate);
            tvDescription = itemView.findViewById(R.id.tv_description_item_cate);
            tvName = itemView.findViewById(R.id.tv_name_item_cate);
        }
    }

    public void setData(List<Category> listCategory) {
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }
}
