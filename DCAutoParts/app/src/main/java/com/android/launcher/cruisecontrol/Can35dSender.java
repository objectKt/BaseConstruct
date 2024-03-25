package com.android.launcher.cruisecontrol;

import android.text.TextUtils;

import com.android.launcher.App;
import com.android.launcher.can.status.Can35dStatus;
import com.android.launcher.entity.Can35dD4Entity;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

/**
 * @description: 车辆功能设置
 * //锁车是自动合拢开
 * 00 00 75 50 F4 E5
 * <p>
 * //后驶时后视镜下降开
 * 00 00 75 50 F1 E5
 * <p>
 * //锁车是自动合拢开， 后驶时后视镜下降开
 * 00 00 75 50 F5 E5
 * <p>
 * //锁车是自动合拢关， 后驶时后视镜下降关
 * <p>
 * D1 D2 D3 D4 D5 D6
 * 00 00 75 50 F0 E5
 * @createDate: 2023/6/1
 */
public class Can35dSender extends CanSenderBase {

    private String dataD5 = "D5";
    //内部照明
    private String dataD1 = Can35dStatus.D1.DELAY_60.getValue();
    //外部照明
    private String dataD2 = Can35dStatus.D2.DELAY_60.getValue();
    //环境照明
    private String dataD3 = Can35dStatus.D3.DELAY_5.getValue();

    private String dataRight = "E50000";

    private Can35dD4Entity can35dD4Entity = new Can35dD4Entity();

    private String dataLength = "06";
    private static final String ID = "0000035D";

    @Override
    public void send() {
        isRunnable = true;
        new Thread(() -> {
            setConfigData();

            while (isRunnable) {
                try {
                    if(!isPause){
                        String sendData = DATA_HEAD + dataLength + ID + dataD1 + dataD2 + dataD3 + can35dD4Entity.getHexData() + dataD5 + dataRight;
//                        LogUtils.printI(Can35dSender.class.getSimpleName(), "sendData=" + sendData + ", length=" + sendData.length());
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void setConfigData() {
        try {
            dataD1 = SPUtils.getString(App.getGlobalContext(), SPUtils.CAN35D_D1, Can35dStatus.D1.DELAY_60.getValue());
            if (TextUtils.isEmpty(dataD2) || dataD2.length() != 2) {
                dataD1 = Can35dStatus.D1.DELAY_60.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataD1 = Can35dStatus.D1.DELAY_60.getValue();
        }
        try {
            dataD2 = SPUtils.getString(App.getGlobalContext(), SPUtils.CAN35D_D2, Can35dStatus.D2.DELAY_60.getValue());
            if (TextUtils.isEmpty(dataD2) || dataD2.length() != 2) {
                dataD2 = Can35dStatus.D2.DELAY_60.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataD2 = Can35dStatus.D2.DELAY_60.getValue();
        }
        try {
            dataD3 = SPUtils.getString(App.getGlobalContext(), SPUtils.CAN35D_D3, Can35dStatus.D3.DELAY_5.getValue());
            if (TextUtils.isEmpty(dataD3) || dataD3.length() != 2) {
                dataD3 = Can35dStatus.D3.DELAY_5.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataD3 = Can35dStatus.D3.DELAY_5.getValue();
        }
        try {
            //D 表示后舱盖高度限制打开， 5：打成二进制后：0000 0101 锁车时自动合拢和后驶时后视镜下降打开
            dataD5 = SPUtils.getString(App.getGlobalContext(), SPUtils.CAN35D_D5, "D5");
            if (TextUtils.isEmpty(dataD5) || dataD5.length() != 2) {
                dataD5 = "D5";
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataD5 = "D5";
        }

        if (can35dD4Entity != null) {
            try {
                String assist = SPUtils.getString(App.getGlobalContext(), SPUtils.CAN35D_D4_ASSIST, Can35dD4Entity.AssistStatus.STEERING_COLUMN_AND_SEAT.getValue());
                if (assist.equals(Can35dD4Entity.AssistStatus.STEERING_COLUMN.getValue())) {
                    can35dD4Entity.setAssist(Can35dD4Entity.AssistStatus.STEERING_COLUMN);
                } else if (assist.equals(Can35dD4Entity.AssistStatus.STATUS_CLOSE.getValue())) {
                    can35dD4Entity.setAssist(Can35dD4Entity.AssistStatus.STATUS_CLOSE);
                } else {
                    can35dD4Entity.setAssist(Can35dD4Entity.AssistStatus.STEERING_COLUMN_AND_SEAT);
                }
            } catch (Exception e) {
                e.printStackTrace();
                can35dD4Entity.setAssist(Can35dD4Entity.AssistStatus.STEERING_COLUMN_AND_SEAT);
            }

            try {
                String autoLock = SPUtils.getString(App.getGlobalContext(), SPUtils.CAN35D_D4_AUTO_LOCK, Can35dD4Entity.SwitchStatus.STATUS_ON.getValue());
                if (autoLock.equals(Can35dD4Entity.SwitchStatus.STATUS_OFF.getValue())) {
                    can35dD4Entity.setAutoLock(Can35dD4Entity.SwitchStatus.STATUS_OFF);
                } else {
                    can35dD4Entity.setAutoLock(Can35dD4Entity.SwitchStatus.STATUS_ON);
                }
            } catch (Exception e) {
                e.printStackTrace();
                can35dD4Entity.setAutoLock(Can35dD4Entity.SwitchStatus.STATUS_ON);
            }

            try {
                String d4Lighting = SPUtils.getString(App.getGlobalContext(), SPUtils.CAN35D_D4_LIGHTING, Can35dD4Entity.SwitchStatus.STATUS_ON.getValue());
                if (d4Lighting.equals(Can35dD4Entity.SwitchStatus.STATUS_ON.getValue())) {
                    can35dD4Entity.setLighting(Can35dD4Entity.SwitchStatus.STATUS_ON);
                } else {
                    can35dD4Entity.setLighting(Can35dD4Entity.SwitchStatus.STATUS_OFF);
                }
            } catch (Exception e) {
                e.printStackTrace();
                can35dD4Entity.setLighting(Can35dD4Entity.SwitchStatus.STATUS_ON);
            }
        }
        LogUtils.printI(Can35dSender.class.getSimpleName(), "dataD1=" + dataD1 + ", dataD2=" + dataD2 + ", dataD3=" + dataD3 + ", can35dD4Entity=" + can35dD4Entity.getBinaryData() + ", dataD5=" + dataD5);
    }



    /**
     * @description: 自动落锁( 不是 35D控制)
     * @createDate: 2023/6/5
     */
//    public synchronized void setAutoLock(Can35dD4Entity.SwitchStatus switchStatus) {
//        LogUtils.printI(Can35dSender.class.getSimpleName(), "setAutoLock----SwitchStatus=" + switchStatus.name());
//        if (can35dD4Entity != null) {
//            can35dD4Entity.setAutoLock(switchStatus);
//        }
//        try {
//            SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D4_AUTO_LOCK, Character.toString(can35dD4Entity.getAutoLock()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * @description: 上下车自动辅助
     * @createDate: 2023/6/5
     */
    public synchronized void updateAssist(Can35dD4Entity.AssistStatus assistStatus) {
        LogUtils.printI(Can35dSender.class.getSimpleName(), "updateAssist----assistStatus=" + assistStatus.name());
        if (can35dD4Entity != null) {
            can35dD4Entity.setAssist(assistStatus);

            try {
                SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D4_ASSIST, can35dD4Entity.getAssist());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 设置背景照明开关状态
     * @createDate: 2023/6/6
     */
    public synchronized void setLighting(Can35dD4Entity.SwitchStatus switchStatus) {
        LogUtils.printI(Can35dSender.class.getSimpleName(), "setLighting----switchStatus=" + switchStatus.name());
        if (can35dD4Entity != null) {
            can35dD4Entity.setLighting(switchStatus);

            try {
                SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D4_LIGHTING, Character.toString(can35dD4Entity.getLighting()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @description: 设置内部照明延迟时间
     * @createDate: 2023/6/6
     */
    public synchronized void setInnerLighting(String status) {
        LogUtils.printI(Can35dSender.class.getSimpleName(), "setInnerLighting----status=" + status);
        if (status != null && status.length() != 2) {
            return;
        }
        dataD1 = status;
        try {
            SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D1, dataD1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @description: 设置外部照明延迟时间
     * @createDate: 2023/6/6
     */
    public synchronized void setOuterLighting(String status) {
        LogUtils.printI(Can35dSender.class.getSimpleName(), "setInnerLighting----status=" + status);
        if (status != null && status.length() != 2) {
            return;
        }
        dataD2 = status;
        try {
            SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D2, dataD2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @description: 设置环境照明
     * @createDate: 2023/6/6
     */
    public synchronized void setEnvirLighting(String status) {
        LogUtils.printI(Can35dSender.class.getSimpleName(), "setEnvirLighting----status=" + status);
        try {
            if (status != null && status.length() != 2) {
                return;
            }
            dataD3 = status;
            SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D3, dataD3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRearHatchCover(String status) {
        LogUtils.printI(Can35dSender.class.getSimpleName(), "setRearHatchCover----status=" + status);
        try {
            if (status != null && status.length() != 2) {
                return;
            }
            dataD3 = status;
            SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D3, dataD3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setD5(String can35dD5) {
        LogUtils.printI(Can35dSender.class.getSimpleName(), "setD5----can35dD5=" + can35dD5);
        try {
            if (can35dD5 != null && can35dD5.length() != 2) {
                return;
            }
            dataD5 = can35dD5;
            SPUtils.putString(App.getGlobalContext(), SPUtils.CAN35D_D5, dataD5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}