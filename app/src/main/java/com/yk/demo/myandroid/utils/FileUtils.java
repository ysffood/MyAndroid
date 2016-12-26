package com.yk.demo.myandroid.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 文件工具类
 * @author yk
 * @version V1.0.0
 * created at 2016/12/22 17:01
 */
public class FileUtils {

    /**
     * 获取缓存路径
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
