package com.github.captain_miao.chinacitypicker.model;

import com.github.captain_miao.citypicker.library.model.CityItem;
import com.github.captain_miao.citypicker.library.model.DistrictItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel implements CityItem {
    @SerializedName("areaId")
    public String code;
    @SerializedName("areaName")
    public String name;
    @SerializedName("counties")
    public List<DistrictModel> districtList;

    public String getCode(){
        return code;
    }
    public String getName(){
        return name;
    }

    public List<? extends DistrictItem> getDistrictList(){
        return districtList;
    }

}
