package com.android.launcher.can;

import android.annotation.SuppressLint;

import com.android.launcher.App;
import dc.library.utils.event.MessageEvent;
import com.android.launcher.cansender.Can045Sender;
import com.android.launcher.cansender.CanFDSender;
import com.android.launcher.service.LivingService;
import dc.library.utils.global.status.DriveModeStatus;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @date： 2023/12/26
 * @author: 78495
*/
public class Can3e1 implements  CanParent{

    public static volatile String currentDriveModeStatus = "";
    public static volatile String lastD2 = "";

    private static final String MODE_S = "5555";
    private static final String MODE_C = "0000";
    private static final String MODE_M = "AAAA";

    private Can045Sender can045Sender = new Can045Sender();

    //正在关闭日行灯
   public static  volatile boolean isCloseDayRunLight = false;

    @SuppressLint("NewApi")
    @Override
    public void handlerCan(List<String> msg) {
        try {
            String driveModeStatus = msg.get(8) + msg.get(9);

            String dayRunLightStatus = msg.get(2);
            if("DC".equalsIgnoreCase(dayRunLightStatus)){
                if(!lastD2.equalsIgnoreCase(dayRunLightStatus)){
                    lastD2 = msg.get(2);
                    if(!LivingService.enableOpenDayRunLight){
                        // 日行灯打开
                        executeCloseTask();
                    }

                }
            }else{
                lastD2 = "";
            }

            if(!currentDriveModeStatus.equals(driveModeStatus)){
                String data = String.join("", msg);
                LogUtils.printI(Can3e1.class.getSimpleName(), "msg="+msg +", driveModeStatus="+driveModeStatus);
                currentDriveModeStatus = driveModeStatus;


                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_DRIVE_MODE_VIEW);
                if(MODE_S.equals(driveModeStatus)){
                    CanFDSender.currentDriveMode = DriveModeStatus.S.getValue();
                }else if(MODE_C.equals(driveModeStatus)){
                    CanFDSender.currentDriveMode = DriveModeStatus.C.getValue();
                }else if(MODE_M.equals(driveModeStatus)){
                    CanFDSender.currentDriveMode = DriveModeStatus.M.getValue();
                }else{
                    CanFDSender.currentDriveMode = DriveModeStatus.S.getValue();
                }

                ACache.get(App.getGlobalContext()).put("CMS",CanFDSender.currentDriveMode);
                messageEvent.data = CanFDSender.currentDriveMode;
                EventBus.getDefault().post(messageEvent);
                String send = "AABB" + data + "CCDD";
                SendHelperUsbToRight.handler(send);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //执行关闭日行灯的任务
    private void executeCloseTask() {
        new Thread(() -> {
            try {
                isCloseDayRunLight = true;
                Thread.sleep(2000);
                for (int i=0; i<12; i++){

                    can045Sender.reset();
                    Thread.sleep(100);
                    for (int j=0; j<15; j++){
                        Thread.sleep(5);
                        can045Sender.back();
                    }
                    Thread.sleep(100);
                    can045Sender.reset();
                    Thread.sleep(500);
                }

                can045Sender.reset();
                Thread.sleep(100);
                for (int j=0; j<15; j++){
                    Thread.sleep(5);
                    can045Sender.left();
                }
                Thread.sleep(100);
                can045Sender.reset();
                Thread.sleep(500);

                for (int i=0; i<2; i++) {
                    can045Sender.reset();
                    Thread.sleep(100);
                    for (int j=0; j<15; j++){
                        Thread.sleep(5);
                        can045Sender.ok();
                    }
                    Thread.sleep(100);
                    can045Sender.reset();
                    Thread.sleep(500);
                }


                for (int i=0; i<4; i++){
                    can045Sender.reset();
                    Thread.sleep(100);
                    for (int j=0; j<15; j++){
                        Thread.sleep(5);
                        can045Sender.back();
                    }
                    Thread.sleep(100);
                    can045Sender.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                isCloseDayRunLight = false;
            }
        }).start();
    }
}
