//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fosu.jobapp.view;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ScrollView;

public class ScrollableHelper {
    private View mCurrentScrollableView;
    private ScrollableHelper.ScrollableContainer mCurrentScrollableCainer;
    private int sysVersion;

    public ScrollableHelper() {
        this.sysVersion = VERSION.SDK_INT;
    }

    public void setCurrentScrollableContainer(ScrollableHelper.ScrollableContainer scrollableContainer) {
        this.mCurrentScrollableCainer = scrollableContainer;
    }

    public void setCurrentScrollableContainer(View view) {
        this.mCurrentScrollableView = view;
    }

    private View getScrollableView() {
        return this.mCurrentScrollableCainer == null ? this.mCurrentScrollableView : this.mCurrentScrollableCainer.getScrollableView();
    }

    public boolean isTop() {
        View scrollableView = this.getScrollableView();
        if (scrollableView == null) {
            return false;
        } else if (scrollableView instanceof AdapterView) {
            return isAdapterViewTop((AdapterView) scrollableView);
        } else if (scrollableView instanceof ScrollView) {
            return isScrollViewTop((ScrollView) scrollableView);
        } else if (scrollableView instanceof RecyclerView) {
            return isRecyclerViewTop((RecyclerView) scrollableView);
        } else if (scrollableView instanceof WebView) {
            return isWebViewTop((WebView) scrollableView);
        } else if (scrollableView instanceof ViewPager) {
            return isWebViewTop((WebView) scrollableView);
        } else {
            throw new IllegalStateException("scrollableView must be a instance of AdapterView|ScrollView|RecyclerView");
        }
    }

    private static boolean isRecyclerViewTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                View childAt = recyclerView.getChildAt(0);
                if (childAt == null) {
                    return true;
                }

                if (firstVisibleItemPosition == 0) {
                    MarginLayoutParams lp = (MarginLayoutParams) childAt.getLayoutParams();
                    int topMargin = lp.topMargin;
                    int top = childAt.getTop();
                    if (top >= topMargin) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean isAdapterViewTop(AdapterView adapterView) {
        if (adapterView != null) {
            int firstVisiblePosition = adapterView.getFirstVisiblePosition();
            View childAt = adapterView.getChildAt(0);
            if (childAt == null || firstVisiblePosition == 0 && childAt != null && childAt.getTop() == 0) {
                return true;
            }
        }

        return false;
    }

    private static boolean isScrollViewTop(ScrollView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        } else {
            return false;
        }
    }

    private static boolean isWebViewTop(WebView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        } else {
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    public void smoothScrollBy(int velocityY, int distance, int duration) {
        View scrollableView = this.getScrollableView();
        if (scrollableView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) scrollableView;
            if (this.sysVersion >= 21) {
                absListView.fling(velocityY);
            } else {
                absListView.smoothScrollBy(distance, duration);
            }
        } else if (scrollableView instanceof ScrollView) {
            ((ScrollView) scrollableView).fling(velocityY);
        } else if (scrollableView instanceof RecyclerView) {
            ((RecyclerView) scrollableView).fling(0, velocityY);
        } else if (scrollableView instanceof WebView) {
            ((WebView) scrollableView).flingScroll(0, velocityY);
        }

    }

    public interface ScrollableContainer {
        View getScrollableView();
    }
}
