package com.yk.demo.myandroid;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.yk.demo.lib.client.OkHttp3Clent;
import com.yk.demo.myandroid.utils.DeviceUtil;

import org.greenrobot.greendao.database.Database;

import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;

/**
 * app application
 * @author yk
 * @version
 * created at 2016/12/1 11:14
 */
public class DefaultApplication extends Application {

    public static DefaultApplication instances;
    /** 数据库配置 */
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private Database scrite_db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private final static String DB_NAME = "app_db"; // 数据库名
    private final String DB_KEY = DeviceUtil.getUuid(instances); // 数据库加密密码

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
//        Log.d("系统当前时间", System.currentTimeMillis()+"");
        initFresco(this);
        setDatabase();
    }

    public static DefaultApplication getInstances(){
        return instances;
    }

    /**
     * 初始化Fresco图片加载器
     * @param context context
     */
    private void initFresco(Context context){
        OkHttpClient okHttpClient = OkHttp3Clent.getHttpClient(); // build on your own
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(context, okHttpClient)
                .setRequestListeners(requestListeners) //Fresco配置日志输出
                .build();
        Fresco.initialize(context, config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, DB_NAME, null);
//        db = mHelper.getWritableDatabase();
        scrite_db = mHelper.getEncryptedReadableDb(DB_KEY);  // 获取加密db对象
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(scrite_db);
//        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }

}
