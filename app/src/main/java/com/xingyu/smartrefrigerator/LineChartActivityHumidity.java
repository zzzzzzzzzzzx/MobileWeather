package com.xingyu.smartrefrigerator;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivityHumidity extends AppCompatActivity {

    private LineChart lineChart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYAxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    private static final String Temperature = "3303_0_5700";
    private static final String Humidity = "3304_0_5700";
    private static final String Concentration = "3325_0_5700";
    private static final String Illumination = "3301_0_5700";
    private static final String Pressure = "3323_0_5700";
    private static final String Ultraviolet = "3300_0_5700";

    int size;
    String[] time = new String[600];
    float[] value = new float[600];
    List<Datastreams> dataJsonStreams;
    List<Datapoints> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_humidity);

        JsonRootBean dataJson=getIntent().getParcelableExtra("dataJson");
        dataJsonStreams = dataJson.getData().getDatastreams();
        for (int i = 0; i < dataJsonStreams.size(); i++) {
            Log.w("lll", "湿度折线图循环次数= " + i);
            String dataJson_id = dataJsonStreams.get(i).getId();
            if (dataJson_id.equals(Humidity)) {
                Log.w("lll", "湿度折线图");
                points = dataJsonStreams.get(i).getDatapoints();//
            }
        }

        //Log.w("bbb","book title->"+(dataJson.getData().getDatastreams()).get(0).getId());

        size = points.size();

        for (int i = 0; i < points.size(); i++) {
            String tmptime = points.get(i).getAt();
            String subtime = tmptime.substring(11,16);//2019-04-17 17:06:46.257 显示04-17 17:06
            String tmpvalue = points.get(i).getValue();
            time[i] = subtime;
            value[i] = Float.parseFloat(tmpvalue);
            Log.w("www","time="+time);
            Log.w("www","subtime="+subtime);
            Log.w("www","value="+value);
        }

        lineChart = findViewById(R.id.chart);
        initChart(lineChart);

        showLineChart(points,"温度 / °C", Color.CYAN);

    }

    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(true);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置背景
        lineChart.setBackgroundColor(Color.WHITE);
        //设置XY轴动画效果
//        lineChart.animateY(2500);
//        lineChart.animateX(1500);


        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYAxis = lineChart.getAxisRight();

        xAxis.setEnabled(true);//显示x轴
        xAxis.setDrawLabels(true);
//        xAxis.setLabelRotationAngle(-60);////设置旋转，顺时针正数，逆时针负数
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
//        //保证Y轴从0开始，不然会上移一点
//        leftYAxis.setAxisMinimum(0f);
//        rightYAxis.setAxisMinimum(0f);
        xAxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(false);
        //设置Y轴网格线为虚线
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        rightYAxis.setEnabled(false);


        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        legend.setEnabled(false);////不设置标签
        //不显示右下角的description label
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setValueTextColor(Color.GREEN);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(2f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        } else {
            lineDataSet.setMode(mode);
        }
        lineDataSet.setHighlightEnabled(true);

        lineDataSet.setDrawValues(false);////不显示数值
    }

    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public void setChartFillDrawable(Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(List<Datapoints> dataList, String name, int color) {
        final List<Entry> entries = new ArrayList<>();
        int j = dataList.size()/24;
//        for (int i = 0; i < dataList.size(); i++) {
//            float data = value[i];
//            entries.add(new Entry(i, data, time[i]));
//        }
        if(j >= 1){
            for (int i = 0; i < j*24; i++) {
                float data = value[i*j];
                entries.add(new Entry(i*j, data, time[i*j]));
            }
        }
        else{
            for (int i = 0; i < dataList.size(); i++) {
                float data = value[i];
                entries.add(new Entry(i, data, time[i]));
            }
        }


        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(8); //一幅图中显示数据点的个数
        //x轴自定义
        CustomXValueFormatter formatter = new CustomXValueFormatter(time);
        xAxis.setValueFormatter(formatter);

        // 设置MarkerView
        MarkerView mv = new XYMarkerView(this, R.layout.marker_viewer,formatter);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

        //曲线下部渐变填充
        Drawable drawable = getResources().getDrawable(R.drawable.fade_blue);
        setChartFillDrawable(drawable);

    }

}
