package com.fosu.jobapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.fosu.jobapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Administrator on 2017/2/9.
 */

public class JobDetailActivity extends Activity {

    @BindView(R.id.tag_group)
    TagGroup tagGroup;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.btn_love)
    ImageButton btnLove;
    @BindView(R.id.btn_collection)
    ImageButton btnCollection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        getActionBar().hide();
        ButterKnife.bind(this);
        tagGroup.setTags(new String[]{"Android", "Java", "SQLite", "Html"});
    }

    @OnClick({R.id.btn_back, R.id.btn_love, R.id.btn_collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_love:
                break;
            case R.id.btn_collection:
                break;
        }
    }
}
