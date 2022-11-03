package com.example.tudtc_app_shop_manager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.CategoryDAO;
import com.example.tudtc_app_shop_manager.DTO.ProductDAO;
import com.example.tudtc_app_shop_manager.adapter.SpinnerCateAdapter;
import com.example.tudtc_app_shop_manager.helper.ChuyenDoi;
import com.example.tudtc_app_shop_manager.model.Category;
import com.example.tudtc_app_shop_manager.model.Product;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;
    private List<Category> categoryList = new ArrayList<>();
    private CategoryDAO categoryDAO;
    private EditText edtMa, edtTen, edtChiTiet, edtGia, edtSLuong;
    private Button btnAdd, btnUp;
    private ProductDAO productDAO;
    private ImageView imgPicturePro;
    private ImageButton btnGoToCate;
    private Intent intent;
    private SpinnerCateAdapter spinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        anhxa();

        categoryList = categoryDAO.getAllCategory();
        spinnerArrayAdapter = new SpinnerCateAdapter(this, categoryList);
        spinner.setAdapter(spinnerArrayAdapter);

        intent = getIntent();
        int action = intent.getIntExtra("action", 0);
        if (action == 2){
            btnAdd.setVisibility(View.GONE);
            String id = intent.getStringExtra("idProduct");
            if (!id.equals("")){
                Product product = productDAO.checkID(id);
                edtMa.setText(product.getId());
                edtTen.setText(product.getName());
                edtChiTiet.setText(product.getDescription());
                edtGia.setText(ChuyenDoi.SoString(product.getPrice()));
                edtSLuong.setText(String.valueOf(product.getAmount()));
                Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
                imgPicturePro.setImageBitmap(bitmap);
                for (int i = 0; i < categoryList.size(); i++) {
                    if (product.getIdCategory().equals(String.valueOf(categoryList.get(i).getIdCategory()))){
                        spinner.setSelection(i);
                        break;
                    }
                }
                edtMa.setFocusable(false);
            }
        } else {
            btnUp.setVisibility(View.GONE);
        }

        btnUp.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        imgPicturePro.setOnClickListener(this);
        btnGoToCate.setOnClickListener(this);
    }

    private void anhxa() {
        spinner = findViewById(R.id.spinner_cate);
        edtMa = findViewById(R.id.edt_id_pro);
        edtTen = findViewById(R.id.edt_name_pro);
        edtChiTiet = findViewById(R.id.edt_dec_product);
        edtGia = findViewById(R.id.edt_price_product);
        edtSLuong = findViewById(R.id.edt_amount_pro);
        btnAdd = findViewById(R.id.btn_add_product);
        btnUp = findViewById(R.id.btn_up_product);
        imgPicturePro = findViewById(R.id.img_picture_product);
        btnGoToCate = findViewById(R.id.img_btn_gotoCate);

        categoryDAO = new CategoryDAO(this);
        productDAO = new ProductDAO(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinnerArrayAdapter.setData(categoryDAO.getAllCategory());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            imgPicturePro.setImageURI(data.getData());
        } else {
            Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeProduct(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_product:
                addProductToList();
                return;
            case R.id.btn_up_product:
                upProduct();
                return;

            case R.id.img_btn_gotoCate:
                goToCate();
                return;

            case R.id.img_picture_product:
                ImagePicker.with(ProductActivity.this)
                        .crop(1000, 1000)
                        .maxResultSize(1000, 1000)
                        .start(1);
                return;
        }
    }

    private void goToCate() {
        startActivity(new Intent(this, CategoryActivity.class));
    }

    private void upProduct() {
        if (validate() < 0){
            return;
        } else {
            Product product = new Product();
            String id = edtMa.getText().toString().trim();
            String name = edtTen.getText().toString().trim();
            String description = edtChiTiet.getText().toString().trim();
            String price = edtGia.getText().toString().trim();
            String amount = edtSLuong.getText().toString().trim();
            Category category = (Category) spinner.getSelectedItem();

            product.setId(id);
            product.setIdCategory(category.getIdCategory());
            product.setName(name);
            product.setDescription(description);
            product.setPrice((int) ChuyenDoi.SoDouble(price));
            product.setAmount(Integer.parseInt(amount));
            product.setImage(imv_to_byte(imgPicturePro));

            if (productDAO.updateProduct(product) == 1){
                Toast.makeText(ProductActivity.this, "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addProductToList() {
        if (validate() < 0){
            return;
        } else {
            Product product = new Product();
            String id = edtMa.getText().toString().trim();
            String name = edtTen.getText().toString().trim();
            String description = edtChiTiet.getText().toString().trim();
            String price = edtGia.getText().toString().trim();
            String amount = edtSLuong.getText().toString().trim();
            Category category = (Category) spinner.getSelectedItem();
            if (productDAO.checkID(id) != null){
                Toast.makeText(this, "MÃ sản phẩm đã có", Toast.LENGTH_SHORT).show();
                return;
            }

            product.setId(id);
            product.setIdCategory(category.getIdCategory());
            product.setName(name);
            product.setDescription(description);
            product.setPrice((int) ChuyenDoi.SoDouble(price));
            product.setAmount(Integer.parseInt(amount));
            product.setImage(imv_to_byte(imgPicturePro));

            if (productDAO.inserProduct(product) == 1){
                Toast.makeText(ProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                show_Notification();
            } else {
                Toast.makeText(ProductActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int validate() {
        String id = edtMa.getText().toString().trim();
        if (id.equals("")){
            Toast.makeText(this, "Kiểm tra lại mã sản phẩm", Toast.LENGTH_SHORT).show();
            return -1;
        }
        String name = edtTen.getText().toString().trim();
        String description = edtChiTiet.getText().toString().trim();
        String price = edtGia.getText().toString().trim();
        String amount = edtSLuong.getText().toString().trim();
        if (name.equals("") || description.equals("") || price.equals("") || amount.equals("")){
            Toast.makeText(this, "Không để trống", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }

    public byte[] imv_to_byte(ImageView view){
        BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void show_Notification() {

        Intent intent = new Intent(this, MainActivity.class);
        String CHANNEL_ID = "MYCHANNEL";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("Có sản phẩm mới thêm được thêm vào")
                .setContentTitle("Thông báo")
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat, "go to app", pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, notification);
    }
}