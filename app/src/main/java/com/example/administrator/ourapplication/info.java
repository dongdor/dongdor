package com.example.administrator.ourapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by Administrator on 2015-09-20.
 */
public class info extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

    }

    public void info(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setData(Uri.parse("http://finance.naver.com/item/main.nhn?code=005930"));
        startActivity(intent);
    }
    }
