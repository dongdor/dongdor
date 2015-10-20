package com.example.administrator.ourapplication;

import android.app.Application;

import org.json.JSONObject;

/**
 * Created by 영수 on 2015-10-08.
 */
public class ApplicationClass extends Application {

    private static final String TAG = "ApplicationClass Application";

    // 서버에 보낼 데이터
    private JSONObject data;

    // 세션 토큰
    public boolean SESSION_TOKEN;

    @Override
    public void onCreate() {
        // 자동로그인 설정
        SESSION_TOKEN = false;

        // Http Client로 데이터 동기화하는 코드
        // TODO
    }


    public void setToken(boolean token) { this.SESSION_TOKEN = token; }
    public boolean getToken() {return this.SESSION_TOKEN;}
}
