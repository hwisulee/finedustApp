package com.finedustApp.finedustalarm;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class XmlParsing extends AppCompatActivity {
    private static String service_key = MainActivity.service_key;
    static String getXmlnearfinedustData() {
        StringBuffer buffer = new StringBuffer();

        String returnType = "xml";
        String num_of_rows = "1";
        String pageNo = "1";
        String stationName = URLEncoder.encode(MainActivity.Current_local);
        String dataTerm = "DAILY";
        String ver = "1.0";

        String queryUrl="http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"
                + "serviceKey=" + service_key
                + "&returnType=" + returnType
                + "&numOfRows=" + num_of_rows
                + "&pageNo=" + pageNo
                + "&stationName=" + stationName
                + "&dataTerm=" + dataTerm
                + "&ver=" + ver;

        try {
            URL url = new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); // url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); // inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); // tag 이름 얻어오기

                        if(tag.equals("item")); // 첫번째 검색결과
                        else if(tag.equals("dataTime")) {
                            buffer.append("측정일시 : ");
                            xpp.next();
                            buffer.append(xpp.getText()); // category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); // 줄바꿈 문자 추가
                        }
                        else if(tag.equals("pm10Value")) {
                            buffer.append("미세먼지 농도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm25Value")) {
                            buffer.append("초미세먼지 농도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm10Grade")){
                            buffer.append("미세먼지 등급 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            //buffer.append("\n");
                        }
                        else if(tag.equals("pm25Grade")) {
                            buffer.append("초미세먼지 등급 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // tag 이름 얻어오기

                        if(tag.equals("item")) {
                            //buffer.append("\n"); // 첫번째 검색결과종료..줄바꿈
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString(); // StringBuffer 문자열 객체 반환
    }

    static String getXmlsidofinedustData() {
        StringBuffer buffer = new StringBuffer();

        String returnType = "xml";
        String num_of_rows = "100";
        String pageNo = "1";
        String sidoName = URLEncoder.encode(MainActivity.Current_sido);
        String ver = "1.0";

        String queryUrl="http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?"
                + "serviceKey=" + service_key
                + "&returnType=" + returnType
                + "&numOfRows=" + num_of_rows
                + "&pageNo=" + pageNo
                + "&sidoName=" + sidoName
                + "&ver=" + ver;

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();

                        if(tag.equals("item"));
                        else if(tag.equals("sidoName")) {
                            buffer.append("시도명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("stationName")) {
                            buffer.append("측정소명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("dataTime")) {
                            buffer.append("측정일시 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm10Value")) {
                            buffer.append("미세먼지 농도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm25Value")) {
                            buffer.append("초미세먼지 농도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm10Grade")){
                            buffer.append("미세먼지 등급 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm25Grade")) {
                            buffer.append("초미세먼지 등급 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();

                        if(tag.equals("item")) {
                            buffer.append("\n");
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    static String getXmlnearStationDailyData() {
        StringBuffer buffer = new StringBuffer();

        String returnType = "xml";
        String num_of_rows = "100";
        String pageNo = "1";
        String stationName = URLEncoder.encode(MainActivity.Current_local);
        String dataTerm = "DAILY";
        String ver = "1.0";

        String queryUrl="http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"
                + "serviceKey=" + service_key
                + "&returnType=" + returnType
                + "&numOfRows=" + num_of_rows
                + "&pageNo=" + pageNo
                + "&stationName=" + stationName
                + "&dataTerm=" + dataTerm
                + "&ver=" + ver;

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();

                        if(tag.equals("item"));
                        else if(tag.equals("dataTime")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm10Value")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();

                        if(tag.equals("item")) {
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    static String getXmlsidoStationDailyData() {
        StringBuffer buffer = new StringBuffer();

        String returnType = "xml";
        String num_of_rows = "100";
        String pageNo = "1";
        String sidoName = URLEncoder.encode(MainActivity.Current_sido);
        String ver = "1.0";

        String queryUrl="http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?"
                + "serviceKey=" + service_key
                + "&returnType=" + returnType
                + "&numOfRows=" + num_of_rows
                + "&pageNo=" + pageNo
                + "&sidoName=" + sidoName
                + "&ver=" + ver;

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();

                        if(tag.equals("item"));
                        else if(tag.equals("stationName")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("pm10Value")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();

                        if(tag.equals("item")) {
                            buffer.append("");
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    static String getXmlnearStationData(String longitude, String latitude) {
        StringBuffer buffer = new StringBuffer();

        String returnType = "xml";

        String queryUrl="http://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList?"
                + "serviceKey=" + service_key
                + "&returnType=" + returnType
                + "&tmX=" + longitude
                + "&tmY=" + latitude;
                //+ "&ver=" + ver;

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();

                        if(tag.equals("item"));
                        else if(tag.equals("stationName")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("addr")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("tm")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if(tag.equals("item")) {
                            buffer.append("");
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    static String getXmlsidoStationData(String address) {
        StringBuffer buffer = new StringBuffer();

        String returnType = "xml";
        String numOfRows = "150";
        String pageNo = "1";
        String addr = address;

        String queryUrl="http://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/getMsrstnList?"
                + "serviceKey=" + service_key
                + "&returnType=" + returnType
                + "&numOfRows=" + numOfRows
                + "&pageNo=" + pageNo
                + "&addr=" + addr;

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();

                        if(tag.equals("item"));
                        else if(tag.equals("stationName")) {
                            buffer.append("N");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("dmX")) {
                            buffer.append("X");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("dmY")) {
                            buffer.append("Y");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if(tag.equals("item")) {
                            buffer.append("");
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
