package com.android.launcher.handler;//package com.android.launcher.handler;
//
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.view.animation.Animation;
//import android.view.animation.RotateAnimation;
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
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 车速
// */
//public class HandlerCarSpeed implements HandlerInteface {
//
//    public String startA="0.0",endA="0.0"  ;
//    public MeterActivity meterActivity;
//    public List<String> miles= Collections.synchronizedList(new ArrayList());
//    public List<String> speeds= Collections.synchronizedList(new ArrayList());;
//
//    public ParamDaoUtil paramDaoUtil = new ParamDaoUtil(App.getGlobalContext());
//    public Param param  ;
//    public CountDownTimer countDownTimer ;
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
//
//
//    private Handler handler = new Handler(Looper.getMainLooper());
//
//    public HandlerCarSpeed() {
//        param = paramDaoUtil.queryParamName("CARSETSPEED") ;
//        String speedLevel = param.getParamValue() ;
//        speedLevel = speedLevel.replace("km/h","") ;
//        speedSet = Integer.parseInt(speedLevel) ;
//        mileStart = mileStartDaoUtil.queryMileStart() ;
//        mileSet = mileSetDaoUtil.queryMileSet() ;
//
//    }
//
//    @Override
//    public void handlerData( String msg) {
//
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                final String speed = FuncUtil.getCarSpeed(msg) ;
//                Log.i("CarSpeed","======1=============="+speed+"-----------"+msg) ;
//                speeds.add(speed) ;
//
//                String currentmile = FuncUtil.getMileJustInTime(speed) ;
//                miles.add(currentmile) ;
//                FuncUtil.currentSpeed = speed ;
//
//                if (countDownTimer ==null){
//
//
//                    float speedf = Integer.parseInt(speed)/1f  ;
//                    if(speedf>0){
//                        App.startFlg = true ;
////                App.startTime= FuncUtil.getCurrentDate() ;
//                    }
//                    if(Integer.parseInt(speed)>speedSet&&!App.startAss){
//                        App.startAss = true ;
//                        aCache.put("MidAttention", FuncUtil.getCurrentDate());
//                    }
//                    String carDegree = getDegree(speedf) ;
////                 Log.i("CarSpeed",msg+"============3========"+carDegree) ;
//                    speedDegree(meterActivity,carDegree);
//
//                    countDownTimer = new CountDownTimer(1000,500) {
//                        @Override
//                        public void onTick(long l) {
//
////                            handler.post(new Runnable() {
////                                @Override
////                                public void run() {
//                            if (meterActivity.viewId == CommonUtil.FRAGMENT_MILE){
//                                Map m = new HashMap() ;
//                                m.put("speed",speed) ;
//                                meterActivity.meterMidParent.updateDate(m);
//                            }
////                                }
////                            });
//
//                        }
//                        @Override
//                        public void onFinish() {
//
////                            FuncUtil.speed(speeds);
//                            Map<String,String> map = FuncUtil.getResultMap(miles);
////                            handler.post(new Runnable() {
////                                @Override
////                                public void run() {
//                            if (meterActivity.viewId == CommonUtil.FRAGMENT_TOTALMAIL){
//                                Log.i("carSpeedyemian","--------------------------------------------1") ;
//                                meterActivity.meterMidParent.updateDate(map);
//                            }
//                            if (meterActivity.viewId == CommonUtil.FRAGMENT_MILE){
//                                Map m = new HashMap() ;
//                                m.put("speed",speed) ;
//                                meterActivity.meterMidParent.updateDate(m);
//                            }
////                                }
////                            });
//
//                            mileStart.setStartMile(map.get("realMile"));
//                            mileStartDaoUtil.insert(mileStart) ;
//                            mileSet.setSetMile(map.get("realMile"));
//                            mileSetDaoUtil.insert(mileSet) ;
//
//                            if (countDownTimer!=null){
//                                countDownTimer.cancel();
//                                countDownTimer  = null ;
//                            }
//                            miles.clear();
//
//                        }
//                    }.start() ;
//                }
//
//            }
//        }) ;
//
//
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
////            Log.i("",startA+"-----------------------"+endA) ;
//            showPointer(meterActivity,fromd.floatValue(),tod.floatValue());
//
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
//        AccelerateDecelerateInterpolator lin = new AccelerateDecelerateInterpolator();
//        rotate.setInterpolator(lin);
//        rotate.setDuration(1000);//设置动画持续时间
//        rotate.setRepeatCount(0);//设置重复次数
//        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        meterActivity.imageView.startAnimation(rotate);
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
//}
