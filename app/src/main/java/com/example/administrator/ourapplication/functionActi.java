package com.example.administrator.ourapplication;


import com.example.administrator.ourapplication.R;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2015-09-14.
 */
public class functionActi extends FragmentActivity {


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function);

    }
    public void func1(View v)
    {

        startActivity(new Intent(this,alarm.class));
    }
    public void func2(View v)
    {

        startActivity(new Intent(this,count.class));
    }
    public void func3(View v)
    {

        startActivity(new Intent(this,timer.class));
    }
    public void func4(View v)
    {

        startActivity(new Intent(this,MainActivity.class));
    }
    public void func5(View v)
    {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(
                "com.example.administrator.ourapplication",
                "com.example.administrator.ourapplication.info"
        );
        intent.setComponent(componentName);
        startActivity(intent);
    }


}
