package com.xingyu.smartrefrigerator;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;


public class CustomXValueFormatter  implements IAxisValueFormatter {

    private String[] mValues;

    public CustomXValueFormatter(String[] values) {
        this.mValues = values;
    }
    private static final String TAG = "MyXFormatter";

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        Log.d(TAG, "----->getFormattedValue: "+value);
        return mValues[(int) value % mValues.length];
    }
}
