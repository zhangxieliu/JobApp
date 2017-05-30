package com.fosu.jobapp.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.BarUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Activity的基类，继承自AppCompatActivity，实现了屏幕左滑返回上一个Activity的接口
 */
public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;
    protected SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    /**
     * 初始化状态栏，如果存在状态栏，则设置状态栏颜色的沉浸式，并处理顶部适应的高度
     * @param view 需要调整的头部View
     */
    public void initStatusBar(View view) {
        if (BarUtils.isStatusBarExists(this)) {
            int statusBarHeight = BarUtils.getStatusBarHeight(this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = layoutParams.height + statusBarHeight;
            view.setLayoutParams(layoutParams);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 初始化加载数据的对话框
     */
    public void initProgressDialog() {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("正在加载数据...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
        BarUtils.setTranslucentForImageView(this, 0, null);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null)
            dialog.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.cancel();
    }
}
