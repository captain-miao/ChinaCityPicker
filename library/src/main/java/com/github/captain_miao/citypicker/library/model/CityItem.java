package com.github.captain_miao.citypicker.library.model;

import java.util.List;

public interface CityItem extends BaseItem {
    String getCode();
    List<? extends DistrictItem> getDistrictList();
}
