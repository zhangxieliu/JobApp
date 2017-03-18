package com.fosu.jobapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.fosu.jobapp.R;
import com.fosu.jobapp.adapter.SearchHistoryAdapter;
import com.fosu.jobapp.base.BaseActivity;
import com.fosu.jobapp.utils.CustomContact;
import com.orhanobut.logger.Logger;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.LinkedList;
import java.util.ListIterator;

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
    private SPUtils spUtils;
    private LinkedList<String> linkedList;
    private static final String SP_KEY = "search_array";
    private SearchHistoryAdapter historyAdapter;

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
        spUtils = new SPUtils(CustomContact.SP_NAME);
        final String searchArray = spUtils.getString(SP_KEY, "");
        linkedList = new LinkedList<>();
        if (!TextUtils.isEmpty(searchArray.trim())) {
            final String[] split = searchArray.split("%#%#");
            for (String s : split) {
                linkedList.add(s);
            }
        }
        mTagGroup.setTags("Android", "Java", "HTML", "Web前端");
        historyAdapter = new SearchHistoryAdapter(mContext, linkedList);
        mLvHistorySearch.setAdapter(historyAdapter);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String searchContent = mEtSearch.getText().toString().trim();
                    Logger.i("搜索框输入内容为：" + searchContent);
                    if (linkedList.size() > 5)
                        linkedList.removeLast();
                    if (linkedList.contains(searchContent)) {
                        return false;
                    }
                    linkedList.addFirst(searchContent);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < linkedList.size(); i++) {
                        if (i == linkedList.size()) {
                            sb.append(linkedList.get(i));
                        } else {
                            sb.append(linkedList.get(i) + "%#%#");
                        }
                    }
                    spUtils.putString(SP_KEY, sb.toString());
                    returnSearchContent(searchContent);
                    return true;
                }
                return false;
            }
        });
        mLvHistorySearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                returnSearchContent(linkedList.get(position));
            }
        });
    }

    private void returnSearchContent(String searchContent) {
        Intent intent = new Intent();
        intent.putExtra("searchContent", searchContent);
        setResult(RESULT_OK, intent);
        finish();
    }

    private static final int REQUEST_CODE_PICK_CITY = 0x001;

    /**
     * 进入选择城市activity
     */
    private void enterSelectCity() {
        //启动
        startActivityForResult(new Intent(mContext, CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
    }

    @OnClick({R.id.layout_city, R.id.tv_cancel, R.id.btn_clear_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_city:
                enterSelectCity();
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.btn_clear_history:
                spUtils.remove(SP_KEY);
                linkedList.clear();
                historyAdapter.setData(linkedList);
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
