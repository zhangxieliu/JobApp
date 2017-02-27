package com.fosu.jobapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.fosu.jobapp.R;
import com.fosu.jobapp.adapter.MessageAdapter;
import com.fosu.jobapp.bean.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/27.
 */

public class ChatFragment extends Fragment {
    @BindView(R.id.msg_listView)
    ListView msgListView;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.button_send)
    Button buttonSend;
    @BindView(R.id.top_bar)
    RelativeLayout mTopBar;
    private List<Message> list_msg;
    private MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();
        initControl();
        initStatusBar();
    }

    /**
     * 初始化状态栏，如果存在状态栏，则设置状态栏颜色的沉浸式，并处理actionbar的高度
     */
    private void initStatusBar() {
        if (BarUtils.isStatusBarExists(getActivity())) {
            int statusBarHeight = BarUtils.getStatusBarHeight(getActivity());
            ViewGroup.LayoutParams layoutParams = mTopBar.getLayoutParams();
            layoutParams.height = SizeUtils.dp2px(65);
            mTopBar.setLayoutParams(layoutParams);
            mTopBar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    private void initControl() {
        adapter = new MessageAdapter(list_msg, getActivity());
        msgListView.setAdapter(adapter);
    }

    private void initData() {
        list_msg = new ArrayList<Message>();
        list_msg.add(new Message("你好", Message.TYPE_RECEIVED));
        list_msg.add(new Message("我可以把我的简历发给你吗？", Message.TYPE_SENT));
        list_msg.add(new Message("非常感谢！", Message.TYPE_RECEIVED));
    }

    @OnClick(R.id.button_send)
    public void onClick() {
        String content = editContent.getText().toString();
        if (content.equals("")) {
            return;
        }
        list_msg.add(new Message(content, Message.TYPE_SENT));
        adapter.notifyDataSetChanged();
    }
}
