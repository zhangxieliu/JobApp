package com.fosu.jobapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.fosu.jobapp.R;
import com.fosu.jobapp.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    @OnClick({R.id.home, R.id.message, R.id.zoom, R.id.account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                loadSelectNav(0);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragments.add(homeFragment);
                    fragmentManager.beginTransaction().add(R.id.container, homeFragment)
                            .commit();
                }
                initFragment(0);
                Log.i("===", 0 + "");
                break;
            case R.id.message:
                loadSelectNav(1);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragments.add(homeFragment);
                    fragmentManager.beginTransaction().add(R.id.container, homeFragment)
                            .commit();
                }
                initFragment(1);
                Log.i("===", 1 + "");
                break;
            case R.id.zoom:
                loadSelectNav(2);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragments.add(homeFragment);
                    fragmentManager.beginTransaction().add(R.id.container, homeFragment)
                            .commit();
                }
                initFragment(2);
                Log.i("===", 2 + "");
                break;
            case R.id.account:
                loadSelectNav(3);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragments.add(homeFragment);
                    fragmentManager.beginTransaction().add(R.id.container, homeFragment)
                            .commit();
                }
                initFragment(3);
                Log.i("===", 3 + "");
                break;
        }
    }

    private void initFragment(int index) {
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
}
