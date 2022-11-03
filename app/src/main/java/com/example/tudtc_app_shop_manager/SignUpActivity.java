package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.UserDAO;
import com.example.tudtc_app_shop_manager.model.User;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSignUp, btnBackLogin;
    Intent intent;
    EditText edtUsername, edtPass, edtPhone, edtFullname;
    RadioGroup rg;
    User user;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        anhxa();
        btnBackLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private void anhxa() {
        btnSignUp = findViewById(R.id.btn_signup);
        btnBackLogin = findViewById(R.id.btn_back);
        edtUsername = findViewById(R.id.edt_userName);
        edtPass = findViewById(R.id.edt_password);
        edtPhone = findViewById(R.id.edt_phone);
        edtFullname = findViewById(R.id.edt_fullName);
        rg = findViewById(R.id.rg_gender);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                user = validate();
                if (user == null){
                    return;
                }
                Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
                userDAO = new UserDAO(getBaseContext());
                if (userDAO.inserUser(user) > 0) {
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    rememberUser(user.getUserName(), user.getPassword());
                    Log.d("--------------", user.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finishAffinity();
                return;

            case R.id.btn_back:
                onBackPressed();
                return;
        }
    }

    private User validate() {
        String username = edtUsername.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String fullname = edtFullname.getText().toString().trim();
        if (username.equals("admin")){
            Toast.makeText(this, "Chọn tên người dùng khác", Toast.LENGTH_SHORT).show();
            edtUsername.requestFocus();
            return null;
        }
        if (username.equals("") || pass.equals("") || phone.equals("") || fullname.equals("")){
            Toast.makeText(this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return null;
        }
        User user1 = new User();
        user1.setUserName(username);
        user1.setPassword(pass);
        user1.setPhone(phone);
        user1.setFullName(fullname);

        int idChecked = rg.getCheckedRadioButtonId();
        switch (idChecked){
            case R.id.rad_male:
                user1.setGender(Byte.parseByte("1"));
                break;
            case R.id.rad_female:
                user1.setGender(Byte.parseByte("2"));
                break;
            case R.id.rad_custom:
                user1.setGender(Byte.parseByte("3"));
                break;
        }
        return user1;
    }

    public void rememberUser(String u, String p){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USERNAME",u);
        edit.putString("PASSWORD",p);
        edit.commit();
    }
}