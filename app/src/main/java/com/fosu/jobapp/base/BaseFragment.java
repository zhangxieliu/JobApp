package com.fosu.jobapp.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.BarUtils;

import cn.bmob.v3.Bmob;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/3/6.
 */

public abstract class BaseFragment extends Fragment {
    public SweetAlertDialog dialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBmob();
        loadData();
    }

    /**
     * 初始化Bob后端云数据库服务
     */
    public void initBmob() {
        //提供以下两种方式进行初始化操作：
        //第一：默认初始化
        Bmob.initialize(getActivity(), "53cd5abd1c22bcf54c7f7042ecd26731");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("53cd5abd1c22bcf54c7f7042ecd26731")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }

    /**
     * 初始化状态栏，如果存在状态栏，则设置状态栏颜色的沉浸式，并处理顶部适应的高度
     *
     * @param view 需要调整的头部View
     */
    public void initStatusBar(View view) {
        if (BarUtils.isStatusBarExists(getActivity())) {
            int statusBarHeight = BarUtils.getStatusBarHeight(getActivity());
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
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("正在加载数据...");
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 加载网络后端数据的方法
     */
    public void loadData() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null)
            dialog.hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.cancel();
    }
}
