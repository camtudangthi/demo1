package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.CartDAO;
import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.Product;
import com.example.tudtc_app_shop_manager.model.ShoppingCart;

public class ProductDetalActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName, tvId, tvDescription, tvPrice, tvAmount, tvAdd;
    private ImageView imgPicture, imgGoToCart, imgBack;
    private Button btnBuyNow;
    private Intent intent;
    private ProductDAO productDAO;
    private CartDAO cartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detal);
        anhxa();
        setInForProduct();

        btnBuyNow.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgGoToCart.setOnClickListener(this);
        tvAdd.setOnClickListener(this);

        cartDAO = new CartDAO(this);
    }

    private void anhxa() {
        tvName = findViewById(R.id.tv_name_pro_detal);
        tvId = findViewById(R.id.tv_id_pro_detal);
        tvPrice = findViewById(R.id.tv_price_pro_detal);
        tvAmount = findViewById(R.id.tv_amount_pro_detal);
        tvDescription = findViewById(R.id.tv_des_pro_detal);
        tvAdd = findViewById(R.id.tv_addCart);
        imgPicture = findViewById(R.id.img_product_detal);
        imgGoToCart = findViewById(R.id.img_goToCart);
        imgBack = findViewById(R.id.img_close_pro_detal);
        btnBuyNow = findViewById(R.id.btn_buy_now);
    }

    private void setInForProduct() {
        intent = getIntent();
        if (intent != null){
            String id = intent.getStringExtra("idProduct");
            productDAO = new ProductDAO(this);
            Product product = productDAO.checkID(id);

            tvId.setText(product.getId());
            tvName.setText(product.getName());
            tvDescription.setText(product.getDescription());
            tvPrice.setText(ChuyenDoi.SoString(product.getPrice()) + " vnd");
            tvAmount.setText("Còn lại: " + product.getAmount());
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            imgPicture.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buy_now:
                Toast.makeText(this, "Mua thành công", Toast.LENGTH_SHORT).show();
                return;

            case R.id.tv_addCart:
                addShoppingCart();
                return;
            case R.id.img_goToCart:
                gotoCart();
                return;

            case R.id.img_close_pro_detal:
                onBackPressed();
                return;
        }
    }

    private void addShoppingCart() {
        String username = getUserName();
        String id = tvId.getText().toString();
        ShoppingCart cart = new ShoppingCart(id, username, 1, false);
        Toast.makeText(this, cart.toString(), Toast.LENGTH_SHORT).show();

        if (cartDAO.checkID(username, id) != null){
            Toast.makeText(this, "Sản phẩm đã có trong giỏ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cartDAO.inserCart(cart) == 1){
            Toast.makeText(ProductDetalActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProductDetalActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoCart() {
        startActivity(new Intent(ProductDetalActivity.this, CartActivity.class));
    }

    public String getUserName(){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String username = pref.getString("USERNAME", "");
        return username;
    }
}