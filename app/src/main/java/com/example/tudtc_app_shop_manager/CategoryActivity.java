package com.example.tudtc_app_shop_manager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.CategoryDAO;
import com.example.tudtc_app_shop_manager.model.Category;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    Intent intent;
    private TextView tvTitle;
    private EditText edtName, edtDes;
    private Button btnUp, btnAdd;
    private ImageView imageView;
    private CategoryDAO categoryDAO;
    private Category category;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        anhxa();
        intent = getIntent();
        int i = intent.getIntExtra("action", 0);
        if (i == 0){
            tvTitle.setText("Thêm danh mục");
            btnUp.setVisibility(View.GONE);
        } else {
            tvTitle.setText("Thông tin danh mục");
            btnAdd.setVisibility(View.GONE);
            ID = intent.getStringExtra("IDCategory");
            category = categoryDAO.getIDCategory(ID);
            if (category != null){
                edtDes.setText(category.getDescriptionCategory());
                edtName.setText(category.getNameCategory());
                Bitmap bitmap = BitmapFactory.decodeByteArray(category.getImage(), 0, category.getImage().length);
                imageView.setImageBitmap(bitmap);
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(CategoryActivity.this)
                        .crop(1000, 1000)
                        .maxResultSize(1000, 1000)
                        .start(1);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String description = edtDes.getText().toString().trim();
                if (name.equals("")){
                    Toast.makeText(CategoryActivity.this, "Tên danh mục không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                category = new Category();
                category.setNameCategory(name);
                category.setDescriptionCategory(description);
                category.setImage(imv_to_byte(imageView));
                if (categoryDAO.inserCategory(category) == 1){
                    Toast.makeText(CategoryActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String description = edtDes.getText().toString().trim();
                if (name.equals("")){
                    Toast.makeText(CategoryActivity.this, "Tên danh mục không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                category = new Category();
                category.setIdCategory(ID);
                category.setNameCategory(name);
                category.setDescriptionCategory(description);
                category.setImage(imv_to_byte(imageView));
                if (categoryDAO.updateCategory(category) == 1){
                    Toast.makeText(CategoryActivity.this, "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhxa() {
        tvTitle = findViewById(R.id.tv_titleCate);
        btnAdd = findViewById(R.id.btn_add_cate);
        btnUp = findViewById(R.id.btn_up_cate);
        imageView = findViewById(R.id.img_cate);
        edtName = findViewById(R.id.edt_name_cate);
        edtDes = findViewById(R.id.edt_dec_cate);

        categoryDAO = new CategoryDAO(this);
    }

    public void closeCate(View view) {
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            imageView.setImageURI(data.getData());
        } else {
            Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] imv_to_byte(ImageView view){
        BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}