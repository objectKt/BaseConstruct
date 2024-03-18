package com.android.launcher.can;//package com.car.left.can;
//
//
//import android.util.Log;
//
//import com.android.launcher.service.MessageWrap;
//import com.car.left.util.EventBusBase;
//
//import java.util.List;
//
////359 车模式
//public class Can359 implements CanParent {
//    public static String  cms = "00" ;
//
//    @Override
//    public void handlerCan(List<String> msg) {
//
////        Log.i("cms","----------------------"+msg) ;
////        String change = msg.get(2);
////
////        if(!change.equals(cms)&&change.equals("80")){
//////            EventBusBase.getInstance().postSticky(new MessageWrap("cms","cms"));
////        }
////        if (change.equals("00")){
////            cms="00" ;
////        }else{
////            cms=change ;
////        }
//    }
//}
