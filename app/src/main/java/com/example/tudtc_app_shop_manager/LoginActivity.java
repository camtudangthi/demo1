package com.example.tudtc_app_shop_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.DTO.UserDAO;
import com.example.tudtc_app_shop_manager.model.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin, btnGoToSignUp;
    Intent intent;
    EditText edtName, edtPass;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();

        btnLogin.setOnClickListener(this);
        btnGoToSignUp.setOnClickListener(this);
    }

    private void anhxa() {
        btnLogin = findViewById(R.id.btn_login);
        btnGoToSignUp = findViewById(R.id.btn_goto_signup);
        edtName = findViewById(R.id.edt_userName_Login);
        edtPass = findViewById(R.id.edt_password_Login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                checkLogin();
                break;

            case R.id.btn_goto_signup:
                Toast.makeText(this, "Ký", Toast.LENGTH_SHORT).show();
                intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void checkLogin() {
        
        String name = edtName.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        if (name.equals("") || pass.equals("")){
            Toast.makeText(this, "Không được đẻ trống", Toast.LENGTH_SHORT).show();
            return;
        }

        intent = new Intent(getBaseContext(), MainActivity.class);
        userDAO = new UserDAO(getBaseContext());
        User user = userDAO.checkLogin(name, pass);
        if (user == null){
            Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.getUserName().equals("admin") ){
            Toast.makeText(this, "Đăng nhập bằng Admin", Toast.LENGTH_SHORT).show();
            rememberUser(name, user.getPassword());
            startActivity(intent);
            finish();
            return;
        }

        if (!user.isBlock()){
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            rememberUser(name, pass);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Tài khoản bị khóa", Toast.LENGTH_SHORT).show();
        }
    }

    public void rememberUser(String u, String p){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USERNAME",u);
        edit.putString("PASSWORD",p);
        edit.commit();
    }
}