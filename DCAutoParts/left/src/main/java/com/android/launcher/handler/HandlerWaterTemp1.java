package com.android.launcher.handler;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 水温
 */
@Deprecated
public class HandlerWaterTemp1 implements HandlerInteface {

    private static final String TAG = HandlerWaterTemp1.class.getSimpleName();

    public String tempmsg ="";


   public  List<String>  waterlist = new ArrayList<>() ;

    @Override
    public void handlerData( String msg) {
        final String water = msg ;
        waterlist.add(water) ;

        if (waterlist.size()>50){
            tempmsg = msg ;
            waterlist.clear();
            LogUtils.printI(TAG, "waterTempmsg="+water);

//            handler.postAtFrontOfQueue(new Runnable() {
//                @Override
//                public void run() {
//                    if(water.equals("--")){
//                        MeterActivity.energySpeedView.setWaterTemp(0);
//                        if (MeterActivity.viewId == CommonUtil.FRAGMENT_MAIN_TAIN_COOLANT){
//                            Map<String,String> tempMap = new HashMap<>() ;
//                            tempMap.put("waterTemp","0");
//                            MeterActivity.meterMidParent.updateDate(tempMap);
//                        }
//                    }else{
//                        int waterNum = Integer.parseInt(water) ;
//                        if (waterNum<50){
//                            waterNum = 51 ;
//                        }else{
//                            if(waterNum>110){
//                                MeterActivity.water_temp_icon.setVisibility(View.GONE);
//                                MeterActivity.water_temp_icon_alert.setVisibility(View.VISIBLE);
//                            }else{
//                                MeterActivity.water_temp_icon.setVisibility(View.VISIBLE);
//                                MeterActivity.water_temp_icon_alert.setVisibility(View.GONE);
//                            }
//                        }
//
//                        MeterActivity.energySpeedView.setWaterTemp(waterNum);
//                        if (MeterActivity.viewId == CommonUtil.FRAGMENT_MAIN_TAIN_COOLANT){
//                            Map<String,String> tempMap = new HashMap<>() ;
//                            tempMap.put("waterTemp",waterNum+"");
//                            MeterActivity.meterMidParent.updateDate(tempMap);
//                        }
//                    }
//                }
//            }) ;
//
//            try {
//                sendWaterTempData(water);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }


    }

    private void sendWaterTempData(String water) {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
        if(water.equals("--")){
            messageEvent.data = 0;
        }else{
            int waterNum = Integer.parseInt(water) ;
            if (waterNum<50){
                messageEvent.data = 51;
            }else{
                messageEvent.data = waterNum;
            }
        }
        EventBus.getDefault().post(messageEvent);
    }
}
