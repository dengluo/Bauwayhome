package com.bauwayhome.ec.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by danny on 2018/2/28.
 */

public class User extends BmobUser {

    private String app_msg;
    private String app_name;
    private Boolean isPerson;
    private String [] info;

    public String getApp_msg() {
        return app_msg;
    }

    public void setApp_msg(String app_msg) {
        this.app_msg = app_msg;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public Boolean getPerson() {
        return isPerson;
    }

    public void setPerson(Boolean person) {
        isPerson = person;
    }

    public String [] getInfo() {
        return info;
    }

    public void setInfo(String[] info) {
        this.info = info;
    }
}
