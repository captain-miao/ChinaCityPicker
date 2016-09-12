package com.github.captain_miao.chinacitypicker.model4fastjson;


import com.alibaba.fastjson.annotation.JSONField;
import com.github.captain_miao.citypicker.library.model.CityItem;
import com.github.captain_miao.citypicker.library.model.ProvinceItem;

import java.util.List;

public class ProvinceModel4FastJson implements ProvinceItem {
    @JSONField(name="areaId")
    public String code;

    @JSONField(name="areaName")
    public String name;

    @JSONField(name="cities")
    public List<CityModel4FastJson> cityList;

    public ProvinceModel4FastJson() {
    }

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
