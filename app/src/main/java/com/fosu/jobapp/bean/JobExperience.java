package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/7.
 */

public class JobExperience extends BmobObject {
    private String experience;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "JobExperience{" +
                "experience='" + experience + '\'' +
                '}';
    }
}
