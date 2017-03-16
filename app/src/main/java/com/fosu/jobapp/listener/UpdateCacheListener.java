package com.fosu.jobapp.listener;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;

/**
 * Created by Administrator on 2017/3/16.
 */

public abstract class UpdateCacheListener extends BmobListener {
    public abstract void done(BmobException e);

    @Override
    protected void postDone(Object o, BmobException e) {
        done(e);
    }
}
