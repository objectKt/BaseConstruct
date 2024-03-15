package com.android.launcher.handler;

import android.os.Handler;
import android.os.Looper;

import com.android.launcher.App;
import com.dc.auto.library.launcher.util.ACache;


/**
 * 驾驶模式
 *
 */
@Deprecated
public class HandlerCMS implements HandlerInteface {

    public Handler handler = new Handler(Looper.getMainLooper()) ;


    @Override
    public void handlerData(  String msg) {
        final String mode = ACache.get(App.getGlobalContext()).getAsString("CMS");
       handler.postAtFrontOfQueue(new Runnable() {
           @Override
           public void run() {
//               MeterActivity.car_mode.setText(mode);

//               MeterFragmentBase.car_mode.setText(mode);
           }
       }) ;
    }
}
