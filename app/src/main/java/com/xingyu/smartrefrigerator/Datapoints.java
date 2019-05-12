package com.xingyu.smartrefrigerator;

import android.os.Parcel;
import android.os.Parcelable;

public class Datapoints implements Parcelable {
    private String at;
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getValue() {
        return value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.at);
        dest.writeString(this.value);
    }

    public Datapoints() {
    }

    protected Datapoints(Parcel in) {
        this.at = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<Datapoints> CREATOR = new Parcelable.Creator<Datapoints>() {
        @Override
        public Datapoints createFromParcel(Parcel source) {
            return new Datapoints(source);
        }

        @Override
        public Datapoints[] newArray(int size) {
            return new Datapoints[size];
        }
    };
}
