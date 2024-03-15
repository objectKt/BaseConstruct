package com.android.launcher.can;

import android.annotation.SuppressLint;

import java.util.List;

@Deprecated
//空调读取内容
public class Can20b implements CanParent {
    String can20b = "";

    //空调系统状态数据
    public static String AIR_SYS_STATE_VALUE_20B;

    private String lastData = "";

    @SuppressLint("NewApi")
    @Override
    public void handlerCan(List<String> msg) {

//        if (msg != null) {
//            LogUtils.printI(Can20b.class.getName(), msg.toString());
//
//            String senddata = String.join("", msg);
//            LogUtils.printI(Can20b.class.getName(), senddata);
//            //        if (!senddata.equals(can20b)){
////            SendToAssistant sendToAssistant = new SendToAssistant() ;
////            sendToAssistant.setId(21);
////            sendToAssistant.setC(senddata);
//
////            FuncUtil.serialHelper.send(FastJsonUtils.BeanToJson(sendToAssistant).getBytes());
//
//            can20b = senddata;
//            setStateValue(senddata);
//
//            if (lastData.equals(senddata)) {
//                return;
//            }
//            lastData = senddata;
//            String send = "AABB" + senddata + "CCDD";
//            SendHelperUsbToRight.handler(send.toUpperCase());
////        }
//        }


    }

    public static synchronized void setStateValue(String senddata) {
        if (!senddata.equals(AIR_SYS_STATE_VALUE_20B)) {
            AIR_SYS_STATE_VALUE_20B = senddata;
        }
    }

}
