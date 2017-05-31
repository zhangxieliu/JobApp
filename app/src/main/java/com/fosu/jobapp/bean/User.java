package com.fosu.jobapp.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/3/7.
 */

public class User extends BmobUser {
    private BmobFile avatar;
    private String introduction;
    private int role;
    private PersonalResume personalResume;
    private int age;

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public PersonalResume getPersonalResume() {
        return personalResume;
    }

    public void setPersonalResume(PersonalResume personalResume) {
        this.personalResume = personalResume;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
