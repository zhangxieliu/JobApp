package com.fosu.jobapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

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
    SmartTabLayout viewPagerTab;
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
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
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
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("公司概述", CompanyInfoFragment.class)
                        .add("热招职位", CompanyInfoFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
