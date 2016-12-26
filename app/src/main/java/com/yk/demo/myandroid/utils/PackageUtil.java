package com.yk.demo.myandroid.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 包管理工具类
 * @author yk
 * @version V1.0.0
 * created at 2016/12/20 15:11
 */
public class PackageUtil {

    /**
     * 获取包信息
     * @param context context
     * @return packageInfo
     */
    public static PackageInfo getPackageInfo(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
       } catch (PackageManager.NameNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return  new PackageInfo();
    }

    /**
     * 获取app版本号
     * @param context context
     * @return 版本号
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

}
