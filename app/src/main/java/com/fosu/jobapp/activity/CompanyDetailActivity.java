package com.fosu.jobapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.fosu.jobapp.R;
import com.fosu.jobapp.fragment.CompanyInfoFragment;
import com.fosu.jobapp.utils.DensityUtils;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2017/2/11.
 */

public class CompanyDetailActivity extends SwipeBackActivity {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.custom_indicator)
    PagerIndicator customIndicator;
    @BindView(R.id.viewPagerTab)
    NavigationTabStrip viewPagerTab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        initBanner();
        initViewPager();
    }


    /**
     * 初始化广告轮播图
     */
    private void initBanner() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
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

            //add your extra information
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
            public void onNextItemAppear(View view) {}
        });
        slider.setDuration(4000);
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new CompanyInfoFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPagerTab.setTitles("公司概述", "热招职位", "公司问答");
        viewPagerTab.setTitleSize(DensityUtils.sp2px(this, 15));
        viewPagerTab.setStripColor(getResources().getColor(R.color.actionbar_bg_color, null));
        viewPagerTab.setStripWeight(DensityUtils.dp2px(this, 3));
        viewPagerTab.setStripFactor(2);
        viewPagerTab.setStripType(NavigationTabStrip.StripType.LINE);
        viewPagerTab.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
//        viewPagerTab.setTypeface("font/drugs.otf");
        viewPagerTab.setCornersRadius(3);
        viewPagerTab.setAnimationDuration(300);
        viewPagerTab.setInactiveColor(Color.GRAY);
        viewPagerTab.setActiveColor(Color.BLACK);
        viewPagerTab.setViewPager(viewPager, 0);
        viewPagerTab.setTabIndex(0, false);
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
