package com.yk.demo.lib.rxjava.presenter;

import java.io.File;
import java.util.Map;
/**
 * 发送复合文件上传请求带字段参数
 * @author yk
 * @version V1.0.0
 * created at 2016/12/13 17:31
 */
public interface IUpSuffixFileRequest {
    /**
     * 多文件上传
     * @param files 上传文件集合
     */
    void sendUpSuffixFileRequest(Map<String, File> files, String test1, String test2);
}
