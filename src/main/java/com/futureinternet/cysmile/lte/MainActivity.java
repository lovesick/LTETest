package com.futureinternet.cysmile.lte;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioKind;
    private Button start, stop;
    private TextView getTime, rxSpeed, txSpeed, totalSpeed;
    private LinearLayout speedView;

    private int FLAG = 1;
    private String kind = "固定位置";
    private static final String KIND_STATIC_LOCATION = "固定位置";
    private static final String KIND_PEOPLE = "用户数量";
    private static final String KIND_SPEED = "移动速度";
    private static final String KIND_DEVICE = "终端型号";

    DateTime dateTime = new DateTime();
    Data data = new Data();
    SaveFile saveFile = new SaveFile();


    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getTime.setText(Data.getGetTime());
                    rxSpeed.setText(Data.getRxspeed());
                    txSpeed.setText(Data.getTxspeed());
                    totalSpeed.setText(Data.getTotalspeed());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTime = (TextView) findViewById(R.id.get_time);
        rxSpeed = (TextView) findViewById(R.id.rxspeed);
        txSpeed = (TextView) findViewById(R.id.txspeed);
        totalSpeed = (TextView) findViewById(R.id.totalspeed);
        speedView = (LinearLayout) findViewById(R.id.speedview);
        radioKind = (RadioGroup) findViewById(R.id.kind);
        start = (Button) findViewById(R.id.start_service);
        stop = (Button) findViewById(R.id.stop_service);

        /*RadioGroup用OnCheckedChangeListener来运行*/
        radioKind.setOnCheckedChangeListener(mkind);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);


        new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {

                    if (Data.getViewFlag() == 1) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        data.setViewFlag(0);
                    }

                }
            }
        }).start();


    }

    private RadioGroup.OnCheckedChangeListener mkind = new
            RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case R.id.kind_static_location:
                            FLAG = 1;
                            kind = KIND_STATIC_LOCATION;
                            break;
                        case R.id.kind_people:
                            FLAG = 2;
                            kind = KIND_PEOPLE;
                            break;
                        case R.id.kind_speed:
                            FLAG = 3;
                            kind = KIND_SPEED;
                            break;
                        case R.id.kind_device:
                            FLAG = 4;
                            kind = KIND_DEVICE;
                            break;
                        default:
                            break;
                    }
                    data.setKind(kind);
                }
            };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                if (data.getKind().equals(""))
                    data.setKind(kind);

                Intent startIntent = new Intent(this, SpeedTestService.class);

                speedView.setVisibility(View.VISIBLE);
                String time = dateTime.fileDateTime();

                data.setTimeTitle("\n" + time + "\n");

                switch (FLAG) {
                    case 1:
                        saveFile.fileAppend(kind, Data.getTimeTitle());
                        startService(startIntent);
                        break;
                    case 2:
                        saveFile.fileAppend(kind, Data.getTimeTitle());
                        startService(startIntent);
                        break;
                    case 3:
                        saveFile.fileAppend(kind, Data.getTimeTitle());
                        Intent speedIntent = new Intent(this, SpeedGpsService.class);
                        startService(startIntent);
                        startService(speedIntent);
                        break;
                    case 4:
                        new Device();
                        saveFile.fileAppend(kind, Data.getTimeTitle());
                        startService(startIntent);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.stop_service:
                if (FLAG == 3) {
                    Intent stopSpeedIntent = new Intent(this, SpeedGpsService.class);
                    stopService(stopSpeedIntent);
                }
                Intent stopIntent = new Intent(this, SpeedTestService.class);
                stopService(stopIntent);

                break;
            default:
                break;
        }
    }


}
