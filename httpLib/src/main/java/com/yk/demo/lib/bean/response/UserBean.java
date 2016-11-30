package com.yk.demo.lib.bean.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 网络请求相关实体类
 * @author yk
 * @version V1.0.0
 * created at 2016/11/24 9:38
 */
public class UserBean implements Parcelable{

    private int id;
    private int no;
    private String name;
    private String sex;
    private String department;

    public UserBean(){}

    private UserBean(Parcel in) {
        this.id = in.readInt();
        this.no = in.readInt();
        this.name = in.readString();
        this.sex = in.readString();
        this.department = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.no);
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.department);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
