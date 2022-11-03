package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.tudtc_app_shop_manager.DTO.UserDAO;
import com.example.tudtc_app_shop_manager.adapter.UserAdapter;
import com.example.tudtc_app_shop_manager.model.User;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {
    private List<User> list = new ArrayList<>();
    private List<User> listSearch = new ArrayList<>();
    private UserDAO userDAO;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        anhxa();

        list = userDAO.getAllUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setData(list);

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

    private void anhxa() {
        recyclerView = findViewById(R.id.RecyclerView_user);
        searchView = findViewById(R.id.sv_user);

        userDAO = new UserDAO(getBaseContext());
        adapter = new UserAdapter(list, this);
    }

    private void filter(String string){
        listSearch.clear();
        for (User user:list) {
            if (user.getUserName().toLowerCase().contains(string.toLowerCase())){
                listSearch.add(user);
            }
        }
        adapter.setData(listSearch);
    }

    public void closeListUser(View view) {
        onBackPressed();
    }
}