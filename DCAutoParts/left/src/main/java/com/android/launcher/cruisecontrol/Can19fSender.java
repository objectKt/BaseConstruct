package com.android.launcher.cruisecontrol;

import com.android.launcher.App;
import com.android.launcher.entity.Can35dD4Entity;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.type.UnitType;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.UnitUtils;

/**
* @description:
* @createDate: 2023/5/30
*/
public class Can19fSender extends CanSenderBase {

    private String dataLeft = "000000F0";
    private String dataRight = "000000";

    private String startData = "00";
    private String currentData = startData;

    private String dataLength = "08";
    private static final String ID = "0000019f";

    private long lastTime;

    //每15s数据加1
    private static final long INTERVAL_TIME = 15 * 1000;

    private Can35dD4Entity.SwitchStatus switchStatus = Can35dD4Entity.SwitchStatus.STATUS_ON;

    public static ACache aCache = ACache.get(App.getGlobalContext()) ;

    //是否设置了自动落锁
    public static boolean isSetAutoClose = false;

    @Override
    public void send() {
        isRunnable = true;
        isSetAutoClose = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                lastTime = System.currentTimeMillis();
                while (isRunnable){
                    try {
                        if(!isPause){
                            String sendData = cms19f();

//                        String sendData = DATA_HEAD + dataLength + ID +dataLeft + currentData + dataRight;
//                                LogUtils.printI(Can19fSender.class.getSimpleName(), "sendData="+sendData +", length="+sendData.length());
                            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            long currentTimeMillis = System.currentTimeMillis();
                            long interval = currentTimeMillis - lastTime;
                            if(interval >= INTERVAL_TIME){
                                lastTime = currentTimeMillis;
                                if("ff".equalsIgnoreCase(currentData)){
                                    currentData = startData;
                                }else{
                                    int number = Integer.parseInt(currentData, 16);
                                    number += 1;
                                    currentData = Integer.toHexString(number);
                                    if(currentData.length() <2){
                                        currentData = "0"+currentData;
                                    }
                                }
                            }
                        }else{
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        isSetAutoClose = false;
                    }
                }
            }
        }).start();
    }


    /**
     * @description: 自动落锁( 不是 35D控制)
     * @createDate: 2023/6/5
     */
    public synchronized void setAutoLock(Can35dD4Entity.SwitchStatus switchStatus) {
        this.switchStatus = switchStatus;
    }

    public  String cms19f() {

        //大于等于AA会自动落锁
        String    autoClose = "00AA";

        int speed = 0;
        try {
            speed = Integer.parseInt(App.carSpeed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int autoCLoseSpeed = 20;
        if(MeterActivity.unitType == UnitType.MI.ordinal()){
            autoCLoseSpeed = (int) UnitUtils.kmToMi(20);
        }

        if(switchStatus == Can35dD4Entity.SwitchStatus.STATUS_OFF){
            autoClose = "0000";
        }else if(speed < autoCLoseSpeed){
            autoClose = "0000";
        }
//        else if(isSetAutoClose){
//            autoClose = "0000";
//        }else{
//            isSetAutoClose = true;
//        }
//        LogUtils.printI(Can19fSender.class.getSimpleName(), "speed="+speed +", switchStatus="+switchStatus);

        String send19f = "" ;
        String canId3 = "AA0000080000019F" ;
        String send19fs = autoClose + "9010001765D8" ;
        String send19fc = autoClose + "9010001765D8" ;
        String send19fm = autoClose + "9010001765D8" ;


        String  currentCms=  aCache.getAsString("CMS");
        String  lightStatus = aCache.getAsString("lightStatus");

        if(lightStatus==null){
            lightStatus= "off" ;
        }
        if(currentCms==null){
            aCache.put("CMS","S");
            currentCms = "S" ;
        }
        if (currentCms.equals("S")){
//            send3e1=lightStatus.equals("on")? send3e1s.replaceFirst("C","D") :send3e1s ;
////            send74e=send74es;
            send19f=send19fs;
        }
        if (currentCms.equals("M")){
//            send3e1=lightStatus.equals("on")? send3e1m.replaceFirst("C","D") :send3e1m ;
////            send74e=send74em;
            send19f=send19fm;
        }

        if (currentCms.equals("C")){
//            send3e1=lightStatus.equals("on")? send3e1c.replaceFirst("C","D") :send3e1c  ;
////            send74e=send74ec;
            send19f=send19fc;
        }

        return canId3+send19f ;
    }


}
