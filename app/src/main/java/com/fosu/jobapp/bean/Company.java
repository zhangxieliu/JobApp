package com.fosu.jobapp.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/3/7.
 */

public class Company extends BmobObject {
    private String companyName; // 公司名称
    private BmobFile companyLogo;   // 公司logo
    private String companyCity; // 公司所在城市
    private String companyCityLocation; // 公司所在城市具体位置
    private List<String> companyTag;    // 公司标签
    private String companyWebsite;  // 公司网址
    private String companyIntroduce;    // 公司介绍
    private CompanyType companyType;
    private CompanyScale companyScale;
    private CompanyAudit companyAudit;

    public Company() {
    }

    public Company(String tableName) {
        super(tableName);
    }

    public Company(String companyName, BmobFile companyLogo, String companyCity, String companyCityLocation, List<String> companyTag, String companyWebsite, String companyIntroduce, CompanyType companyType, CompanyScale companyScale, CompanyAudit companyAudit) {
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.companyCity = companyCity;
        this.companyCityLocation = companyCityLocation;
        this.companyTag = companyTag;
        this.companyWebsite = companyWebsite;
        this.companyIntroduce = companyIntroduce;
        this.companyType = companyType;
        this.companyScale = companyScale;
        this.companyAudit = companyAudit;
    }

    public Company(String tableName, String companyName, BmobFile companyLogo, String companyCity, String companyCityLocation, List<String> companyTag, String companyWebsite, String companyIntroduce, CompanyType companyType, CompanyScale companyScale, CompanyAudit companyAudit) {
        super(tableName);
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.companyCity = companyCity;
        this.companyCityLocation = companyCityLocation;
        this.companyTag = companyTag;
        this.companyWebsite = companyWebsite;
        this.companyIntroduce = companyIntroduce;
        this.companyType = companyType;
        this.companyScale = companyScale;
        this.companyAudit = companyAudit;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BmobFile getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(BmobFile companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyCityLocation() {
        return companyCityLocation;
    }

    public void setCompanyCityLocation(String companyCityLocation) {
        this.companyCityLocation = companyCityLocation;
    }

    public List<String> getCompanyTag() {
        return companyTag;
    }

    public void setCompanyTag(List<String> companyTag) {
        this.companyTag = companyTag;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyIntroduce() {
        return companyIntroduce;
    }

    public void setCompanyIntroduce(String companyIntroduce) {
        this.companyIntroduce = companyIntroduce;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public CompanyScale getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(CompanyScale companyScale) {
        this.companyScale = companyScale;
    }

    public CompanyAudit getCompanyAudit() {
        return companyAudit;
    }

    public void setCompanyAudit(CompanyAudit companyAudit) {
        this.companyAudit = companyAudit;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", companyLogo=" + companyLogo.getUrl() +
                ", companyCity='" + companyCity + '\'' +
                ", companyCityLocation='" + companyCityLocation + '\'' +
                ", companyTag=" + companyTag +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", companyIntroduce='" + companyIntroduce + '\'' +
                ", companyType=" + companyType +
                ", companyScale=" + companyScale +
                ", companyAudit=" + companyAudit +
                '}';
    }
}
