package com.github.captain_miao.chinacitypicker.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取Assets目录下面的json文件
 * Created by liji on 2016/5/6.
 */
public class JAssetsUtils {
    
    /**
     * 读取Assets目录下面指定文件并返回String数据
     * @param context
     * @param fileName
     * @return
     */
    public static String getJsonDataFromAssets(Context context, String fileName) {
        InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int len;
            while ((len = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();

            return new String(outputStream.toByteArray(), "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
