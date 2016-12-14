package com.yk.demo.lib.sevice;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UploadFile;

import java.io.File;
import java.util.Map;

import rx.Observable;

/**
 * 文件上传接口服务抽象类
 * @author yk
 * @version V1.0.0
 * created at 2016/12/12 17:09
 */
public interface IFileUpModel {
    /**
     * 文件上传请求接口
     * @param file 上传的文件
     * @return 服务器返回对象
     */
    public Observable<HttpResponse<UploadFile>> uploadFile(File file);

    /**
     * 多文件上传请求接口
     * @param files 上传的文件集合
     * @return 服务器返回对象
     */
    public Observable<HttpResponse<UploadFile>> uploadMultiFile(Map<String,File> files);

    /**
     * 复合上传文件加参数
     * @param files 上传的文件集合
     * @param test1 上传参数字段1
     * @param test2 上传参数字段2
     * @return 服务器返回对象
     */
    public Observable<HttpResponse<UploadFile>> uploadSuffixFile(Map<String,File> files, String test1, String test2);
    
}
