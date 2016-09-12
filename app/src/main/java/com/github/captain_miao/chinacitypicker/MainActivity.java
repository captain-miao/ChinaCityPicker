package com.github.captain_miao.chinacitypicker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.captain_miao.chinacitypicker.model.ProvinceModel;
import com.github.captain_miao.chinacitypicker.model4fastjson.ProvinceModel4FastJson;
import com.github.captain_miao.chinacitypicker.utils.JAssetsUtils;
import com.github.captain_miao.citypicker.library.model.CityItem;
import com.github.captain_miao.citypicker.library.model.DistrictItem;
import com.github.captain_miao.citypicker.library.model.ProvinceItem;
import com.github.captain_miao.citypicker.library.widget.CityPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<ProvinceItem> mProvinceItems;
    List<ProvinceModel4FastJson> mProvinceModels;
    CityPickerView mCityPickerView;
    TextView mTvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvAddress = (TextView) findViewById(R.id.tv_select_address);
        findViewById(R.id.tv_select_address_gson).setOnClickListener(this);
        findViewById(R.id.tv_select_address_fast_json).setOnClickListener(this);

        initData();
        initDataByFastJson();
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address_gson: {
                if (mProvinceItems != null && mProvinceItems.size() > 0) {
                    if (mCityPickerView == null) {
                        mCityPickerView = new CityPickerView(MainActivity.this, mProvinceItems);
                        mCityPickerView.setTextColor(getResources().getColor(R.color.color_33));
                        mCityPickerView.setTextSize(17);
                        mCityPickerView.setVisibleItems(7);
                        mCityPickerView.setIsCyclic(false);
                        mCityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                            @Override
                            public void onSelected(ProvinceItem province, CityItem city, DistrictItem district) {
                                mTvAddress.setText(
                                        getString(R.string.selected_address, province.getName(),
                                                city.getName(), district.getName()));
                            }
                        });
                    }
                    mCityPickerView.show();
                } else {
                    Toast.makeText(MainActivity.this, "load data fail", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.tv_select_address_fast_json: {
                if (mProvinceModels != null && mProvinceModels.size() > 0) {
                    if (mCityPickerView == null) {
                        mCityPickerView = new CityPickerView(MainActivity.this, mProvinceModels);
                        mCityPickerView.setTextColor(getResources().getColor(R.color.color_33));
                        mCityPickerView.setTextSize(17);
                        mCityPickerView.setVisibleItems(7);
                        mCityPickerView.setIsCyclic(false);
                        mCityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                            @Override
                            public void onSelected(ProvinceItem province, CityItem city, DistrictItem district) {
                                mTvAddress.setText(
                                        getString(R.string.selected_address, province.getName(),
                                                city.getName(), district.getName()));
                            }
                        });
                    }
                    mCityPickerView.show();
                } else {
                    Toast.makeText(MainActivity.this, "load data fail", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void initData(){
        new AsyncTask<Boolean, Boolean, List<ProvinceItem>>() {
            @Override
            protected List<ProvinceItem> doInBackground(Boolean... bool) {
                try {
                    // 获取解析出来的数据
                    String json = JAssetsUtils.getJsonDataFromAssets(MainActivity.this, "city.json");
                    return new Gson().fromJson(json, new TypeToken<List<ProvinceModel>>() {
                    }.getType());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<ProvinceItem> provinceItems) {
                mProvinceItems = provinceItems;
            }
        }.execute();
    }



    private void initDataByFastJson(){
        new AsyncTask<Boolean, Boolean, List<ProvinceModel4FastJson>>() {
            @Override
            protected List<ProvinceModel4FastJson> doInBackground(Boolean... bool) {
                try {
                    // 获取解析出来的数据
                    String json = JAssetsUtils.getJsonDataFromAssets(MainActivity.this, "city.json");
                    return JSON.parseObject(json, new TypeReference<List<ProvinceModel4FastJson>>() {});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<ProvinceModel4FastJson> provinceItems) {
                mProvinceModels = provinceItems;
            }
        }.execute();
    }
}
