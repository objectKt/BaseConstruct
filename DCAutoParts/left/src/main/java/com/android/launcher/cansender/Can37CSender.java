package com.android.launcher.cansender;

import com.android.launcher.cruisecontrol.CanSenderBase;
import dc.library.utils.global.type.GearsType;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;

/**
 * @description:
 *  37C D档位切换到p档位 发送  两条命令相隔 100 毫秒
 *
 *    03 20 11 06 00 C4 B7 B4
 * 	 05 20 15 01 00 C3 B7 B4
 * 	 切换到D档位发送
 * 	 03 20 10 06 00 C3 B7 B4
 * 	 05 20 14 01 00 C4 B7 B4
 *  其他档位切换不发送数据
 * @createDate: 2023/7/18
 */
public class Can37CSender extends CanSenderBase {

    private GearsType lastType = GearsType.TYPE_P;

    private String dataLength = "08";

    private static final String ID = "0000037C";

    @Override
    public void send() {
    }

    private void sendDData() {
        new Thread(() -> {
            try {
                String sendData = DATA_HEAD + dataLength + ID + "0320100600C3B7B4";
                LogUtils.printI(Can37CSender.class.getSimpleName(), "sendDData----sendData="+sendData +", lenth="+sendData.length());
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                Thread.sleep(100);
                sendData = DATA_HEAD + dataLength + ID + "0520140100C4B7B4";
                LogUtils.printI(Can37CSender.class.getSimpleName(), "sendDData----sendData="+sendData +", lenth="+sendData.length());
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendPData() {
        new Thread(() -> {
            try {
                String sendData = DATA_HEAD + dataLength + ID + "0320110600C4B7B4";
                LogUtils.printI(Can37CSender.class.getSimpleName(), "sendPData----sendData="+sendData +", lenth="+sendData.length());
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                Thread.sleep(100);
                sendData = DATA_HEAD + dataLength + ID + "0520150100C3B7B4";
                LogUtils.printI(Can37CSender.class.getSimpleName(), "sendPData----sendData="+sendData +", lenth="+sendData.length());
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setGears(GearsType type) {
//        if(type == GearsType.TYPE_P && lastType == GearsType.TYPE_D){
//            //D档位切换到p档位
//            sendPData();
//        }else if(type == GearsType.TYPE_D && lastType == GearsType.TYPE_P){
//            //p档位切换到D档位
//            sendDData();
//        }
//        lastType = type;
    }
}
