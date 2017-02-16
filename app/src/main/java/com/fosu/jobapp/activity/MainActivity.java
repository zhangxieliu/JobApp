package com.fosu.jobapp.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fosu.jobapp.R;
import com.fosu.jobapp.fragment.AccountFragment;
import com.fosu.jobapp.fragment.CompanyFragment;
import com.fosu.jobapp.fragment.HomeFragment;
import com.fosu.jobapp.listener.OnActivityListener;
import com.fosu.jobapp.utils.LogUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.zaaach.citypicker.CityPickerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class MainActivity extends SwipeBackActivity {
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    private FragmentManager fragmentManager;
    private SwipeBackLayout mSwipeBackLayout;
    private long exitTime = 0;
    private HomeFragment homeFragment;
    private CompanyFragment zoomFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint

        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#03a9f4"));
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();
        init();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void init() {
        fragmentManager = getFragmentManager();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment = null;
                mainLayout.setFitsSystemWindows(true);
                switch (tabId) {
                    case R.id.tab_home:
                        if (homeFragment == null){
                            homeFragment = new HomeFragment();
                            homeFragment.setOnActivityListener(new OnActivityListener() {
                                @Override
                                public void onActivity() {
//                                    enterSelectCity();
                                    startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), 0x002);
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
                        mainLayout.setFitsSystemWindows(false);
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        });
    }

    private static final int REQUEST_CODE_PICK_CITY = 0;

    /**
     * 进入选择城市activity
     */
    private void enterSelectCity() {
        //启动
        startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),REQUEST_CODE_PICK_CITY);
    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
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
