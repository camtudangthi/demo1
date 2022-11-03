package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.adapter.ProductAdapter;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {
    private Intent intent;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList= new ArrayList<>();
    private List<Product> listSeach= new ArrayList<>();
    private ProductDAO productDAO;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        anhxa();
        productList = productDAO.getAllProduct();

        adapter = new ProductAdapter(productList, ListProductActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String newText) {
        listSeach.clear();
        for (Product product:productList) {
            if (product.getName().toLowerCase().contains(newText.toLowerCase())){
                listSeach.add(product);
            }
        }
        adapter.setData(listSeach);
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.RecyclerView_product);
        searchView = findViewById(R.id.sv_product);

        productDAO = new ProductDAO(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        productList = productDAO.getAllProduct();
        adapter.setData(productList);
    }

    public void gotoProduct(View view) {
        intent = new Intent(getBaseContext(), ProductActivity.class);
        intent.putExtra("action", 1);
        startActivity(intent);
    }

    public void closeListPro(View view) {
        onBackPressed();
    }
}