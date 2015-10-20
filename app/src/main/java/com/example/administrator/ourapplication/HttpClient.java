package com.example.administrator.ourapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 영수 on 2015-10-08.
 */
public class HttpClient {

    private static final String TAG = "HttpClient";

    protected Activity actControl;

    public HttpClient setActControl(Activity a) {
        this.actControl = a;

        return this;
    }

    ////////////////////////////////////////////////////////////////////////

    // 서버 URL
    private final static String serverURL = "http://128.199.151.72/clicky/";

    /***************
     * 사용 예
     * btn.setOnClickListener(new View.OnClickListener() {
     *   @Override
     *   public void onClick(View v) {
     *      try {
     *          JSONObject data = new JSONObject("{ \"id\": \"nayak\"}");
     *          sendData("user/join/", data);
     *      } catch (JSONException e) {
     *          e.printStackTrace();
     *      }
     *   }
     *
     * @param subURL 보낼 url 주소 입력
     * @param data JSONObject 로 보내려는 데이터 보냄
     *****************/
    // Called when send data (JSON Object로 여러개 받음)
    public boolean sendData(String subURL, JSONObject data) {
        // set the server URL
        String url = serverURL + subURL;

        // call data from web URL
        try {
            ConnectivityManager conManager = (ConnectivityManager)actControl.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conManager.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {

                Log.d(TAG, "Network Connection" + ":" + "success");

                switch (subURL) {
                    case "user/join":
                        // 데이터 전송!!
                        Registration reg = new Registration(); // [CHECK]  데이터 ArrayList or 하나로만
                        reg.setData(actControl, data).execute(url);
                        break;

                    case "user/login":
                        // 데이터 전송!!
                        Login login = new Login(); // [CHECK]  데이터 ArrayList or 하나로만
                        login.setData(actControl, data).execute(url);
                        break;

                    case "btn/func":
                        break;
                    case "btn":
                        break;
                    default:
                        //TODO 똑바로 입력하시오
                }

            } else {
                Toast toast = Toast.makeText(actControl.getApplicationContext(), "Network isn't connected", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** 로그인 통신 부분
     *
     */
    private class Login extends HttpTrans {

        @Override
        public void resultProcess(JSONObject result) {
            /**
             *
             *  id, password 저장, 프로그레스바 제거 및 액티비티 변경
             */

            try {
                // Preferences에 id, password 저장
                SharedPreferences prefs = actControl.getSharedPreferences("PrefName", actControl.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("id").remove("password");
                editor.putString("id", data.getString("id"));
                editor.putString("password", data.getString("password"));
                editor.putBoolean("is_login", true);
                editor.commit();

                // 프로그래스바 제거
                ProgressBar pb1 = (ProgressBar) actControl.findViewById(R.id.ProgressBar01);
                pb1.setVisibility(View.INVISIBLE);

                Toast toast;
                // 결과에 따라서 인텐트 생성, 액티비티실행
                if (result.getBoolean("status")) {
                    toast = Toast.makeText(actControl.getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG);
                    toast.show();

                    Log.d(TAG, "로그인 성공");
                    Intent nextIntent = new Intent(actControl, ButtonList.class);
                    nextIntent.addFlags(nextIntent.FLAG_ACTIVITY_CLEAR_TASK);
                    nextIntent.addFlags(nextIntent.FLAG_ACTIVITY_NEW_TASK);
                    actControl.startActivity(nextIntent);
                } else {
                    toast = Toast.makeText(actControl.getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG);
                    toast.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /** 회원가입 통신 부분
     *
     */
    private class Registration extends HttpTrans {

        @Override
        public void resultProcess(JSONObject result) {
            /**
             *
             *  id, password 저장, 프로그레스바 제거 및 액티비티 변경
             */

            try {
                // Preferences에 id, password 저장
                SharedPreferences prefs = actControl.getSharedPreferences("PrefName", actControl.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("id", data.getString("id"));
                editor.putString("password", data.getString("password"));
                editor.putBoolean("is_login", true);
                editor.commit();

                // 프로그래스바 제거
                ProgressBar pb1 = (ProgressBar) actControl.findViewById(R.id.ProgressBar01);
                pb1.setVisibility(View.INVISIBLE);

                Toast toast;
                // 결과에 따라서 인텐트 생성, 액티비티실행
                if (result.getBoolean("status")) {
                    toast = Toast.makeText(actControl.getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG);
                    toast.show();

                    Log.d(TAG, "회원가입 성공");
                    Intent nextIntent = new Intent(actControl, ButtonList.class);
                    nextIntent.addFlags(nextIntent.FLAG_ACTIVITY_CLEAR_TASK);
                    nextIntent.addFlags(nextIntent.FLAG_ACTIVITY_NEW_TASK);
                    actControl.startActivity(nextIntent);
                } else {
                    toast = Toast.makeText(actControl.getApplicationContext(), "회원가입 실패", Toast.LENGTH_LONG);
                    toast.show();
                }

                Log.d(TAG, "회원가입 실패");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
