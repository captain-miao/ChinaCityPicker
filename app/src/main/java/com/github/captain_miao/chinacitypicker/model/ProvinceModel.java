package com.github.captain_miao.chinacitypicker.model;

import com.github.captain_miao.citypicker.library.model.CityItem;
import com.github.captain_miao.citypicker.library.model.ProvinceItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProvinceModel implements ProvinceItem {
    @SerializedName("areaId")
    public String code;
    @SerializedName("areaName")
    public String name;
    @SerializedName("cities")
    public List<CityModel> cityList;

    public String getCode(){
        return code;
    }
    public String getName(){
        return name;
    }

    public List<? extends CityItem> getCityList(){
        return cityList;
    }

}
