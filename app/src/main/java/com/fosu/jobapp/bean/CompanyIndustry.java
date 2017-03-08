package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/8.
 */

public class CompanyIndustry extends BmobObject implements SimpleInfo {
    private String industry;

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "CompanyIndustry{" +
                "industry='" + industry + '\'' +
                '}';
    }

    @Override
    public String getInfo() {
        return industry;
    }
}
