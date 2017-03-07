package com.fosu.jobapp.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.fosu.jobapp.R;
import com.fosu.jobapp.utils.ImageUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/6.
 */

public class ZxingActivity extends BaseActivity {
    private static final String TAG = "ZxingActivity";
    @BindView(R.id.top_bar)
    RelativeLayout mTopBar;
    @BindView(R.id.btn_light_enable)
    Button mBtnLightEnable;
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.bind(this);
        mContext = this;
        initStatusBar();
        initZxing();
    }

    private void initZxing() {
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_camera);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 初始化状态栏，如果存在状态栏，则设置状态栏颜色的沉浸式，并处理actionbar的高度
     */
    private void initStatusBar() {
        // 首先判断手机是否存在状态栏
        if (BarUtils.isStatusBarExists(this)) {
            // 获取状态栏的高度
            int statusBarHeight = BarUtils.getStatusBarHeight(this);
            // 获取底部标题栏的的高度，并根据状态栏高度设置顶部栏的padding值
            ViewGroup.LayoutParams layoutParams = mTopBar.getLayoutParams();
//            layoutParams.height = SizeUtils.dp2px(65);
            layoutParams.height = layoutParams.height + statusBarHeight;
            mTopBar.setLayoutParams(layoutParams);
            mTopBar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            LogUtils.i(TAG, result);
            resultIntent.putExtras(bundle);
            mContext.setResult(RESULT_OK, resultIntent);
            mContext.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            mContext.setResult(RESULT_OK, resultIntent);
            mContext.finish();
        }
    };

    private static final int REQUEST_IMAGE = 0x004;

    @OnClick({R.id.back, R.id.btn_light_enable, R.id.btn_select_picture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();// 点击返回按钮退出当前activity
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);//设置activity跳转动画
                }
                break;
            case R.id.btn_light_enable:
                if (mBtnLightEnable.getText().equals("开启闪光灯")) {
                    CodeUtils.isLightEnable(true);
                    mBtnLightEnable.setText("关闭闪光灯");
                } else {
                    CodeUtils.isLightEnable(false);
                    mBtnLightEnable.setText("开启闪光灯");
                }
                break;
            case R.id.btn_select_picture:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片

                    CodeUtils.analyzeBitmap(ImageUtils.getImageAbsolutePath(mContext, uri), analyzeCallback);
                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
