package com.android.launcher.handler;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.launcher.App;
import com.android.launcher.R;
import com.android.launcher.cansender.Can37CSender;

/**
 * 档位变化
 */
@Deprecated
public class HandlerDegree implements HandlerInteface {

    public Handler handler = new Handler(Looper.getMainLooper()) ;

    private Can37CSender can37CSender = new Can37CSender();

    public void handlerDegree(final String deg){

        Log.i("degree",deg+"-----------------") ;
        final Drawable drawable = App.getGlobalContext().getDrawable(R.drawable.gear_border) ;

//        handler.postAtFrontOfQueue(() -> {
//            switch (deg) {
//                case "50":
//                    FuncUtil.degree=false ;
//                    can37CSender.setGears(GearsType.TYPE_P);
//
//                    MeterFragmentBase.degree_p.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setBackground(null);
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//                case "52":
//                    can37CSender.setGears(GearsType.TYPE_R);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(null);
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(drawable);
//                    break;
//                case "4E":
//                    can37CSender.setGears(GearsType.TYPE_N);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(null);
//                    MeterFragmentBase.degree_n.setBackground(drawable);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//
//                case "01":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//
//                case "44":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//                case "D1":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D1");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//                case "D2":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D2");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//                case "D3":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D3");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//                case "D4":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D4");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//                case "D5":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D5");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//                case "D6":
//                    can37CSender.setGears(GearsType.TYPE_D);
//
//                    MeterFragmentBase.degree_p.setBackground(null);
//                    MeterFragmentBase.degree_d.setBackground(drawable);
//                    MeterFragmentBase.degree_d.setText("D6");
//                    MeterFragmentBase.degree_n.setBackground(null);
//                    MeterFragmentBase.degree_r.setBackground(null);
//                    break;
//            }
//        }) ;

    }

    @Override
    public void handlerData(String msg) {


        Log.i("degree",msg+"-----------------") ;
        //档位变化
        String degree = msg.toUpperCase();

        handlerDegree(degree);
//        if (degree.equals("50")) {
//            Message message = Message.obtain();
//            message.obj = "50";
//            handler.sendMessage(message);
//        }
//        if (degree.equals("52")) {
//            Message message = Message.obtain();
//            message.obj = "52";
//            handler.sendMessage(message);
//        }
//        if (degree.equals("4E")) {
//            Message message = Message.obtain();
//            message.obj = "4E";
//            handler.sendMessage(message);
//
//        }
//        if (degree.equals("44")) {
//            Message message = Message.obtain();
//            message.obj = "44";
//            handler.sendMessage(message);
//
//        }
//        if (degree.equals("D1")) {
//            Message message = Message.obtain();
//            message.obj = "D1";
//            handler.sendMessage(message);
//        }
//        if (degree.equals("D2")) {
//
//            Message message = Message.obtain();
//            message.obj = "D2";
//            handler.sendMessage(message);
//        }
//        if (degree.equals("D3")) {
//            Message message = Message.obtain();
//            message.obj = "D3";
//            handler.sendMessage(message);
//        }
//
//        if (degree.equals("D4")) {
//            Message message = Message.obtain();
//            message.obj = "D4";
//            handler.sendMessage(message);
//        }
//        if (degree.equals("D5")) {
//            Message message = Message.obtain();
//            message.obj = "D5";
//            handler.sendMessage(message);
//
//        }
//        if (degree.equals("D6")) {
//            Message message = Message.obtain();
//            message.obj = "D6";
//            handler.sendMessage(message);
//        }
    }
}
