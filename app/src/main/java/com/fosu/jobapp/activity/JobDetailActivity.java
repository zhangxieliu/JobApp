package com.fosu.jobapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.fosu.jobapp.R;
import com.fosu.jobapp.base.BaseActivity;
import com.fosu.jobapp.bean.Company;
import com.fosu.jobapp.bean.DeliveryRecord;
import com.fosu.jobapp.bean.Job;
import com.fosu.jobapp.bean.JobOperation;
import com.fosu.jobapp.bean.PersonalResume;
import com.fosu.jobapp.bean.User;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.orhanobut.logger.Logger;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.SocializeUtils;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
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
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.companyType)
    TextView companyType;
    private Job job;

    private ProgressDialog dialog;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        mContext = this;
        loadData();
        initThumbUpView();
        initStatusBar();
        dialog = new ProgressDialog(this);
    }

    private JobOperation jobOperation;
    private String id1;
    private String id2;

    /**
     * 初始化感兴趣和收藏小按钮
     */
    private void initThumbUpView() {
        mThumbUpView.setUnLikeType(ThumbUpView.LikeType.broken);
        BmobQuery<JobOperation> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(mContext, User.class));
        query.addWhereEqualTo("job", job);
        query.addWhereEqualTo("type", 1);
        query.findObjects(mContext, new FindListener<JobOperation>() {
            @Override
            public void onSuccess(List<JobOperation> list) {
                if (list == null || list.size() == 0) {
                    mThumbUpView.setUnlike();
                } else {
                    mThumbUpView.setLike();
                    id1 = list.get(0).getObjectId();
                }
            }

            @Override
            public void onError(int i, String s) {
                Logger.i(s + "8888888");
                mThumbUpView.setUnlike();
                btnCollection.setChecked(false);
            }
        });
        BmobQuery<JobOperation> query2 = new BmobQuery<>();
        query.addWhereEqualTo("user", new BmobPointer(BmobUser.getCurrentUser(mContext, User.class)));
        query.addWhereEqualTo("job", new BmobPointer(job));
        query.addWhereEqualTo("type", 2);
        query2.findObjects(mContext, new FindListener<JobOperation>() {
            @Override
            public void onSuccess(List<JobOperation> list) {
                if (list == null || list.size() == 0) {
                    btnCollection.setChecked(false);
                } else {

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        mThumbUpView.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(final boolean like) {
                jobOperation = new JobOperation();
                jobOperation.setUser(BmobUser.getCurrentUser(mContext, User.class));
                jobOperation.setJob(job);
                jobOperation.setType(1);
                if (id1 != null) {
                    jobOperation.setObjectId(id1);
                    jobOperation.delete(mContext);
                }
                if (like) {
                    jobOperation.save(mContext, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            ToastUtils.showShortToast("感兴趣");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Logger.i("code:" + i + ", msg:" + s);
                        }
                    });
                }
            }
        });
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

    /**
     * 加载后台数据
     */
    private void loadData() {
        Intent intent = getIntent();
        job = (Job) intent.getSerializableExtra("jobInfo");
        Company company = job.getCompany();
        tvCompanyName.setText(company.getCompanyName());
        companyName.setText(company.getCompanyName());
        companyType.setText(company.getCompanyType().getType());
        Glide.with(this).load(company.getCompanyLogo().getUrl()).asBitmap().into(companyLogo);
        tvCompanyInfo.setText(company.getCompanyIndustry().get(0) + " | " +
                company.getCompanyType().getType() + " | " + company.getCompanyScale().getScale());
        tvJobBenefits.setText(job.getJobBenefits());
        tvJobType.setText(job.getJobType().getType());
        tvEducation.setText(job.getJobEducation().getEducation());
        tvExperience.setText(job.getJobExperience().getExperience());
        tvCity.setText(job.getJobCity());
        tvJobSalary.setText(job.getJobSalary().get(0) + "K-" + job.getJobSalary().get(1) + "K");
        tvJobName.setText(job.getJobName());
        tagGroup.setTags(job.getJobTag());
        RichText.from(job.getJobDescribe())
                .type(RichType.HTML) // 文本类型
                .autoFix(true)  //是否自动修复，默认为true
                .bind(this) // 绑定richText对象到某个object上，方便后面的清理
                .into(tvRequirement);
        BmobQuery<DeliveryRecord> query = new BmobQuery<>();
        query.addWhereEqualTo("job", job);
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(this, User.class));
        query.findObjects(mContext, new FindListener<DeliveryRecord>() {
            @Override
            public void onSuccess(List<DeliveryRecord> list) {
                if (list == null || list.size() == 0) {
                    btnSend.setText("发送简历");
                    btnSend.setEnabled(true);
                } else {
                    btnSend.setText("已投递");
                    btnSend.setEnabled(false);
                }
            }

            @Override
            public void onError(int i, String s) {
                btnSend.setText("发送简历");
                btnSend.setEnabled(true);
            }
        });
    }

    @OnClick({R.id.btn_back, R.id.btn_collection, R.id.img_share, R.id.btn_send, R.id.company_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();// 点击返回按钮退出当前activity
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);//设置activity跳转动画
                break;
            case R.id.btn_collection:
                break;
            case R.id.img_share:
                new ShareAction(this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.SINA)
                        .withMedia(new UMImage(this, R.drawable.wall01))
                        .setCallback(umShareListener)
                        .open();
                break;
            case R.id.btn_send:
                DeliveryRecord deliveryRecord = new DeliveryRecord();
                deliveryRecord.setJob(job);
                PersonalResume personalResume = new PersonalResume();
                personalResume.setObjectId("wXdOWWWb");
                deliveryRecord.setPersonalResume(personalResume);
                deliveryRecord.setUser(BmobUser.getCurrentUser(this, User.class));
                deliveryRecord.setStatus("已投递");
                deliveryRecord.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtils.showShortToast("职位投递成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ToastUtils.showShortToast("职位投递失败");
                    }
                });
                break;
            case R.id.company_detail:
                Intent intent = new Intent(this, CompanyDetailActivity.class);
                intent.putExtra("companyId", this.job.getCompany().getObjectId());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.showShortToast("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            SocializeUtils.safeCloseDialog(dialog);
            ToastUtils.showShortToast("分享失败" + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            SocializeUtils.safeCloseDialog(dialog);
            ToastUtils.showShortToast("分享取消");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
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
        UMShareAPI.get(this).release();
    }
}
