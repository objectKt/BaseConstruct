package com.android.launcher.cansender;

import com.android.launcher.App;
import com.android.launcher.cruisecontrol.CanSenderBase;
import com.android.launcher.status.DriveModeStatus;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

/**
 * 00000001
 * @createDate: 2023/7/17
 */
public class CanFDSender extends CanSenderBase {

    private String dataLength = "04";

    private String d1 = "00";
    private String d2 = "00";
    private String d3 = "00";

    private static final String STATUS_DOWN = "01";
    private static final String STATUS_RESUME = "00";

    private static final String ID = "000000fd";

    public static volatile String currentDriveMode = DriveModeStatus.S.getValue();

    @Override
    public void send() {
        new Thread(() -> {
            try {

                String sendData = DATA_HEAD + dataLength + ID + d1 + d2 + d3 + STATUS_DOWN + "00000000";
                LogUtils.printI(CanFDSender.class.getSimpleName(), "sendData=" + sendData + ", length=" + sendData.length());
                if(sendData.length() == 32){
                    MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void resume() {
        try {
            int count = SPUtils.getInt(App.getGlobalContext(), SPUtils.SP_HEADREST_DOWN_COUNT, 0);
            LogUtils.printI(CanFDSender.class.getSimpleName(), "resume-----count=" + count);
            new Thread(() -> {
                try {
                    for (int i = 0; i < count; i++) {
                        String sendData = DATA_HEAD + dataLength + ID + d1 + d2 + d3  + STATUS_RESUME + "00000000";
                        LogUtils.printI(CanFDSender.class.getSimpleName(), "sendData=" + sendData + ", length=" + sendData.length());
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                        Thread.sleep(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置驾驶模式
     * 切换顺序  CMSCMS
     *
     * @date： 2023/12/26
     * @author: 78495
     */
    public void setDriveMode(final String driveMode) {
        new Thread(() -> {
            try {
                LogUtils.printI(CanFDSender.class.getSimpleName(), "setDriveMode----driveMode=" + driveMode + ", currentDriveMode=" + currentDriveMode);
                if (currentDriveMode.equals(DriveModeStatus.S.getValue())) {
                    if (driveMode.equals(DriveModeStatus.C.getValue())) {
                        //S切换到C档
                        changeDriveMode();
                    } else if (driveMode.equals(DriveModeStatus.M.getValue())) {
                        //S切换到M档
                        changeDriveMode();
                        Thread.sleep(1200);
                        changeDriveMode();
                    }
                } else if (currentDriveMode.equals(DriveModeStatus.C.getValue())) {
                    if (driveMode.equals(DriveModeStatus.S.getValue())) {
                        //C切换到S档
                        changeDriveMode();
                        Thread.sleep(1200);
                        changeDriveMode();
                    } else if (driveMode.equals(DriveModeStatus.M.getValue())) {
                        //C切换到M档
                        changeDriveMode();
                    }
                } else if (currentDriveMode.equals(DriveModeStatus.M.getValue())) {
                    if (driveMode.equals(DriveModeStatus.S.getValue())) {
                        //M切换到S档
                        changeDriveMode();
                    } else if (driveMode.equals(DriveModeStatus.C.getValue())) {
                        //M切换到C档
                        changeDriveMode();
                        Thread.sleep(1200);
                        changeDriveMode();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //执行一次换一个驾驶模式
    private void changeDriveMode() throws InterruptedException {

        String sendData = DATA_HEAD + dataLength + ID + d1 + d2 + d3 + "00" + "00000000";
        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
        Thread.sleep(200);
        sendData = DATA_HEAD + dataLength + ID + d1 + d2 + d3 + "80" + "00000000";
        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
    }

    //座椅功能调节
    public void setSeatAdjustment(Boolean isOpen) {
        new Thread(() -> {
            try {
                String sendData = DATA_HEAD + dataLength + ID + "00" + d2 + d3 + "00" + "00000000";
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                Thread.sleep(200);
                LogUtils.printI(CanFDSender.class.getSimpleName(),"setSeatAdjustment----");
                 sendData = DATA_HEAD + dataLength + ID + "04" + d2 + d3 + "00" + "00000000";
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //驾驶室管理和数据系统, 按一下发10
    public void setONKey(Boolean isOpen) {
        new Thread(() -> {
            try {
                LogUtils.printI(CanFDSender.class.getSimpleName(),"setSeatAdjustment----");
                String sendData = DATA_HEAD + dataLength + ID + "00" + d2 + d3 + "00" + "00000000";
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                Thread.sleep(200);
                sendData = DATA_HEAD + dataLength + ID + "10" + d2 + d3 + "00" + "00000000";
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
