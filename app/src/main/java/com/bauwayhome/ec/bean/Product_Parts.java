package com.bauwayhome.ec.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 烟油配件实体类
 * Created by Danny on 2018/3/14.
 */

public class Product_Parts extends BmobObject{
    private String parts_name;
    private String model;
    private String flavor;
    private String capacity;
    private String parts_url;
    private BmobFile parts_image;

    public Product_Parts() {
        this.setTableName("Product_Parts");
    }
    public Product_Parts(String parts_name, String model, String flavor, String capacity, BmobFile parts_image ) {
        this.parts_name = parts_name;
        this.model = model;
        this.flavor = flavor;
        this.capacity = capacity;
        this.parts_image = parts_image;
    }

    public String getParts_name() {
        return parts_name;
    }

    public void setParts_name(String parts_name) {
        this.parts_name = parts_name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getIconUrl() { return parts_image.getFileUrl(); }

    public BmobFile getParts_image() {
        return parts_image;
    }

    public void setParts_image(BmobFile parts_image) {
        this.parts_image = parts_image;
    }

    public String getParts_url() {
        return parts_url;
    }

    public void setParts_url(String parts_url) {
        this.parts_url = parts_url;
    }
}
