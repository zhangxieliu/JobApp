package com.fosu.jobapp.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.fosu.jobapp.R;
import com.fosu.jobapp.activity.JobDetailActivity;
import com.fosu.jobapp.activity.ZxingActivity;
import com.fosu.jobapp.adapter.JobAdapter;
import com.fosu.jobapp.bean.Job;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yalantis.phoenix.PullToRefreshView;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/2/8.
 */

public class HomeFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.recycleView)
    SwipeMenuRecyclerView mRecyclerView;
    @BindView(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.top_bar)
    RelativeLayout mTopBar;
    private SliderLayout slide;
    private View view;
    private int mDistanceY = 0;
    private BmobQuery<Job> query;
    private JobAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initStatusBar();
        initBanner();
        initRecyclerView();
        initBmob();
        loadJobData();
    }

    private void initBmob() {
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
        query = new BmobQuery<>();
        query.include("company,company.companyType,company.companyScale,company.companyAudit," +
                "jobExperience,jobEducation,jobType");
    }

    /**
     * 初始化状态栏，如果存在状态栏，则设置状态栏颜色的沉浸式，并处理actionbar的高度
     */
    private void initStatusBar() {
        if (BarUtils.isStatusBarExists(getActivity())) {
            int statusBarHeight = BarUtils.getStatusBarHeight(getActivity());
            ViewGroup.LayoutParams layoutParams = mTopBar.getLayoutParams();
            layoutParams.height = SizeUtils.dp2px(65);
            mTopBar.setLayoutParams(layoutParams);
            mTopBar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 初始化广告轮播图
     */
    private void initBanner() {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner_view, null, false);
        slide = (SliderLayout) view.findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Android", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_android.jpg");
        url_maps.put("IOS", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_ios.jpg");
        url_maps.put("前端", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_other.jpg");
        url_maps.put("大数据", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_js.jpg");
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(null);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            slide.addSlider(textSliderView);
        }
        slide.setPresetTransformer(SliderLayout.Transformer.Default);
        slide.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//        slide.setCustomAnimation(new DescriptionAnimation());
        slide.setDuration(4000);
    }

    private List<Job> mJobs;

    /**
     * 初始化SwipeRecyclerView的布局，设置adapter，添加滚动监听实现状态栏颜色渐变
     */
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mDistanceY += dy;
                int toolbarHeight = mTopBar.getBottom();
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    mTopBar.setBackgroundColor(Color.argb((int) alpha, 3, 169, 244));
                } else {
                    mTopBar.setBackgroundResource(R.color.actionbar_bg_color);
                }
            }
        });
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJobData();
            }
        });
        initSwipeMenu();
        adapter = new JobAdapter(getActivity());
        adapter.setHeaderView(view);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new JobAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, Object object) {
                startActivity(new Intent(getActivity(), JobDetailActivity.class));
                getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });
    }

    private void loadJobData() {
        query.findObjects(getActivity(), new FindListener<Job>() {
            @Override
            public void onSuccess(List<Job> jobs) {
                mJobs = jobs;
                adapter.addDatas(jobs);
                mPullToRefreshView.setRefreshing(false);
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.i(TAG, "Error:" + s);
                ToastUtils.showShortToast("数据获取异常");
            }
        });
    }

    /**
     * 初始化SwipeRecyclerView侧滑项菜单
     */
    private void initSwipeMenu() {
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                        .setBackgroundDrawable(R.color.actionbar_bg_color)
                        .setImage(R.drawable.icon_not_interested) // 图标。
                        .setText("不感兴趣") // 文字。
                        .setTextColor(getResources().getColor(R.color.text_gray_color)) // 文字颜色。
                        .setTextSize(12) // 文字大小。
                        .setWidth(SizeUtils.dp2px(100))
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                if (viewType == JobAdapter.TYPE_NORMAL)
                    swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.
            }
        };
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 开始自动翻页
        slide.startAutoCycle();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止自动翻页
        slide.stopAutoCycle();
    }

    private static final int REQUEST_CODE_PICK_CITY = 0x001;

    /**
     * 进入选择城市activity
     */
    private void enterSelectCity() {
        //启动
        startActivityForResult(new Intent(getActivity(), CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
    }

    private static final int REQUEST_CODE = 0x002;   // 请求二维码扫描的请求码
    @OnClick({R.id.search, R.id.zxing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                enterSelectCity();
                break;
            case R.id.zxing:
                cameraTask();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                LogUtils.i("当前选择：" + city);
            }
        } else if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtils.showShortToast("解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.showShortToast("解析二维码失败");
                }
            }
        }
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            LogUtils.i(TAG, "从设置页面返回...");
        }
    }

    /**
     * EsayPermissions接管权限处理逻辑
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 0x101;

    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            Intent intent = new Intent(getActivity(), ZxingActivity.class);
            // 启动扫描二维码的Activity
            startActivityForResult(intent, REQUEST_CODE);
            getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求相机权限",
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.i(TAG, "执行onPermissionsGranted()---申请使用相机权限成功...");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.i(TAG, "执行onPermissionsDenied()---申请请求权限...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this)
                        .setRationale("二维码功能需要相机权限,请打开设置页面设置。")
                        .setTitle("应用请求使用相机功能")
                        .setPositiveButton("确认")
                        .setNegativeButton("取消")
                        .setRequestCode(REQUEST_CAMERA_PERM)
                        .build()
                        .show();
            }
        }
    }
}
