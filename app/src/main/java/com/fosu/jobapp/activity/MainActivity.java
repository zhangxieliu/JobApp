package com.fosu.jobapp.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fosu.jobapp.R;
import com.fosu.jobapp.fragment.AccountFragment;
import com.fosu.jobapp.fragment.ChatFragment;
import com.fosu.jobapp.fragment.CompanyFragment;
import com.fosu.jobapp.fragment.HomeFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener {
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    private FragmentManager fragmentManager;
    private long exitTime = 0;//上次点击返回键的时间
    private Map<Integer, Fragment> fragments;// 底部导航菜单栏的所有Fragment
    private int currentTab = 0;// 当前选中的底部导航菜单项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        fragments = new HashMap<>();
        HomeFragment homeFragment = new HomeFragment();
        CompanyFragment zoomFragment = new CompanyFragment();
        ChatFragment chatFragment = new ChatFragment();
        AccountFragment accountFragment = new AccountFragment();
        fragments = new HashMap<>();
        fragments.put(0, homeFragment);
        fragments.put(1, zoomFragment);
        fragments.put(2, chatFragment);
        fragments.put(3, accountFragment);
        fragmentManager.beginTransaction().add(R.id.container, fragments.get(0)).commit();
        bottomBar.setOnTabSelectListener(this);
    }

    /**
     * 改变选中的底部导航菜单栏
     * @param page 当前选中项
     */
    private void changeTab(int page) {
        if (currentTab == page) {
            return;
        }
        Fragment fragment = fragments.get(page);
        FragmentTransaction ft = fragmentManager.beginTransaction();
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

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                changeTab(0);
                break;
            case R.id.tab_company:
                changeTab(1);
                break;
            case R.id.tab_zoom:
                changeTab(2);
                break;
            case R.id.tab_account:
                changeTab(3);
                break;
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
