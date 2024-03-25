package com.android.launcher.handler;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MeterFragmentBase;

/**
 * abs处理
 *
 */
@Deprecated
public class HandlerAbsflg implements HandlerInteface {

    public Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void handlerData(final String msg) {

        final String absf = msg;
        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {

//                if (absf.equals("on")) {
////                    MeterActivity.abs.setVisibility(View.VISIBLE);
//
//                    MeterFragmentBase.abs.setVisibility(View.VISIBLE);
//                } else {
////                    MeterActivity.abs.setVisibility(View.INVISIBLE);
//                    MeterFragmentBase.abs.setVisibility(View.INVISIBLE);
//                }
            }
        });

    }
}
