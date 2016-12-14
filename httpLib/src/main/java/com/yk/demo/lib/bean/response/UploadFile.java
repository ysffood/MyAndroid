package com.yk.demo.lib.bean.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  文件上传返回结果实体类
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/12/12 17:07
 */
public class UploadFile implements Parcelable {
    private String fileName;

    public UploadFile() {
    }

    private UploadFile(Parcel in) {
        fileName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UploadFile> CREATOR = new Creator<UploadFile>() {
        @Override
        public UploadFile createFromParcel(Parcel in) {
            return new UploadFile(in);
        }

        @Override
        public UploadFile[] newArray(int size) {
            return new UploadFile[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
