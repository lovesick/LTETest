package com.futureinternet.cysmile.lte;

import android.net.TrafficStats;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by cysmile on 2015/6/18.
 */
public class SpeedTest {


    DecimalFormat df = new DecimalFormat("0.00");
    Data data = new Data();
    DateTime dateTime = new DateTime();

    public void test() {

        double rxbytes0, txbytes0, totalbytes0, rxbytes, txbytes,
                totalbytes, rxspeed, txspeed, totalspeed;

        rxbytes0 = getMobileRxBytes();
        txbytes0 = getMobileTxBytes();
        totalbytes0 = rxbytes0 + txbytes0;

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rxbytes = getMobileRxBytes();
        txbytes = getMobileTxBytes();
        totalbytes = rxbytes + txbytes;

        rxspeed = rxbytes - rxbytes0;
        txspeed = txbytes - txbytes0;
        totalspeed = totalbytes - totalbytes0;

        data.setgetTime(dateTime.currentDateTime());
        data.setRxspeed(df.format(rxspeed));
        data.setTxspeed(df.format(txspeed));
        data.setTotalspeed(df.format(totalspeed));

    }


    public double getMobileRxBytes() { // 获取通过Mobile连接收到的字节总数，不包含WiFi
        return (double) TrafficStats.getMobileRxBytes() == TrafficStats.UNSUPPORTED ? 0
                : (Math.round(TrafficStats.getMobileRxBytes()) / 1024.0);
    }

    public double getMobileTxBytes() { // 获取通过Mobile连接发送的字节总数，不包含WiFi
        return (double) TrafficStats.getMobileTxBytes() == TrafficStats.UNSUPPORTED ? 0
                : (Math.round(TrafficStats.getMobileTxBytes()) / 1024.0);
    }
}
