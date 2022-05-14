package com.finedustApp.finedustalarm;

import static com.kakao.util.maps.helper.Utility.getPackageInfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    // API, JAVA
    ProgressDialog StartProgressDialog;
    GpsTracker gpsTracker;
    MapReverseGeoCoder mapReverseGeoCoder;
    LocationTxt locationTxt;
    MainHandler mainHandler;

    // UI, Layout
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView text1, text2, Tlocation, dustStatetxt;
    ImageView dustState;
    MapView mapView;
    ScrollView mainScroll;
    LineChart lineChart;
    CombinedChart combinedChart;
    ViewGroup mapViewContainer;
    static Context context;

    // Variable
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final String TAG = "HashKey";

    static String KakaoKey = null;
    static String restkey = null;
    static String service_key = null;
    static String Current_sido;
    static String Current_local;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    String[] splitTemp1_pm10 = new String[100];
    String[] splitTemp1_time = new String[100];
    String[] splitTemp2_pm10 = new String[100];
    String[] splitTemp2_name = new String[100];
    String[] sidoStationName = new String[200];
    String[] sidoStationLat = new String[200];
    String[] sidoStationLon = new String[200];

    String MyLocation;
    String nearfinedustXml, sidofinedustXml, nearStationXml, sidoStationXml, transCoord_84toTM;
    String nearStationData, sidoStationData, nearStationTM, nearStationAddr, nearStationName;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new MainHandler(Looper.getMainLooper());

        // apiKey setting
        MainActivity.context = getApplicationContext();
        KakaoKey = context.getString(R.string.kakaoKey);
        restkey = context.getString(R.string.restKey);
        service_key = context.getString(R.string.apiKey);

        // get id from views to using view's data
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        text1 = (TextView)findViewById(R.id.result1);
        text2 = (TextView)findViewById(R.id.result2);
        Tlocation = (TextView)findViewById(R.id.location);
        dustStatetxt = (TextView)findViewById(R.id.dustStatetxt);

        mainScroll =(ScrollView)findViewById(R.id.MainScroll);
        dustState = (ImageView)findViewById(R.id.dustState);

        lineChart = (LineChart)findViewById(R.id.localChart);
        combinedChart = (CombinedChart)findViewById(R.id.sidoChart);

        // setup my GPS to address to using kakao OpenAPI
        gpsTracker = new GpsTracker(MainActivity.this);
        MapPoint initVal = MapPoint.mapPointWithGeoCoord(gpsTracker.latitude, gpsTracker.longitude);
        mapReverseGeoCoder = new MapReverseGeoCoder(KakaoKey, initVal, this, this);
        mapReverseGeoCoder.startFindingAddress();
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // Avoid overlapping the two scrolls - (Main Scroll View, Kakao Scroll view)
        mapView.setOnTouchListener((v, event) -> {
            int action = event.getAction();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mainScroll.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    mainScroll.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mainScroll.requestDisallowInterceptTouchEvent(true);
                    break;
            }
            return false;
        });

        // Refresh event that occurs when this app is launched
        StartProgressDialog = new ProgressDialog(this);
        StartProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    class MainHandler extends Handler {
        private final Looper mainLopper;

        public MainHandler(Looper mainLopper) { this.mainLopper = Looper.getMainLooper(); }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            runOnUiThread(() -> {
                // Setting UI view
                Tlocation.setText(MyLocation);
                text1.setText(Current_local + " 미세먼지 측정 결과" + "\n" + nearfinedustXml);
                text2.setText(Current_sido + "권 미세먼지 측정 결과" + "\n" + sidofinedustXml);

                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(gpsTracker.latitude, gpsTracker.longitude), true);
                mapView.setZoomLevel(8, true);
                finedustState();
                localChart();
                sidoChart();

                StartProgressDialog.dismiss();
            });
        }
    }

    class bgThread extends Thread {
        private final Thread targetThread;
        private State bgState;

        public bgThread(TargetThread targetThread) {
            this.targetThread = targetThread;
        }

        public State getState() { return bgState; }

        public void run() {
            while(true) {
                State state = targetThread.getState();
                System.out.println("Thread state : " + state);

                if (state == State.NEW) {
                    targetThread.start();
                }

                if (state == State.TERMINATED) {
                    bgState  = state;
                    mainHandler.sendEmptyMessage(0);
                    break;
                }

                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class TargetThread extends Thread {
        public void run() {
            // transCoord (WGS84 -> TM) - for using OpenAPI
            transCoord_84toTM = transCoord.transCoord(gpsTracker.longitude, gpsTracker.latitude);
            String[] transCoordData = transCoord_84toTM.split(",");
            String TM_X = transCoordData[0].substring(4);
            String TM_Y = transCoordData[1].substring(4);

            // nearStationData (address, station, tm) - setup StationData to be displayed
            nearStationData = XmlParsing.getXmlnearStationData(TM_X, TM_Y);

            String[] nearStationTempData = nearStationData.split("\n");
            nearStationTM = nearStationTempData[0];
            nearStationAddr = nearStationTempData[1];
            nearStationName = nearStationTempData[2];
            System.out.println(nearStationTM + " " + nearStationAddr + " " + nearStationName);

            for (int i = 0; i < LocationTxt.finedustsidoData.length; i++) {
                if (nearStationAddr.contains(LocationTxt.finedustsidoData[i])) {
                    Current_sido = LocationTxt.finedustsidoData[i];
                }
            }

            for (int i = 0; i < LocationTxt.finedustStationData.length; i++) {
                if (nearStationName.contains(LocationTxt.finedustStationData[i])) {
                    Current_local = LocationTxt.finedustStationData[i];
                }
            }

            // sidoStationData (station, latitude, longitude) - remove NULL values
            sidoStationData = XmlParsing.getXmlsidoStationData(Current_sido);
            String[] sidoStationtempData = sidoStationData.split("\n");

            for (int i = 0; i < sidoStationtempData.length; i++) {
                if (sidoStationtempData[i].startsWith("N")) {
                    sidoStationName[i] = sidoStationtempData[i].substring(1);
                }
                else if (sidoStationtempData[i].startsWith("X")) {
                    sidoStationLat[i] = sidoStationtempData[i].substring(1);
                }
                else if (sidoStationtempData[i].startsWith("Y")) {
                    sidoStationLon[i] = sidoStationtempData[i].substring(1);
                }
            }

            final List<String> sidoStationNameList = new ArrayList<>();
            Collections.addAll(sidoStationNameList, sidoStationName);
            for (int i = sidoStationNameList.size() - 1; i > 0; i--) {
                if (sidoStationNameList.get(i) == null) {
                    sidoStationNameList.remove(i);
                }
            }
            for (int i = 0; i < sidoStationNameList.size(); i++) {
                if (sidoStationNameList.get(i) == null) {
                    sidoStationNameList.remove(i);
                }
            }

            final List<String> sidoStationLatList = new ArrayList<>();
            Collections.addAll(sidoStationLatList, sidoStationLat);
            for (int i = sidoStationLatList.size() - 1; i > 0; i--) {
                if (sidoStationLatList.get(i) == null) {
                    sidoStationLatList.remove(i);
                }
            }

            final List<String> sidoStationLonList = new ArrayList<>();
            Collections.addAll(sidoStationLonList, sidoStationLon);
            for (int i = sidoStationLonList.size() - 1; i > 0; i--) {
                if (sidoStationLonList.get(i) == null) {
                    sidoStationLonList.remove(i);
                }
            }

            for (int i = 0; i < sidoStationLonList.size(); i++) {
                if (sidoStationLonList.get(i) == null) {
                    sidoStationLonList.remove(i);
                }
            }

            // Creating Map Markers based on coordinate values for each station
            for (int i = 0; i < sidoStationLatList.size(); i++) {
                MapPOIItem Localpoint = new MapPOIItem();
                Localpoint.setItemName(sidoStationNameList.get(i) + " 측정소");
                Localpoint.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(sidoStationLatList.get(i)), Double.parseDouble(sidoStationLonList.get(i))));
                Localpoint.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                Localpoint.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(Localpoint);
            }

            // Creating Map Marker based on coordinate values for my location
            MapPOIItem MyPoint = new MapPOIItem();
            MyPoint.setItemName("내 위치");
            MyPoint.setTag(0);
            MyPoint.setMapPoint(MapPoint.mapPointWithGeoCoord(gpsTracker.latitude, gpsTracker.longitude));
            MyPoint.setMarkerType(MapPOIItem.MarkerType.BluePin);
            MyPoint.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(MyPoint);

            // finedust Data XmlParsing (Data.go.kr - OpenAPI)
            nearfinedustXml = XmlParsing.getXmlnearfinedustData();
            sidofinedustXml = XmlParsing.getXmlsidofinedustData();
            nearStationXml = XmlParsing.getXmlnearStationDailyData();
            sidoStationXml = XmlParsing.getXmlsidoStationDailyData();

            XmlDataSplit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Checking GPS permission
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }
        else {
            checkRunTimePermission();
        }

        // debugging Hash Key
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = Base64.getEncoder().encodeToString(md.digest());
                Log.e("Hash Key", something);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapViewContainer.removeAllViews();
    }

    public void tryRestart() {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        startActivity(mainIntent);
        System.exit(0);
    }

    // release Hash Key
    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    public void LocationWithParsing(String address) {
        StartProgressDialog.show();
        MyLocation = address;

        bgThread thread = new bgThread(new TargetThread());
        thread.setDaemon(true);
        thread.start();
    }

    public void finedustState() {
        // 내 지역 미세먼지 농도에 따른 이미지 변경
        String[] StateTemp1 = nearfinedustXml.split("\n");
        String[] StateTemp2 = StateTemp1[0].split(" ");
        int numtemp = Integer.parseInt(StateTemp2[3]);

        if (numtemp >= 0 && numtemp <= 30) { dustState.setImageResource(R.drawable.ic_smile__basic_); dustStatetxt.setText("미세먼지 상태 : 좋음"); }
        else if (numtemp >= 31 && numtemp <= 80) { dustState.setImageResource(R.drawable.ic_emotionless); dustStatetxt.setText("미세먼지 상태 : 보통"); }
        else if (numtemp >= 81 && numtemp <= 120) { dustState.setImageResource(R.drawable.ic_unsure); dustStatetxt.setText("미세먼지 상태 : 약간 나쁨"); }
        else if (numtemp >= 121 && numtemp <= 200) { dustState.setImageResource(R.drawable.ic_sad); dustStatetxt.setText("미세먼지 상태 : 나쁨"); }
        else if (numtemp >= 201 && numtemp <= 300) { dustState.setImageResource(R.drawable.ic_x_eyes); dustStatetxt.setText("미세먼지 상태 : 매우나쁨"); }
        else if (numtemp >= 301) { dustState.setImageResource(R.drawable.ic_evil); dustStatetxt.setText("미세먼지 상태 : 매우나쁨"); }
    }

    public void XmlDataSplit() {
        // 24-hour finedust concentration and time data from GPS-based measuring stations
        String[] splitTemp1 = nearStationXml.split("\n");

        // pm10
        int x = 0;
        for (int i = 0; i < splitTemp1.length; i += 2) {
            splitTemp1_pm10[x] = splitTemp1[i];
            x += 1;
        }
        
        // time
        int y = 0;
        for (int i = 1; i < splitTemp1.length; i += 2) {
            splitTemp1_time[y] = splitTemp1[i];
            y += 1;
        }

        // Current time fine dust and name data of cities and provinces based on GPS
        String[] splitTemp2 = sidoStationXml.split("\n");
        
        // pm10
        int a = 0;
        for (int i = 0; i < splitTemp2.length; i += 2) {
            splitTemp2_pm10[a] = splitTemp2[i];
            a += 1;
        }

        // Station Name
        int b = 0;
        for (int i = 1; i < splitTemp2.length; i += 2) {
            splitTemp2_name[b] = splitTemp2[i];
            b += 1;
        }
    }

    public void localChart() {
        // Fine dust concentration data
        final List<String> ListTemp1 = new ArrayList<>();
        Collections.addAll(ListTemp1, splitTemp1_pm10);
        for (int i = ListTemp1.size() - 1; i > 0; i--) {
            if (ListTemp1.get(i) == null) {
                ListTemp1.remove(i);
            }
        }
        Collections.reverse(ListTemp1);

        for (int i = 0; i < ListTemp1.size(); i++) {
            if (ListTemp1.get(i).equals("-")) {
                ListTemp1.set(i, "0");
            }
            else if (!ListTemp1.get(i).equals("-")) {
                continue;
            }
        }
        
        // measurement time data
        List<String> ListTemp2 = new ArrayList<>();
        Collections.addAll(ListTemp2, splitTemp1_time);
        for (int i = ListTemp2.size() - 1; i > 0; i--) {
            if (ListTemp2.get(i) == null) {
                ListTemp2.remove(i);
            }
        }
        Collections.reverse(ListTemp2);

        String[] Temp2Array = ListTemp2.toArray(new String[ListTemp2.size()]);
        for (int i = 0; i < Temp2Array.length; i++) {
            Temp2Array[i] = Temp2Array[i].substring(5);
        }
        ListTemp2 = new ArrayList<>(Arrays.asList(Temp2Array));


        // add to chart
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < ListTemp2.size(); i++) {
            labels.add(ListTemp2.get(i));
        }

        for (int i = 0; i < ListTemp2.size(); i++) {
            if (ListTemp2.get(i).equals("-")) {
                ListTemp2.set(i, "0");
            }
        }

        // Line Data
        ArrayList<Entry> entry1 = new ArrayList<>();
        for (int i = 0; i < ListTemp1.size(); i++) {
            entry1.add(new Entry(i, Float.parseFloat(ListTemp1.get(i))));
        }
        LineDataSet lineDataSet1 = new LineDataSet(entry1, Current_local + " 미세먼지 농도");
        lineDataSet1.setColor(Color.rgb(255, 127, 0));
        lineDataSet1.setLineWidth(2.5f);
        lineDataSet1.setDrawValues(false);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<Entry> entry2 = new ArrayList<>();
        for (int i = 0; i < ListTemp1.size(); i++) {
            entry2.add(new Entry(i, 100));
        }
        LineDataSet lineDataSet2 = new LineDataSet(entry2, "대기환경기준 : 100㎍/㎥");
        lineDataSet2.setColor(Color.rgb(255, 0, 0));
        lineDataSet2.setLineWidth(2.5f);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // Chart
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(null);
        lineChart.setScaleMinima(1f, 1f);

        Legend l = lineChart.getLegend();
        //l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setDrawInside(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    public void sidoChart() {
        // Fine dust concentration data
        final List<String> ListTemp1 = new ArrayList<>();
        Collections.addAll(ListTemp1, splitTemp2_pm10);
        for (int i = ListTemp1.size() - 1; i > 0; i--) {
            if (ListTemp1.get(i) == null) {
                ListTemp1.remove(i);
            }
        }

        for (int i = 0; i < ListTemp1.size(); i++) {
            if (ListTemp1.get(i) == null) {
                ListTemp1.set(i, "0");
            }
            else if (!(ListTemp1.get(i) == null)) {
                continue;
            }
        }

        for (int i = 0; i < ListTemp1.size(); i++) {
            if (ListTemp1.get(i).equals("-")) {
                ListTemp1.set(i, "0");
            }
            else if (!ListTemp1.get(i).equals("-")) {
                continue;
            }
        }

        // Measuring station name data
        final List<String> ListTemp2 = new ArrayList<>();
        Collections.addAll(ListTemp2, splitTemp2_name);
        for (int i = ListTemp2.size() - 1; i > 0; i--) {
            if (ListTemp2.get(i) == null) {
                ListTemp2.remove(i);
            }
        }
        // add to chart
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < ListTemp2.size(); i++) {
            labels.add(ListTemp2.get(i));
        }

        ArrayList<BarEntry> ChartData = new ArrayList<>();
        for (int i = 0; i < ListTemp1.size(); i++) {
            if (!ListTemp1.get(i).equals("null")) {
                ChartData.add(new BarEntry(i, Float.parseFloat(ListTemp1.get(i))));
            }
            else {
                ChartData.add(new BarEntry(i, 0));
            }
        }

        // Line Data
        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < ListTemp1.size(); i++) {
            entries.add(new Entry(i, 100));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "대기환경기준 : 100㎍/㎥");
        lineDataSet.setColor(Color.rgb(255, 0, 0));
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData lineData = new LineData(lineDataSet);

        // Bar Data
        ArrayList<BarEntry> entries1 = new ArrayList<>();

        for (int i = 0; i < ListTemp1.size(); i++) {
            if (!ListTemp1.get(i).equals("null")) {
                entries1.add(new BarEntry(i, Float.parseFloat(ListTemp1.get(i))));
            }
            else {
                entries1.add(new BarEntry(i, 0));
            }
        }

        BarDataSet barDataSet = new BarDataSet(entries1, Current_sido + "권 미세먼지 농도");
        barDataSet.setColor(Color.rgb(60, 220, 78));
        barDataSet.setValueTextColor(Color.rgb(60, 220, 78));
        barDataSet.setValueTextSize(10f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.45f;

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(barWidth);

        // Chart
        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[] {CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});
        combinedChart.setDescription(null);
        combinedChart.setScaleMinima(1f, 1f);


        Legend l = combinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        CombinedData data = new CombinedData();
        data.setData(lineData);
        data.setData(barData);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        combinedChart.setData(data);
        combinedChart.invalidate();
    }

    @Override
    public void onRefresh() {
        mapReverseGeoCoder.startFindingAddress();
        lineChart.fitScreen();
        combinedChart.fitScreen();

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) { }
            else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(MainActivity.this, "권한이 거부되었습니다. 앱을 다시 실행하여 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "권한이 거부되었습니다. 애플리케이션 정보에서 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();

            }
            ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +"위치 접근 권한을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", (dialog, which) -> {
            Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());
        builder.create().show();

        tryRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_ENABLE_REQUEST_CODE) {
            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {
                    Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                    checkRunTimePermission();
                }
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        LocationWithParsing(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        Toast.makeText(MainActivity.this, "주소 미발견", Toast.LENGTH_LONG).show();

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }
        else {
            checkRunTimePermission();
        }
    }
}