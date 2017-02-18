package com.fosu.jobapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;
import com.fosu.jobapp.R;
import com.fosu.jobapp.fragment.AccountFragment;
import com.fosu.jobapp.fragment.CompanyFragment;
import com.fosu.jobapp.fragment.HomeFragment;
import com.fosu.jobapp.listener.OnActivityListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.zaaach.citypicker.CityPickerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    private FragmentManager fragmentManager;
    private long exitTime = 0;
    private HomeFragment homeFragment;
    private CompanyFragment zoomFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragmentManager = getFragmentManager();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment = null;
                switch (tabId) {
                    case R.id.tab_home:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            homeFragment.setOnActivityListener(new OnActivityListener() {
                                @Override
                                public void onActivity() {
//                                    enterSelectCity();
                                    startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), 0x002);
                                    overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                                }
                            });
                        }
                        fragment = homeFragment;
                        break;
                    case R.id.tab_company:
                        if (zoomFragment == null)
                            zoomFragment = new CompanyFragment();
                        fragment = zoomFragment;
                        break;
                    case R.id.tab_zoom:
                        if (homeFragment == null)
                            homeFragment = new HomeFragment();
                        fragment = homeFragment;
                        break;
                    case R.id.tab_account:
                        if (accountFragment == null)
                            accountFragment = new AccountFragment();
                        fragment = accountFragment;
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        });
    }

    private static final int REQUEST_CODE_PICK_CITY = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                LogUtils.i("当前选择：" + city);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
