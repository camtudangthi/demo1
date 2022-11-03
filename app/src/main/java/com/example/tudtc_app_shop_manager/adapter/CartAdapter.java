package com.example.tudtc_app_shop_manager.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.CartActivity;
import com.example.tudtc_app_shop_manager.DTO.CartDAO;
import com.example.tudtc_app_shop_manager.DTO.CategoryDAO;
import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.ProductDetalActivity;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.Product;
import com.example.tudtc_app_shop_manager.model.ShoppingCart;
import com.example.tudtc_app_shop_manager.myInterface.IClickGoToAddCate;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CategoryHolder>{
    private List<ShoppingCart> carts;
    private CartActivity context;
    private CartDAO cartDAO;
    private ProductDAO productDAO;

    public CartAdapter(List<ShoppingCart> carts, CartActivity context) {
        this.carts = carts;
        this.context = context;
        this.cartDAO = new CartDAO(context);
        this.productDAO = new ProductDAO(context);
    }

    @NonNull
    @Override
    public CartAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.CategoryHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, @SuppressLint("RecyclerView") int position) {
        ShoppingCart cart = carts.get(position);
        if (cart == null){
            return;
        }
        Product product = productDAO.checkID(cart.getIdProduct());
        if (product == null){
            return;
        }
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(ChuyenDoi.SoString(product.getPrice()) + " vnd");
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        holder.img.setImageBitmap(bitmap);
        holder.edtAmount.setText(String.valueOf(cart.getAmount()));
        if (cart.isSelect()){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                context.setSelectCart(isChecked, position);
            }
        });

        holder.tvGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.edtAmount.setText(String.valueOf(GiamSoLuong(position)));
            }
        });

        holder.tvTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.edtAmount.setText(String.valueOf(TangSoLuong(position)));
            }
        });

        holder.edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setAmount(position, s.toString());
            }
        });

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delCart(position);
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProduct(position);
            }
        });
    }

    private void delCart(int position) {
        if (cartDAO.deleteCart(carts.get(position).getUsername(), carts.get(position).getIdProduct()) == 1){
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            carts.remove(position);
            setData(carts);
            context.TongTien();
        }
    }

    @Override
    public int getItemCount() {
        if (carts != null){
            return carts.size();
        }
        return 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private ImageView img, imgDel;
        private CheckBox checkBox;
        private TextView tvName, tvPrice, tvGiam, tvTang;
        private EditText edtAmount;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_cart);
            img = itemView.findViewById(R.id.img_pro_cart);
            imgDel = itemView.findViewById(R.id.img_del_cart);
            checkBox = itemView.findViewById(R.id.chk_buy);
            tvName = itemView.findViewById(R.id.tv_name_pro_cart);
            tvPrice = itemView.findViewById(R.id.tv_price_cart);
            tvGiam = itemView.findViewById(R.id.tv_giam);
            tvTang = itemView.findViewById(R.id.tv_tang);
            edtAmount = itemView.findViewById(R.id.edt_amount_cart);
        }
    }

    public void setData(List<ShoppingCart> carts) {
        this.carts = carts;
        notifyDataSetChanged();
    }

    public int GiamSoLuong(int position) {
        int amountNew = carts.get(position).getAmount();
        if (amountNew == 1){
            return carts.get(position).getAmount();
        }
        return amountNew-1;
    }

    public void setAmount(int position, String toString) {
        Product product = productDAO.checkID(carts.get(position).getIdProduct());
        int amountNew;
        if (ChuyenDoi.SoDouble(toString) > product.getAmount()){
            Toast.makeText(context, "Số lượng vượt quá sản phẩm có sẵn", Toast.LENGTH_SHORT).show();
            amountNew = product.getAmount();
        } else {
            amountNew = (int) ChuyenDoi.SoDouble(toString);
        }
        carts.get(position).setAmount(amountNew);
        if (cartDAO.updateCartAmount(carts.get(position)) == 1){
            context.TongTien();
        }
    }

    public int TangSoLuong(int position) {
        int amountNew = carts.get(position).getAmount();
        Product product = productDAO.checkID(carts.get(position).getIdProduct());
        if (carts.get(position).getAmount() == product.getAmount()){
            Toast.makeText(context, "Số lượng vượt quá sản phẩm có sẵn", Toast.LENGTH_SHORT).show();
            return amountNew;
        }
        return amountNew + 1;
    }

    private void gotoProduct(int position) {
        Intent intent = new Intent(context, ProductDetalActivity.class);
        intent.putExtra("idProduct", carts.get(position).getIdProduct());
        context.startActivity(intent);
    }
}
