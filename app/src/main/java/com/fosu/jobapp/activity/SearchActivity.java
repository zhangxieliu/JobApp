package com.fosu.jobapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.fosu.jobapp.R;
import com.fosu.jobapp.base.BaseActivity;
import com.orhanobut.logger.Logger;
import com.zaaach.citypicker.CityPickerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Administrator on 2017/2/16.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    public RelativeLayout mTopBar;
    @BindView(R.id.tag_group)
    TagGroup mTagGroup;
    @BindView(R.id.lv_history_search)
    ListView mLvHistorySearch;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mContext = this;
        initStatusBar(mTopBar);
        init();
    }

    private void init() {
        mTagGroup.setTags("ios", "android", "java", "php", "前端", "后端开发", "node");
        mLvHistorySearch.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
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
                    convertView = View.inflate(mContext, R.layout.layout_history_search_item, null);
                }
                return convertView;
            }
        });

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    Logger.i("搜索框输入内容为："+ mEtSearch.getText().toString());
                    ToastUtils.showShortToast(mEtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }


    private static final int REQUEST_CODE_PICK_CITY = 0x001;

    /**
     * 进入选择城市activity
     */
    private void enterSelectCity() {
        //启动
        startActivityForResult(new Intent(mContext, CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
    }

    @OnClick({R.id.layout_city, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_city:
                enterSelectCity();
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                Logger.i("当前选择的城市是：" + city);
                ToastUtils.showShortToast(city);
            }
        }
    }
}
