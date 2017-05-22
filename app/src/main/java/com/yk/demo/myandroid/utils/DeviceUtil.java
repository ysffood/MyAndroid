package com.yk.demo.myandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 获取手机的常用基本信息
 * @author yk
 * @version V1.0.0
 * created at 2016/12/19 11:43
 */
public class DeviceUtil {

    /**
     *  获取设备唯一标识号
     *
     * @param context context
     * @return uuid
     */
    public static String getUuid(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity activity
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity activity
     * @return 屏幕高度
     */
    public static int getScreenHeight(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
}
