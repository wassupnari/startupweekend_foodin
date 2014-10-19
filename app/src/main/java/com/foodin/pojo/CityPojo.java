package com.foodin.pojo;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by wassupnari on 10/18/14.
 */
public class CityPojo {
    @Expose
    public String city;
    @Expose
    public List<ItemPojo> list;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ItemPojo> getList() {
        return list;
    }

    public void setList(List<ItemPojo> list) {
        this.list = list;
    }
}
