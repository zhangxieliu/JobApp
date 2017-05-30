package com.fosu.jobapp.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/30.
 */

public class JobOperation extends BmobObject {
    private User user;
    private Job job;
    private Integer type;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JobOperation{" +
                "user=" + user +
                ", job=" + job +
                ", type=" + type +
                '}';
    }
}
