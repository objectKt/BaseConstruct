package com.android.launcher.can;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import io.reactivex.rxjava3.annotations.NonNull;

import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;

import java.util.List;

//刹车p (已测试)
public class Can17a implements CanParent {

    public CountDownTimer timer = null;

    public static String lastPflg = "";

    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            try {
                String pflg = (String) msg.obj;
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }

                timer = new CountDownTimer(1000, 2000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        LogUtils.printI(Can17a.class.getSimpleName(), "onFinish----------pflg=" + pflg);

                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        if (pflg.equals("00")) {
                            CommonUtil.meterHandler.get("brake").handlerData("0");
                        }
                        if (pflg.equals("08")) {
                            CommonUtil.meterHandler.get("brake").handlerData("8");
                        }
                        if (pflg.equals("04")) {
                            CommonUtil.meterHandler.get("brake").handlerData("4");
                        }
                        if (pflg.equals("02")) {
                            CommonUtil.meterHandler.get("brake").handlerData("2");
                        }
                        if (pflg.equals("01")) {
                            CommonUtil.meterHandler.get("brake").handlerData("1");
                        }
                        if (pflg.equals("07")) {
                            CommonUtil.meterHandler.get("brake").handlerData("7");
                        }
                        lastPflg = "";
                    }
                };

                timer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void handlerCan(List<String> msg) {

        String pflg = msg.get(3);
        LogUtils.printI(Can17a.class.getSimpleName(), "pflg=" + pflg + ", lastPflg=" + lastPflg);
        if (!lastPflg.equals(pflg)) {
            Message message = Message.obtain();
            message.obj = pflg;
            handler.sendMessage(message);

            lastPflg = pflg;
        }
    }

    public void release() {
        try {
            if (timer != null) {
                timer.cancel();
            }
            lastPflg = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
