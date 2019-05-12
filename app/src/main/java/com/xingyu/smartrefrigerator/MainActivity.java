package com.xingyu.smartrefrigerator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//百度地图
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import android.view.Window;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import java.util.List;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {


    private static final String DeviceID = "520207544";
    private static final String ApiKey = "=1pc0Sm1OPO0gtgYq2nApsmOC8I=";
    private static final String DeviceIME = "869662030686809";

    private static final String Temperature = "3303_0_5700";
    private static final String Humidity = "3304_0_5700";
    private static final String Concentration = "3325_0_5700";
    private static final String Illumination = "3301_0_5700";
    private static final String Pressure = "3323_0_5700";
    private static final String Ultraviolet = "3300_0_5700";
    private static final String Longitude = "3336_0_5515";//经度
    private static final String Latitude = "3336_0_5514";//纬度




    //接收到的信息
    private String receivedInfo;//数据
    private String receivedState;//设备状态
    List<Datastreams> str;//数据
    private int dataCount;//为了得到数据流个数
    List<Datapoints> poi;
    private String devState;//设备状态
    JsonRootBean dataJson = new JsonRootBean();
    private String findTime;//查询时间
    private Boolean findFlag = true;
    //百度地图
    // 百度地图控件
    private MapView mMapView = null;
    // 百度地图对象
    private BaiduMap bdMap;
    private LocationListener locationListener;
    private double LONGITUDE = 116.413384;//经度 初始化位置为北京天安门
    private double LATITUDE = 39.910925;//纬度





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        LocationClient mLocationClient = new LocationClient(getApplicationContext());
        //开启定位
        mLocationClient.start();
        init();

        final TextView Text_time1 = (TextView)findViewById(R.id.time1);
        final TextView Text_time2 = (TextView)findViewById(R.id.time2);
        final TextView Text_time3 = (TextView)findViewById(R.id.time3);
        final TextView Text_time4 = (TextView)findViewById(R.id.time4);
        final TextView Text_time5 = (TextView)findViewById(R.id.time5);
        final TextView Text_time6 = (TextView)findViewById(R.id.time6);
//        final TextView Text_time7 = (TextView)findViewById(R.id.time7);
//        final TextView Text_time8 = (TextView)findViewById(R.id.time8);
        final TextView Text_temperature1 = (TextView)findViewById(R.id.temperature1);
        final TextView Text_temperature2 = (TextView)findViewById(R.id.temperature2);
        final TextView Text_temperature3 = (TextView)findViewById(R.id.temperature3);
        final TextView Text_temperature4 = (TextView)findViewById(R.id.temperature4);
        final TextView Text_temperature5 = (TextView)findViewById(R.id.temperature5);
        final TextView Text_temperature6 = (TextView)findViewById(R.id.temperature6);
//        final TextView Text_temperature7 = (TextView)findViewById(R.id.temperature7);
//        final TextView Text_temperature8 = (TextView)findViewById(R.id.temperature8);
        final TextView Text_humidity1 = (TextView)findViewById(R.id.humidity1);
        final TextView Text_humidity2 = (TextView)findViewById(R.id.humidity2);
        final TextView Text_humidity3 = (TextView)findViewById(R.id.humidity3);
        final TextView Text_humidity4 = (TextView)findViewById(R.id.humidity4);
        final TextView Text_humidity5 = (TextView)findViewById(R.id.humidity5);
        final TextView Text_humidity6 = (TextView)findViewById(R.id.humidity6);
//        final TextView Text_humidity7 = (TextView)findViewById(R.id.humidity7);
//        final TextView Text_humidity8 = (TextView)findViewById(R.id.humidity8);
        final TextView Text_illumination1 = (TextView)findViewById(R.id.illumination1);
        final TextView Text_illumination2 = (TextView)findViewById(R.id.illumination2);
        final TextView Text_illumination3 = (TextView)findViewById(R.id.illumination3);
        final TextView Text_illumination4 = (TextView)findViewById(R.id.illumination4);
        final TextView Text_illumination5 = (TextView)findViewById(R.id.illumination5);
        final TextView Text_illumination6 = (TextView)findViewById(R.id.illumination6);
//        final TextView Text_illumination7 = (TextView)findViewById(R.id.illumination7);
//        final TextView Text_illumination8 = (TextView)findViewById(R.id.illumination8);
        final TextView Text_pressure1 = (TextView)findViewById(R.id.pressure1);
        final TextView Text_pressure2 = (TextView)findViewById(R.id.pressure2);
        final TextView Text_pressure3 = (TextView)findViewById(R.id.pressure3);
        final TextView Text_pressure4 = (TextView)findViewById(R.id.pressure4);
        final TextView Text_pressure5 = (TextView)findViewById(R.id.pressure5);
        final TextView Text_pressure6 = (TextView)findViewById(R.id.pressure6);
//        final TextView Text_pressure7 = (TextView)findViewById(R.id.pressure7);
//        final TextView Text_pressure8 = (TextView)findViewById(R.id.pressure8);
        final TextView Text_ultraviolet1 = (TextView)findViewById(R.id.ultraviolet1);
        final TextView Text_ultraviolet2 = (TextView)findViewById(R.id.ultraviolet2);
        final TextView Text_ultraviolet3 = (TextView)findViewById(R.id.ultraviolet3);
        final TextView Text_ultraviolet4 = (TextView)findViewById(R.id.ultraviolet4);
        final TextView Text_ultraviolet5 = (TextView)findViewById(R.id.ultraviolet5);
        final TextView Text_ultraviolet6 = (TextView)findViewById(R.id.ultraviolet6);
//        final TextView Text_ultraviolet7 = (TextView)findViewById(R.id.ultraviolet7);
//        final TextView Text_ultraviolet8 = (TextView)findViewById(R.id.ultraviolet8);
        final TextView Text_concentration1 = (TextView)findViewById(R.id.concentration1);
        final TextView Text_concentration2 = (TextView)findViewById(R.id.concentration2);
        final TextView Text_concentration3 = (TextView)findViewById(R.id.concentration3);
        final TextView Text_concentration4 = (TextView)findViewById(R.id.concentration4);
        final TextView Text_concentration5 = (TextView)findViewById(R.id.concentration5);
        final TextView Text_concentration6 = (TextView)findViewById(R.id.concentration6);
//        final TextView Text_concentration7 = (TextView)findViewById(R.id.concentration7);
//        final TextView Text_concentration8 = (TextView)findViewById(R.id.concentration8);


        ImageButton Button_GetInfo = (ImageButton)findViewById(R.id.Button_GetInfo);
        ImageButton Button_Temperature = (ImageButton)findViewById(R.id.Temperature);
        ImageButton Button_Humidity = (ImageButton)findViewById(R.id.Humidity);
        ImageButton Button_Illumination = (ImageButton)findViewById(R.id.Illumination);
        ImageButton Button_Pressure = (ImageButton)findViewById(R.id.Pressure);
        ImageButton Button_Ultraviolet = (ImageButton)findViewById(R.id.Ultraviolet);
        ImageButton Button_Concentration = (ImageButton)findViewById(R.id.Concentration);
        final TextView device_state = (TextView)findViewById(R.id.device_state);
        Button Button_find = (Button)findViewById(R.id.find_button);


        //获取数据线程
        final Runnable getRunable = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                receivedInfo = null;
                receivedInfo = getInfo("3303_0_5602");

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        poi = str.get(0).getDatapoints();
//                        Text_time1.setText((poi.get(0).getAt()).substring(0,16));
//                        Text_temperature1.setText(poi.get(0).getValue());
//                        Text_time2.setText(poi.get(1).getAt());
//                        Text_temperature2.setText(poi.get(1).getValue());
//                        for (int i = 0; i < poi.size(); i++) {
//                            String time_1 = poi.get(i).getAt();
//                            String value_1 = poi.get(i).getValue();
//                            Text_time1.append(time_1);
//                            Text_temperature1.append(value_1);
//                        }
                        Log.w("ttt", "datacount= "+ dataCount);
                        if(dataCount == 0){
                            Toast.makeText(MainActivity.this, "该日期没有数据", Toast.LENGTH_SHORT).show();
                            LONGITUDE = 116.413384;//经度 位置为北京天安门
                            LATITUDE = 39.910925;//纬度

                            Text_time1.setText("--:--");
                            Text_time2.setText("--:--");
                            Text_time3.setText("--:--");
                            Text_time4.setText("--:--");
                            Text_time5.setText("--:--");
                            Text_time6.setText("--:--");
//                            Text_time7.setText("--:--");
//                            Text_time8.setText("--:--");
                            Text_temperature1.setText("--.--");
                            Text_temperature2.setText("--.--");
                            Text_temperature3.setText("--.--");
                            Text_temperature4.setText("--.--");
                            Text_temperature5.setText("--.--");
                            Text_temperature6.setText("--.--");
//                            Text_temperature7.setText("--.--");
//                            Text_temperature8.setText("--.--");
                            Text_humidity1.setText("--.--");
                            Text_humidity2.setText("--.--");
                            Text_humidity3.setText("--.--");
                            Text_humidity4.setText("--.--");
                            Text_humidity5.setText("--.--");
                            Text_humidity6.setText("--.--");
//                            Text_humidity7.setText("--.--");
//                            Text_humidity8.setText("--.--");
                            Text_illumination1.setText("--.--");
                            Text_illumination2.setText("--.--");
                            Text_illumination3.setText("--.--");
                            Text_illumination4.setText("--.--");
                            Text_illumination5.setText("--.--");
                            Text_illumination6.setText("--.--");
//                            Text_illumination7.setText("--.--");
//                            Text_illumination8.setText("--.--");
                            Text_pressure1.setText("--.--");
                            Text_pressure2.setText("--.--");
                            Text_pressure3.setText("--.--");
                            Text_pressure4.setText("--.--");
                            Text_pressure5.setText("--.--");
                            Text_pressure6.setText("--.--");
//                            Text_pressure7.setText("--.--");
//                            Text_pressure8.setText("--.--");
                            Text_ultraviolet1.setText("--.--");
                            Text_ultraviolet2.setText("--.--");
                            Text_ultraviolet3.setText("--.--");
                            Text_ultraviolet4.setText("--.--");
                            Text_ultraviolet5.setText("--.--");
                            Text_ultraviolet6.setText("--.--");
//                            Text_ultraviolet7.setText("--.--");
//                            Text_ultraviolet8.setText("--.--");
                            Text_concentration1.setText("--.--");
                            Text_concentration2.setText("--.--");
                            Text_concentration3.setText("--.--");
                            Text_concentration4.setText("--.--");
                            Text_concentration5.setText("--.--");
                            Text_concentration6.setText("--.--");
//                            Text_concentration7.setText("--.--");
//                            Text_concentration8.setText("--.--");
                        }
                        else {
                            int j = 0;
                            for (int i = 0; i < str.size(); i++) {
                                Log.w("ttt", "循环次数= "+ i);
                                String strid = str.get(i).getId();
                                j = str.get(i).getDatapoints().size() / 8;
                                if (strid.equals(Temperature)) {
                                    Log.w("ttt", "温度");
                                    Log.w("ttt", "温度datapoins数量= " + str.get(i).getDatapoints().size());
                                    Text_time1.setText(((str.get(i).getDatapoints()).get(j-1).getAt()).substring(0, 16));
                                    Text_time2.setText(((str.get(i).getDatapoints()).get(2*j-1).getAt()).substring(0, 16));
                                    Text_time3.setText(((str.get(i).getDatapoints()).get(3*j-1).getAt()).substring(0, 16));
                                    Text_time4.setText(((str.get(i).getDatapoints()).get(4*j-1).getAt()).substring(0, 16));
                                    Text_time5.setText(((str.get(i).getDatapoints()).get(5*j-1).getAt()).substring(0, 16));
                                    Text_time6.setText(((str.get(i).getDatapoints()).get(6*j-1).getAt()).substring(0, 16));
//                                    Text_time7.setText(((str.get(i).getDatapoints()).get(7*j-1).getAt()).substring(0, 16));
//                                    Text_time8.setText(((str.get(i).getDatapoints()).get(8*j-1).getAt()).substring(0, 16));
                                    Text_temperature1.setText((str.get(i).getDatapoints()).get(j-1).getValue());
                                    Text_temperature2.setText((str.get(i).getDatapoints()).get(2*j-1).getValue());
                                    Text_temperature3.setText((str.get(i).getDatapoints()).get(3*j-1).getValue());
                                    Text_temperature4.setText((str.get(i).getDatapoints()).get(4*j-1).getValue());
                                    Text_temperature5.setText((str.get(i).getDatapoints()).get(5*j-1).getValue());
                                    Text_temperature6.setText((str.get(i).getDatapoints()).get(6*j-1).getValue());
//                                    Text_temperature7.setText((str.get(i).getDatapoints()).get(7*j-1).getValue());
//                                    Text_temperature8.setText((str.get(i).getDatapoints()).get(8*j-1).getValue());
                                }
                                if (strid.equals(Humidity)) {
                                    Log.w("ttt", "湿度");
                                    Log.w("ttt", "湿度datapoins数量= " + str.get(i).getDatapoints().size());
                                    Text_humidity1.setText((str.get(i).getDatapoints()).get(j-1).getValue());
                                    Text_humidity2.setText((str.get(i).getDatapoints()).get(2*j-1).getValue());
                                    Text_humidity3.setText((str.get(i).getDatapoints()).get(3*j-1).getValue());
                                    Text_humidity4.setText((str.get(i).getDatapoints()).get(4*j-1).getValue());
                                    Text_humidity5.setText((str.get(i).getDatapoints()).get(5*j-1).getValue());
                                    Text_humidity6.setText((str.get(i).getDatapoints()).get(6*j-1).getValue());
//                                    Text_humidity7.setText((str.get(i).getDatapoints()).get(7*j-1).getValue());
//                                    Text_humidity8.setText((str.get(i).getDatapoints()).get(8*j-1).getValue());
                                }
                                if (strid.equals(Illumination)) {
                                    Log.w("ttt", "亮度");
                                    Log.w("ttt", "亮度datapoins数量= " + str.get(i).getDatapoints().size());
                                    Text_illumination1.setText((str.get(i).getDatapoints()).get(j-1).getValue());
                                    Text_illumination2.setText((str.get(i).getDatapoints()).get(2*j-1).getValue());
                                    Text_illumination3.setText((str.get(i).getDatapoints()).get(3*j-1).getValue());
                                    Text_illumination4.setText((str.get(i).getDatapoints()).get(4*j-1).getValue());
                                    Text_illumination5.setText((str.get(i).getDatapoints()).get(5*j-1).getValue());
                                    Text_illumination6.setText((str.get(i).getDatapoints()).get(6*j-1).getValue());
//                                    Text_illumination7.setText((str.get(i).getDatapoints()).get(7*j-1).getValue());
//                                    Text_illumination8.setText((str.get(i).getDatapoints()).get(8*j-1).getValue());
                                }
                                if (strid.equals(Pressure)) {
                                    Log.w("ttt", "压力");
                                    Log.w("ttt", "压力datapoins数量= " + str.get(i).getDatapoints().size());
                                    Text_pressure1.setText((str.get(i).getDatapoints()).get(j-1).getValue());
                                    Text_pressure2.setText((str.get(i).getDatapoints()).get(2*j-1).getValue());
                                    Text_pressure3.setText((str.get(i).getDatapoints()).get(3*j-1).getValue());
                                    Text_pressure4.setText((str.get(i).getDatapoints()).get(4*j-1).getValue());
                                    Text_pressure5.setText((str.get(i).getDatapoints()).get(5*j-1).getValue());
                                    Text_pressure6.setText((str.get(i).getDatapoints()).get(6*j-1).getValue());
//                                    Text_pressure7.setText((str.get(i).getDatapoints()).get(7*j-1).getValue());
//                                    Text_pressure8.setText((str.get(i).getDatapoints()).get(8*j-1).getValue());
                                }
                                if (strid.equals(Ultraviolet)) {
                                    Log.w("ttt", "紫外线");
                                    Log.w("ttt", "紫外线datapoins数量= " + str.get(i).getDatapoints().size());
                                    Text_ultraviolet1.setText((str.get(i).getDatapoints()).get(j-1).getValue());
                                    Text_ultraviolet2.setText((str.get(i).getDatapoints()).get(2*j-1).getValue());
                                    Text_ultraviolet3.setText((str.get(i).getDatapoints()).get(3*j-1).getValue());
                                    Text_ultraviolet4.setText((str.get(i).getDatapoints()).get(4*j-1).getValue());
                                    Text_ultraviolet5.setText((str.get(i).getDatapoints()).get(5*j-1).getValue());
                                    Text_ultraviolet6.setText((str.get(i).getDatapoints()).get(6*j-1).getValue());
//                                    Text_ultraviolet7.setText((str.get(i).getDatapoints()).get(7*j-1).getValue());
//                                    Text_ultraviolet8.setText((str.get(i).getDatapoints()).get(8*j-1).getValue());
                                }
                                if (strid.equals(Concentration)) {
                                    Log.w("ttt", "PM2.5");
                                    Log.w("ttt", "PM2.5datapoins数量= " + str.get(i).getDatapoints().size());
                                    Text_concentration1.setText((str.get(i).getDatapoints()).get(j-1).getValue());
                                    Text_concentration2.setText((str.get(i).getDatapoints()).get(2*j-1).getValue());
                                    Text_concentration3.setText((str.get(i).getDatapoints()).get(3*j-1).getValue());
                                    Text_concentration4.setText((str.get(i).getDatapoints()).get(4*j-1).getValue());
                                    Text_concentration5.setText((str.get(i).getDatapoints()).get(5*j-1).getValue());
                                    Text_concentration6.setText((str.get(i).getDatapoints()).get(6*j-1).getValue());
//                                    Text_concentration7.setText((str.get(i).getDatapoints()).get(7*j-1).getValue());
//                                    Text_concentration8.setText((str.get(i).getDatapoints()).get(8*j-1).getValue());
                                }
                                if (strid.equals(Latitude)) {
                                    LATITUDE = Double.parseDouble((str.get(i).getDatapoints()).get(0).getValue());
                                    Log.w("ttt", "纬度"+ LATITUDE);
                                }
                                if (strid.equals(Longitude)) {
                                    LONGITUDE = Double.parseDouble((str.get(i).getDatapoints()).get(0).getValue());
                                    Log.w("ttt", "纬度"+ LONGITUDE);
                                }
                            }
                            Log.w("ttt", "跳出循环");
                        }
                        Log.w("ttt", "跳出else");
                        init();
                    }
                });
                Looper.loop();
            }
        };

        //获取设备状态线程
        final Runnable getRunableDev = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                receivedState = null;
                receivedState = getState();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(devState.equals("true")){
                            device_state.setText("在线");
                            device_state.setTextColor(Color.parseColor("#7CFC00"));
                        }
                        else{
                            device_state.setText("离线");
                            device_state.setTextColor(Color.parseColor("#FF6347"));
                        }
                    }
                });
                Looper.loop();
            }
        };

        //获取数据
        Button_GetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findFlag = true;
                new Thread(getRunable).start();
                new Thread(getRunableDev).start();

            }
        });

        //获取历史数据
        Button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFlag = false;
                EditText findT= (EditText) findViewById(R.id.find);
                findTime = findT.getText().toString();

                new Thread(getRunable).start();
                init();
            }
        });

        //显示温度折线图
        Button_Temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivity.class);
                intent.putExtra("dataJson",dataJson);
                startActivity(intent);
            }
        });
        //显示湿度折线图
        Button_Humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivityHumidity.class);
                intent.putExtra("dataJson",dataJson);
                startActivity(intent);
            }
        });
        //显示PM2.5折线图
        Button_Concentration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivityConcentration.class);
                intent.putExtra("dataJson",dataJson);
                startActivity(intent);
            }
        });
        //显示光照强度折线图
        Button_Illumination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivityIllumination.class);
                intent.putExtra("dataJson",dataJson);
                startActivity(intent);
            }
        });
        //显示大气压折线图
        Button_Pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivityPressure.class);
                intent.putExtra("dataJson",dataJson);
                startActivity(intent);
            }
        });
        //显示紫外线折线图
        Button_Ultraviolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivityUltraviolet.class);
                intent.putExtra("dataJson",dataJson);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }//oncreate

    /**
     * 百度地图初始化方法
     */
    private void init() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        bdMap = mMapView.getMap();
        //普通地图
        bdMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
        //bdMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        LatLng loc = new LatLng(LATITUDE, LONGITUDE);
        bdMap.setMyLocationEnabled(true);
        mMapView.getMap().setMyLocationEnabled(true);
        MyLocationData data = new MyLocationData.Builder()
                .latitude(loc.latitude)
                .longitude(loc.longitude).build();
        mMapView.getMap().setMyLocationData(data);

        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pointer), 70, 70, true);
        BitmapDescriptor bitmapD = BitmapDescriptorFactory.fromBitmap(bitmap);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapD);
        mMapView.getMap().setMyLocationConfiguration(config);

        // 设置地图居中
        MapStatus mapStatus = new MapStatus.Builder().target(loc).build();
        MapStatusUpdate mapStatusUpdate =
                MapStatusUpdateFactory.newMapStatus(mapStatus);
        mMapView.getMap().setMapStatus(mapStatusUpdate);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.refresh:
                Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }





    //从云服务器获取数据
    public String getInfo(String dataStream){
        Log.w("ttt", "getinfo");
        Toast.makeText(MainActivity.this, "开始从云服务器获取数据", Toast.LENGTH_SHORT).show();    //提示
        String response = null;
        try{
            if(findFlag){
                URL url = new URL("http://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=3303_0_5700,3304_0_5700,3325_0_5700,3301_0_5700,3323_0_5700,3300_0_5700,3336_0_5515,3336_0_5514&limit=15");//&limit=15
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setConnectTimeout(15*1000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("api-key",ApiKey);
                if (connection.getResponseCode() == 200){   //返回码是200，网络正常
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    while((len = inputStream.read(buffer))!=-1){
                        os.write(buffer,0,len);
                    }
                    inputStream.close();
                    os.close();
                    response = os.toString();
                }else{
                    //返回码不是200，网络异常
                    Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Log.w("ttt", "flag"+findFlag);
                Log.w("ttt", "时间"+findTime);
                URL url = new URL("http://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=3303_0_5700,3304_0_5700,3325_0_5700,3301_0_5700,3323_0_5700,3300_0_5700,3336_0_5515,3336_0_5514&start="+findTime+"T00:00:00&end="+findTime+"T23:59:59");//&limit=15
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setConnectTimeout(15*1000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("api-key",ApiKey);
                if (connection.getResponseCode() == 200){   //返回码是200，网络正常
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    while((len = inputStream.read(buffer))!=-1){
                        os.write(buffer,0,len);
                    }
                    inputStream.close();
                    os.close();
                    response = os.toString();
                }else{
                    //返回码不是200，网络异常
                    Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            }
//            URL url = new URL("http://api.heclouds.com/devices/" + DeviceID + "/datapoints?datastream_id=3303_0_5700,3304_0_5700,3325_0_5700,3301_0_5700,3323_0_5700,3300_0_5700&start="+findTime+"T00:00:00&end="+findTime+"T24:00:00&limit=600");
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//            connection.setConnectTimeout(15*1000);
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("api-key",ApiKey);
//            if (connection.getResponseCode() == 200){   //返回码是200，网络正常
//                InputStream inputStream = connection.getInputStream();
//                ByteArrayOutputStream os = new ByteArrayOutputStream();
//                int len = 0;
//                byte buffer[] = new byte[1024];
//                while((len = inputStream.read(buffer))!=-1){
//                    os.write(buffer,0,len);
//                }
//                inputStream.close();
//                os.close();
//                response = os.toString();
//            }else{
//                //返回码不是200，网络异常
//                Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
//            }

        }catch (IOException e){
            Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Log.w("ttt", "数据解析前");
        parseJSONWithGSON(response);//数据解析
        return response;

    }

    //从云服务器获取设备状态
    public String getState(){
        Toast.makeText(MainActivity.this, "开始从云服务器获取设备状态", Toast.LENGTH_SHORT).show();
        String response1 = null;
        try{
            URL url1 = new URL("http://api.heclouds.com/devices/status?devIds=" + DeviceID );
            HttpURLConnection connection1 = (HttpURLConnection)url1.openConnection();
            connection1.setConnectTimeout(15*1000);
            connection1.setRequestMethod("GET");
            connection1.setRequestProperty("api-key",ApiKey);
            if (connection1.getResponseCode() == 200){   //返回码是200，网络正常
                InputStream inputStream1 = connection1.getInputStream();
                ByteArrayOutputStream os1 = new ByteArrayOutputStream();
                int len1 = 0;
                byte buffer1[] = new byte[1024];
                while((len1 = inputStream1.read(buffer1))!=-1){
                    os1.write(buffer1,0,len1);
                }
                inputStream1.close();
                os1.close();
                response1 = os1.toString();
            }else{
                //返回码不是200，网络异常
                Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

        }catch (IOException e){
            Toast.makeText(MainActivity.this, "获取设备状态失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        parseJSONWithGSONDev(response1);//设备状态解析
        return response1;

    }

    //数据解析
    private void parseJSONWithGSON(String jsonData) {
        Log.w("ttt", "数据解析");
        JsonRootBean app = new Gson().fromJson(jsonData, JsonRootBean.class);
        dataJson = app;
        List<Datastreams> streams = app.getData().getDatastreams();
        dataCount = app.getData().getCount();
        str = streams;
//        List<Datapoints> points = streams.get(0).getDatapoints();
////        String myid = streams.get(0).getId();
////        int count = app.getData().getCount();//获取数据的数量
////
////        Log.w("ttt","id="+myid);
////        for (int i = 0; i < points.size(); i++) {
////            String time = points.get(i).getAt();
////            String subtime = time.substring(0,16);////
////            String value = points.get(i).getValue();
////            Log.w("www","time="+time);
////            Log.w("www","subtime="+subtime);////
////            Log.w("www","value="+value);
////        }
    }

    //设备状态解析
    private void parseJSONWithGSONDev(String jsonData) {
        JsonRootBeanDev app1 = new Gson().fromJson(jsonData, JsonRootBeanDev.class);
        int total_count = app1.getData().getTotal_count();//获取数据的数量
        for(int i = 0; i < total_count; ++i){
            if( (app1.getData().getDevices().get(i).getId()) .equals(DeviceID)){
                devState = (app1.getData().getDevices()).get(0).getOnline();
            }
        }

        Log.w("aaa","state="+devState);
    }

}
