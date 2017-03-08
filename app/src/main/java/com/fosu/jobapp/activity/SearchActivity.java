package com.fosu.jobapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fosu.jobapp.R;
import com.fosu.jobapp.base.BaseActivity;

/**
 * Created by Administrator on 2017/2/16.
 */

public class SearchActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        ButterKnife.bind(this);
        init();
    }

    private void init() {
    }
}
