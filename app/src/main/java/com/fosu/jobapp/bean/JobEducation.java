package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/7.
 */

public class JobEducation extends BmobObject {
    private String education;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public String toString() {
        return "JobEducation{" +
                "education='" + education + '\'' +
                '}';
    }
}
