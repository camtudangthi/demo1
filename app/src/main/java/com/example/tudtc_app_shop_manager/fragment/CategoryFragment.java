package com.example.tudtc_app_shop_manager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.DTO.CategoryDAO;
import com.example.tudtc_app_shop_manager.ListCategoryActivity;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.adapter.Category2Adapter;
import com.example.tudtc_app_shop_manager.adapter.CategoryAdapter;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.myInterface.IClickGoToAddCate;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private View view;
    private List<Category> list = new ArrayList<>();
    private Category2Adapter adapter;
    private RecyclerView recyclerView;
    private CategoryDAO categoryDAO;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category2, container, false);

        anhxa();
        list = categoryDAO.getAllCategory();
        adapter = new Category2Adapter(list, getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list = categoryDAO.getAllCategory();
        adapter.setData(list);
    }

    private void anhxa() {
        recyclerView = view.findViewById(R.id.RecyclerView_cate);

        categoryDAO = new CategoryDAO(getActivity());
    }
}