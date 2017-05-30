package com.fosu.jobapp.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/29.
 */

public class ProjectExperience extends BmobObject {
    private String projectName;
    private Date startTime;
    private Date endTime;
    private String projectRole;
    private String projectURL;
    private String projectContent;
    private String projectPerformance;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    public String getProjectURL() {
        return projectURL;
    }

    public void setProjectURL(String projectURL) {
        this.projectURL = projectURL;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getProjectPerformance() {
        return projectPerformance;
    }

    public void setProjectPerformance(String projectPerformance) {
        this.projectPerformance = projectPerformance;
    }

    @Override
    public String toString() {
        return "ProjectExperience{" +
                "projectName='" + projectName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", projectRole='" + projectRole + '\'' +
                ", projectURL='" + projectURL + '\'' +
                ", projectContent='" + projectContent + '\'' +
                ", projectPerformance='" + projectPerformance + '\'' +
                '}';
    }
}
