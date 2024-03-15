package com.android.launcher.cansender;

import com.android.launcher.App;
import com.android.launcher.can.Can45;
import com.android.launcher.cruisecontrol.CanSenderBase;
import com.android.launcher.entity.BinaryEntity;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

/**
 * @description:
 * @createDate: 2023/8/17
 */
public class Can045Sender extends CanSenderBase {


    private String dataLength = "08";

    private static final String ID = "00000045";

    private String d1 = "00";
    private String d2 = "00";
    private String d3 = "00";
    private String d4 = "00";
    private String d5 = "00";
    private String d6 = "00";
    private String d7 = "00";
    private String d8 = "00";


    @Override
    public void send() {

    }

    public void back() {
        try {


            // 02
            //0000 0010
            BinaryEntity binaryEntity = new BinaryEntity("00");
            binaryEntity.setB1(BinaryEntity.Value.NUM_1);
            d6 = binaryEntity.getHexData();
            if (d6.length() == 1) {
                d6 = "0" + d6;
            }
            String sendData = DATA_HEAD + dataLength + ID + "00" + "00" + "00" + "00" + "00" + d6 + "00" + "00";
            LogUtils.printI(Can045Sender.class.getSimpleName(), "back---sendData=" + sendData + ", length=" + sendData.length());
            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

//            Thread.sleep(200);
//
//            sendData = DATA_HEAD + dataLength + ID + "00" + "00" + "00" + "00" + "00" + "00" + "00" + "00";
//            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ok() {
        try {
            // 08
            //0000 1000
            BinaryEntity binaryEntity = new BinaryEntity("00");
            binaryEntity.setB3(BinaryEntity.Value.NUM_1);

            d6 = binaryEntity.getHexData();
            if (d6.length() == 1) {
                d6 = "0" + d6;
            }
            String sendData = DATA_HEAD + dataLength + ID + "00" + "00" + "00" + "00" + "00" + d6 + "00" + "00";
            LogUtils.printI(Can045Sender.class.getSimpleName(), "ok---sendData=" + sendData + ", length=" + sendData.length());
            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void right() {
        try {
            //0000 1000
            BinaryEntity binaryEntity = new BinaryEntity("00");
            binaryEntity.setB2(BinaryEntity.Value.NUM_1);

            d5 = binaryEntity.getHexData();
            if (d5.length() == 1) {
                d5 = "0" + d5;
            }
            String sendData = DATA_HEAD + dataLength + ID + "00000000" + d5 + "000000";
            LogUtils.printI(Can045Sender.class.getSimpleName(), "right---sendData=" + sendData + ", length=" + sendData.length());
            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        try {
            String sendData = DATA_HEAD + dataLength + ID + "0000000000000000";
            LogUtils.printI(Can045Sender.class.getSimpleName(), "reset---sendData=" + sendData + ", length=" + sendData.length());
            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void left() {
        try {

            BinaryEntity binaryEntity = new BinaryEntity("00");
            binaryEntity.setB3(BinaryEntity.Value.NUM_1);
            d5 = binaryEntity.getHexData();
            if (d5.length() == 1) {
                d5 = "0" + d5;
            }

            String sendData = DATA_HEAD + dataLength + ID + "00000000" + d5 + "000000";
            LogUtils.printI(Can045Sender.class.getSimpleName(), "left---sendData=" + sendData + ", length=" + sendData.length());
            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
