package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.CartDAO;
import com.example.tudtc_app_shop_manager.DTO.HoaDonChiTietDAO;
import com.example.tudtc_app_shop_manager.DTO.HoaDonDAO;
import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.adapter.CartAdapter;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.ChiTietHoaDon;
import com.example.tudtc_app_shop_manager.model.HoaDon;
import com.example.tudtc_app_shop_manager.model.Product;
import com.example.tudtc_app_shop_manager.model.ShoppingCart;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private TextView tvCountCart, tvSum;
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private HoaDonDAO hoaDonDAO;
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private List<ShoppingCart> carts = new ArrayList<>();
    private CartAdapter adapter;
    private RecyclerView rv;
    private CheckBox chkAll;
    private Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        anhxa();
        info();

        chkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (ShoppingCart cart:carts){
                        cart.setSelect(true);
                    }
                } else {
                    for (ShoppingCart cart:carts){
                        cart.setSelect(false);
                    }
                }
                adapter.setData(carts);
                TongTien();
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    themHoaDon();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void themHoaDon() throws ParseException {
        Boolean check = false;
        //check xem đã chọn sản phẩm nào chưa
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).isSelect()){
                check = true;
                break;
            }
        }

        if (!check){
            Toast.makeText(this, "Bạn chưa chọn sản phẩm nào", Toast.LENGTH_SHORT).show();
            return;
        }

        HoaDon don = new HoaDon();
        don.setDatePurchase(new Date());
        don.setUsername(getUserName());
        don.setPaid(false);
        don.setStatus(0);
        hoaDonDAO.inserHoaDon(don);
        int idHoaDon = hoaDonDAO.getIdHoaDon(getUserName());
        Boolean successful = false;
        Toast.makeText(this, ""+idHoaDon, Toast.LENGTH_LONG).show();
        for (ShoppingCart cart:carts) {
            if (cart.isSelect()){
                Product product = productDAO.checkID(cart.getIdProduct());
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(idHoaDon, product, cart.getAmount());
                if (hoaDonChiTietDAO.inserHoaDonChiTiet(chiTietHoaDon)==1){
                    successful = true;
                    cartDAO.deleteCart(getUserName(), product.getId());
                } else {
                    successful = false;
                }
            }
        }

        if (successful){
            Toast.makeText(this, "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
            carts = cartDAO.getAllCart();
            adapter.setData(carts);
        }
    }

    private void info() {
        carts = cartDAO.getTheoUser(getUserName());
        tvCountCart.setText(carts.size() + " sản phẩm");
        adapter = new CartAdapter(carts, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        TongTien();
    }

    private void anhxa() {
        tvCountCart = findViewById(R.id.tv_amount_pro_cart);
        tvSum = findViewById(R.id.tv_sum);
        rv = findViewById(R.id.rv_list_cart);
        chkAll = findViewById(R.id.chk_all);
        btnBuy = findViewById(R.id.btn_buy);

        cartDAO = new CartDAO(this);
        productDAO = new ProductDAO(this);
        hoaDonDAO = new HoaDonDAO(this);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
    }

    public String getUserName(){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String username = pref.getString("USERNAME", "");
        return username;
    }

    public void setSelectCart(boolean isChecked, int i) {
        if (isChecked){
            carts.get(i).setSelect(true);
            TongTien();
        } else {
            carts.get(i).setSelect(false);
            TongTien();
        }
    }

    public void TongTien(){
        double sum = 0;
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).isSelect()){
                Product product = productDAO.checkID(carts.get(i).getIdProduct());
                if (product == null){
                    return;
                }
                sum += carts.get(i).getAmount()*product.getPrice();
            }
        }
        tvCountCart.setText(carts.size() + " sản phẩm");
        tvSum.setText(ChuyenDoi.SoString(sum) + " vnd");
    }
}