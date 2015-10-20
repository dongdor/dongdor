package com.example.administrator.ourapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 영수 on 2015-10-17.
 */
public class UserRegistration extends Activity {

    private static final String TAG = "UserRegistration";

    // 로그인 관련 변수
    private EditText user_id;
    private EditText user_pw;
    private JSONObject jobj = new JSONObject();

    private ApplicationClass app;

    // GCM 관련 변수
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_regist_layout);

        // 토큰생성 받는 리시버
        registBroadcastReceiver();

        app = (ApplicationClass)getApplicationContext();

        user_id = (EditText) findViewById(R.id.user_id);
        user_pw = (EditText) findViewById(R.id.user_pw);

        Button loginProcess = (Button) findViewById(R.id.regi_btn);
        loginProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 회원가입할때 토큰 생성
                    getInstanceIdToken();
                    Log.d(TAG, "토큰생성");

                    //jobj = new JSONObject("{ id : " + user_id.getText() + ",pw : " + user_pw.getText() + ",reg_id : "+ token +"}");

                    jobj.put("id", user_id.getText());
                    jobj.put("password", user_pw.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    // GCM 토큰 생성
    /**
     * Instance ID를 이용하여 디바이스 토큰을 가져오는 RegistrationIntentService를 실행한다.
     */
    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
            Log.d(TAG, "토큰 생성 서비스 실행");
        }
    }

    /**
     * LocalBroadcast 리시버를 정의한다. 토큰을 획득하기 위한 READY, GENERATING, COMPLETE 액션에 따라 UI에 변화를 준다.
     */
    public void registBroadcastReceiver(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();


                if(action.equals(QuickstartPreferences.REGISTRATION_READY)){
                    // 액션이 READY일 경우
                    // TODO
                    Log.d(TAG, "READY");
                } else if(action.equals(QuickstartPreferences.REGISTRATION_GENERATING)){
                    // 액션이 GENERATING일 경우
                    // TODO
                    Log.d(TAG, "GENERATING");
                } else if(action.equals(QuickstartPreferences.REGISTRATION_COMPLETE)){
                    // 액션이 COMPLETE일 경우
                    Log.d(TAG, "COMPLETE");
                    token = intent.getStringExtra("token");

                    Toast toast = Toast.makeText(getApplicationContext(), "토큰생성 완료", Toast.LENGTH_LONG);
                    toast.show();

                    ProgressBar pb1 = (ProgressBar)findViewById(R.id.ProgressBar01);
                    pb1.setVisibility(View.VISIBLE);

                    Log.d(TAG, "회원가입 통신 실행");

                    try {
                        jobj.put("reg_id", token);

                        // 통신 실행
                        if (new HttpClient().setActControl(UserRegistration.this).sendData("user/join", jobj)) {
                            Log.d(TAG, "회원가입 성공");
                        } else
                            Log.d(TAG, "회원가입 실패");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
    }

    /**
     * 앱이 실행되어 화면에 나타날때 LocalBoardcastManager에 액션을 정의하여 등록한다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));

    }

    /**
     * 앱이 화면에서 사라지면 등록된 LocalBoardcast를 모두 삭제한다.
     */
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Google Play Service를 사용할 수 있는 환경이지를 체크한다.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
