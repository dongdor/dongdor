package com.example.administrator.ourapplication;

/**
 * Created by 영수 on 2015-10-19.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/** 회원가입 통신 부분
 *
 */
abstract class HttpTrans extends AsyncTask<String,Integer,String> {

    private static final String TAG = "HttpTrans";

    protected JSONObject data = new JSONObject();

    protected JSONObject resultData = new JSONObject();
    protected Activity actControl;

    public HttpTrans setData(Activity a, JSONObject data) {
        this.actControl = a;
        this.data = data;

        return this;
    }

    public void resultProcess(JSONObject result) {

    }

    @Override
    protected String doInBackground(String... arg0) {
        String content;
        try {
            content = executeClient(arg0[0], data.length());

        } catch (Exception e) {
            e.printStackTrace();
            return "Json download failed";
        }
        return content;
    }

    // content 반환 후
    @Override
    protected void onPostExecute(String result) {
        try {
            /** if 데이터 변수를 JSON 형태로 가져온다면 파싱 불필요 */

            // 서버에서 받아온 값 JSON 파싱
            JSONObject jObject = new JSONObject(result);

            Log.d(TAG, "데이터 길이 : " + jObject.length());

            // 서버에서 받아온 값의 길이에 따라서 3가지 Data 이름 담음
            String[] jsonName = {"status", "message", "data"};

            // 파싱된 값 담을 변수
            String[] parsedData = new String[jObject.length()];

            // 데이터 파싱
            for (int i=0; i<jObject.length(); i++) {
                parsedData[i] = jObject.getString(jsonName[i]);
                Log.d(TAG, jsonName[i] + ":" + parsedData[i]);
            }

            Log.d(TAG, "파싱 완료");


            resultProcess(jObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 실제 전송하는 부분
    public String executeClient(String url, int dataSize) {
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL serverUrl = new URL(url);       // URL 설정
            HttpURLConnection http = (HttpURLConnection) serverUrl.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
            http.setRequestMethod("POST");         // 전송 방식은 POST

            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //--------------------------
            //   서버로 값 전송
            //--------------------------
            StringBuffer buffer = new StringBuffer();

            JSONArray names = data.names();

            for(int i=0; i<data.length(); i++) {
                Log.d(TAG, names.getString(i) + ":" + data.getString(names.getString(i)));
            }
            // [CHECK] 데이터 갯수만큼 POST 변수 증가
            for (int i=0; i<dataSize; i++) {
                buffer.append(String.valueOf(names.getString(i)))
                        .append("=")
                        .append(data.getString(names.getString(i)));   // POST 변수 설정

                if(i < dataSize-1) {
                    buffer.append("&");
                }
            }

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();
            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            Integer inte=http.getResponseCode();
            Log.d(TAG, "응답 코드 : "+inte.toString());

            resultData = new JSONObject(builder.toString());                       // 전송결과를 전역 변수에 저장
            Log.d(TAG, "전송성공 결과 : " + resultData.toString());

        } catch (MalformedURLException e) {
            Log.d(TAG, "test");
            //
        } catch (IOException e) {
            Log.d(TAG, "test");
            //
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return resultData.toString();
        }
        //return null;
    }
}