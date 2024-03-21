package com.android.launcher.can;

import android.text.TextUtils;

import dc.library.utils.event.MessageEvent;
import dc.library.utils.global.status.Can45OnStatus;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 调大灯 左右转向灯  方向盘按键
 */
public class Can45 implements CanParent {

    private static final String TAG = "Can45";

    //操作的时间间隔
    private static final int INTERVAL = 2000;

    //静音键上次操作的时间，因为一秒内会返回多次
    private static long silenceLastTime = 0L;

    public int i;

    public  static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        try {
            //[45, 8, 40, 00, 01, 00, 06, 0A, 30, 33]
            //	      D0  D1  D2  D3  D4  D5  D6  D7

            String sendData = String.join("", msg);
            sendData = sendData.substring(0, sendData.length() - 4);
            if (!lastData.equals(sendData)) {
                lastData = sendData;

                String dat = sendData.substring(3);
                LogUtils.printI(TAG, "handlerCan--" + msg.toString() + "----dat=" + dat);

                if (!TextUtils.isEmpty(dat) && dat.length() >= 12) {
                    //处理D2数据
                    disposeD2(dat.substring(4, 6));
                    //处理D3数据
                    disposeD3(dat.substring(6, 8));
                    //处理D4数据
                    disposeD4(dat.substring(8, 10));
                    //处理D5数据
                    disposeD5(dat.substring(10));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

////            if (candata.containsKey(dat)){
//            String deng = msg.get(4);
//            String dengbinary = CommonUtil.convertHexToBinary(deng);
////            Log.i("zuoyou","------------------"+msg+"========="+dengbinary) ;
//            dengbinary = dengbinary.substring(4, dengbinary.length());
//            String bstring[] = dengbinary.split("");
//
//            Log.i("zhuanxiang", "========" + Arrays.toString(bstring) + "输出的次数+++++=====" + (i++));
//
//            if (bstring[1].equals("0")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("tiaodadeng", "00"));
//                CommonUtil.meterHandler.get("tiaodadeng").handlerData("00");
//            } else if (bstring[1].equals("1")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("tiaodadeng", "01"));
//                CommonUtil.meterHandler.get("tiaodadeng").handlerData("01");
//            }
//
//            if (bstring[2].equals("0")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("yuanguangzhishi", "00"));
//                CommonUtil.meterHandler.get("yuanguangzhishi").handlerData("00");
//                FuncUtil.yuanguang = "00";
//            } else if (bstring[2].equals("1")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("yuanguangzhishi", "01"));
//                FuncUtil.yuanguang = "01";
//                CommonUtil.meterHandler.get("yuanguangzhishi").handlerData("01");
//            }
//
//            if (bstring[3].equals("0")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("youzhuanxiang", "00"));
//                CommonUtil.meterHandler.get("youzhuanxiang").handlerData("00");
//            } else if (bstring[3].equals("1")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("youzhuanxiang", "01"));
//                CommonUtil.meterHandler.get("youzhuanxiang").handlerData("01");
//            }
////
//            if (bstring[4].equals("0")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("zuozhuanxiang", "00"));
//                CommonUtil.meterHandler.get("zuozhuanxiang").handlerData("00");
//            } else if (bstring[4].equals("1")) {
////            EventBusMeter.getInstance().postSticky(new MessageWrap("zuozhuanxiang", "01"));
//                CommonUtil.meterHandler.get("zuozhuanxiang").handlerData("01");
//            }


//            //左
//            if(deng.equals("00000001")){
////                EventBusMeter.getInstance().postSticky(new MessageWrap("lamp", "01" ));
//                CommonUtil.meterHandler.get("lamp").handlerData("01");
//            }
//            //右
//            if(deng.equals("00000010")){
////                EventBusMeter.getInstance().postSticky(new MessageWrap("lamp", "02" ));
//                CommonUtil.meterHandler.get("lamp").handlerData("02");
//
//            }
//            //远光灯
//            if(deng.equals("00000100")){
////                EventBusMeter.getInstance().postSticky(new MessageWrap("lamp", "04" ));
//                CommonUtil.meterHandler.get("lamp").handlerData("04");
//            }
//            //远光灯+左转
//            if(deng.equals("00000101")){
////                EventBusMeter.getInstance().postSticky(new MessageWrap("lamp", "04" ));
//                CommonUtil.meterHandler.get("lamp").handlerData("04");
//            }
//            //远光灯+右转
//            if(deng.equals("00000101")){
////                EventBusMeter.getInstance().postSticky(new MessageWrap("lamp", "04" ));
//                CommonUtil.meterHandler.get("lamp").handlerData("04");
//            }

//            StringBuffer sb = new StringBuffer();
//            for (int i = 6; i < 8; i++) {
//                sb.append(CommonUtil.convertHexToBinary(msg.get(i)));
//            }
//            List<String> lamps = CommonUtil.getLampResult(sb.toString());
//            for (String lamp : lamps) {
//            EventBusMeter.getInstance().postSticky(new MessageWrap("lamp", lamp));
        //方向盘按键控制
//                CommonUtil.meterHandler.get("lamp").handlerData(lamp);
//            }
//        }
    }

    private void disposeD5(String d5) {
        String d5Binary = convertHexToBinary(d5);
        LogUtils.printI(TAG, "disposeD5----D5=" + d5 + ", d5Binary=" + d5Binary);
        if (d5Binary.length() == 8) {


            int helpIndex = 7;
            int backIndex = 6; //返回
            int silenceIndex = 5; //静音
            int okIndex = 4; //确认
            String help = d5Binary.substring(helpIndex);
            String back = d5Binary.substring(backIndex, backIndex + 1);
            String silence = d5Binary.substring(silenceIndex, silenceIndex + 1);
            String ok = d5Binary.substring(okIndex, okIndex + 1);
            LogUtils.printI(TAG, "disposeD5----help=" + help + ", back=" + back + ", silence=" + silence + ", ok=" + ok);


            if (Can45OnStatus.STATE_ON.getValue().equals(ok)) {
                CommonUtil.meterHandler.get("lamp").handlerData("9");
            }

            if (Can45OnStatus.STATE_ON.getValue().equals(back)) {
                CommonUtil.meterHandler.get("lamp").handlerData("10");
            }

            if (Can45OnStatus.STATE_ON.getValue().equals(help)) {

            }

            long currentTimeMillis = System.currentTimeMillis();
            if (silenceLastTime == 0L) {
                silenceLastTime = currentTimeMillis;
            }
            long intervalTime = currentTimeMillis - silenceLastTime;
            if (intervalTime <= INTERVAL) {
                return;
            }
            silenceLastTime = currentTimeMillis;
            if (Can45OnStatus.STATE_ON.getValue().equals(silence)) {
                CommonUtil.meterHandler.get("lamp").handlerData("15");
            }
        }
    }

    private void disposeD4(String d4) {
        String d4Binary = convertHexToBinary(d4);
        LogUtils.printI(TAG, "disposeD4----D4=" + d4 + ", d4Binary=" + d4Binary);
        if (d4Binary.length() == 8) {
            int upIndex = 7;
            int downIndex = 6;
            int rightIndex = 5;
            int leftIndex = 4;
            int audioAddIndex = 3; //音量+
            int audioSubtractIndex = 2; //音量-
            int answerCallsIndex = 1; //接电话
            int hangUpIndex = 0; //挂电话
            String up = d4Binary.substring(upIndex);
            String down = d4Binary.substring(downIndex, downIndex + 1);
            String right = d4Binary.substring(rightIndex, rightIndex + 1);
            String left = d4Binary.substring(leftIndex, leftIndex + 1);
            String audioAdd = d4Binary.substring(audioAddIndex, audioAddIndex + 1);
            String audioSubtract = d4Binary.substring(audioSubtractIndex, audioSubtractIndex + 1);
            String answerCalls = d4Binary.substring(answerCallsIndex, answerCallsIndex + 1);
            String hangUp = d4Binary.substring(hangUpIndex, hangUpIndex + 1);

            LogUtils.printI(TAG, "disposeD4----map_up=" + up + ", down=" + down + ", right=" + right + ", left=" + left);

            if (Can45OnStatus.STATE_ON.getValue().equals(up)) {
                CommonUtil.meterHandler.get("lamp").handlerData("5");
            }

            if (Can45OnStatus.STATE_ON.getValue().equals(down)) {
                CommonUtil.meterHandler.get("lamp").handlerData("6");
            }

            if (Can45OnStatus.STATE_ON.getValue().equals(right)) {
                CommonUtil.meterHandler.get("lamp").handlerData("8");
            }

            if (Can45OnStatus.STATE_ON.getValue().equals(left)) {
                CommonUtil.meterHandler.get("lamp").handlerData("7");
            }
            if (Can45OnStatus.STATE_ON.getValue().equals(audioAdd)) {
                CommonUtil.meterHandler.get("lamp").handlerData("11");
            }
            if (Can45OnStatus.STATE_ON.getValue().equals(audioSubtract)) {
                CommonUtil.meterHandler.get("lamp").handlerData("12");
            }

            if(Can45OnStatus.STATE_ON.getValue().equals(answerCalls)){ //接电话
                SendHelperUsbToRight.handler("AABB2500CCDD");
            }
            if(Can45OnStatus.STATE_ON.getValue().equals(hangUp)){ //挂电话
                SendHelperUsbToRight.handler("AABB2600CCDD");
            }
        }
    }

    /**
     * @description:
     * @createDate: 2023/5/5
     */
    private void disposeD3(String d3) {
        String d3Binary = convertHexToBinary(d3);
        LogUtils.printI(TAG, "disposeD3----D3=" + d3 + ", d3Binary=" + d3Binary);
        if (d3Binary.length() == 8) {
            int bugleIndex = 1;
            String bugle = d3Binary.substring(bugleIndex, bugleIndex + 1);
            LogUtils.printI(TAG, "disposeD2----bugle=" + bugle);
            if (Can45OnStatus.STATE_OFF.getValue().equals(bugle)) {

            } else {

            }
        }
    }

    /**
     * @description:
     * @createDate: 2023/5/5
     */
    private void disposeD2(String d2) {
        String d2Binary = convertHexToBinary(d2);
        LogUtils.printI(TAG, "disposeD2----D2=" + d2 + ", d2Binary=" + d2Binary);
        if (d2Binary.length() == 8) {

            int leftTurnSignalIndex = 7; //左转向
            int rightTurnSignalIndex = 6; //右转向
            int bigLightIndex = 5; //大灯
            int beanLightIndex = 4;  //远光灯
            String leftTurnSignal = d2Binary.substring(leftTurnSignalIndex);
            String rightTurnSignal = d2Binary.substring(rightTurnSignalIndex, rightTurnSignalIndex + 1);
            String beanLight = d2Binary.substring(beanLightIndex, beanLightIndex + 1);
            String bigLight = d2Binary.substring(bigLightIndex, bigLightIndex + 1);

            LogUtils.printI(TAG, "disposeD2----leftTurnSignal=" + leftTurnSignal + ", rightTurnSignal=" + rightTurnSignal + ", beanLight=" + beanLight + ", bigLight=" + bigLight);

//            if (Can45OnStatus.STATE_OFF.getValue().equals(leftTurnSignal)) { //关
////                if(!SoundPlayer.doubleFlashPlay) {
//                    CommonUtil.meterHandler.get("zuozhuanxiang").handlerData("00");
////                }
//            } else { //开
////                if(!SoundPlayer.doubleFlashPlay) {
//                    CommonUtil.meterHandler.get("zuozhuanxiang").handlerData("01");
////                }
//            }
//
//            if (Can45OnStatus.STATE_OFF.getValue().equals(rightTurnSignal)) {
////                if(!SoundPlayer.doubleFlashPlay) {
//                    CommonUtil.meterHandler.get("youzhuanxiang").handlerData("00");
////                }
//            } else {
////                if(!SoundPlayer.doubleFlashPlay){
//                    CommonUtil.meterHandler.get("youzhuanxiang").handlerData("01");
////                }
//            }

            if (Can45OnStatus.STATE_OFF.getValue().equals(beanLight)) {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_HIGH_BEAM);
                messageEvent.data = false;
                EventBus.getDefault().post(messageEvent);
            } else {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_HIGH_BEAM);
                messageEvent.data = true;
                EventBus.getDefault().post(messageEvent);
            }

            if (Can45OnStatus.STATE_OFF.getValue().equals(bigLight)) {
                CommonUtil.meterHandler.get("tiaodadeng").handlerData("00");
            } else {
                CommonUtil.meterHandler.get("tiaodadeng").handlerData("01");
            }

        }
    }


    /**
     * 16进制字符串转二进制字符串
     *
     * @param hexString
     * @return
     */
    public static String convertHexToBinary(String hexString){
        long l = Long.parseLong(hexString, 16);
        String binaryString = Long.toBinaryString(l);
        int shouldBinaryLen = hexString.length()*4;
        StringBuffer addZero = new StringBuffer();
        int addZeroNum = shouldBinaryLen-binaryString.length();
        for(int i=1;i<=addZeroNum;i++){
            addZero.append("0");
        }
        return addZero.toString()+binaryString;
    }

}
