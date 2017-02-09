package com.fosu.jobapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fosu.jobapp.R;
import com.fosu.jobapp.fragment.AccountFragment;
import com.fosu.jobapp.fragment.HomeFragment;
import com.fosu.jobapp.fragment.ZoomFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {
    @BindView(R.id.home)
    LinearLayout home;
    @BindView(R.id.message)
    LinearLayout message;
    @BindView(R.id.zoom)
    LinearLayout zoom;
    @BindView(R.id.account)
    LinearLayout account;
    private FragmentManager fragmentManager;
    private List<LinearLayout> nav;
    private List<Fragment> fragments = new ArrayList<>();
    private HomeFragment homeFragment;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setCustomView(R.layout.layout_actionbar);//ActionBar的自定义布局文件
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragmentManager = getFragmentManager();
        nav = new ArrayList<>();
        nav.add(home);
        nav.add(message);
        nav.add(zoom);
        nav.add(account);
        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        AccountFragment accountFragment = new AccountFragment();
        fragments.add(accountFragment);
        ZoomFragment zoomFragment = new ZoomFragment();
        fragments.add(zoomFragment);
        fragmentManager.beginTransaction()
                .add(R.id.container, fragments.get(0))
                .add(R.id.container, fragments.get(1))
                .add(R.id.container, fragments.get(2))
                .hide(fragments.get(0))
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .commit();
        if (!fragments.get(0).isHidden()) {
            fragmentManager.beginTransaction().show(fragments.get(0)).commit();
        }
    }

    @OnClick({R.id.home, R.id.message, R.id.zoom, R.id.account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                loadSelectNav(0);
                initFragment(0);
                Log.i("===", 0 + "");
                break;
            case R.id.message:
                loadSelectNav(1);
                initFragment(1);
                Log.i("===", 1 + "");
                break;
            case R.id.zoom:
                loadSelectNav(2);
                initFragment(2);
                Log.i("===", 2 + "");
                break;
            case R.id.account:
                loadSelectNav(3);
                initFragment(3);
                Log.i("===", 3 + "");
                break;
        }
    }

    private void initFragment(int index) {
        if (index == 2) {
            getActionBar().show();
        } else {
            getActionBar().hide();
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (i == index && fragments.get(i).isHidden()) {
                fragmentManager.beginTransaction().show(fragments.get(i)).commit();
            } else {
                fragmentManager.beginTransaction().hide(fragments.get(i)).commit();
            }
        }
    }

    private void loadSelectNav(int index) {
        for (int i = 0; i < nav.size(); i++) {
            nav.get(i).setEnabled(i != index);
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
