package com.yk.demo.lib.rxjava.presenter;

import java.io.File;

/**
 * 发送单文件上传请求
 * @author yk
 * @version V1.0.0
 * created at 2016/12/12 17:31
 */
public interface IUpFileRequest {
    /**
     * 单文件上传
     * @param file 上传文件
     */
    void sendUpFileRequest(File file);
}
