package com.github.captain_miao.citypicker.library.model;

import java.util.List;

public interface ProvinceItem extends BaseItem {

    String getCode();
    List<? extends CityItem> getCityList();

}
