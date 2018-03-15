package com.bauwayhome.ec.bean;

import cn.bmob.v3.BmobObject;

/**
 * 产品实体类
 * Created by Danny on 2018/3/14.
 */

public class Product_IQOS extends BmobObject{
    private String iqos_name;
    private String iqos_size;
    private String iqos_model;
    private String iqos_battery;

    public Product_IQOS() {
        this.setTableName("Product_IQOS");
    }

    public String getIqos_name() {
        return iqos_name;
    }

    public void setIqos_name(String iqos_name) {
        this.iqos_name = iqos_name;
    }

    public String getIqos_size() {
        return iqos_size;
    }

    public void setIqos_size(String iqos_size) {
        this.iqos_size = iqos_size;
    }

    public String getIqos_model() {
        return iqos_model;
    }

    public void setIqos_model(String iqos_model) {
        this.iqos_model = iqos_model;
    }

    public String getIqos_battery() {
        return iqos_battery;
    }

    public void setIqos_battery(String iqos_battery) {
        this.iqos_battery = iqos_battery;
    }
}
