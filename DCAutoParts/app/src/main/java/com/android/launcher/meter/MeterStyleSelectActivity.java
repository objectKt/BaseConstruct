package com.android.launcher.meter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.android.launcher.R;
import com.android.launcher.view.TreedRotateSpeedView;

import java.util.Random;

/**
* @description: 仪表盘样式选择
* @createDate: 2023/6/19
*/
@Deprecated
public class MeterStyleSelectActivity extends Activity {

    private TreedRotateSpeedView treedRotateSpeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_style_select);

        TextView speedTV = findViewById(R.id.speedTV);
        treedRotateSpeedView = findViewById(R.id.treedRotateSpeedView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        testSpeed();
                    }
                }).start();

            }
        },2000);
    }


    private static final int MAX_SPEED = 8000; // 最大转速
    private static final int MIN_SPEED = 0; // 最小转速

    private  void testSpeed() {
        Random random = new Random();
        int speed = 0;
        while (true) {
            try {
                Thread.sleep(100); // 模拟每 0.5 秒更新一次转速
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int delta = random.nextInt(200) - 99; // 模拟随机波动
            speed += delta;
            if (speed > MAX_SPEED || speed < MIN_SPEED) {
                speed -= delta; // 转速超出范围则回退
            }
            treedRotateSpeedView.setSweepAngle(speed);
        }
    }
}