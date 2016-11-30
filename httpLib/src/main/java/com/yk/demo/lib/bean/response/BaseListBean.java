package com.yk.demo.lib.bean.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 用户列表返回实体类
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/11/24 16:58
 */
public class BaseListBean implements Parcelable{

    private String name;
    private List<ChildInfo> childInfo;

    public BaseListBean() {
    }

    protected BaseListBean(Parcel in) {
        name = in.readString();
        childInfo = in.createTypedArrayList(ChildInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(childInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseListBean> CREATOR = new Creator<BaseListBean>() {
        @Override
        public BaseListBean createFromParcel(Parcel in) {
            return new BaseListBean(in);
        }

        @Override
        public BaseListBean[] newArray(int size) {
            return new BaseListBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildInfo> getChildInfo() {
        return childInfo;
    }

    public void setChildInfo(List<ChildInfo> childInfo) {
        this.childInfo = childInfo;
    }

    static class ChildInfo implements Parcelable{
        private String childName;

        public ChildInfo() {
        }

        protected ChildInfo(Parcel in) {
            childName = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(childName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ChildInfo> CREATOR = new Creator<ChildInfo>() {
            @Override
            public ChildInfo createFromParcel(Parcel in) {
                return new ChildInfo(in);
            }

            @Override
            public ChildInfo[] newArray(int size) {
                return new ChildInfo[size];
            }
        };

        public String getChildName() {
            return childName;
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }
    }
}
