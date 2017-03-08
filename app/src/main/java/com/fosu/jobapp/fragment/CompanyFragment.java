package com.fosu.jobapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.fosu.jobapp.R;
import com.fosu.jobapp.activity.CompanyDetailActivity;
import com.fosu.jobapp.adapter.CompanyAdapter;
import com.fosu.jobapp.adapter.ConstellationAdapter;
import com.fosu.jobapp.adapter.GirdDropDownAdapter;
import com.fosu.jobapp.adapter.ListDropDownAdapter;
import com.fosu.jobapp.base.BaseFragment;
import com.fosu.jobapp.bean.Company;
import com.fosu.jobapp.bean.CompanyIndustry;
import com.fosu.jobapp.bean.CompanyScale;
import com.fosu.jobapp.bean.CompanyType;
import com.fosu.jobapp.bean.SimpleInfo;
import com.yalantis.phoenix.PullToRefreshView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CompanyFragment extends BaseFragment {
    private static final String TAG = "CompanyFragment";
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @BindView(R.id.top_bar)
    RelativeLayout mTopBar;
    private View contentView;
    private Context mContext;

    private String headers[] = {"城市", "融资", "规模", "行业"};
    private GirdDropDownAdapter cityAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String experiences[] = {"不限", "应届生", "一年以内", "1-3年", "3-5年", "5-10年", "10年以上"};
    private String educations[] = {"不限", "本科", "大专", "中专", "高中"};
    private String type[] = {"全部", "未融资", "天使轮", "A轮", "B轮", "C轮", "D轮", "不需要融资"};
    private String scale[] = {"全部"};
    private List<String> industry;
    private Set<Integer> selType = new HashSet<>();
    private Set<Integer> selScale = new HashSet<>();
    private Set<Integer> selIndustry = new HashSet<>();

    private BmobQuery<Company> query;
    private PullToRefreshView mPullToRefreshView;
    private SwipeMenuRecyclerView mRecyclerView;
    private List<Company> mCompanies;
    private CompanyAdapter adapter;
    private ConstellationAdapter scaleAdapter;
    private ConstellationAdapter typeAdapter;
    private ConstellationAdapter industryAdapter;
    private BmobQuery<CompanyType> companyTypeBmobQuery;
    private BmobQuery<CompanyScale> companyScaleBmobQuery;
    private BmobQuery<CompanyIndustry> companyIndustryBmobQuery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        ButterKnife.bind(this, view);
        initView();
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusBar(mTopBar);
        initRecyclerView();
        initProgressDialog();
    }

    @Override
    public void initBmob() {
        super.initBmob();
        query = new BmobQuery<>();
        query.include("companyType,companyScale,companyAudit");
        companyTypeBmobQuery = new BmobQuery<>();
        companyScaleBmobQuery = new BmobQuery<>();
        companyIndustryBmobQuery = new BmobQuery<>();
    }

    private void initView() {
        // 城市列表下拉列表
        final ListView cityView = new ListView(getActivity());
        cityAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        // 公司类型的下拉列表
        final View companyTypeView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_drop_down_grid, null);
        GridView companyType = ButterKnife.findById(companyTypeView, R.id.grid_view);
        typeAdapter = new ConstellationAdapter(getActivity(), Arrays.asList(type));
        companyType.setAdapter(typeAdapter);
        Button ok1 = ButterKnife.findById(companyTypeView, R.id.btn_ok);
        Button reset1 = ButterKnife.findById(companyTypeView, R.id.btn_reset);
        ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(selType.contains(0) ?
                        headers[1] :
                        headers[1] + "(" + selType.size() + ")");
                mDropDownMenu.closeMenu();
            }
        });
        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selType.clear();
                selType.add(0);
                typeAdapter.setCheckItem(selIndustry);
            }
        });
        companyType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selType.clear();
                    selType.add(0);
                } else if (selType.contains(position)) {
                    selType.remove(position);
                } else {
                    selType.remove(0);
                    selType.add(position);
                }
                typeAdapter.setCheckItem(selType);
            }
        });

        // 公司规模的下拉列表
        final View companyScaleView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_drop_down_grid, null);
        GridView companyScale = ButterKnife.findById(companyScaleView, R.id.grid_view);
        scaleAdapter = new ConstellationAdapter(getActivity(), Arrays.asList(scale));
        companyScale.setAdapter(scaleAdapter);
        Button ok2 = ButterKnife.findById(companyScaleView, R.id.btn_ok);
        Button reset2 = ButterKnife.findById(companyScaleView, R.id.btn_reset);
        ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(selScale.contains(0) ?
                        headers[2] :
                        headers[2] + "(" + selScale.size() + ")");
                mDropDownMenu.closeMenu();
            }
        });
        reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selScale.clear();
                selScale.add(0);
                scaleAdapter.setCheckItem(selIndustry);
            }
        });
        companyScale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selScale.clear();
                    selScale.add(0);
                } else if (selScale.contains(position)) {
                    selScale.remove(position);
                } else {
                    selScale.remove(0);
                    selScale.add(position);
                }
                scaleAdapter.setCheckItem(selScale);
            }
        });

        // 公司行业的下拉列表
        final View companyIndustryView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_drop_down_grid, null);
        GridView companyIndustry = ButterKnife.findById(companyIndustryView, R.id.grid_view);
        industryAdapter = new ConstellationAdapter(getActivity());
        companyIndustry.setAdapter(industryAdapter);
        Button ok3 = ButterKnife.findById(companyIndustryView, R.id.btn_ok);
        Button reset3 = ButterKnife.findById(companyIndustryView, R.id.btn_reset);
        ok3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(selIndustry.contains(0) ?
                        headers[3] :
                        headers[3] + "(" + selIndustry.size() + ")");
                mDropDownMenu.closeMenu();
            }
        });
        reset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selIndustry.clear();
                selIndustry.add(0);
                industryAdapter.setCheckItem(selIndustry);
            }
        });
        companyIndustry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selIndustry.clear();
                    selIndustry.add(0);
                } else if (selIndustry.contains(position)) {
                    selIndustry.remove(position);
                } else {
                    selIndustry.remove(0);
                    selIndustry.add(position);
                }
                industryAdapter.setCheckItem(selIndustry);
            }
        });

        List<View> popupViews = new ArrayList<>();
        popupViews.add(cityView);
        popupViews.add(companyTypeView);
        popupViews.add(companyScaleView);
        popupViews.add(companyIndustryView);

        // 解析出公司列表的view
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pull_refresh, null);
        // 设置下拉列表view和内容view
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    /**
     * 初始化SwipeRecyclerView的布局，设置adapter，添加滚动监听实现状态栏颜色渐变
     */
    private void initRecyclerView() {
        mRecyclerView = (SwipeMenuRecyclerView) contentView.findViewById(R.id.recycleView);
        mPullToRefreshView = (PullToRefreshView) contentView.findViewById(R.id.pull_to_refresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new CompanyAdapter(getActivity());
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //十秒后如果数据还没加载，则自动停止下拉动画
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 10000);
            }
        });
        adapter.setOnItemClickListener(new CompanyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, Object object) {
                startActivity(new Intent(getActivity(), CompanyDetailActivity.class));
                getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        query.findObjects(getActivity(), new FindListener<Company>() {
            @Override
            public void onSuccess(List<Company> companies) {
                mCompanies = companies;
                adapter.setCompanies(companies);
                mRecyclerView.setAdapter(adapter);
                mPullToRefreshView.setRefreshing(false);
                dialog.cancel();
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.i(TAG, "Error:" + s);
                ToastUtils.showShortToast("数据获取异常");
                dialog.cancel();
            }
        });
        companyTypeBmobQuery.findObjects(mContext, new FindListener<CompanyType>() {
            @Override
            public void onSuccess(List<CompanyType> list) {
                typeAdapter.setData(getString(list));
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.i(TAG, "Error:" + s);
            }
        });
        companyScaleBmobQuery.findObjects(mContext, new FindListener<CompanyScale>() {
            @Override
            public void onSuccess(List<CompanyScale> list) {
                scaleAdapter.setData(getString(list));
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.i(TAG, "Error:" + s);
            }
        });
        companyIndustryBmobQuery.findObjects(mContext, new FindListener<CompanyIndustry>() {
            @Override
            public void onSuccess(List<CompanyIndustry> list) {
                LogUtils.i(TAG, list.toString());
                industryAdapter.setData(getString(list));
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.i(TAG, "Error:" + s);
            }
        });
    }

    private List<String> getString(List objects) {
        List<String> strList = new ArrayList<>();
        strList.add("全部");
        for (Object object : objects) {
            SimpleInfo simpleInfo = (SimpleInfo) object;
            strList.add(simpleInfo.getInfo());
        }
        return strList;
    }

    @Override
    public void onDestroy() {
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onDestroy();
        }
    }
}
