package com.yk.demo.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * http请求返回公共实体类
 * 后台返回数据字段依次映射
 * 以此类属性字段去后台返回数据中查找,依次映射入此类中
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/11/24 16:49
 */
public class HttpResponse<T> implements Parcelable{
    /** 请求返回码 */
    private int code;
    /** 请求返回信息 */
    private String message;
    /** 请求返回数据 */
    private T data;

    public HttpResponse() {
    }

    protected HttpResponse(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HttpResponse> CREATOR = new Creator<HttpResponse>() {
        @Override
        public HttpResponse createFromParcel(Parcel in) {
            return new HttpResponse(in);
        }

        @Override
        public HttpResponse[] newArray(int size) {
            return new HttpResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
