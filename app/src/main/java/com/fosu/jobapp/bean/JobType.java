package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/7.
 */

public class JobType extends BmobObject {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JobType{" +
                "type='" + type + '\'' +
                '}';
    }
}
