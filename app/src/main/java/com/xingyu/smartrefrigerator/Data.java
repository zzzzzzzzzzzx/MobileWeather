package com.xingyu.smartrefrigerator;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Data implements Parcelable {
    private int count;
    private List<Datastreams> datastreams;

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setDatastreams(List<Datastreams> datastreams) {
        this.datastreams = datastreams;
    }
    public List<Datastreams> getDatastreams() {
        return datastreams;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(this.datastreams);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.count = in.readInt();
        this.datastreams = in.createTypedArrayList(Datastreams.CREATOR);
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
