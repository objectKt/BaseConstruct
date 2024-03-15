package com.android.launcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.Nullable;


import com.amap.api.maps.MapsInitializer;
import com.android.launcher.meter.MeterActivity;
import com.dc.auto.library.launcher.util.ACache;

import java.lang.reflect.Method;


public class LeftSplashActivity extends Activity {

    public ACache aCache = ACache.get(App.getGlobalContext());
    public TextView logo;

    public CountDownTimer countDownTimer;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);

        try {
            MapsInitializer.updatePrivacyShow(getApplicationContext(), true, true);
            MapsInitializer.updatePrivacyAgree(getApplicationContext(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("leftacc", "------- ----MainActivity-------oncreate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        Log.i("leftacc", "------- -----------orientation");
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i("leftacc", "------- -----------focus" + hasFocus);
    }

    @Override
    protected void onStart() {

        Log.i("leftacc", "------- ----MainActivity-------onStart");
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            Log.i("leftacc", "取消掉定时器" + "-------------------------1");
        }

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method method = c.getMethod("get", String.class, String.class);
            String os = (String) method.invoke(c, "sys.suspend.state", "");
            Log.i("leftacc", "------- ----MainActivity-------onstart=4=========" + os);
            if (os.equals("0")) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                if (countDownTimer == null) {
                    countDownTimer = new CountDownTimer(5 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Log.i("leftacc", "------- ----MainActivity--5*1000-----onTick=4=========" + millisUntilFinished);
                        }

                        @Override
                        public void onFinish() {
                            Log.i("leftacc", "------- ----MainActivity--5*1000-----onFinish=4=========");
                            toMeterActivity();
                        }
                    }.start();
                }
            } else {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;

                }
                if (countDownTimer == null) {
                    countDownTimer = new CountDownTimer(15 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Log.i("leftacc", "------- ----MainActivity--15*1000-----onTick=4=========" + millisUntilFinished);
                        }

                        @Override
                        public void onFinish() {
                            Log.i("leftacc", "------- ----MainActivity--15*1000-----onFinish=4=========");
                            toMeterActivity();
                        }
                    }.start();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }

    private void toMeterActivity() {
        Intent intent = new Intent(LeftSplashActivity.this, MeterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("leftacc", "------- ----MainActivity-------onRestart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("leftacc", "------- ---MainActivity-------onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            Log.i("leftacc", "MainActivity-------onPause 中取消内容======================");
        } else {
            Log.i("leftacc", "MainActivity------onPause 进入MeterActivity======================");

        }
        if (!App.currentMeterActivity) {
            toMeterActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("leftacc", "------- ---MainActivity-------onStop");
    }

    @Override
    protected void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            Log.i("leftacc", "onDestroy 中取消内容======================");
        }

        super.onDestroy();

        /******************************测试部分*************************************************/


    }

}
