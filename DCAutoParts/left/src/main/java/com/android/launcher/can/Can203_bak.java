package com.android.launcher.can;//
//
//
//package com.android.launcher.can;
//
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.util.Log;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.view.animation.Animation;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.RotateAnimation;
//
//import io.reactivex.rxjava3.annotations.NonNull;
//
//import com.android.launcher.App;
//import com.android.launcher.MeterActivity;
//import com.android.launcher.dao.MileSetDaoUtil;
//import com.android.launcher.dao.MileStartDaoUtil;
//import com.android.launcher.dao.ParamDaoUtil;
//import com.android.launcher.entity.MileSet;
//import com.android.launcher.entity.MileStart;
//import com.android.launcher.entity.Param;
//import com.dc.auto.library.launcher.util.ACache;
//import com.android.launcher.util.CommonUtil;
//import com.android.launcher.util.FuncUtil;
//import com.android.launcher.util.HandlerRequest105;
//import com.android.launcher.util.HandlerRequest203;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.CopyOnWriteArrayList;
//
////车速
//public class Can203 implements CanParent {
//
//    public HandlerRequest105 handlerRequest203 = new HandlerRequest105() ;
//    public SendThread203 sendThread203 ;
//    public String startA="0.0",endA="0.0"  ;
//    public List<String> miles= new CopyOnWriteArrayList();
//
//    public ParamDaoUtil paramDaoUtil = new ParamDaoUtil(App.getGlobalContext());
//    public Param param  ;
//
//    public int speedSet ;
//    public ACache aCache = ACache.get(App.getGlobalContext()) ;
//    public MileStartDaoUtil mileStartDaoUtil = new MileStartDaoUtil(App.getGlobalContext()) ;
//    public MileStart mileStart ;
//
//    public MileSetDaoUtil mileSetDaoUtil = new MileSetDaoUtil(App.getGlobalContext()) ;
//    public MileSet mileSet ;
//
//
//    public boolean run ;
//    public   String speed ;
//
//    public ArrayList<String> tempArr = new ArrayList<>();
//
//    public int sp ;
//    public long ll  ;
//    public int count = 4 ;
//    public int num = 0 ;
//    public Handler handler = new Handler(Looper.getMainLooper()){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//
//            String speed = (String) msg.obj ;
////            String carDegree = getDegree(new BigDecimal(speed).floatValue()) ;
////            speedDegree(MeterActivity.localMeterActivity,carDegree);
//            tempArr.add(speed) ;
////            if (tempArr.size()>24){
////                int tempMilespeed = 0;
////                for (int i = 0; i < tempArr.size(); i++) {
////                    tempMilespeed= Integer.parseInt(tempArr.get(i)) + tempMilespeed;
////                }
////                tempMilespeed = tempMilespeed/tempArr.size() ;
////                String carDegree = getDegree(tempMilespeed) ;
////                speedDegree(MeterActivity.localMeterActivity,carDegree);
////                tempArr.clear();
////            }
//
//            if (tempArr.size()>count){
//                Log.i("tempssss",tempArr.size()+"==========="+count) ;
//                int tempMilespeed = 0;
//                for (int i = 0; i < tempArr.size(); i++) {
//                    tempMilespeed= Integer.parseInt(tempArr.get(i).split("-")[0]) + tempMilespeed;
//                }
//                tempMilespeed = tempMilespeed/tempArr.size() ;
//
//                int abs = Math.abs(tempMilespeed-sp) ;
//
//                Log.i("tempssss",abs+"============================") ;
//                BigDecimal tempMilespeed1 = null ;
//                if (abs<3){
//                    tempMilespeed1 = FuncUtil.subtract(new BigDecimal(tempMilespeed),new BigDecimal(abs*0.5f)) ;
//                    count  = 29 ;
//                }else{
//                    count = 14;
//                }
//                sp = tempMilespeed ;
//                tempMilespeed1 = new BigDecimal(tempMilespeed) ;
//                String data = tempArr.get(tempArr.size()-1  ) ;
//                String[] ss = data.split("-") ;
//
//                long cc = Long.parseLong(ss[1]) ;
//                String data1 = tempArr.get(0) ;
//                String[] data11 = data1.split("-") ;
//                long bb = Long.parseLong(data11[1]) ;
//                ll = cc - bb ;
//                Log.i("tempssss",ll+"=ss===========================") ;
//                String carDegree = getDegree(tempMilespeed1.floatValue()) ;
//                speedDegree(MeterActivity.localMeterActivity,carDegree);
//                tempArr.clear();
//
//            }
//
//        }
//    };
//
//    public Can203(){
//        param = paramDaoUtil.queryParamName("CARSETSPEED") ;
//        if(param!=null){
//            String speedLevel = param.getParamValue() ;
//            speedLevel = speedLevel.replace("km/h","") ;
//            speedSet = Integer.parseInt(speedLevel) ;
//        }
//
//        mileStart = mileStartDaoUtil.queryMileStart() ;
//        mileSet = mileSetDaoUtil.queryMileSet() ;
////        new Timer().schedule(new TimerTask() {
////            @Override
////            public void run() {
////                if (run){
////                    try {
////                        String speed = handlerRequest203.get();
////                        Log.i("speed111",speed+"----------") ;
//////                        speedDegree(MeterActivity.localMeterActivity,speed);
////
////                        Message message = new Message() ;
////                        message.what= 1 ;
////                        message.obj = speed +"-"+System.currentTimeMillis() ;
////                        handler.sendMessage(message) ;
////
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////        },0,20);
//
////        sendThread203 = new Can203.SendThread203() ;
////        sendThread203.start();
//        countSpeed();
//    }
//
//
//
//
//    private void countSpeed() {
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (run){
//                    float speedf = Integer.parseInt(speed)/1f  ;
//                    if(speedf>0){
//                        App.startFlg = true ;
//                    }
//                    if(Integer.parseInt(speed)>speedSet&&!App.startAss){
//                        App.startAss = true ;
//                        aCache.put("MidAttention", FuncUtil.getCurrentDate());
//                    }
////                    String carDegree = getDegree(speedf) ;
////            Log.i("CarSpeed",msg+"============3========"+carDegree) ;
////                    speedDegree(MeterActivity.localMeterActivity,carDegree);
//
////                    Log.i("Can203",currentMile+"====") ;
//                    Map<String,String> map = FuncUtil.getResultMap(miles);
////
//                    if (MeterActivity.localMeterActivity.viewId == CommonUtil.FRAGMENT_TOTALMAIL){
//                        Log.i("carSpeedyemian","--------------------------------------------1"+map.toString()) ;
//                        MeterActivity.localMeterActivity.meterMidParent.updateDate(map);
//                    }
//                    if (MeterActivity.localMeterActivity.viewId == CommonUtil.FRAGMENT_MILE){
//                        Map m = new HashMap() ;
//                        m.put("speed",speed) ;
//                        MeterActivity.localMeterActivity.meterMidParent.updateDate(m);
//                    }
//
//                    mileStart.setStartMile(map.get("realMile"));
//                    mileStartDaoUtil.insert(mileStart) ;
//                    mileSet.setSetMile(map.get("realMile"));
//                    mileSetDaoUtil.insert(mileSet) ;
//
//                    miles.clear();
//                }
//            }
//        },1000,1000);
//    }
//
//
//    private class  SendThread203 extends Thread{
//        @Override
//        public void run() {
//            super.run();
//            while (true){
//                if(speed!=null && !speed.equals("0")){
//                    try {
//                        handlerRequest203.put(speed);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//
//    @Override
//    public void handlerCan(List<String> msg) {
////
//
//        Log.i("CANMSG",msg.toString()+"----------------------------------") ;
//
//        String carspeed = msg.get(2) +msg.get(3) ;
//
//        speed= FuncUtil.getCarSpeed(carspeed) ;
//        Log.i("can203",speed+"===========") ;
//        if (!speed.equals("0")){
//            run = true ;
////            try {
////                handlerRequest203.put(speed);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
////        else{
////            String degre = getDegree(0) ;
////            speedDegree(MeterActivity.localMeterActivity,degre);
////        }
//
//        String currentmile = FuncUtil.getMileJustInTime(speed) ;
//        miles.add(currentmile) ;
//        App.carSpeed = speed ;
//
//
//        Message message = new Message() ;
//        message.what= 1 ;
//        message.obj = speed +"-"+System.currentTimeMillis() ;
//        handler.sendMessage(message) ;
//    }
//
//
//    /**
//     * 里程角度变化
//     * @param meterActivity
//     * @param endDegree
//     */
//    public void speedDegree(MeterActivity meterActivity, String endDegree){
//        BigDecimal fromd = new BigDecimal(startA) ;
//        endA= endDegree  ;
//        if (!endA.equals("0.0")){
//            BigDecimal tod = new BigDecimal(endA) ;
////            Log.i("start",startA+"-----------------------"+endA) ;
//            showPointer(meterActivity,fromd.floatValue(),tod.floatValue());
//            startA = endA ;
//        }
//    }
//
//    /**
//     * 指针转动
//     * @param fromDegrees
//     * @param toDegrees
//     */
//    public void showPointer(MeterActivity meterActivity, Float fromDegrees, Float toDegrees){
//        RotateAnimation rotate  = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        LinearInterpolator lin = new LinearInterpolator() ;
//        Log.i("AFDFS",ll+"------------------------") ;
//        rotate.setInterpolator(lin);
//        rotate.setDuration(ll+20);//设置动画持续时间
//        rotate.setRepeatCount(0);//设置重复次数
//        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        meterActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                meterActivity.imageView.startAnimation(rotate);
//            }
//        });
//    }
//
//    /**
//     * 获取角度
//     * @param v
//     * @return
//     */
//    private String getDegree(float v) {
//
//        String result ="0.0" ;
//        if(v<60.0f){
//            BigDecimal bignum1 = new BigDecimal(v);
//            result = bignum1.toString() ;
//        }else if(v>60.0f && v<180.0f || new BigDecimal(v).compareTo(new BigDecimal(180.0))==0){
//            BigDecimal bignum1 = new BigDecimal(v);
//            BigDecimal bignum2 = new BigDecimal(2.0f);
//            BigDecimal bignum3 = bignum1.subtract(bignum2);
//            result =bignum3.toString() ;
//        }else if(v>180.0f && v <210.0f || new BigDecimal(v).compareTo(new BigDecimal(210.0))==0){
//            BigDecimal bignum1 = new BigDecimal(v);
//            BigDecimal bignum2 = new BigDecimal(3.0f);
//            BigDecimal bignum3 = bignum1.subtract(bignum2);
//            result =bignum3.toString() ;
//        }else if(v>210.0f && v <220.0f || new BigDecimal(v).compareTo(new BigDecimal(220.0))==0){
//            BigDecimal bignum1 = new BigDecimal(v);
//            BigDecimal bignum2 = new BigDecimal(4.0f);
//            BigDecimal bignum3 = bignum1.subtract(bignum2);
//            result =bignum3.toString() ;
//        }else if(v>220.0f && v <230.0f || new BigDecimal(v).compareTo(new BigDecimal(230.0))==0){
//            BigDecimal bignum1 = new BigDecimal(v);
//            BigDecimal bignum2 = new BigDecimal(5.0f);
//            BigDecimal bignum3 = bignum1.subtract(bignum2);
//            result =bignum3.toString() ;
//        }else if(v>230.0f && v <260.0f || new BigDecimal(v).compareTo(new BigDecimal(260.0))==0){
//            BigDecimal bignum1 = new BigDecimal(v);
//            BigDecimal bignum2 = new BigDecimal(6.0f);
//            BigDecimal bignum3 = bignum1.subtract(bignum2);
//            result =bignum3.toString() ;
//        }else if (v>260.0f){
//            v = 260.0f ;
//            BigDecimal bignum1 = new BigDecimal(v);
//            BigDecimal bignum2 = new BigDecimal(6.0f);
//            BigDecimal bignum3 = bignum1.subtract(bignum2);
//            result =bignum3.toString() ;
//        }
//        return result;
//    }
//
//}
