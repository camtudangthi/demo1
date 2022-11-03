package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.CategoryDAO;
import com.example.tudtc_app_shop_manager.adapter.CategoryAdapter;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.User;
import com.example.tudtc_app_shop_manager.myInterface.IClickGoToAddCate;

import java.util.ArrayList;
import java.util.List;

public class ListCategoryActivity extends AppCompatActivity {
    private Intent intent;
    private ImageButton btngotoAdd;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private CategoryDAO categoryDAO;
    private List<Category> list = new ArrayList<>();
    private List<Category> listSearch = new ArrayList<>();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        anhxa();
        categoryDAO = new CategoryDAO(this);
        list = categoryDAO.getAllCategory();
        adapter = new CategoryAdapter(list, ListCategoryActivity.this, new IClickGoToAddCate() {
            @Override
            public void onClickGoToAddCate(Category category) {
                intent = new Intent(ListCategoryActivity.this, CategoryActivity.class);
                intent.putExtra("IDCategory", String.valueOf(category.getIdCategory()));
                intent.putExtra("action", 1);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCategoryActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btngotoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddCategory(0);
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        list = categoryDAO.getAllCategory();
        adapter.setData(list);
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.RecyclerView_category);
        btngotoAdd = findViewById(R.id.btn_add_category);
        searchView = findViewById(R.id.sv_category);
    }

    public void gotoAddCategory(int i) {
        intent = new Intent(ListCategoryActivity.this, CategoryActivity.class);
        intent.putExtra("action", i);
        startActivity(intent);
    }

    private void filter(String string){
        listSearch.clear();
        for (Category category:list) {
            if (category.getNameCategory().toLowerCase().contains(string.toLowerCase())){
                listSearch.add(category);
            }
        }
        adapter.setData(listSearch);
    }

    public void closeListCate(View view) {
        onBackPressed();
    }
}