package com.fosu.jobapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.fosu.jobapp.R;
import com.fosu.jobapp.base.BaseActivity;
import com.fosu.jobapp.bean.Company;
import com.fosu.jobapp.fragment.CompanyInfoFragment;
import com.fosu.jobapp.listener.OnFragmentListener;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * 显示公司详细信息的Activity
 */

public class CompanyDetailActivity extends BaseActivity {
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.viewPagerTab)
    NavigationTabStrip viewPagerTab;
    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;
    private Context mContext;
    //    @BindView(R.id.btn_back)
//    ImageView btnBack;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.custom_indicator)
    PagerIndicator customIndicator;
    //    @BindView(R.id.back_layout)
//    FrameLayout mBackLayout;
    @BindView(R.id.iv_company_logo)
    ImageView ivCompanyLogo;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_website)
    TextView tvCompanyWebsite;
    @BindView(R.id.tv_company_info)
    TextView tvCompanyInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        ButterKnife.bind(this);
        mContext = this;
        initProgressDialog();
        initBanner();
        initData();
        BarUtils.setTranslucentForImageView(this, rootLayout);
    }

    private void initData() {
        Intent intent = getIntent();
        String companyId = intent.getStringExtra("companyId");
        BmobQuery<Company> query = new BmobQuery<>();
        query.include("companyType,companyScale,companyAudit");
        query.getObject(mContext, companyId, new GetListener<Company>() {
            @Override
            public void onSuccess(Company company) {
                tvCompanyName.setText(company.getCompanyName());
                tvCompanyWebsite.setText(company.getCompanyWebsite());
                tvCompanyInfo.setText(company.getCompanyIndustry().get(0) + " | " +
                        company.getCompanyType().getType() + " | " + company.getCompanyScale().getScale());
                Glide.with(mContext).load(company.getCompanyLogo().getUrl()).into(ivCompanyLogo);
                initViewPager(company);
                CompanyDetailActivity.this.dialog.cancel();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showShortToast("数据获取错误：" + s);
                CompanyDetailActivity.this.dialog.cancel();
            }
        });
    }
//
//    /**
//     * 初始化状态栏，如果存在状态栏，则设置状态栏颜色的沉浸式，并处理actionbar的高度
//     */
//    private void initStatusBar() {
//        if (BarUtils.isStatusBarExists(this)) {
//            int statusBarHeight = BarUtils.getStatusBarHeight(this);
//            ViewGroup.LayoutParams layoutParams = mBackLayout.getLayoutParams();
//            layoutParams.height = SizeUtils.dp2px(layoutParams.height + statusBarHeight);
//            mBackLayout.setLayoutParams(layoutParams);
//            mBackLayout.setPadding(0, statusBarHeight, 0, 0);
//        }
//    }


    /**
     * 初始化广告轮播图
     */
    private void initBanner() {
        HashMap<String, String> url_maps = new HashMap<>();
        url_maps.put("Android", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_android.jpg");
        url_maps.put("IOS", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_ios.jpg");
        url_maps.put("前端", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_other.jpg");
        url_maps.put("大数据", "https://raw.githubusercontent.com/hugeterry/CoordinatorTabLayout/master/sample/src/main/res/mipmap-hdpi/bg_js.jpg");
        for (String name : url_maps.keySet()) {
            TextSliderView imageSliderView = new TextSliderView(this);
            imageSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(null);

            // 添加额外的信息
            imageSliderView.bundle(new Bundle());
            imageSliderView.getBundle()
                    .putString("extra", name);

            slider.addSlider(imageSliderView);
        }
        slider.setCustomIndicator(customIndicator);
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
//        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation() {
            @Override
            public void onNextItemAppear(View view) {
            }
        });
        slider.setDuration(4000);//设置图片轮播时间
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager(final Company company) {
        viewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CompanyInfoFragment companyInfoFragment = new CompanyInfoFragment();
                if (!TextUtils.isEmpty(company.getCompanyIntroduce()))
                    companyInfoFragment.setListener(new OnFragmentListener() {
                        @Override
                        public void doSomething(Object object) {
//                            ExpandableTextView expandableTextView = (ExpandableTextView) object;
//                            expandableTextView.setText(company.getCompanyIntroduce());
                            TextView expandableTextView = (TextView) object;
                            expandableTextView.setText(company.getCompanyIntroduce());
                        }
                    });
                return companyInfoFragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPagerTab.setViewPager(viewPage, 0);
        viewPagerTab.setTabIndex(0, false);
    }

//    @OnClick(R.id.btn_back)
//    public void onClick() {
//        finish();
//        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);// 设置Activity跳转动画
//    }
}
