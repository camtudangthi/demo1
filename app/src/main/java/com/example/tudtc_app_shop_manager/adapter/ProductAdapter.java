package com.example.tudtc_app_shop_manager.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.ProductActivity;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.Product;
import com.example.tudtc_app_shop_manager.myInterface.IClickGoToAddCate;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{
    private List<Product> listProduct;
    private Context context;
    private ProductDAO productDAO;

    public ProductAdapter(List<Product> listProduct, Context context) {
        this.listProduct = listProduct;
        this.context = context;
        this.productDAO = new ProductDAO(context);
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductAdapter.ProductHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = listProduct.get(position);
        if (product == null){
            return;
        }
        holder.tvName.setText(product.getName());
        holder.tvAmount.setText("SL: " + String.valueOf(product.getAmount()));
        holder.tvPrice.setText(ChuyenDoi.SoString(product.getPrice()) + " vnd");
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.img.setImageBitmap(bitmap);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProduct(position);
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delProduct(position);
                return false;
            }
        });
    }

    private void delProduct(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo")
                .setMessage("Xác nhận xóa sản phẩm này ?")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = productDAO.deleteProductByID(listProduct.get(position).getId());
                        if (check > 0){
                            Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                            listProduct.remove(position);
                            setData(listProduct);
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

    private void gotoProduct(int position) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("action", 2);
        intent.putExtra("idProduct", listProduct.get(position).getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (listProduct != null){
            return listProduct.size();
        }
        return 0;
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private ImageView img;
        private TextView tvName, tvAmount, tvPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_product);
            img = itemView.findViewById(R.id.img_item_product);
            tvAmount = itemView.findViewById(R.id.tv_amount_item);
            tvName = itemView.findViewById(R.id.tv_name_item_product);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

    public void setData(List<Product> listProduct) {
        this.listProduct = listProduct;
        notifyDataSetChanged();
    }
}
