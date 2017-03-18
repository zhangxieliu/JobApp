package com.fosu.jobapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.fosu.jobapp.R;
import com.fosu.jobapp.listener.OnFragmentListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/11.
 */

public class CompanyInfoFragment extends Fragment {
    private static final String TAG = "CompanyInfoFragment";

    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private OnFragmentListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listener.doSomething(expandTextView);
        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Logger.i("scrollX:" + scrollX + ", scrollY:" + scrollY +
                        ", oldScrollX:" + oldScrollX + ", oldScrollY" + oldScrollY);
            }
        });
    }

    public OnFragmentListener getListener() {
        return listener;
    }

    public void setListener(OnFragmentListener listener) {
        this.listener = listener;
    }
}
