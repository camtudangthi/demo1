package com.example.tudtc_app_shop_manager;

import static com.example.tudtc_app_shop_manager.SplashScreenActivity.showDebugDBAddressLogToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.amitshekhar.DebugDB;
import com.example.tudtc_app_shop_manager.adapter.ViewPagerAdapter;
import com.example.tudtc_app_shop_manager.fragment.AccountFragment;
import com.example.tudtc_app_shop_manager.fragment.AdminFragment;
import com.example.tudtc_app_shop_manager.fragment.CategoryFragment;
import com.example.tudtc_app_shop_manager.fragment.MailFragment;
import com.example.tudtc_app_shop_manager.fragment.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager2 viewPager2;
    List<Fragment> arr = new ArrayList<>();
    private String Nguoisudung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        getUser();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("===============================", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }

        arr.add(new TrangChuFragment());
        arr.add(new CategoryFragment());
        arr.add(new MailFragment());
        if (Nguoisudung.equals("admin")){
            arr.add(new AdminFragment());
        } else {
            arr.add(new AccountFragment());
        }
        ViewPagerAdapter myAdapterViewPager = new ViewPagerAdapter(this, arr);
        viewPager2.setAdapter(myAdapterViewPager);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.nav_category:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.nav_email:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.nav_acc:
                        viewPager2.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        navigationView.setSelectedItemId(R.id.nav_category);
                        break;
                    case 2:
                        navigationView.setSelectedItemId(R.id.nav_email);
                        break;
                    case 3:
                        navigationView.setSelectedItemId(R.id.nav_acc);
                        break;
                }
                super.onPageSelected(position);
            }
        });

        DebugDB.getAddressLog();
        showDebugDBAddressLogToast(this);
    }

    private void anhxa() {
        navigationView = findViewById(R.id.nav_view);
        viewPager2 = findViewById(R.id.view_pager);
    }

    private void getUser(){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        Nguoisudung = pref.getString("USERNAME", "");
    }
}