package com.example.tudtc_app_shop_manager.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.ProductActivity;
import com.example.tudtc_app_shop_manager.ProductDetalActivity;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.Product;

import java.util.List;

public class Product2Adapter extends RecyclerView.Adapter<Product2Adapter.ProductHolder>{
    private List<Product> listProduct;
    private Context context;
    private ProductDAO productDAO;

    public Product2Adapter(List<Product> listProduct, Context context) {
        this.listProduct = listProduct;
        this.context = context;
        this.productDAO = new ProductDAO(context);
    }

    @NonNull
    @Override
    public Product2Adapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_main, parent, false);
        return new Product2Adapter.ProductHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = listProduct.get(position);
        if (product == null){
            return;
        }
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(ChuyenDoi.SoString(product.getPrice()) + " vnd");
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.img.setImageBitmap(bitmap);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProduct(position);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
        holder.imgNew.setAnimation(animation);
    }


    private void gotoProduct(int position) {
        Intent intent = new Intent(context, ProductDetalActivity.class);
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
        private ImageView img, imgNew;
        private TextView tvName, tvPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_product_main);
            img = itemView.findViewById(R.id.img_item_picture);
            imgNew = itemView.findViewById(R.id.img_pro_new);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_item_price);
        }
    }

    public void setData(List<Product> listProduct) {
        this.listProduct = listProduct;
        notifyDataSetChanged();
    }
}
