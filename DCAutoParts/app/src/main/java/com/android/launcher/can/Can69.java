package com.android.launcher.can;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.launcher.App;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import java.util.List;

// 大灯状态
public class Can69 implements CanParent {

    public static String lampContent="" ;

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String senddata = String.join("", msg);
        if (!lastData.equals(senddata)) {
            lastData = senddata;
            
            String lampStatus =  msg.get(2);
            String lampOnOrOff = msg.get(3) ;
            LogUtils.printI(Can69.class.getSimpleName(), "handlerCan---msg="+msg +", lampStatus="+lampStatus +", lampOnOrOff="+lampOnOrOff);

            String send ="AABB"+senddata+"CCDD" ;
            SendHelperUsbToRight.handler(send);

            // 判断是否开启 如果是没有开启则将所有的灯都关闭
            if(lampOnOrOff.equals("01")){
                //所有的灯关闭
                CommonUtil.meterHandler.get("lampStatus").handlerData("off");
                lampContent = "" ;
                FuncUtil.saveBrightness(App.getGlobalContext(),255);
            }else{
                String binaryLampStatus = CommonUtil.convertHexToBinary(lampStatus) ;
                LogUtils.printI(Can69.class.getSimpleName(), "binaryLampStatus="+binaryLampStatus) ;
                if (!binaryLampStatus.equals(lampContent)){

                    if("01000100".equals(binaryLampStatus)){
                        //示廓灯
                        CommonUtil.meterHandler.get("lampStatus").handlerData("width");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=false;
                        startcheckyuanguang();
                    }
                    if("00000000".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("off");
                        FuncUtil.saveBrightness(App.getGlobalContext(),255);
                        FuncUtil.jinguang=false;
                        startcheckyuanguang();
                    }
                    //示宽灯开启
                    if("00000100".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("width");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=false;
                        startcheckyuanguang();
                    }

                    //示宽灯+雾前
                    if("00010100".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("widthfogf");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=false;
                        startcheckyuanguang();
                    }
                    //示宽灯+雾后
                    if("00110100".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("widthfogb");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=false;
                        startcheckyuanguang();
                    }
                    //近光灯亮起+示宽灯
                    if("00001100".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("lowwidth");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=true ;
                        startcheckyuanguang();
                    }
                    //近光+雾1+示宽灯
                    if("00011100".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("lowwidthfogf");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=true;
                        startcheckyuanguang();
                    }
                    //近光+雾2+示宽灯
                    if("00111100".equals(binaryLampStatus)||"00101100".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("lowwidthfogb");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=true;
                        startcheckyuanguang();
                    }

                    //近光灯亮起
                    if("00001000".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("low");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=true;
                        startcheckyuanguang();
                    }
                    //近光+雾1
                    if("00011000".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("lowfogf");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=true;
                        startcheckyuanguang();
                    }
                    //近光+雾2
                    if("00111000".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("lowfogb");

                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=true;
                        startcheckyuanguang();
                    }

                    //auto
                    if("01001100".equals(binaryLampStatus)){
                        CommonUtil.meterHandler.get("lampStatus").handlerData("auto");
                        FuncUtil.saveBrightness(App.getGlobalContext(),15);
                        FuncUtil.jinguang=true;
                        startcheckyuanguang();
                    }
                }
                lampContent = binaryLampStatus ;
            }
        }
    }

    private void startcheckyuanguang() {
        CommonUtil.meterHandler.get("yuanguangzhishi").handlerData(FuncUtil.yuanguang);
    }
}
