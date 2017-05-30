package com.fosu.jobapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.fosu.jobapp.R;
import com.fosu.jobapp.activity.LoginActivity;
import com.fosu.jobapp.activity.ResumeActivity;
import com.fosu.jobapp.base.BaseFragment;
import com.fosu.jobapp.bean.User;
import com.fosu.jobapp.listener.OnNODoubleClickListener;
import com.fosu.jobapp.model.UserModel;
import com.fosu.jobapp.view.PullToZoomListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/2/9.
 */

public class AccountFragment extends BaseFragment {
    private Context mContext;
    @BindView(R.id.listView)
    PullToZoomListView listView;
    private String[] settings = {"个人简历", "投递记录", "我的收藏", "设置", "关于我"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return settings.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.layout_account_info_item, null);
                }
                TextView tv = ButterKnife.findById(convertView, R.id.tv_name);
                tv.setText(settings[position]);
                return convertView;
            }
        });
    }

    private void initView() {
        View headerView = listView.getHeaderView();
        CircleImageView avatar = ButterKnife.findById(headerView, R.id.avatar);
        TextView tvUsername = ButterKnife.findById(headerView, R.id.tv_username);
        LinearLayout registerOrLogin = ButterKnife.findById(headerView, R.id.register_or_login);
        User user = UserModel.getInstance().getCurrentUser();
        if (user != null) {
            Glide.with(mContext).load(user.getAvatar().getUrl()).into(avatar);
            tvUsername.setText(user.getUsername());
            registerOrLogin.setVisibility(View.GONE);
        }
        Button btnRegister = ButterKnife.findById(headerView, R.id.btn_register);
        Button btnLogin = ButterKnife.findById(headerView, R.id.btn_login);
        btnLogin.setOnClickListener(new OnNODoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new OnNODoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtils.showShortToast("暂未实现");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Intent intent = new Intent(mContext, ResumeActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
