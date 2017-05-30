package com.fosu.jobapp.bean;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/29.
 */

public class WorkExperience extends BmobObject {
    private String companyName;
    private List<String> workLocation;
    private String jobName;
    private String department;
    private Date startTime;
    private Date endTime;
    private String jobContent;
    private String jobPerformance;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(List<String> workLocation) {
        this.workLocation = workLocation;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getJobPerformance() {
        return jobPerformance;
    }

    public void setJobPerformance(String jobPerformance) {
        this.jobPerformance = jobPerformance;
    }

    @Override
    public String toString() {
        return "WorkExperience{" +
                "companyName='" + companyName + '\'' +
                ", workLocation=" + workLocation +
                ", jobName='" + jobName + '\'' +
                ", department='" + department + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", jobContent='" + jobContent + '\'' +
                ", jobPerformance='" + jobPerformance + '\'' +
                '}';
    }
}
