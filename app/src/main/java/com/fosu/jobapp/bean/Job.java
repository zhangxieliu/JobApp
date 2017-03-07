package com.fosu.jobapp.bean;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Administrator on 2017/3/7.
 */

public class Job extends BmobObject {
    private String jobName;
    private String jobCity;
    private String jobCityLocation;
    private String jobBenefits;
    private List<String> jobSalary;
    private List<String> jobTag;
    private String jobDescribe;
    private String jobRequirement;
    private BmobDate jobPublicDate;
    private Company company;
    private JobExperience jobExperience;
    private JobEducation jobEducation;
    private JobType jobType;
    private User user;

    public Job() {
    }

    public Job(String tableName) {
        super(tableName);
    }

    public Job(String jobName, String jobCity, String jobCityLocation, String jobBenefits, List<String> jobSalary, List<String> jobTag, String jobDescribe, String jobRequirement, BmobDate jobPublicDate, Company company, JobExperience jobExperience, JobEducation jobEducation, JobType jobType, User user) {
        this.jobName = jobName;
        this.jobCity = jobCity;
        this.jobCityLocation = jobCityLocation;
        this.jobBenefits = jobBenefits;
        this.jobSalary = jobSalary;
        this.jobTag = jobTag;
        this.jobDescribe = jobDescribe;
        this.jobRequirement = jobRequirement;
        this.jobPublicDate = jobPublicDate;
        this.company = company;
        this.jobExperience = jobExperience;
        this.jobEducation = jobEducation;
        this.jobType = jobType;
        this.user = user;
    }

    public Job(String tableName, String jobName, String jobCity, String jobCityLocation, String jobBenefits, List<String> jobSalary, List<String> jobTag, String jobDescribe, String jobRequirement, BmobDate jobPublicDate, Company company, JobExperience jobExperience, JobEducation jobEducation, JobType jobType, User user) {
        super(tableName);
        this.jobName = jobName;
        this.jobCity = jobCity;
        this.jobCityLocation = jobCityLocation;
        this.jobBenefits = jobBenefits;
        this.jobSalary = jobSalary;
        this.jobTag = jobTag;
        this.jobDescribe = jobDescribe;
        this.jobRequirement = jobRequirement;
        this.jobPublicDate = jobPublicDate;
        this.company = company;
        this.jobExperience = jobExperience;
        this.jobEducation = jobEducation;
        this.jobType = jobType;
        this.user = user;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobCity() {
        return jobCity;
    }

    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    public String getJobCityLocation() {
        return jobCityLocation;
    }

    public void setJobCityLocation(String jobCityLocation) {
        this.jobCityLocation = jobCityLocation;
    }

    public String getJobBenefits() {
        return jobBenefits;
    }

    public void setJobBenefits(String jobBenefits) {
        this.jobBenefits = jobBenefits;
    }

    public List<String> getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(List<String> jobSalary) {
        this.jobSalary = jobSalary;
    }

    public List<String> getJobTag() {
        return jobTag;
    }

    public void setJobTag(List<String> jobTag) {
        this.jobTag = jobTag;
    }

    public String getJobDescribe() {
        return jobDescribe;
    }

    public void setJobDescribe(String jobDescribe) {
        this.jobDescribe = jobDescribe;
    }

    public String getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }

    public BmobDate getJobPublicDate() {
        return jobPublicDate;
    }

    public void setJobPublicDate(BmobDate jobPublicDate) {
        this.jobPublicDate = jobPublicDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public JobExperience getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(JobExperience jobExperience) {
        this.jobExperience = jobExperience;
    }

    public JobEducation getJobEducation() {
        return jobEducation;
    }

    public void setJobEducation(JobEducation jobEducation) {
        this.jobEducation = jobEducation;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobName='" + jobName + '\'' +
                ", jobCity='" + jobCity + '\'' +
                ", jobCityLocation='" + jobCityLocation + '\'' +
                ", jobBenefits='" + jobBenefits + '\'' +
                ", jobSalary=" + jobSalary +
                ", jobTag=" + jobTag +
                ", jobDescribe='" + jobDescribe + '\'' +
                ", jobRequirement='" + jobRequirement + '\'' +
                ", jobPublicDate=" + jobPublicDate +
                ", company=" + company +
                ", jobExperience=" + jobExperience +
                ", jobEducation=" + jobEducation +
                ", jobType=" + jobType +
                ", user=" + user +
                '}';
    }
}
