package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/7.
 */

public class CompanyType extends BmobObject implements SimpleInfo {
    private String type;

    public CompanyType(String type) {
        this.type = type;
    }

    public CompanyType(String tableName, String type) {
        super(tableName);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CompanyType{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public String getInfo() {
        return type;
    }
}
