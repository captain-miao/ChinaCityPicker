package com.github.captain_miao.citypicker.library.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.captain_miao.citypicker.library.R;
import com.github.captain_miao.citypicker.library.model.CityItem;
import com.github.captain_miao.citypicker.library.model.DistrictItem;
import com.github.captain_miao.citypicker.library.model.ProvinceItem;
import com.github.captain_miao.citypicker.library.widget.wheel.OnWheelChangedListener;
import com.github.captain_miao.citypicker.library.widget.wheel.WheelView;
import com.github.captain_miao.citypicker.library.widget.wheel.adapters.ArrayWheelAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 省市区三级选择
 * 作者：liji on 2015/12/17 10:40
 * 邮箱：lijiwork@sina.com
 */
public class CityPickerView implements CanShow, OnWheelChangedListener {

    private Context context;

    private PopupWindow popwindow;

    private View popview;

    private WheelView mViewProvince;

    private WheelView mViewCity;

    private WheelView mViewDistrict;

    private TextView mTvOK;

    private TextView mTvCancel;

    /**
     * 所有省
     */
    protected ProvinceItem[] mProvinces;

    /**
     * key - 省 value - 市
     */
    protected Map<String, CityItem[]> mCitisMap = new HashMap<>();

    /**
     * key - 市 values - 区
     */
    protected Map<String, DistrictItem[]> mDistrictsMap = new HashMap<>();


    /**
     * 当前省的名称
     */
    protected ProvinceItem mCurrentProvince;

    /**
     * 当前市的名称
     */
    protected CityItem mCurrentCity;

    /**
     * 当前区的名称
     */
    protected DistrictItem mCurrentDistrict;

    private OnCityItemClickListener listener;

    public interface OnCityItemClickListener {
        void onSelected(ProvinceItem province, CityItem city, DistrictItem district);
    }

    public void setOnCityItemClickListener(OnCityItemClickListener listener) {
        this.listener = listener;
    }


    /**
     * Default text color
     */
    public static final int DEFAULT_TEXT_COLOR = 0xFF585858;

    /**
     * Default text size
     */
    public static final int DEFAULT_TEXT_SIZE = 18;

    // Text settings
    private int textColor = DEFAULT_TEXT_COLOR;
    private int textSize = DEFAULT_TEXT_SIZE;

    /**
     * 滚轮显示的item个数
     */
    private static final int DEF_VISIBLE_ITEMS = 5;

    // Count of visible items
    private int visibleItems = DEF_VISIBLE_ITEMS;


    /**
     * 滚轮是否循环滚动
     */
    boolean isCyclic = true;


    public CityPickerView(final Context context, List<? extends ProvinceItem> provinceList) {
        super();
        this.context = context;
        popview = LayoutInflater.from(context).inflate(R.layout.pop_citypicker, null);

        mViewProvince = (WheelView) popview.findViewById(R.id.id_province);
        mViewCity = (WheelView) popview.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) popview.findViewById(R.id.id_district);
        mTvOK = (TextView) popview.findViewById(R.id.tv_confirm);
        mTvCancel = (TextView) popview.findViewById(R.id.tv_cancel);

        popwindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popwindow.setBackgroundDrawable(new ColorDrawable());
        popwindow.setAnimationStyle(R.style.AnimBottom);
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(true);
        popwindow.setFocusable(true);

        //初始化城市数据
        initProvinceDatas(provinceList);


        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        mTvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelected(mCurrentProvince, mCurrentCity, mCurrentDistrict);
                hide();
            }
        });
    }


    /**
     * 设置颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        this.textColor = color;
    }

    /**
     * 设置颜色
     *
     * @param colorString
     */
    public void setTextColor(String colorString) {
        this.textColor = Color.parseColor(colorString);
    }

    private int getTextColor() {
        return textColor;
    }

    /**
     * 设置文字大小
     *
     * @param textSp
     */
    public void setTextSize(int textSp) {
        this.textSize = textSp;
    }

    private int getTextSize() {
        return textSize;
    }

    /**
     * 设置滚轮显示个数
     *
     * @param count
     */
    public void setVisibleItems(int count) {
        this.visibleItems = count;
    }

    private int getVisibleItems() {
        return this.visibleItems;
    }

    /**
     * 设置滚轮是否循环滚动
     *
     * @param isCyclic
     */
    public void setIsCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
    }

    private boolean getIsCyclic() {
        return this.isCyclic;
    }

    private void setUpData() {
        ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter<>(context, mProvinces);
        mViewProvince.setViewAdapter(arrayWheelAdapter);
        // 设置可见条目数量
        mViewProvince.setVisibleItems(getVisibleItems());
        mViewCity.setVisibleItems(getVisibleItems());
        mViewDistrict.setVisibleItems(getVisibleItems());
        mViewProvince.setCyclic(getIsCyclic());
        mViewCity.setCyclic(getIsCyclic());
        mViewDistrict.setCyclic(getIsCyclic());

        arrayWheelAdapter.setTextColor(getTextColor());
        arrayWheelAdapter.setTextSize(getTextSize());

        updateCities();
        updateAreas();
    }


    protected void initProvinceDatas(List<? extends ProvinceItem> provinceList) {

            mProvinces = new ProvinceItem[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinces[i] = provinceList.get(i);
                List<? extends CityItem> cityList = provinceList.get(i).getCityList();
                CityItem[] cityItems = new CityItem[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityItems[j] = cityList.get(j);
                    List<? extends DistrictItem> districtList = cityList.get(j).getDistrictList();
                    //String[] districtNameArray = new String[districtList.size()];
                    DistrictItem[] districtArray = new DistrictItem[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        districtArray[k] = districtList.get(k);
                        //districtNameArray[k] = districtModel.getName();
                    }
                    cityItems[j].getDistrictList().clear();
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictsMap.put(cityItems[j].getName(), districtArray);
                }
                mProvinces[i].getCityList().clear();
                // 省-市的数据，保存到mCitisDatasMap
                mCitisMap.put(provinceList.get(i).getName(), cityItems);
            }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCity = mCitisMap.get(mCurrentProvince.getName())[pCurrent];
        DistrictItem[] areas = mDistrictsMap.get(mCurrentCity.getName());

        if (areas == null) {
            areas = new DistrictItem[0];
        }
        ArrayWheelAdapter districtWheel = new ArrayWheelAdapter<>(context, areas);
        // 设置可见条目数量
        districtWheel.setTextColor(getTextColor());
        districtWheel.setTextSize(getTextSize());
        mViewDistrict.setViewAdapter(districtWheel);
        mViewDistrict.setCurrentItem(0);

        //获取第一个区名称
        mCurrentDistrict = mDistrictsMap.get(mCurrentCity.getName())[0];
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProvince = mProvinces[pCurrent];
        CityItem[] cities = mCitisMap.get(mCurrentProvince.getName());
        if (cities == null) {
            cities = new CityItem[0];
        }

        ArrayWheelAdapter cityWheel = new ArrayWheelAdapter<>(context, cities);
        // 设置可见条目数量
        cityWheel.setTextColor(getTextColor());
        cityWheel.setTextSize(getTextSize());
        mViewCity.setViewAdapter(cityWheel);
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void setType(int type) {
    }

    @Override
    public void show() {
        if (!isShow()) {
            setUpData();
            popwindow.showAtLocation(popview, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void hide() {
        if (isShow()) {
            popwindow.dismiss();
        }
    }

    @Override
    public boolean isShow() {
        return popwindow.isShowing();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrict = mDistrictsMap.get(mCurrentCity.getName())[newValue];
        }
    }


    public WheelView getViewProvince() {
        return mViewProvince;
    }

    public WheelView getViewCity() {
        return mViewCity;
    }

    public WheelView getViewDistrict() {
        return mViewDistrict;
    }
}
