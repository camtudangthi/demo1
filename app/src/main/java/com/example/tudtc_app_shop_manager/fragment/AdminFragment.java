package com.example.tudtc_app_shop_manager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tudtc_app_shop_manager.AboutUsActivity;
import com.example.tudtc_app_shop_manager.DTO.UserDAO;
import com.example.tudtc_app_shop_manager.DoanhThuActivity;
import com.example.tudtc_app_shop_manager.ListCategoryActivity;
import com.example.tudtc_app_shop_manager.ListHoaDonActivity;
import com.example.tudtc_app_shop_manager.ListProductActivity;
import com.example.tudtc_app_shop_manager.ListUserActivity;
import com.example.tudtc_app_shop_manager.LoginActivity;
import com.example.tudtc_app_shop_manager.NewsActivity;
import com.example.tudtc_app_shop_manager.ProductHotActivity;
import com.example.tudtc_app_shop_manager.model.User;
import com.example.tudtc_app_shop_manager.R;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class AdminFragment extends Fragment implements View.OnClickListener {
    private View view, viewDialog;
    private Intent intent;
    private Button btnHuy, btnChange;
    Dialog dialogChangePass;
    EditText edtPassOld, edtPassNew;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    String[] manager = {"Tin tức",
            "Giới thiệu với bạn bè",
            "Quản lý người dùng",
            "Quản lý sản phẩm",
            "Quản lý danh mục",
            "Quản lý hóa đơn",
            "Sản phẩm bán chạy",
            "Doanh thu",
            "Đổi mật khẩu",
            "Đăng xuất",
            "Địa chỉ shop"};
    private ListView lv;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    public AdminFragment() {
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
        view = inflater.inflate(R.layout.fragment_admin, container, false);
        viewDialog = getLayoutInflater().inflate(R.layout.dialog_change_pass, null);
        pref = getActivity().getSharedPreferences("USER_FILE",getActivity().MODE_PRIVATE);
        edit = pref.edit();

        anhxa();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.simple_item, R.id.tv_item, manager);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //Tin tức
                        startActivity(new Intent(getActivity(), NewsActivity.class));
                        break;

                    case 1:
                        //Chia sẻ
                        shareWithFace();
                        break;

                    case 2:
                        //Người dùng
                        startActivity(new Intent(getActivity(), ListUserActivity.class));
                        break;

                    case 3:
                        //Sản phẳm
                        startActivity(new Intent(getActivity(), ListProductActivity.class));
                        break;

                    case 4:
                        //Danh mục
                        startActivity(new Intent(getActivity(), ListCategoryActivity.class));
                        break;
                    case 5:
                        //Hóa đơn
                        startActivity(new Intent(getActivity(), ListHoaDonActivity.class));
                        break;
                    case 6:
                        //Sản phẳm bán chạy
                        startActivity(new Intent(getActivity(), ProductHotActivity.class));
                        break;
                    case 7:
                        //Sản phẳm
                        startActivity(new Intent(getActivity(), DoanhThuActivity.class));
                        break;
                    case 8:
                        //Sản phẳm
                        showDialogChangePass();
                        break;

                    case 9:
                        edit.putString("USERNAME","").commit();
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finishAffinity();
                        break;

                    case 10:
                        startActivity(new Intent(getActivity(), AboutUsActivity.class));
                        break;
                }
            }
        });

        btnChange.setOnClickListener(this);
        btnHuy.setOnClickListener(this);
        return view;
    }

    private void shareWithFace() {
        shareDialog = new ShareDialog(getActivity());
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://www.bachhoaannhien.com/"))
                    .build();
            shareDialog.show(linkContent);
        }
    }

    private void anhxa() {
        lv = view.findViewById(R.id.lv_manager);

        btnHuy = viewDialog.findViewById(R.id.btn_close_dialog);
        btnChange = viewDialog.findViewById(R.id.btn_change);
        edtPassOld = viewDialog.findViewById(R.id.edt_pass_old);
        edtPassNew = viewDialog.findViewById(R.id.edt_pass_new);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            edit.putString("PASSWORD", newPass).commit();
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

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}