package com.xingyu.smartrefrigerator;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Datastreams implements Parcelable {
    private List<Datapoints> datapoints;
    private String id;

    public void setDatapoints(List<Datapoints> datapoints) {
        this.datapoints = datapoints;
    }
    public List<Datapoints> getDatapoints() {
        return datapoints;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.datapoints);
        dest.writeString(this.id);
    }

    public Datastreams() {
    }

    protected Datastreams(Parcel in) {
        this.datapoints = in.createTypedArrayList(Datapoints.CREATOR);
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Datastreams> CREATOR = new Parcelable.Creator<Datastreams>() {
        @Override
        public Datastreams createFromParcel(Parcel source) {
            return new Datastreams(source);
        }

        @Override
        public Datastreams[] newArray(int size) {
            return new Datastreams[size];
        }
    };
}
