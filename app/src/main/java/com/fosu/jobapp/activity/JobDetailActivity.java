package com.fosu.jobapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fosu.jobapp.R;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2017/2/9.
 */

public class JobDetailActivity extends SwipeBackActivity {

    @BindView(R.id.tag_group)
    TagGroup tagGroup;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.tv_requirement)
    TextView tvRequirement;
    @BindView(R.id.thumbUpView)
    ThumbUpView mThumbUpView;
    private String text = "<h3>职位描述</h3>\n" +
            "<p>1.本科及以上学历，四年以上Android开发经验，具备完整的Android应用开发经验<br />\n" +
            "2.熟悉Android开发平台及框架原理，以及Android控件的使用，熟练掌握Android界面和交互开发<br />\n" +
            "3.熟悉Java语言以及常用的算法和数据结构，对设计模式有一定理解，良好的面向对象编程基础<br />\n" +
            "4.熟练掌握svn/git之一的SCM工具<br />\n" +
            "5.聪明严谨，有良好的编码风格和工作习惯<br />\n" +
            "<h4>加分项：</h4>\n" +
            "<p>1.擅长音视频、图形图像处理<br />\n" +
            "2.有发布的Android应用<br />\n" +
            "3.Github开源项目<br />\n";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        tagGroup.setTags(new String[]{"Android", "Java", "SQLite", "Html"});
        RichText.from(text)
                .type(RichType.HTML)
                .autoFix(true)  //是否自动修复，默认为true
                .bind(this) // 绑定richText对象到某个object上，方便后面的清理
                .into(tvRequirement);
    }

    private void initThumbUpView() {
        mThumbUpView.setUnLikeType(ThumbUpView.LikeType.broken);
        mThumbUpView.setCracksColor(Color.rgb(22, 33, 44));
        mThumbUpView.setFillColor(Color.rgb(11, 200, 77));
        mThumbUpView.setEdgeColor(Color.rgb(33, 3, 219));
        mThumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like) {

                } else {

                }
            }
        });
        mThumbUpView.Like();
        mThumbUpView.UnLike();
    }

    @OnClick({R.id.btn_back, R.id.btn_collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_collection:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(this);
    }
}
