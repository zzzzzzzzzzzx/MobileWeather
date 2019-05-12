package com.xingyu.smartrefrigerator;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class JsonRootBean implements Parcelable {
    private int errno;
    private Data data;
    private String error;

    public void setErrno(int errno) {
        this.errno = errno;
    }
    public int getErrno() {
        return errno;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.errno);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.error);
    }

    public JsonRootBean() {
    }

    protected JsonRootBean(Parcel in) {
        this.errno = in.readInt();
        this.data = in.readParcelable(Data.class.getClassLoader());
        this.error = in.readString();
    }

    public static final Parcelable.Creator<JsonRootBean> CREATOR = new Parcelable.Creator<JsonRootBean>() {
        @Override
        public JsonRootBean createFromParcel(Parcel source) {
            return new JsonRootBean(source);
        }

        @Override
        public JsonRootBean[] newArray(int size) {
            return new JsonRootBean[size];
        }
    };
}
