package com.example.tudtc_app_shop_manager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tudtc_app_shop_manager.DTO.UserDAO;
import com.example.tudtc_app_shop_manager.LoginActivity;
import com.example.tudtc_app_shop_manager.model.User;
import com.example.tudtc_app_shop_manager.R;

public class AccountFragment extends Fragment implements View.OnClickListener {
    View view, viewDialog;
    UserDAO userDAO;
    TextView tvFullname, tvName, tvLogout;
    User user;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Intent intent;
    private Button btnHuy, btnChange;

    TextView tvChange;
    Dialog dialogChangePass;
    EditText edtPassOld, edtPassNew;

    public AccountFragment() {
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
        view = inflater.inflate(R.layout.fragment_account, container, false);
        viewDialog = getLayoutInflater().inflate(R.layout.dialog_change_pass, null);
        anhxa();
        inforUser();
        tvLogout.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        btnHuy.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        return view;
    }

    private void anhxa() {
        userDAO = new UserDAO(getActivity());
        pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        editor = pref.edit();

        tvFullname = view.findViewById(R.id.tv_fullname);
        tvName = view.findViewById(R.id.tv_username);
        tvLogout = view.findViewById(R.id.tv_logout);
        tvChange = view.findViewById(R.id.tv_change_pass);


        btnHuy = viewDialog.findViewById(R.id.btn_close_dialog);
        btnChange = viewDialog.findViewById(R.id.btn_change);
        edtPassOld = viewDialog.findViewById(R.id.edt_pass_old);
        edtPassNew = viewDialog.findViewById(R.id.edt_pass_new);
    }

    private void inforUser(){
        String name = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        user = userDAO.checkLogin(name, pass);
        tvFullname.setText(user.getFullName() + "");
        tvName.setText(user.getUserName() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:
                editor.putString("USERNAME", "").commit();
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
                return;

            case R.id.tv_change_pass:
                showDialogChangePass();
                return;

            case R.id.btn_close_dialog:
                dialogChangePass.cancel();
                return;

            case R.id.btn_change:
                changePass();
                return;
        }
    }
    private void changePass() {
        String old = edtPassOld.getText().toString().trim();
        String newPass = edtPassNew.getText().toString().trim();
        if (!old.equals(getPass())){
            Toast.makeText(getActivity(), "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            edtPassOld.requestFocus();
            return;
        }
        if (newPass.equals("")){
            Toast.makeText(getActivity(), "Nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
            edtPassNew.requestFocus();
            return;
        }
        UserDAO userDAO = new UserDAO(getActivity());
        User user = new User();
        user.setUserName(getName());
        user.setPassword(newPass);
        if (userDAO.changePasswordUser(user) == 1){
            Toast.makeText(getActivity(), "Đổi thành công", Toast.LENGTH_SHORT).show();
            editor.putString("PASSWORD", newPass).commit();
            dialogChangePass.cancel();
        }
    }

    private void showDialogChangePass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewDialog);
        dialogChangePass = builder.create();
        dialogChangePass.show();
    }

    public String getPass(){
        String pass = pref.getString("PASSWORD", "");
        return pass;
    }

    public String getName(){
        String username = pref.getString("USERNAME", "");
        return username;
    }
}