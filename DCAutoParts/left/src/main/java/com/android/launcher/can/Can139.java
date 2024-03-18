package com.android.launcher.can;

import dc.library.auto.event.MessageEvent;

import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

// 车身温度。

/**
 * @description: 车门， 车身温度，ESP
 * @createDate: 2023/9/25
 */
public class Can139 implements CanParent {

    public static volatile String lastData = "";

    public static volatile float currentCarTemp = 0f;

    public static volatile String lastESPStatus = "";

    public static volatile long lastUpdateTempDate = 0L;

    private static final long UPDATE_INTERVAL = 2 * 60 * 1000;

    @Override
    public void handlerCan(List<String> msg) {

        //139899F5FF2400B30000
        String senddata = String.join("", msg);
        String temp1 = msg.get(7);

        //[139, 8, 56, F5, FF, 05, 0C, 90, 00, 00]
        if (!lastData.equals(senddata)) {
            LogUtils.printI(Can139.class.getSimpleName(), "handlerCan---msg=" + msg);
            lastData = senddata;

            String temp = msg.get(7);
            if ("ff".equals(temp)) {
                currentCarTemp = 0.0f;

//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_CAR_TEMP);
//                messageEvent.data = currentCarTemp+"℃";
//                EventBus.getDefault().post(messageEvent);
            } else {
                try {
                    disposeCarTempData(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            String espStatus = msg.get(6);
            LogUtils.printI(Can139.class.getSimpleName(), "espStatus=" + espStatus);

            if ("08".equals(espStatus)) { //按了一次ESP OFF开关
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.ESP_SWITCH);
                messageEvent.data = espStatus;
                EventBus.getDefault().post(messageEvent);
            }
//            if (!lastESPStatus.equals(espStatus)) { //按了一次ESP OFF开关
//                lastESPStatus = espStatus;
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.ESP_SWITCH);
//                messageEvent.data = espStatus;
//                EventBus.getDefault().post(messageEvent);
//            }

            String alert = msg.get(5);

//            alert = alert.substring(0,1) ;

//            if (alert.equals("1")){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW);
//                alertVo.setAlertMessage(AlertMessage.ALERT_139_10);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }

//            String alert1 = msg.get(5) ;
//            alert1 = alert1.substring(alert1.length()-1,alert1.length()) ;
//            if (alert1.equals("8")){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW);
//                alertVo.setAlertMessage(AlertMessage.ALERT_139_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (alert1.equals("2")){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW);
//                alertVo.setAlertMessage(AlertMessage.ALERT_139_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }

//            String alert2 = msg.get(6) ;
//
//            alert2 = alert2.substring(0,1) ;
//            if (alert2.equals("1")){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW);
//                alertVo.setAlertMessage(AlertMessage.ALERT_139_10_10);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (alert2.equals("2")){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW);
//                alertVo.setAlertMessage(AlertMessage.ALERT_139_20_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (alert2.equals("8")){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW);
//                alertVo.setAlertMessage(AlertMessage.ALERT_139_80_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }


            String door = msg.get(2);
//
            if (door.equals("55")) {
                FuncUtil.doorStatus = true;
                CommonUtil.meterHandler.get("door").handlerData("55");
            }
            if (door.equals("56")) {
                CommonUtil.meterHandler.get("door").handlerData("56");
            }
            if (door.equals("59")) {
                CommonUtil.meterHandler.get("door").handlerData("59");
            }
            if (door.equals("65")) {
                CommonUtil.meterHandler.get("door").handlerData("65");
            }
            if (door.equals("95")) {
                CommonUtil.meterHandler.get("door").handlerData("95");
            }


            if (door.equals("66")) {
                CommonUtil.meterHandler.get("door").handlerData("66");
            }

            if (door.equals("96")) {
                CommonUtil.meterHandler.get("door").handlerData("96");
            }

            if ("5A".equals(door.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("5A");
            }

            if ("A5".equals(door.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("A5");
            }

            if (door.equals("69")) {
                CommonUtil.meterHandler.get("door").handlerData("69");
            }
            if (door.equals("99")) {
                CommonUtil.meterHandler.get("door").handlerData("99");
            }
            if ("A9".equals(door.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("A9");
            }
            if ("9A".equals(door.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("9A");
            }
            if ("6A".equals(door.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("6A");
            }

            if ("A6".equals(door.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("A6");
            }

            if ("AA".equals(door.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("AA");
            }

            String ss = msg.get(3);

            if ("55".equals(ss.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("hq55");
            }
            if ("5A".equals(ss.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("hq5A");
            }
            if ("59".equals(ss.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("hq59");
            }
            if ("56".equals(ss.toUpperCase())) {
                CommonUtil.meterHandler.get("door").handlerData("hq56");
            }
//
        }

    }

    private void disposeCarTempData(String temp) {
        int carIntTemp;
        int carDecimalTemp;
        String tempIntHex = temp.substring(0, 1);
        String tempDecimalHex = temp.substring(1, 2);
        if ("A".equals(tempIntHex)) {
            carIntTemp = 10;
        } else if ("B".equals(tempIntHex)) {
            carIntTemp = 11;
        } else if ("C".equals(tempIntHex)) {
            carIntTemp = 12;
        } else if ("D".equals(tempIntHex)) {
            carIntTemp = 13;
        } else if ("E".equals(tempIntHex)) {
            carIntTemp = 14;
        } else if ("F".equals(tempIntHex)) {
            carIntTemp = 15;
        } else {
            carIntTemp = Integer.parseInt(tempIntHex);
        }

        if ("A".equals(tempDecimalHex)) {
            carDecimalTemp = 10;
        } else if ("B".equals(tempDecimalHex)) {
            carDecimalTemp = 11;
        } else if ("C".equals(tempDecimalHex)) {
            carDecimalTemp = 12;
        } else if ("D".equals(tempDecimalHex)) {
            carDecimalTemp = 13;
        } else if ("E".equals(tempDecimalHex)) {
            carDecimalTemp = 14;
        } else if ("F".equals(tempDecimalHex)) {
            carDecimalTemp = 15;
        } else {
            carDecimalTemp = Integer.parseInt(tempDecimalHex);
        }
        LogUtils.printI(Can139.class.getSimpleName(), "tempIntHex=" + tempIntHex + ", tempDecimalHex=" + tempDecimalHex + ", carIntTemp=" + carIntTemp + ", carDecimalTemp=" + carDecimalTemp);

        int carTemp = (int) (carIntTemp * 2 + BigDecimalUtils.div(String.valueOf(carDecimalTemp), "10", 1) * 2.5);
//                int carTemp = CommonUtil.getHexToDecimal(temp);


        if (carTemp > 42) {
            float tempFloat = carTemp * 0.5f - 40;
//                    float tempFloat = carTemp - 200;
            if (tempFloat < 16) {
                tempFloat = 16;
            }
            if (tempFloat > 42) {
                if (currentCarTemp <= 0.01f) {
                    currentCarTemp = 42;
                } else {
                    currentCarTemp++;
                }
                if (currentCarTemp > 42f) {
                    currentCarTemp = 42f;
                }
            } else {
                currentCarTemp = tempFloat;
            }
        } else {
            currentCarTemp = carTemp;
        }
        LogUtils.printI(Can139.class.getSimpleName(), "carTemp=" + carTemp + ", currentCarTemp=" + currentCarTemp);
    }

//    private void disposeDoorStatus(List<String> msg) {
//        String door = msg.get(2);
//
//        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_DOOR_HINT);
//        messageEvent.data = door;
//        EventBus.getDefault().post(messageEvent);
//
//        String ss = msg.get(3);
//
//        if ("55".equals(ss.toUpperCase())) {
//            messageEvent.data = "hq55";
//            EventBus.getDefault().post(messageEvent);
//        } else if ("5A".equals(ss.toUpperCase())) {
//            messageEvent.data = "hq5A";
//            EventBus.getDefault().post(messageEvent);
//        } else if ("59".equals(ss.toUpperCase())) {
//            messageEvent.data = "hq59";
//            EventBus.getDefault().post(messageEvent);
//        } else if ("56".equals(ss.toUpperCase())) {
//            CommonUtil.meterHandler.get("door").handlerData("hq56");
//            messageEvent.data = "hq59";
//            EventBus.getDefault().post(messageEvent);
//        }
//    }
}
