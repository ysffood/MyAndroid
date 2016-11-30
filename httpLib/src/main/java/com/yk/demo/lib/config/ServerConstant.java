package com.yk.demo.lib.config;

/**
 * 服务器地址静态配置
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/11/23 16:22
 */
public class ServerConstant {
    /** 服务器IP */
    private final static String IP = "192.168.191.1";
    /** 服务器端口号 */
    private final static String PORT = "8080";
    /** 服务器地址 */
    public final static String BASE_URL = "http://"+IP +":"+ PORT+"/";
}
