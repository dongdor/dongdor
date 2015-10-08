package com.example.administrator.ourapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.administrator.ourapplication.CountService;
/**
 * Created by Administrator on 2015-09-18.
 */
public class count  extends Activity {

    private IMyAidlInterface mBinder = null;



    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("superdroid", "onServiceConnected()");
            mBinder = IMyAidlInterface.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Log.d("superdroid","onServiceDisconnected()");


        }


    };

    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count);
        Intent serviceIntent = new Intent("com.superdroid.service.CountService");
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    protected void onDestroy(){

        unbindService(mConnection);
        super.onDestroy();
    }
    public void onClick(View v)
    {
        switch(v.getId()) {
            case R.id.start_count_btn:
            {
                    Intent serviceIntent = new Intent("com.superdroid.service.CountService");

                    startService(serviceIntent); // 여기에 담아서 서비스를 스타트 시킨다.
                    break;

            }
            case R.id.stop_count_btn:
            {
                Intent serviceIntent = new Intent("com.superdroid.service.CountService");
                stopService(serviceIntent);
                       break;
            }
            case R.id.get_cur_number_btn:
            {

                int curCountNumber;
                try{
                    curCountNumber = mBinder.getCurCountNumber();
                    Toast.makeText(this,
                            "cur count" + curCountNumber,Toast.LENGTH_LONG).show();
                }
                catch ( RemoteException e)
                {
                    e.printStackTrace();
                }

                break;
            }

        }

    }
}
