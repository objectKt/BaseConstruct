package com.android.launcher.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.android.launcher.App;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.LogcatHelper;

/**
 * @description: 上传日志服务
 * @createDate: 2023/7/18
 */
public class UploadLogService extends Service {

    public UploadLogService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.printI(UploadLogService.class.getSimpleName(), "onStartCommand------");
        new Thread(() -> {
            try {
                LogcatHelper.getInstance(App.getGlobalContext()).stop();
                //ZipLogUtil.logZip();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    LogcatHelper.getInstance(App.getGlobalContext()).start();
                    stopSelf();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.printI(UploadLogService.class.getSimpleName(), "onCreate------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.printI(UploadLogService.class.getSimpleName(), "onDestroy------");
    }

    public static void startMyService(Context context){
        try {
            Intent intent = new Intent(context, UploadLogService.class);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMyService(Context context){
        try {
            Intent intent = new Intent(context, UploadLogService.class);
            context.stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
