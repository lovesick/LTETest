package com.futureinternet.cysmile.lte;

import android.os.Build;

/**
 * Created by cysmile on 2015/6/18.
 */
public class Device {

    Data data = new Data();
    DateTime dateTime = new DateTime();

    public Device() {

        String model = android.os.Build.MODEL;
        String version = Build.VERSION.RELEASE;

        String time = dateTime.fileDateTime();

        data.setTimeTitle("\n" + time + "终端型号：" + model + "    版本号：" + version + "\n");

    }
}
