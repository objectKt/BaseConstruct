package com.android.launcher.can;

import android.os.CountDownTimer;
import android.util.Log;

import com.android.launcher.util.CommonUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 发动机转速
 */
public class Can105_bak implements CanParent {

    public List<String> list = new CopyOnWriteArrayList<>() ;
    boolean run ;
    public int realSpeed ;
    private ReadThread mReadThread;
    private HandlerThread handlerThread ;
    public Can105_bak(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                mReadThread = new ReadThread();
                mReadThread.start();
                handlerThread = new HandlerThread() ;
                handlerThread.start();
            }
        }.start();

    }




    private class HandlerThread extends  Thread{

        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    Thread.sleep(20);
//                    String speed = handlerRequest105.get() ;
//                    Log.i("Speed","--------handler----"+speed) ;
//                    CommonUtil.meterHandler.get("energySpeed").handlerData(MeterActivity.localMeterActivity,speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//
    public int speed;
    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (true){
//                Log.i("Speed",realSpeed+"------put111----");
                if (realSpeed!=0){
//                    Log.i("Speed",realSpeed+"--------put----") ;
//                    realSpeed = 0 ;
                    try {

                        Log.i("Speed",realSpeed+"--------put----") ;
//                        handlerRequest105.put(realSpeed+"");

                        realSpeed = 0 ;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
//

    public CountDownTimer timer  = null ;

    @Override
    public void handlerCan(List<String> msg) {

        String speedAl = msg.get(2) + msg.get(3);
        String vaild =msg.get(4) ;

//        Log.i("can105",msg.toString()+"-----------------111---") ;
        if (!vaild.equals("ff")) {
            run = true ;
//            int realSpeed = CommonUtil.getStandard(CommonUtil.getHexToDecimal(speedAl));
            realSpeed = CommonUtil.getHexToDecimal(speedAl) ;
//            Log.i("Speed",realSpeed+"---------12------------1----");
//            if (scendSpeed!=0){
//                int chazhi = realSpeed - scendSpeed ;
//                Log.i("Speed",chazhi+"---12--1---"+realSpeed+"====="+scendSpeed);
//            }

//            scendSpeed = realSpeed ;

//            if (timer==null){
//                timer = new CountDownTimer(20,10) {
//                    @Override
//                    public void onTick(long l) {
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                    }
//                } ;
//
//            }





//            list.add(realSpeed+"") ;
//
//            if (list.size()>5){
//                int chazhi = Integer.parseInt(list.get(list.size())) - Integer.parseInt(list.get(0)) ;
//                Log.i("chazhi",chazhi+"----------------1-1-11111111111---------") ;
//            }
//            CommonUtil.meterHandler.get("energySpeed").handlerData(MeterPresentation.localMeterPresentation,realSpeed + "");
//            EventBusMeter.getInstance().postSticky(new MessageWrap("energySpeed", realSpeed + ""));
        } else {
            run = false;
//            CommonUtil.meterHandler.get("energySpeed").handlerData(MeterPresentation.localMeterPresentation,0 + "");
//            EventBusMeter.getInstance().postSticky(new MessageWrap("energySpeed", 0 + ""));
        }
    }
}
