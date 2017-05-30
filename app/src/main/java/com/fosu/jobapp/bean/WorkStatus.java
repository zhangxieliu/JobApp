package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/29.
 */

public class WorkStatus extends BmobObject {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WorkStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
