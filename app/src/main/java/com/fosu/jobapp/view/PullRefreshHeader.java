package com.fosu.jobapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.fosu.jobapp.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import in.srain.cube.views.ptr.indicator.PtrTensionIndicator;

/**
 * Created by Administrator on 2017/2/10.
 */

public class PullRefreshHeader extends FrameLayout implements PtrUIHandler {
    private View headerView;
    private PtrFrameLayout mPtrFrameLayout;
    private PtrTensionIndicator mPtrTensionIndicator;

    public PullRefreshHeader(Context context) {
        super(context);
        init();
    }

    public PullRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_pull_refresh_header, this);
    }

    public void setUp(PtrFrameLayout ptrFrameLayout) {
        mPtrFrameLayout = ptrFrameLayout;
        mPtrTensionIndicator = new PtrTensionIndicator();
        mPtrFrameLayout.setPtrIndicator(mPtrTensionIndicator);
    }

    /**
     * 重置 View ，隐藏忙碌进度条，隐藏箭头 View ，更新最后刷新时间。
     * Content 重新回到顶部， Header 消失，整个下拉刷新过程完全结束以后，重置 View 。
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    /**
     * 准备刷新，隐藏忙碌进度条，显示箭头 View ，显示文字，如果是下拉刷新，显示“下拉刷新”，如果是释放刷新，显示“下拉”。
     * 准备刷新，Header 将要出现时调用。
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    /**
     * 准备刷新，隐藏忙碌进度条，显示箭头 View ，显示文字，如果是下拉刷新，显示“下拉刷新”，如果是释放刷新，显示“下拉”。
     * 准备刷新，Header 将要出现时调用。
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    /**
     * 刷新结束，隐藏箭头 View ，隐藏忙碌进度条，显示文字，显示“更新完成”，写入最后刷新时间。
     * 刷新结束，Header 开始向上移动之前调用。
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    /**
     * 下拉过程中位置变化回调。
     * <p>
     * 在拖动情况下，当下拉距离从 小于刷新高度到大于刷新高度 时，箭头 View 从向下，变成向上，同时改变文字显示。
     * 当下拉距离从 大于刷新高度到小于刷新高度 时，箭头 View 从向上，变为向下，同时改变文字显示。
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
