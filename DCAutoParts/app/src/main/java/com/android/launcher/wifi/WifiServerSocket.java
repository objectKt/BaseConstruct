package com.android.launcher.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import com.android.dx.stock.ProxyBuilder;
import com.android.launcher.App;
import com.android.launcher.util.LogUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xxh
 *
 */
@Deprecated
public class WifiServerSocket {
    private static final String TAG = "ServerSocket";
    private ServerRunnable serverRunnable = null;
    private Thread thread = null;

    private WifiServerSocket() {
    }

    private static class VideoServerSocketInstance {
        private static final WifiServerSocket INSTANCE = new WifiServerSocket();
    }

    public static WifiServerSocket getInstance() {
        return VideoServerSocketInstance.INSTANCE;
    }

    //启动一个SocketServer的线程
    public void start() {
        //如果未启动
        if (serverRunnable == null || !serverRunnable.isStart()) {
            LogUtils.printI(TAG, "start: 开始启动ServerSocket");
            serverRunnable = new ServerRunnable();
            thread = new Thread(serverRunnable);
            thread.start();
        } else {
            Log.i(TAG, "start:已经启动 ");
        }
    }

    public void sendeMessage(String message) {
        serverRunnable.sendMessage(message);
    }

    /**
     * 打开WiFi热点
     * @param context
     */
    public static void startTethering(Context context) {

        LogUtils.printE(TAG, "开启热点----");

        //1、环境属性记录
        String property = System.getProperty("dexmaker.dexcache");

        //2、设置新的属性
        System.setProperty("dexmaker.dexcache", context.getCacheDir().getPath());

        //3、反射操作打开热点
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        try {
            Class classOnStartTetheringCallback = Class.forName("android.net.ConnectivityManager$OnStartTetheringCallback");
            Method startTethering = connectivityManager.getClass().getDeclaredMethod("startTethering", int.class, boolean.class, classOnStartTetheringCallback);
            Object proxy = ProxyBuilder.forClass(classOnStartTetheringCallback).handler(new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    LogUtils.printI(WifiServerSocket.class.getSimpleName(),"method="+method.getName());
                    return null;

                }
            }).build();
            startTethering.invoke(connectivityManager, 0, false, proxy);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printI(WifiServerSocket.class.getSimpleName(),"startTethering----"+e.getMessage());
        }

        //4、恢复环境属性
        if (property != null) {
            System.setProperty("dexmaker.dexcache", property);
        }

//        String ip = getLocalIpAddress();
//
//        Log.i("iplocal",ip+"----------------") ;
    }


    /**
    * @description: 关闭热点
    * @createDate: 2023/5/8
    */
    public static void closeTethering(Context context){
        try {
            LogUtils.printE(TAG, "关闭热点----e=");
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            try {
                Method stopTethering = connManager.getClass().getDeclaredMethod("stopTethering", int.class);
                stopTethering.invoke(connManager,0);
            } catch (Exception e) {
                LogUtils.printE(TAG, "关闭热点失败--e="+e.getMessage());
                e.printStackTrace();
            }

            Class<?> tetheringClass = Class.forName("android.net.TetheringManager");
            Field tetheringField = ConnectivityManager.class.getDeclaredField("mTethering");
            tetheringField.setAccessible(true);
            Object tetheringInstance = tetheringField.get(connManager);
            Method stopTetheringMethod = tetheringClass.getDeclaredMethod("stopAllTethering");
            stopTetheringMethod.invoke(tetheringInstance);
        } catch (Exception e) {
            LogUtils.printE(TAG, "关闭热点失败----e="+e.getMessage());
            e.printStackTrace();
        }

    }

    public static boolean isWifiApOpen(Context context) {
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            //通过放射获取 getWifiApState()方法
            Method method = manager.getClass().getDeclaredMethod("getWifiApState");
            //调用getWifiApState() ，获取返回值
            int state = (int) method.invoke(manager);
            //通过放射获取 WIFI_AP的开启状态属性
            Field field = manager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            //获取属性值
            int value = (int) field.get(manager);
            //判断是否开启
            if (state == value) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printE(TAG, "isWifiApOpen---e="+e.getMessage());
        }
        return false;
    }


    public void stop() {
        if(serverRunnable != null){
            serverRunnable.stop();
        }

    }

    public boolean isStart() {
        if (serverRunnable == null) {
            return serverRunnable.isStart();
        }else {
            return false;
        }
    }




    // 开启热点
    private boolean startWifiHotspot(String ssid, String password) {
        // 获取系统 WiFi 管理器
        WifiManager wifiManager  = (WifiManager) App.getGlobalContext().getSystemService(Context.WIFI_SERVICE);


        // 关闭系统 WiFi
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }


        // 开启热点
        try {
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = ssid; // WiFi 名称
            wifiConfig.preSharedKey = password; // WiFi 密码
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            method.invoke(wifiManager, wifiConfig, true); // 开启热点
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void closeWifiSwitch(Context context){
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
        } catch (Exception e) {
            LogUtils.printI(WifiServerSocket.TAG, "closeWifiSwitch---"+e.getMessage());
            e.printStackTrace();
        }
    }

}
