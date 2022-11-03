package com.example.tudtc_app_shop_manager.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.adapter.Product2Adapter;
import com.example.tudtc_app_shop_manager.adapter.SliderAdapter;
import com.example.tudtc_app_shop_manager.model.Product;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class TrangChuFragment extends Fragment {
    private View view;
    private RecyclerView rvProduct;
    private SliderView slv;
    private SliderAdapter sliderAdapter;
    private SearchView sv;
    private List<Product> productList = new ArrayList<>();
    private ProductDAO productDAO;
    private Product2Adapter adapter;
    int[] images = {R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        anhxa();

        slv.setSliderAdapter(sliderAdapter);
        slv.setIndicatorAnimation(IndicatorAnimationType.WORM);
        slv.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        slv.startAutoCycle();

        productDAO = new ProductDAO(getActivity());
        productList = productDAO.getAllProduct();
        adapter = new Product2Adapter(productList, getActivity());
        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvProduct.setAdapter(adapter);
        return view;
    }

    private void anhxa() {
        rvProduct = view.findViewById(R.id.rv_product_main);
        sv = view.findViewById(R.id.sv_product_main);
        sliderAdapter = new SliderAdapter(images);
        slv = view.findViewById(R.id.slv_image);

    }

    @Override
    public void onResume() {
        super.onResume();
        productList = productDAO.getAllProduct();
        adapter.setData(productList);
        sliderAdapter.notifyDataSetChanged();
    }
}