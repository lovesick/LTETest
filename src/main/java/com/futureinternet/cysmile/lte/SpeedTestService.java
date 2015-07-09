package com.futureinternet.cysmile.lte;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;

public class SpeedTestService extends Service {

    SpeedTest speedTest = new SpeedTest();
    DateTime dateTime = new DateTime();
    SaveFile saveFile = new SaveFile();
    Data data = new Data();


    public SpeedTestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                speedTest.test();
                if (data.getKind().equals("移动速度")) {
                    data.setTimeLine(dateTime.currentDateTime() + Data.getGpsspeed() + "km/h   ");
                    Log.d("移动速度", Data.getTimeLine());
                } else {
                    data.setTimeLine(dateTime.currentDateTime());
                }

                saveFile.fileAppend(Data.getKind(), Data.getTimeLine() + Data.getTxspeed() + "   " + Data.getRxspeed() + "   "
                        + Data.getTotalspeed() + "\n");

                data.setViewFlag(1);

            }
        }).start();


        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //int anHour = 60 * 60 * 1000; //一小时
        int anHour = 60 * 1000; //一分钟
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);
    }


}
