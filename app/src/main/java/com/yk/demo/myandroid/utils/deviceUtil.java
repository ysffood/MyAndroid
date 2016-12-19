package com.yk.demo.myandroid.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 获取手机的常用基本信息
 * @author yk
 * @version V1.0.0
 * created at 2016/12/19 11:43
 */
public class DeviceUtil {

    public static String getUuid(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
