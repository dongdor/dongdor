package com.example.administrator.ourapplication;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class CountService extends Service{
    private int mCurnNum = 0;
    private Thread mCountThread = null;

    public void onCreate()
    {
        super.onCreate();
        Log.i("superdroid", "onCreate()");

        ////////////////////////////////////////////포그라운드
        Intent intent = new Intent(this, count.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle("Cout Service")
                .setContentText("Running Count_Service")
                .setSmallIcon(R.drawable.ic_launcher)//로고 모양
                .setContentIntent(pIntent)
                .build();

        startForeground(1234,noti);


        //////////////////////////////////////////포그라운드
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        Log.i("superdroid","onStartCommand()"+intent);
        if(mCountThread == null)
        {
            mCountThread = new Thread("Count Thread")
            {
                public void run()
                {
                    while(true)
                    {
                        Log.i("superdroid","Count : "+mCurnNum);
                        mCurnNum++;

                        try{Thread.sleep(1000);}
                        catch(InterruptedException e)
                        {
                            break;
                        };
                    }
                }
            };
        mCountThread.start();
        }
        return START_REDELIVER_INTENT;
    }

    public void onDestroy()
    {
        Log.i("superdroid", "onDestroy()");
        if(mCountThread != null)
        {
            mCountThread.interrupt();
            mCountThread = null;
            mCurnNum = 0;
        }

        super.onDestroy();
    }

    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub()
    {
        public int getCurCountNumber() throws RemoteException
        {
            return mCurnNum;
        }
    };

    public IBinder onBind(Intent intent)
    {
        Log.i("superdroid","onBind()"+ intent);
        return null;
    }

    public boolean onUnbind(Intent intent)
    {
        Log.i("superdroid","onUnbind()");
        return super.onUnbind(intent);
    }
}