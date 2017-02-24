package com.fosu.jobapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    private FragmentManager fragmentManager;
    private long exitTime = 0;
    private Map<Integer, Fragment> fragments;
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragmentManager = getFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        CompanyFragment zoomFragment = new CompanyFragment();
        AccountFragment accountFragment = new AccountFragment();
        fragments.put(0, homeFragment);
        fragments.put(1, zoomFragment);
        fragments.put(2, accountFragment);
        fragmentManager.beginTransaction().add(R.id.container, fragments.get(0)).commit();
        currentTab = 0;
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        ((HomeFragment) fragments.get(0)).setOnActivityListener(new OnActivityListener() {
                            @Override
                            public void onActivity() {
//                                    enterSelectCity();
                                startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), 0x002);
                                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                            }
                        });
                        changeTab(0);
                        break;
                    case R.id.tab_company:
                        changeTab(1);
                        break;
                    case R.id.tab_zoom:
                        changeTab(0);
                        break;
                    case R.id.tab_account:
                        changeTab(2);
                        break;
                }
            }
        });
    }

    private void changeTab(int page) {
        if (currentTab == page) {
            return;
        }
        Fragment fragment = fragments.get(page);
        FragmentTransaction ft = MainActivity.this.getFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            ft.add(R.id.container, fragment);
        }
        ft.hide(fragments.get(currentTab));
        ft.show(fragments.get(page));
        currentTab = page;
        if (!this.isFinishing()) {
            ft.commitAllowingStateLoss();
        }
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
