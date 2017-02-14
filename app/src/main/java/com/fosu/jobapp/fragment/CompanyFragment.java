package com.fosu.jobapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.fosu.jobapp.R;
import com.fosu.jobapp.activity.CompanyDetailActivity;
import com.fosu.jobapp.adapter.CompanyAdapter;
import com.fosu.jobapp.adapter.ConstellationAdapter;
import com.fosu.jobapp.adapter.GirdDropDownAdapter;
import com.fosu.jobapp.adapter.ListDropDownAdapter;
import com.yalantis.taurus.PullToRefreshView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CompanyFragment extends Fragment {
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private View contentView;
    //    @BindView(R.id.recycleView)
//    SwipeMenuRecyclerView mRecyclerView;
//    @BindView(R.id.pull_to_refresh)
//    PullToRefreshView mPullToRefreshView;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_zoom, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        initRecyclerView();
//    }
//
    /**
     * 初始化SwipeRecyclerView的布局，设置adapter，添加滚动监听实现状态栏颜色渐变
     */
    private void initRecyclerView() {
        SwipeMenuRecyclerView mRecyclerView = (SwipeMenuRecyclerView) contentView.findViewById(R.id.recycleView);
        final PullToRefreshView mPullToRefreshView = (PullToRefreshView) contentView.findViewById(R.id.pull_to_refresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        CompanyAdapter adapter = new CompanyAdapter(getActivity(), null);
        mRecyclerView.setAdapter(adapter);
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
        adapter.setOnItemClickListener(new CompanyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion, Object object) {
                Log.i("===", "click");
                startActivity(new Intent(getActivity(), CompanyDetailActivity.class));
                getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });
    }

    private String headers[] = {"城市", "经验", "学历", "公司"};
    private List<View> popupViews = new ArrayList<>();


    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String experiences[] = {"不限", "应届生", "一年以内", "1-3年", "3-5年", "5-10年", "10年以上"};
    private String educations[] = {"不限", "本科", "大专", "中专", "高中"};
    private String constellations[] = {"全部", "未融资", "天使轮", "A轮", "B轮", "5-10年", "10年以上"};
    private String personRequires[] = {"全部", "0-20人", "20-99人", "100-499人", "500-999人", "1000-9999人", "10000人以上"};

    private int constellationPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView();
        initRecyclerView();
    }

    private void initView() {
        //init city menu
        final ListView cityView = new ListView(getActivity());
        cityAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(getActivity());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(experiences));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(getActivity());
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(educations));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(getActivity(), Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);

        GridView personSize = ButterKnife.findById(constellationView, R.id.person_size);
        final ConstellationAdapter person = new ConstellationAdapter(getActivity(), Arrays.asList(personRequires));
        personSize.setAdapter(person);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : experiences[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : educations[position]);
                mDropDownMenu.closeMenu();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompanyFragment.this.constellationAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });
        personSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                person.setCheckItem(position);
                constellationPosition = position;
            }
        });

        //init context view
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zoom, null);

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onPause() {
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onPause();
        }
    }
}
