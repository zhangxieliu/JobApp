package com.fosu.jobapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.fosu.jobapp.R;
import com.fosu.jobapp.base.BaseActivity;
import com.fosu.jobapp.bean.User;
import com.fosu.jobapp.model.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/3/16.
 */

public class LoginActivity extends BaseActivity {
    private Context mContext;
    @BindView(R.id.top_bar)
    RelativeLayout topBar;
    @BindView(R.id.user_default)
    CircleImageView userDefault;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);  // 设置禁用屏幕左滑退出当前Activity功能
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initStatusBar(topBar);
        mContext = this;
        User currentUser = UserModel.getInstance().getCurrentUser();
        if (currentUser != null) {
            Glide.with(mContext).load(currentUser.getAvatar().getUrl()).into(userDefault);
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
        initView();
    }

    private void initView() {
        spUtils = new SPUtils("job_login_data");
        String username = spUtils.getString("username", "");
        String password = spUtils.getString("password", "");
        boolean isRemember = spUtils.getBoolean("isRemember", false);
        etUsername.setText(username);
        etPassword.setText(password);
        cbRemember.setChecked(isRemember);
        btnLogin.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick({R.id.cb_remember, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_remember:
                break;
            case R.id.btn_login:
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                UserModel.getInstance().login(username, password, new LogInListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            User user = (User) o;
                            BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(
                                    user.getObjectId(), user.getUsername(), user.getAvatar().getUrl()));
                            if (cbRemember.isChecked()) {
                                spUtils.putString("username", username);
                                spUtils.putString("password", password);
                                spUtils.putBoolean("isRemember", true);
                            } else {
                                spUtils.remove("password");
                                spUtils.putBoolean("isRemember", false);
                            }
                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();
                        } else {
                            ToastUtils.showShortToast(e.getMessage() + "(" + e.getErrorCode() + ")");
                        }
                    }
                });
                break;
        }
    }
}
