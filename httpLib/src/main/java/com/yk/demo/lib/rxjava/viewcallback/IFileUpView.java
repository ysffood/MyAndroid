package com.yk.demo.lib.rxjava.viewcallback;

/**
 * 观察者角色抽象类
 * 用户Presenter与页面之间数据回调交互
 * @author yk
 * @version V1.0.0
 * created at 2016/12/12 17:25
 */

public interface IFileUpView {

    /**
     * 上传单文件请求返回成功
     * @param data 返回数据
     */
    void getFileUpSuccessful(Object data);

    /**
     * 上传单文件请求返回失败
     * @param e 失败异常
     */
    void getFileUpFailure(Exception e);

    /**
     * 上传多文件请求返回成功
     * @param data 返回数据
     */
    void getMultiFileUpSuccessful(Object data);

    /**
     * 上传多件请求返回失败
     * @param e 失败异常
     */
    void getMultiFileUpFailure(Exception e);

    /**
     * 复合上传请求返回成功
     * @param data 返回数据
     */
    void getSuffixFileUpSuccessful(Object data);

    /**
     * 复合上传请求返回失败
     * @param e 失败异常
     */
    void getSuffixFileUpFailure(Exception e);
}
