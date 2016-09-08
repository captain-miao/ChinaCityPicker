package com.github.captain_miao.chinacitypicker.model;

import com.github.captain_miao.citypicker.library.model.DistrictItem;
import com.google.gson.annotations.SerializedName;

public class DistrictModel implements DistrictItem {
    @SerializedName("areaId")
    public String code;
    @SerializedName("areaName")
    public String name;

    public DistrictModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return code;
    }
    public String getName(){
        return name;
    }
}
