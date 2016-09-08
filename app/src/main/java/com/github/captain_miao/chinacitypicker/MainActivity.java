package com.github.captain_miao.chinacitypicker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.captain_miao.chinacitypicker.model.ProvinceModel;
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
    CityPickerView mCityPickerView;
    TextView mTvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvAddress = (TextView) findViewById(R.id.tv_select_address);
        mTvAddress.setOnClickListener(this);

        initData();
    }




    @Override
    public void onClick(View view) {
        if (mProvinceItems != null && mProvinceItems.size() > 0) {
            if (mCityPickerView == null) {
                mCityPickerView = new CityPickerView(MainActivity.this, mProvinceItems);
                mCityPickerView.setTextColor(getResources().getColor(R.color.color_33));
                //mCityPickerView.setTextSize(convertSp2Pixels(this, 17));
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


    public static int convertSp2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip, context.getResources().getDisplayMetrics());
    }
}
