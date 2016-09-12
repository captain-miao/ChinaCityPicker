package com.github.captain_miao.chinacitypicker.model4fastjson;


import com.alibaba.fastjson.annotation.JSONField;
import com.github.captain_miao.citypicker.library.model.CityItem;
import com.github.captain_miao.citypicker.library.model.DistrictItem;

import java.util.List;

public class CityModel4FastJson implements CityItem {
    @JSONField(name="areaId")
    public String code;
    @JSONField(name="areaName")
    public String name;
    @JSONField(name="counties")
    public List<DistrictModel4FastJson> districtList;

    public CityModel4FastJson() {
    }

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
