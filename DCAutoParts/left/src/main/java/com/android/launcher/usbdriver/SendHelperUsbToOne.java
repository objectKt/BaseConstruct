package com.android.launcher.usbdriver;

import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.HandlerRequest;
@Deprecated
public class SendHelperUsbToOne {

    public HandlerRequest handlerRequest = new HandlerRequest() ;
    public SendHelperUsbToOne(){
        sendControlThread();
    }


    // 控制输出
    private void sendControlThread() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!MUsb1Receiver.mreadFlg){
//                    try {
//                        Thread.sleep(40);
//                        byte[] bytes  = handlerRequest.sendEvent() ;
//                        MUsb1Receiver.write(bytes);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }


    public  void handler( byte[] bytes){
        try {
            if (FuncUtil.usbStatus){
                handlerRequest.buildEvent(bytes);
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }



}
