package com.android.launcher.can;

import com.android.launcher.MessageEvent;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 水温表(已测试)
 * 频率：100毫秒一次
 */
public class Can30d implements CanParent {

    public static volatile String lastData = "";

    //当前水温
    public static volatile int currentWaterTemp = 0;

    public static volatile boolean systemSleep = false;

    public static volatile List<Integer> waterlist = new ArrayList<>();

    @Override
    public void handlerCan(List<String> msg) {
        try {
            //[30d, 8, 77, 52, 6C, FF, FF, 02, 7E, 7F]
            String tempHex = msg.get(2);
            // 转换出温度后减去40 是正常显示温度
//            if (!lastData.equals(tempHex)) {
//                lastData = tempHex;
            String data = msg.get(2);
            if ("FF".equalsIgnoreCase(data)) {
                LogUtils.printI(Can30d.class.getSimpleName(), "data=" + data);
                return;
            }
            currentWaterTemp = CommonUtil.getHexToDecimal(data) - 40;
            if (currentWaterTemp < 0) {
                currentWaterTemp = 0;
            }
            if (currentWaterTemp > 90 && currentWaterTemp < 110) {
                currentWaterTemp = 94;
            }
            if (waterlist.isEmpty()) {
                LogUtils.printI(Can30d.class.getSimpleName(), "msg=" + msg + ", currentWaterTemp=" + currentWaterTemp);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
                messageEvent.data = currentWaterTemp;
                EventBus.getDefault().post(messageEvent);
                waterlist.add(currentWaterTemp);
            } else {
                waterlist.add(currentWaterTemp);
                if (waterlist.size() >= 500) {
                    currentWaterTemp = getAvgTemp(waterlist);
                    LogUtils.printI(Can30d.class.getSimpleName(), "msg=" + msg + ", currentWaterTemp=" + currentWaterTemp);
                    waterlist.clear();
                    waterlist.add(currentWaterTemp);
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
                    messageEvent.data = currentWaterTemp;
                    EventBus.getDefault().post(messageEvent);
                }
            }
//            }
//            if (!msg.get(2).equals("ff")) {
//                if (lastData.equals(tempData)) {
//                    return;
//                }
//                lastData = tempData;
            //            Log.i("AAAAA", CommonUtil.getHexToDecimal(msg.get(2))+"====") ;
//                int waterTemp = CommonUtil.getHexToDecimal(msg.get(2)) - 40;
//                LogUtils.printI(Can30d.class.getSimpleName(), "msg=" + msg+", waterTemp=" + waterTemp);
            //[30d, 8, 8B, 7C, 85, FF, FF, 02, 25, 7F], waterTemp=99
//                if (waterTemp < 0) {
//                    waterTemp = 0;
//                }
//                if (waterTemp > 90 && waterTemp < 110) {
//                    waterTemp = 94;
//                }
            //            EventBusMeter.getInstance().postSticky(new MessageWrap("waterTemp", waterTemp + ""));
            //                if (tempwater!=waterTemp){
//                CommonUtil.meterHandler.get("waterTemp").handlerData(waterTemp + "");
            //                    tempwater = waterTemp ;
            //                }
//            } else {
//                CommonUtil.meterHandler.get("waterTemp").handlerData("--");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int getAvgTemp(List<Integer> list) {

        Collections.sort(list);

        //去掉最高和最低值
        list = list.subList(50, list.size() - 51);

        int sum = 0;
        for (int i = 1; i < list.size() - 1; i++) {
            sum += list.get(i);
        }

        return (int) (sum / (list.size() - 2));
    }

}
