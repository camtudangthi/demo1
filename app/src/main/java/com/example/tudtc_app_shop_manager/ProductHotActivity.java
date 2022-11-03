package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.adapter.ProductAdapter;
import com.example.tudtc_app_shop_manager.model.ChiTietHoaDon;
import com.example.tudtc_app_shop_manager.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductHotActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList= new ArrayList<>();
    private List<ChiTietHoaDon> list = new ArrayList<>();
    private HoaDonChiTietDAO donChiTietDAO;
    private ProductDAO productDAO;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_hot);
        recyclerView = findViewById(R.id.rv_top);
        editText = findViewById(R.id.edt_month);

        donChiTietDAO = new HoaDonChiTietDAO(this);
        productDAO = new ProductDAO(this);
        adapter = new ProductAdapter(productList, ProductHotActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int month = Integer.parseInt(s.toString());
                if (month >12){
                    Toast.makeText(ProductHotActivity.this, "Tháng từ 1 đến 12", Toast.LENGTH_SHORT).show();
                    return;
                }
                productList = productDAO.getTop(String.valueOf(month));
                adapter.setData(productList);
            }
        });
    }

    public void closeTop(View view) {
        onBackPressed();
    }
}