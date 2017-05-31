package com.fosu.jobapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.fosu.jobapp.R;
import com.fosu.jobapp.base.BaseActivity;
import com.fosu.jobapp.bean.PersonalResume;
import com.fosu.jobapp.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ResumeActivity extends BaseActivity {
    @BindView(R.id.user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.experience)
    TextView experience;
    @BindView(R.id.birthDate)
    TextView birthDate;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.workDate)
    TextView workDate;
    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.workContent)
    TextView workContent;
    @BindView(R.id.projectDate)
    TextView projectDate;
    @BindView(R.id.projectName)
    TextView projectName;
    @BindView(R.id.projectRole)
    TextView projectRole;
    @BindView(R.id.projectURL)
    TextView projectURL;
    @BindView(R.id.projectContent)
    TextView projectContent;
    @BindView(R.id.projectPerformance)
    TextView projectPerformance;
    @BindView(R.id.advantage)
    TextView advantage;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ButterKnife.bind(this);
        mContext = this;
        loadData();
    }

    private void loadData() {
        final User user = BmobUser.getCurrentUser(mContext, User.class);
        BmobQuery<PersonalResume> query = new BmobQuery<>();
        query.getObject(mContext, "", new GetListener<PersonalResume>() {
            @Override
            public void onSuccess(PersonalResume personalResume) {
                Glide.with(mContext)
                        .load(user.getAvatar().getUrl())
                        .asBitmap()
                        .into(userAvatar);
                introduce.setText(user.getIntroduction());
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showShortToast("获取简历数据出错");
            }
        });
    }

    private void fillViewData(PersonalResume personalResume) {
        name.setText(personalResume.getName());

    }
}
