package com.fosu.jobapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.fosu.jobapp.R;
import com.fosu.jobapp.activity.JobDetailActivity;
import com.fosu.jobapp.adapter.JobAdapter;
import com.fosu.jobapp.listener.OnActivityListener;
import com.fosu.jobapp.utils.DensityUtils;
import com.yalantis.phoenix.PullToRefreshView;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/8.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.recycleView)
    SwipeMenuRecyclerView mRecyclerView;
    @BindView(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private SliderLayout slide;
    private View view;
    private int mDistanceY = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initBanner();
        initRecyclerView();
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

    /**
     * 初始化SwipeRecyclerView的布局，设置adapter，添加滚动监听实现状态栏颜色渐变
     */
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        JobAdapter adapter = new JobAdapter(getActivity(), null);
        mRecyclerView.setAdapter(adapter);
        adapter.setHeaderView(view);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mDistanceY += dy;
                int toolbarHeight = mToolbar.getBottom();
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    mToolbar.setBackgroundColor(Color.argb((int) alpha, 3, 169, 244));
                } else {
                    mToolbar.setBackgroundResource(R.color.actionbar_bg_color);
                }
            }
        });
        adapter.setOnItemClickListener(new JobAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, Object object) {
                startActivity(new Intent(getActivity(), JobDetailActivity.class));
                getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });
        initSwipeMenu();
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
                        .setWidth(DensityUtils.dp2px(getActivity(), 100))
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

    private OnActivityListener listener;
    public void setOnActivityListener(OnActivityListener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.toolbar)
    public void onClick() {
        listener.onActivity();
    }
}
