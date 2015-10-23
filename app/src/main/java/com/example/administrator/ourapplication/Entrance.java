package com.example.administrator.ourapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 영수 on 2015-10-17.
 *
 * 처음 실행 액티비티 (로딩 프로세스)
 * if (Not Login) {
 *     Login processing
 * } else {
 *     Load ButtonList Activity
 * }
 */
public class Entrance extends Activity {

    private static final String TAG = "Entrance";

    // 로그인 관련 변수
    private EditText user_id;
    private EditText user_pw;
    private JSONObject jobj;

    ApplicationClass app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrance_layout);

        ProgressBar pb1 = (ProgressBar)findViewById(R.id.ProgressBar01);

        pb1.setVisibility(View.VISIBLE);

        Log.d(TAG, "로딩중...");
        app = (ApplicationClass)getApplicationContext();

        user_id = (EditText)findViewById(R.id.user_id);
        user_pw = (EditText)findViewById(R.id.user_pw);

        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        String id = prefs.getString("id", "");
        String password = prefs.getString("password", "");
        boolean is_login = prefs.getBoolean("is_login", true);

        Log.d(TAG, "로그인 상태 : "+ is_login);

        /**
         * 자동 로그인 프로세스
         */

        if (is_login) {
            Log.d(TAG, "로그인 통신 실행");

            try {
                user_id.setText(id);
                user_pw.setText(password);

                jobj = new JSONObject("{ id : " + user_id.getText() + ",password : " + user_pw.getText() + "}");

                // 자동로그인 통신 실행
                new HttpClient().setActControl(Entrance.this).sendData("user/login", jobj) ;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        /**
         * 로그인 프로세스
         */

        Log.d(TAG, "자동로그인 대기! \n SESSION_TOKEN : " + is_login);
        pb1.setVisibility(View.INVISIBLE);

        Button login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "로그인 통신 실행");

                try {
                    jobj = new JSONObject("{ id : " + user_id.getText() + ",password : " + user_pw.getText() + "}");

                    // 통신 실행
                    if (new HttpClient().setActControl(Entrance.this).sendData("user/login", jobj)) {
                        Log.d(TAG, app.GCM_TOKEN);
                        Intent intent = new Intent(Entrance.this, ButtonList.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Log.d(TAG, "로그인 성공");
                    } else {
                        Log.d(TAG, "로그인 실패");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        /**
         * 회원가입 버튼
         */
        Button regi_btn = (Button)findViewById(R.id.regi_btn);
        regi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entrance.this, UserRegistration.class);
                startActivity(intent);
            }
        });

    }
}
