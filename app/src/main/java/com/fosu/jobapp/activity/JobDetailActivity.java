package com.fosu.jobapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.bumptech.glide.Glide;
import com.fosu.jobapp.R;
import com.fosu.jobapp.bean.Company;
import com.fosu.jobapp.bean.Job;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

/**
 * 用于显示职位详细信息的Activity
 */

public class JobDetailActivity extends BaseActivity {

    @BindView(R.id.tag_group)
    TagGroup tagGroup;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.tv_requirement)
    TextView tvRequirement;
    @BindView(R.id.thumbUpView)
    ThumbUpView mThumbUpView;
    @BindView(R.id.btn_collection)
    ShineButton btnCollection;
    @BindView(R.id.top_bar)
    RelativeLayout mTopLayout;
    @BindView(R.id.tv_job_name)
    TextView tvJobName;
    @BindView(R.id.tv_job_salary)
    TextView tvJobSalary;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_experience)
    TextView tvExperience;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_job_type)
    TextView tvJobType;
    @BindView(R.id.tv_job_benefits)
    TextView tvJobBenefits;
    @BindView(R.id.company_logo)
    ImageView companyLogo;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_info)
    TextView tvCompanyInfo;
    @BindView(R.id.tv_company_comment)
    TextView tvCompanyComment;
    @BindView(R.id.company_detail)
    LinearLayout companyDetail;
    @BindView(R.id.bottom_btn_layout)
    LinearLayout bottomBtnLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        initThumbUpView();
        initStatusBar();
        loadData();
    }

    /**
     * 初始化感兴趣和收藏小按钮
     */
    private void initThumbUpView() {
        mThumbUpView.setUnLikeType(ThumbUpView.LikeType.broken);
        mThumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like) {
                    //TODO 这里做感兴趣的职位保存操作
                    Toast.makeText(JobDetailActivity.this, "感兴趣", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO 这里做不感兴趣的职位删除保存操作
                    Toast.makeText(JobDetailActivity.this, "不感兴趣", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        mThumbUpView.Like();
//        mThumbUpView.UnLike();
        btnCollection.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked) {
                    //TODO 这里做收藏职位操作
                    Toast.makeText(JobDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO 这里做取消收藏职位操作
                    Toast.makeText(JobDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 初始化状态栏，如果存在状态栏，则设置状态栏颜色的沉浸式，并处理actionbar的高度
     */
    private void initStatusBar() {
        // 首先判断手机是否存在状态栏
        if (BarUtils.isStatusBarExists(this)) {
            // 获取状态栏的高度
            int statusBarHeight = BarUtils.getStatusBarHeight(this);
            // 获取底部标题栏的的高度，并根据状态栏高度设置顶部栏的padding值
            ViewGroup.LayoutParams layoutParams = mTopLayout.getLayoutParams();
            layoutParams.height = SizeUtils.dp2px(65);
            mTopLayout.setLayoutParams(layoutParams);
            mTopLayout.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    private void loadData() {
        Intent intent = getIntent();
        Job job = (Job) intent.getSerializableExtra("jobInfo");
        Company company = job.getCompany();
        tvCompanyName.setText(company.getCompanyName());
        Glide.with(this).load(company.getCompanyLogo().getUrl()).asBitmap().into(companyLogo);
        tvCompanyInfo.setText(company.getCompanyTag().get(0) + "|" +
                company.getCompanyType().getType() + "|"+ company.getCompanyScale().getScale());
        tvJobBenefits.setText(job.getJobBenefits());
        tvJobType.setText(job.getJobType().getType());
        tvEducation.setText(job.getJobEducation().getEducation());
        tvExperience.setText(job.getJobExperience().getExperience());
        tvCity.setText(job.getJobCity());
        tvJobSalary.setText(job.getJobSalary().get(0) + "K-" + job.getJobSalary().get(1) + "K");
        tvJobName.setText(job.getJobName());
        tagGroup.setTags(job.getJobTag());
        RichText.from(job.getJobRequirement())
                .type(RichType.HTML) // 文本类型
                .autoFix(true)  //是否自动修复，默认为true
                .bind(this) // 绑定richText对象到某个object上，方便后面的清理
                .into(tvRequirement);
    }

    @OnClick({R.id.btn_back, R.id.btn_collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();// 点击返回按钮退出当前activity
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);//设置activity跳转动画
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
        RichText.clear(this);// 清除富文本缓存
    }
}
