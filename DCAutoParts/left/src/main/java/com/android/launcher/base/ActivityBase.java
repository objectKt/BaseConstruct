package com.android.launcher.base;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.view.BottomStatusView;
import com.android.launcher.meter.view.SpeedCenterView;
import com.android.launcher.type.LanguageType;
import com.android.launcher.type.SteerWheelKeyType;
import com.android.launcher.util.LanguageUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ActivityBase<T extends IPresenter> extends AppCompatActivity {

    protected String TAG;
    protected T mPresenter;
    public BottomStatusView bottomStatusView;
    public SpeedCenterView speedCenterView;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            TAG = this.getClass().getSimpleName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.printI(TAG, "onCreate------");
        try {
            int language = SPUtils.getInt(this, SPUtils.SP_SELECT_LANGUAGE, LanguageType.SYSTEM.ordinal());
            if (language == LanguageType.ZH.ordinal()) {
                LanguageUtils.setLang(this, LanguageType.ZH);
            } else if (language == LanguageType.EN.ordinal()) {
                LanguageUtils.setLang(this, LanguageType.EN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_new_meter);
        bottomStatusView = findViewById(R.id.bottomStatusView);
        speedCenterView = findViewById(R.id.speedCenterView);
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            executorService = Executors.newCachedThreadPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView(savedInstanceState);
        setupData();
    }

    public void executeAsyncTask(Runnable runnable) {
        if (executorService != null) {
            executorService.execute(runnable);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.type == MessageEvent.Type.STEER_WHEEL_TYPE) {
            if (event.data != null) {
                int keyType = (int) event.data;
                if (keyType == SteerWheelKeyType.KEY_BACK.ordinal()) {
                    onBack();
                } else if (keyType == SteerWheelKeyType.KEY_OK.ordinal()) {
                    onOK();
                } else if (keyType == SteerWheelKeyType.KEY_LEFT.ordinal()) {
                    onLeft();
                } else if (keyType == SteerWheelKeyType.KEY_RIGHT.ordinal()) {
                    onRight();
                } else if (keyType == SteerWheelKeyType.KEY_UP.ordinal()) {
                    onUp();
                } else if (keyType == SteerWheelKeyType.KEY_DOWN.ordinal()) {
                    onDown();
                }
            }
        } else {
            disposeMessageEvent(event);
        }
    }

    protected abstract void onDown();

    protected abstract void onUp();

    protected abstract void onRight();

    protected abstract void onLeft();

    protected abstract void onOK();

    protected abstract void onBack();

    protected abstract void disposeMessageEvent(MessageEvent event);

    protected abstract void setupData();

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.printI(TAG, "onResume------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.printI(TAG, "onPause------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.printI(TAG, "onStart------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.printI(TAG, "onStop------");
    }

    @Override
    protected void onDestroy() {
        try {
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();

        }
        super.onDestroy();
        LogUtils.printI(TAG, "onDestroy------");
        try {
            if (mPresenter != null) {
                mPresenter.release();
            }
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}