package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/7.
 */

public class CompanyAudit extends BmobObject implements SimpleInfo {
    private String audit;

    public CompanyAudit(String audit) {
        this.audit = audit;
    }

    public CompanyAudit(String tableName, String audit) {
        super(tableName);
        this.audit = audit;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    @Override
    public String toString() {
        return "CompanyAudit{" +
                "audit='" + audit + '\'' +
                '}';
    }

    @Override
    public String getInfo() {
        return audit;
    }
}
