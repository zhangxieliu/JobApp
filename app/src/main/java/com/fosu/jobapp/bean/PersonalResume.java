package com.fosu.jobapp.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/29.
 */

public class PersonalResume extends BmobObject {
    private String name;
    private String resumeTitle;
    private String gender;
    private String startWorkTime;
    private Date birthDate;
    private String weixin;
    private String phone;
    private String advantage;
    private WorkStatus workStatus;
    private JobEducation highEducation;
    private WorkExperience workExperience;
    private ProjectExperience projectExperience;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResumeTitle() {
        return resumeTitle;
    }

    public void setResumeTitle(String resumeTitle) {
        this.resumeTitle = resumeTitle;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public JobEducation getHighEducation() {
        return highEducation;
    }

    public void setHighEducation(JobEducation highEducation) {
        this.highEducation = highEducation;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }

    public ProjectExperience getProjectExperience() {
        return projectExperience;
    }

    public void setProjectExperience(ProjectExperience projectExperience) {
        this.projectExperience = projectExperience;
    }

    @Override
    public String toString() {
        return "PersonalResume{" +
                "name='" + name + '\'' +
                ", resumeTitle='" + resumeTitle + '\'' +
                ", gender='" + gender + '\'' +
                ", startWorkTime='" + startWorkTime + '\'' +
                ", birthDate=" + birthDate +
                ", weixin='" + weixin + '\'' +
                ", phone='" + phone + '\'' +
                ", advantage='" + advantage + '\'' +
                ", workStatus=" + workStatus +
                ", highEducation=" + highEducation +
                ", workExperience=" + workExperience +
                ", projectExperience=" + projectExperience +
                '}';
    }
}