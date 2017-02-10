package com.fosu.jobapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fosu.jobapp.R;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichType;

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
    @BindView(R.id.tv_requirement)
    TextView tvRequirement;
    private String text = "<h1>测试标题</h1>\n" +
            "<h4>SwipeRefreshLayout里面需要注意的Api：</h4>\n" +
            "<p>1.RefreshListener(OnRefreshListener listener)  设置下拉监听，当用户下拉的时候会去执行回调<br />\n" +
            "2.setColorSchemeColors(int... colors) 设置 进度条的颜色变化，最多可以设置4种颜色<br />\n" +
            "3.setProgressViewOffset(boolean scale, int start, int end) 调整进度条距离屏幕顶部的距离<br />\n" +
            "4.setRefreshing(boolean refreshing) 设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false<br />\n" +
            "SwipeRefreshLayout 是谷歌公司推出的用于下拉刷新的控件，SwipeRefreshLayout已经被放到了sdk中，在Version 19.1之后SwipeRefreshLayout 被放到support v4中。\n" +
            "源码在SDK\\sdk\\extras\\android\\support\\v4\\src\\java\\android\\support\\v4\\widget\\SwipeRefreshLayout.java</p>\n" +
            "<p>谷歌公司只提供了下拉刷新的功能，RecyclerView的出现基本就是为了替代ListView，GridView的。</p>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        getActionBar().hide();
        ButterKnife.bind(this);
        tagGroup.setTags(new String[]{"Android", "Java", "SQLite", "Html"});
        RichText.from(text)
                .type(RichType.HTML)
                .autoFix(true)  //是否自动修复，默认为true
                .bind(this) // 绑定richText对象到某个object上，方便后面的清理
                .into(tvRequirement);
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
