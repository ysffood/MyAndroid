package com.yk.demo.lib.sevice.imp;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UploadFile;
import com.yk.demo.lib.mapper.ApiManager;
import com.yk.demo.lib.mapper.model.FileUpApi;
import com.yk.demo.lib.sevice.IFileUpModel;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 文件上传接口具体服务类
 * @author yk
 * @version V1.0.0
 * created at 2016/12/12 17:14
 */
public class FileUpModelImp implements IFileUpModel{

    private FileUpModelImp(){}

    private static class FileUpModelImpHolder{
        private static FileUpModelImp instance = new FileUpModelImp();
    }

    public static FileUpModelImp getFileUpModelImp(){
        return FileUpModelImpHolder.instance;
    }

    /**
     * 文件上传接口请求具体服务类
     * @param file 上传的文件
     * @return 服务器返回对象
     */
    @Override
    public Observable<HttpResponse<UploadFile>> uploadFile(File file) {
        FileUpApi fileUpApi = ApiManager.getInstance().getFileUpApi();
        //创建RequwstBody对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return fileUpApi.uploadFile(requestBody);
    }

    /**
     * 多文件上传接口请求具体服务类
     * @param files 上传的文件集合
     * @return 服务器返回对象
     */
    @Override
    public Observable<HttpResponse<UploadFile>> uploadMultiFile(Map<String, File> files) {
        FileUpApi fileUpApi = ApiManager.getInstance().getFileUpApi();
        //组装partMap对象
        Map<String, RequestBody> partMap = new HashMap<>();
        Set<Map.Entry<String, File>> set = files.entrySet();
        Iterator<Map.Entry<String, File>> iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, File> entry =  iterator.next();
            String key = entry.getKey();
            File file = entry.getValue();
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            partMap.put("file\";filename=\""+file.getName(), fileBody);
        }
        return fileUpApi.uploadMultiFile(partMap);
    }

    /**
     * 复合文件上传文件加参数
     * @param files 上传的文件集合
     * @param test1 上传参数字段1
     * @param test2 上传参数字段2
     * @return 服务器返回对象
     */
    @Override
    public Observable<HttpResponse<UploadFile>> uploadSuffixFile(Map<String, File> files, String test1, String test2) {
        FileUpApi fileUpApi = ApiManager.getInstance().getFileUpApi();
        //组装partMap对象
        Map<String, RequestBody> partMap = new HashMap<>();
        Set<Map.Entry<String, File>> set = files.entrySet();
        Iterator<Map.Entry<String, File>> iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, File> entry =  iterator.next();
            String key = entry.getKey();
            File file = entry.getValue();
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            partMap.put("file\";filename=\""+file.getName(), fileBody);
        }
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), test1);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain"), test2);
        return fileUpApi.uploadSuffixFile(partMap,requestBody1,requestBody2);
    }
}
