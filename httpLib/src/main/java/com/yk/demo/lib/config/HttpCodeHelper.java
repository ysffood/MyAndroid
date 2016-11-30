package com.yk.demo.lib.config;

/**
 * HTTP网络请求返回码全局统一配置管理
 * @author yk
 * @version V1.0.0
 * created at 2016/11/25 16:50
 */
public class HttpCodeHelper {
    /** 服务器返回成功 */
    private static final int CODE_SUCCESS = 200;

    public static String getErrorMessage(int code){
        String message = null;
        switch (code){
            case CODE_SUCCESS:
                message = "返回成功";
                break;
            default:
                message = "未适配的错误码";
                break;
        }
        return message;
    }
}
