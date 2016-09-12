package com.github.captain_miao.chinacitypicker.model4fastjson;


import com.alibaba.fastjson.annotation.JSONField;
import com.github.captain_miao.citypicker.library.model.DistrictItem;

public class DistrictModel4FastJson implements DistrictItem {
    @JSONField(name="areaId")
    public String code;

    @JSONField(name="areaName")
    public String name;

    public DistrictModel4FastJson() {
    }

    public DistrictModel4FastJson(String code, String name) {
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
