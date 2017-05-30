package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/29.
 */

public class DeliveryRecord extends BmobObject {
    private User user;
    private Job job;
    private String status;
    private PersonalResume personalResume;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PersonalResume getPersonalResume() {
        return personalResume;
    }

    public void setPersonalResume(PersonalResume personalResume) {
        this.personalResume = personalResume;
    }

    @Override
    public String toString() {
        return "DeliveryRecord{" +
                "user=" + user +
                ", job=" + job +
                ", status='" + status + '\'' +
                ", personalResume=" + personalResume +
                '}';
    }
}
