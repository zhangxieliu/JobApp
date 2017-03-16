package com.fosu.jobapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fosu.jobapp.R;
import com.fosu.jobapp.listener.OnFragmentListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/11.
 */

public class CompanyInfoFragment extends Fragment {
    private static final String TAG = "CompanyInfoFragment";

    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
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
    }

    public OnFragmentListener getListener() {
        return listener;
    }

    public void setListener(OnFragmentListener listener) {
        this.listener = listener;
    }
}
