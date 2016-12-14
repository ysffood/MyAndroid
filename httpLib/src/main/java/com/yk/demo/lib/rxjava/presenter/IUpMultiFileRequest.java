package com.yk.demo.lib.rxjava.presenter;

import java.io.File;
import java.util.Map;

/**
 * 发送多文件上传请求
 * @author yk
 * @version V1.0.0
 * created at 2016/12/13 15:15
 */
public interface IUpMultiFileRequest {
    /**
     * 多文件上传
     * @param files 上传文件集合
     */
    void sendUpMultiFileRequest(Map<String, File> files);
}
