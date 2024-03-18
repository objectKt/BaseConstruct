package com.android.launcher.can;

import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import java.util.List;

/**
 * 空调控制前后
 */
@Deprecated
public class Can1E5 implements CanParent {

    public static final String COMMAND_1E5_STAT = "AA00000800000";

    public static List<String> E5;

    //空调系统状态数据
    public static String AIR_SYS_STATE_VALUE;

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

//        Log.i("1E5",msg.toString()+"-----------") ;

        //发送空调当前控制状态
//        try {
//            String senddata = String.join("", msg);
//            String status = msg.get(7);
//            String airflowPattern = msg.get(8);
//            String audioModel = msg.get(9);
//            String data = status + airflowPattern + audioModel;
//            if (!lastData.equals(data)) {
//                LogUtils.printI(Can1E5.class.getSimpleName(), "handlerCan----msg=" + msg + ", data=" + data + " ,lastData=" + lastData);
//                String send = "AABB" + senddata + "CCDD";
//                LogUtils.printI(Can1E5.class.getSimpleName(), "handlerCan----send=" + send);
//                SendHelperUsbToRight.handler(send.toUpperCase());
//                lastData = data;
//
//            }
//
//
//            setStateValue(senddata);
//
//            E5 = msg;
//            if (msg.get(msg.size() - 1).equals("DF")) {
//                String listMsg = String.join("", msg);
//                String send1e5 = "AA000008000001E5" + listMsg.substring(4, listMsg.length() - 2) + "CF";
//
//                LogUtils.printI(Can1E5.class.getSimpleName(), "handlerCan----send1e5=" + send1e5);
//                final byte[] bytes = FuncUtil.toByteArray(send1e5.toUpperCase());
//                MUsb1Receiver.write(bytes);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static synchronized void setStateValue(String senddata) {
        if (!senddata.equals(AIR_SYS_STATE_VALUE)) {
            AIR_SYS_STATE_VALUE = senddata;
        }
    }

}


