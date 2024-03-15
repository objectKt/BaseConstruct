package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.cansender.Can045Sender;

/**
 * 打开日行灯
 * @date： 2024/1/3
 * @author: 78495
*/
public class DayRunLightOpenTask extends TaskBase{


    private Can045Sender can045Sender = new Can045Sender();

    public DayRunLightOpenTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            for (int i=0; i<12; i++){

                can045Sender.reset();
                Thread.sleep(100);
                for (int j=0; j<15; j++){
                    Thread.sleep(5);
                    can045Sender.back();
                }
                Thread.sleep(100);
                can045Sender.reset();
                Thread.sleep(500);
            }

            can045Sender.reset();
            Thread.sleep(100);
            for (int j=0; j<15; j++){
                Thread.sleep(5);
                can045Sender.left();
            }
            Thread.sleep(100);
            can045Sender.reset();
            Thread.sleep(500);

            for (int i=0; i<2; i++) {
                can045Sender.reset();
                Thread.sleep(100);
                for (int j=0; j<15; j++){
                    Thread.sleep(5);
                    can045Sender.ok();
                }
                Thread.sleep(100);
                can045Sender.reset();
                Thread.sleep(500);
            }

            for (int i=0; i<4; i++){
                can045Sender.reset();
                Thread.sleep(100);
                for (int j=0; j<15; j++){
                    Thread.sleep(5);
                    can045Sender.back();
                }
                Thread.sleep(100);
                can045Sender.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
