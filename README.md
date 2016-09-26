## Cities Of China Picker

## Gradle
Get library from  [oss.sonatype.org.io](https://oss.sonatype.org/content/repositories/snapshots)
```java

repositories {

    maven { url 'https://oss.sonatype.org/content/repositories/releases' }
    maven { url "https://oss.sonatype.org/content/repositories/snapshots" }

}

dependencies {
    compile 'com.github.captain-miao:chinacitypicker:1.0.0'
}

```

## 功能
1. 数据和选择器 分离
2. 自定义选择器请覆盖 pop_citypicker.xml
3. todo Wheel的属性自定义

## 支持的属性设置
```
    <color name="city_picker_bg">#eeeeee</color>
    <color name="province_line_border">#C7C7C7</color>
    
    mCityPickerView.setTextColor(getResources().getColor(R.color.colorAccent));
    mCityPickerView.setTextSize(20);
    mCityPickerView.setVisibleItems(7);
    mCityPickerView.setIsCyclic(false);

```

## Screenshot
![cities-of-china-picker](https://raw.githubusercontent.com/captain-miao/me.github.com/master/screenshot/cities-of-china-picker.jpg "cities-of-china-picker")

### 感谢

- crazyandcoder/citypicker:[https://github.com/crazyandcoder/citypicker](https://github.com/crazyandcoder/citypicker)



