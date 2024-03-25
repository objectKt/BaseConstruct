package com.android.launcher.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.TextView;

import com.android.launcher.App;
import com.android.launcher.R;
import com.android.launcher.dao.ParamDaoUtil;
import com.android.launcher.entity.Param;
import com.android.launcher.handler.HandlerAbsflg;
import com.android.launcher.handler.HandlerAlert;
import com.android.launcher.handler.HandlerAn;
import com.android.launcher.handler.HandlerBrake;
import com.android.launcher.handler.HandlerCMS;
import com.android.launcher.handler.HandlerCall;
import com.android.launcher.handler.HandlerCarSteady;
import com.android.launcher.handler.HandlerCarTemp;
import com.android.launcher.handler.HandlerDegree;
import com.android.launcher.handler.HandlerDoor;
import com.android.launcher.handler.HandlerInteface;
import com.android.launcher.handler.HandlerLamp;
import com.android.launcher.handler.HandlerLampStatus;
import com.android.launcher.handler.HandlerOil;
import com.android.launcher.handler.HandlerRadarFront;
import com.android.launcher.handler.HandlerSafe;
import com.android.launcher.handler.HandlerTaiYa;
import com.android.launcher.handler.HandlerTiaoDadeng;
import com.android.launcher.handler.HandlerWaterTemp1;
import com.android.launcher.handler.HandlerWidthLamp;
import com.android.launcher.handler.HandlerYeshi;
import com.android.launcher.handler.HandlerYouZhuanXiang;
import com.android.launcher.handler.HandlerYuanGuangZhiShi;
import com.android.launcher.handler.HandlerZouZhuanXiang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

//import com.car.left.handler.HandlerCMS;
//import com.android.launcher.handler.HandlerCarSpeed;
//import com.car.left.handler.HandlerDingsu;
//import com.car.left.handler.HandlerEmergencyFlash;
//import com.android.launcher.handler.HandlerEmergencyFlash;
//import com.android.launcher.handler.HandlerEnergySpeed;
//import com.car.left.handler.HandlerRadarBack;


public class CommonUtil {


    public static MediaPlayer mediaPlayer ;
//    public static boolean isAdded = false;
//    public static WindowManager wm ;
//    public static View car_air ;
//    public static TextView  air_left_temp,air_left_wind,air_left_zuoyi,
//            air_on_off,air_right_zuoyi,air_right_wind,air_right_temp;
//
//    public static FrameLayout frame_air_off,frame_air_on;
    public static ParamDaoUtil paramDaoUtil = new ParamDaoUtil(App.getGlobalContext()) ;

    public static char[] ch=  "0000000000000000".toCharArray() ;
    public static Map<String,String> map = new HashMap<String,String>(){{
        put("0", "13") ;
        put("1", "14") ;
        put("2", "12") ;
        put("3", "11") ;
        put("4", "7") ;
        put("5", "8") ;
        put("6", "6") ;
        put("7", "5") ;
        put("12", "9") ;
        put("13", "15") ;
        put("14", "10") ;
        put("15", "16") ;
    }};

    public static Map<String,String> airTemp = new HashMap<String,String>(){{
        put("28", "Lo") ;
        put("32", "16") ;
        put("3c", "17") ;
        put("46", "18") ;
        put("50", "19") ;
        put("5a", "20") ;
        put("64", "21") ;
        put("6e", "22") ;
        put("78", "23") ;
        put("82", "24") ;
        put("8c", "25") ;
        put("96", "26") ;
        put("a0", "27") ;
        put("aa", "28") ;
        put("b4", "Hi") ;
    }};

    public static Map<String,String> windMap = new HashMap<String,String>(){{
        put("2e", "1") ;
        put("38", "2") ;
        put("42", "3") ;
        put("4c", "4") ;
        put("60", "5") ;
        put("78", "6") ;
        put("c8", "7") ;
    }};


    public static final int FRAGMENT_TOTALMAIL = R.layout.meter_mid_totalmile ;
    public static final int FRAGMENT_NAV = R.layout.meter_mid_nav ;
    public static final int FRAGMENT_PHONE_CALL = R.layout.meter_mid_phone_call ;




//    public static final int CARINFO_LIST = R.layout.car_info_list ;
//    public static int CARINFO_CHAIR =R.layout.car_info_chair;
//    public static final int CARINFO_AIR =R.layout.car_info_air ;
//    public static final int CARINFO_REAR_COMPARTMENT=R.layout.car_info_rear_compartment ;
//
//    public static final int CARINFO_DYNAMIC_SELECT = R.layout.car_info_dynamic_select ;
//    public static final int CARINFO_DYNAMIC_SELECT_INDIVIDUAL = R.layout.car_info_dynamic_select_individual ;
//    public static final int CARINFO_DYNAMIC_SELECT_INDIVIDUAL_1 = R.layout.car_info_dynamic_select_individual_1 ;
//    public static final int CARINFO_DYNAMIC_SELECT_INDIVIDUAL_2 = R.layout.car_info_dynamic_select_individual_2 ;
//    public static final int CARINFO_DYNAMIC_SELECT_INDIVIDUAL_3 = R.layout.car_info_dynamic_select_individual_2 ;
//    public static final int CARINFO_DYNAMIC_SELECT_INDIVIDUAL_4 = R.layout.car_info_dynamic_select_individual_2 ;
//    public static final int CARINFO_DYNAMIC_SELECT_ENERGYDATA = R.layout.car_info_dynamic_select_energydata ;


//    public static final int CARINFO_ASS = R.layout.car_info_ass;
//    public static final int CARINFO_ASS_ESP = R.layout.car_info_ass_esp;
//    public static final int CARINFO_ASS_ZHI = R.layout.car_info_ass_zhi;
//    public static final int CARINFO_ASS_ATTENTION = R.layout.car_info_ass_attention;


//    public static final int CARINFO_ENERGY = R.layout.car_info_energy ;
//    public static final int CARINFO_LAMP = R.layout.car_info_lamp;
//    public static final int CARINFO_LAMP_FRONT = R.layout.car_info_lamp_front;
//    public static final int CARINFO_LAMP_BACK = R.layout.car_info_lamp_back;
//    public static final int CARINFO_CARSET =R.layout.car_info_carset ;
//    public static final int CARINFO_CARSET_SPEEDLIMIT =R.layout.car_info_carset_speedlimit ;

//    public static final int CARINFO_MANUAL =R.layout.car_info_manual ;
//    public static final int CARINFO_ANTI = R.layout.car_info_anti;
//    public static final int CARINFO_INDOORPROTECTION = R.layout.car_info_indoor_protection;
//    public static final int CARINFO_GETTINGOFF = R.layout.car_info_gettingoff;
//    public static final int CARINFO_REARVIEW_MIRROR =R.layout.car_info_rearview_mirror ;
//
//    public static final int CARINFO_AIR_FLOW_MODEL = R.layout.car_info_air_flow_model;
//    public static final int CARINFO_AIR_FLOW_MODEL_MAIN = R.layout.car_info_air_flow_model_main;
//    public static final int CARINFO_AIR_FLOW_MODEL_FRONT_PASSENGER = R.layout.car_info_air_flow_front_passenger;
//    public static final int CARINFO_AIR_AC = R.layout.car_info_air_ac;
//    public static final int CARINFO_AIR_ONOFF = R.layout.car_info_air_onoff;
//    public static final int CARINFO_AIR_WIND_RIGHT = R.layout.car_info_air_wind_right;
//    public static final int CARINFO_AIR_WIND_LEFT = R.layout.car_info_air_wind_left;

//    public static final int CARINFO_AIR_WIND = R.layout.car_info_air_wind;
//    public static final int CARINFO_AIR_WIND_MAIN = R.layout.car_info_air_wind_main;
//    public static final int CARINFO_AIR_WIND_FRONT_PASSENGER = R.layout.car_info_air_wind_front_passenger;
//
//    public static final int CARINFO_AIR_TEMP = R.layout.car_info_air_temp ;
//    public static final int  CARINFO_AIR_TEMP_MAIN = R.layout.car_info_air_temp_main;
//    public static final int CARINFO_AIR_TEMP_FRONT_PASSENGER = R.layout.car_info_air_temp_front_passenger;

    //    public static final int CARINFO_AIR_WIND_RIGHT_SPEED = R.layout.car_info_air_wind_right_speed;
//    public static final int CARINFO_AIR_WIND_LEFT_SPEED = R.layout.car_info_air_wind_left_speed;



//
//    public static Map<Integer, CarInfoParent> carInfo = new HashMap<Integer, CarInfoParent>(){{
//        put(CARINFO_LIST,new CarInfoList());
//        put(CARINFO_CHAIR,new CarInfoChair());
//        put(CARINFO_AIR,new CarInfoAir());
//
//        put(CARINFO_DYNAMIC_SELECT,new CarInfoDynamicSelectList());
//        put(CARINFO_DYNAMIC_SELECT_INDIVIDUAL,new CarInfoDynamicSelectInvidual());
//        put(CARINFO_DYNAMIC_SELECT_INDIVIDUAL_1,new CarInfoDynamicSelectInvidual_1());
//        put(CARINFO_DYNAMIC_SELECT_INDIVIDUAL_2,new CarInfoDynamicSelectInvidual_2());
//        put(CARINFO_DYNAMIC_SELECT_INDIVIDUAL_3,new CarInfoDynamicSelectInvidual_2());
//        put(CARINFO_DYNAMIC_SELECT_INDIVIDUAL_4,new CarInfoDynamicSelectInvidual_2());
//
//        put(CARINFO_DYNAMIC_SELECT_ENERGYDATA,new CarInfoDynamicSelectEnergydata());
//
//        put(CARINFO_ASS,new CarInfoAss());
//        put(CARINFO_ASS_ESP,new CarInfoAssESP());
//        put(CARINFO_ASS_ZHI,new CarInfoAssZhi());
//        put(CARINFO_ASS_ATTENTION,new CarInfoAssAttention());
//
//
//
//        put(CARINFO_ENERGY,new CarInfoEnergy());
//        put(CARINFO_LAMP_FRONT,new CarInfoLampFront());
//        put(CARINFO_LAMP,new CarInfoLamp());
//        put(CARINFO_LAMP_BACK,new CarInfoLampBack());
//
//
//        put(CARINFO_CARSET,new CarInfoCarset());
//        put(CARINFO_CARSET_SPEEDLIMIT,new CarInfoCarsetSpeedLimit());
//
//        put(CARINFO_REAR_COMPARTMENT,new CarInfoRearCompartment());
//        put(CARINFO_ANTI,new CarInfoAnti());
//        put(CARINFO_INDOORPROTECTION,new CarInfoIndoorProtection());
//        put(CARINFO_GETTINGOFF,new CarInfoGettingOff());
//        put(CARINFO_REARVIEW_MIRROR,new CarInfoRearViewMirror());
//
//        put(CARINFO_AIR_FLOW_MODEL,new CarInfoAirFlowModel());
//        put(CARINFO_AIR_FLOW_MODEL_MAIN,new CarInfoAirFlowModelMain()) ;
//        put(CARINFO_AIR_FLOW_MODEL_FRONT_PASSENGER,new CarInfoAirFlowModelFrontPassenger()) ;
//        put(CARINFO_AIR_WIND,new CarInfoAirWind());
////        put(CARINFO_AIR_AC,new CarInfoAirAc());
////        put(CARINFO_AIR_ONOFF,new CarInfoAirOnoff());
////        put(CARINFO_AIR_WIND_RIGHT,new CarInfoAirWindRight());
////        put(CARINFO_AIR_WIND_LEFT,new CarInfoAirWindLeft());
////        put(CARINFO_AIR_WIND_RIGHT_SPEED,new CarInfoAirWindRightSpeed());
////        put(CARINFO_AIR_WIND_LEFT_SPEED,new CarInfoAirWindLeftSpeed());
//
//         put(CARINFO_AIR_WIND_MAIN,new CarInfoAirWindMain());
//        put(CARINFO_AIR_WIND_FRONT_PASSENGER,new CarInfoAirWindFrontPassenger());
//
//        put(CARINFO_AIR_TEMP,new CarInfoAirTemp());
//        put(CARINFO_AIR_TEMP_MAIN,new CarInfoAirTempMain());
//        put(CARINFO_AIR_TEMP_FRONT_PASSENGER,new CarInfoAirTempFrontPassenger());
//
//
//    }};


//  public static final int CARSET_LIST = R.layout.car_set_list;
//    public static final int CARSET_SHOW =  R.layout.car_set_show;
////    public static final int CARSET_INPUT = R.layout.car_set_input;
//    public static final int CARSET_AUDIO = R.layout.car_set_audio;
//    public static final int CARSET_CONNECT = R.layout.car_set_connect;
//    public static final int CARSET_BLUETOOTH = R.layout.car_set_bluetooth;
//    public static final int CARSET_COMMAND_TOUCH = R.layout.car_set_command_touch;
////    public static final int CARSET_TIMEDATE = R.layout.car_set_timedate;
//    public static final int CARSET_LANGUAGE = R.layout.car_set_language;
//    public static final int CARSET_UNIT =R.layout.car_set_unit;
//    public static final int CARSET_PERSONAL =R.layout.car_set_personal;
//    public static final int CARSET_PERSONPASSWORD =R.layout.car_set_personpassword;
//    public static final int CARSET_SYSTEMINFO =R.layout.car_set_systeminfo;
//    public static final int CARSET_RESET = R.layout.car_set_reset;
//    public static final int CARSET_AUDIO_LEVEL = R.layout.car_set_audio_level;
//

//    public static Map<Integer, CarSetParent> carSet = new HashMap<Integer, CarSetParent>(){{
//        put(CARSET_LIST,new CarSetList());
//        put(CARSET_SHOW,new CarSetShow());
////        put(CARSET_INPUT,new CarSetInput());
//        put(CARSET_AUDIO,new CarSetAudio());
//        put(CARSET_CONNECT,new CarSetConnect());
//        put(CARSET_BLUETOOTH,new CarSetBlueTooth());
//        put(CARSET_COMMAND_TOUCH,new CarSetCommandTouch());
////        put(CARSET_TIMEDATE,new CarSetTimeDate());
//        put(CARSET_LANGUAGE,new CarSetLanguage());
//        put(CARSET_UNIT,new CarSetUnit());
//        put(CARSET_PERSONAL,new CarSetPersonal());
//        put(CARSET_PERSONPASSWORD,new CarSetPersonalPassword());
//        put(CARSET_SYSTEMINFO,new CarSetSystemInfo());
//        put(CARSET_RESET,new CarSetReset());
//        put(CARSET_AUDIO_LEVEL,new CarSetAudioLevel()) ;
//    }};
//



    public static Map<String, HandlerInteface> meterHandler = new HashMap<String, HandlerInteface>(){{
//        put("energySpeed",new HandlerEnergySpeed()) ;
//        put("carTemp",new HandlerCarTemp()) ;
        put("lamp",new HandlerLamp()) ;
        put("waterTemp",new HandlerWaterTemp1());
        put("degree",new HandlerDegree());
//        put("carSpeed",new HandlerCarSpeed());
        put("lampStatus",new HandlerLampStatus());
        put("alert",new HandlerAlert());
        put("carSteady",new HandlerCarSteady());
//        put("emergencyFlash",new HandlerEmergencyFlash());
        put("yeshi",new HandlerYeshi());
//        put("widthLamp",new HandlerWidthLamp());
        put("CMS",new HandlerCMS());
        put("oil",new HandlerOil());
        put("safe",new HandlerSafe());
        put("brake",new HandlerBrake());
        put("absflg",new HandlerAbsflg());
        put("call",new HandlerCall());
        put("an",new HandlerAn());
        put("tiaodadeng",new HandlerTiaoDadeng()) ;
        put("yuanguangzhishi",new HandlerYuanGuangZhiShi()) ;
        put("youzhuanxiang",new HandlerYouZhuanXiang()) ;
        put("zuozhuanxiang",new HandlerZouZhuanXiang()) ;
        put("radarfront",new HandlerRadarFront()) ;
//        put("radarback",new HandlerRadarBack()) ;
        put("taiya",new HandlerTaiYa());
        put("door",new HandlerDoor());
    }};

    public static String getTime(int progress) {// 将毫秒转换成00:00
        int sec = progress / 1000; // 获取秒
        int min = sec / 60; // 获取分
        sec = sec % 60;// 获取剩余秒数 (分:秒)
        return String.format("%02d:%02d", min, sec);
    }


    /**
     * 获取风速
     * @param index
     * @return
     */
    public static String getWind(String index){
        return windMap.get(index) ;
    }



    /**
     * 获取温度
     * @param index
     * @return
     */
    public static String getTemp(String index){
        return airTemp.get(index) ;
    }
    /**
     * 16进制字符串 转10进制
     * @param hex
     * @return
     */
    public static int getHexToDecimal(String hex){
        return Integer.parseInt(hex,16);
    }

    /**
     * 获取稳定的数据 防止指针来回晃动
     * @param hexToDecimal
     * @return
     */
    public static int getStandard(int hexToDecimal) {

        if(hexToDecimal>100){
            int aa = hexToDecimal / 100 ;
            int yu = hexToDecimal % 100 ;
            String finalSpeed= "" ;
            if(yu > 15 ) {
                finalSpeed = (aa+1)+"00" ;
            }else {
                finalSpeed = aa+"15" ;
            }

            return Integer.parseInt(finalSpeed) ;
        }else{
            return hexToDecimal ;
        }
    }


    /**
     * 16进制
     * @param num
     *
     * @return
     */
    public static String getHexData(String num) {

        String hexLen =  Integer.toHexString(Integer.parseInt(num));
        StringBuffer b = new StringBuffer(hexLen);
        while(b.length()<2){
            b.insert(0, '0');
        }
        return b.toString() ;
    }

    /**
     * 16进制字符串转二进制字符串
     *
     * @param hexString
     * @return
     */
    public static String convertHexToBinary(String hexString){
        long l = Long.parseLong(hexString, 16);
        String binaryString = Long.toBinaryString(l);
        int shouldBinaryLen = hexString.length()*4;
        StringBuffer addZero = new StringBuffer();
        int addZeroNum = shouldBinaryLen-binaryString.length();
        for(int i=1;i<=addZeroNum;i++){
            addZero.append("0");
        }
        return addZero.toString()+binaryString;
    }


    public static Activity getCurrentActivity () {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *获取结果
     * @return
     * @param prepareString
     */
    public static List<String> getLampResult(String prepareString) {

        List<String> result = new ArrayList<>() ;
        char[] parr = prepareString.toCharArray();
        for(int i = 0 ;i <ch.length;i++ ) {
            char c = ch[i] ;
            char p = parr[i] ;

            if(!String.valueOf(c).equals(String.valueOf(p))) {

                if(String.valueOf(c).equals("0")) {
                    result.add(map.get(i+""));
                    ch[i]='1';
                }else {
                    ch[i]='0';
                }
            }
        }
        return result ;
    }


    /***
     * 去除String数组中的空值
     */
    private static String[] deleteArrayNull(String string[]) {
        String strArr[] = string;

        // step1: 定义一个list列表，并循环赋值
        ArrayList<String> strList = new ArrayList<String>();
        for (int i = 0; i < strArr.length; i++) {
            strList.add(strArr[i]);
        }

        // step2: 删除list列表中所有的空值
        while (strList.remove(null)) {
            ;
        }
        while (strList.remove("")) {
            ;
        }

        // step3: 把list列表转换给一个新定义的中间数组，并赋值给它
        String strArrLast[] = strList.toArray(new String[strList.size()]);

        return strArrLast;
    }

    /**
     * 获取 id为35d的内容
     * @return
     */
    public static String getCommand35d() {
        ACache aCache = ACache.get(App.getGlobalContext()) ;

        //内部照明
        String LAMP = "";

//        String  l = aCache.getAsString("LAMP") ;
        Param param = paramDaoUtil.queryParamName("LAMP") ;
        String l = param.getParamValue() ;
        switch (l){
            case "0秒":
                LAMP = "00";
                break;
            case "15秒":
                LAMP = "0F";
                break;
            case "30秒":
                LAMP = "1E";
                break;
            case "45秒":
                LAMP = "2D";
                break;
            case "60秒":
                LAMP = "3C";
                break;
        }

        //外部照明
        Param param1 = paramDaoUtil.queryParamName("LAMPBACK") ;
        String LAMPBACK= param1.getParamValue() ;
        switch (LAMPBACK){
            case "0秒":
                LAMPBACK = "00";
                break;
            case "15秒":
                LAMPBACK = "0F";
                break;
            case "30秒":
                LAMPBACK = "1E";
                break;
            case "45秒":
                LAMPBACK = "2D";
                break;
            case "60秒":
                LAMPBACK = "3C";
                break;
        }
        //上下车辅助

        Param param2 = paramDaoUtil.queryParamName("GETTINGOFF") ;
        String GETTINGOFF= param2.getParamValue() ;

        if(GETTINGOFF.equals("关闭")){
            GETTINGOFF="50" ;
        }
        if(GETTINGOFF.equals("开启")){
            GETTINGOFF="55" ;
        }

        //后舱盖高度限制

        Param param3 = paramDaoUtil.queryParamName("REAR") ;
        String REAR= param3.getParamValue() ;

        if(REAR.equals("开启")){
            REAR="D" ;
        }
        if(REAR.equals("关闭")){
            REAR="C" ;
        }

        //后舱盖高度限制
        Param param4 = paramDaoUtil.queryParamName("MIRROR") ;
        String MIRROR= param4.getParamValue() ;
        if(MIRROR.equals("开启")){
            MIRROR="1" ;
        }
        if(MIRROR.equals("关闭")){
            MIRROR="0" ;
        }

        String command = LAMP+LAMPBACK+"75"+GETTINGOFF+REAR+MIRROR+"E5" ;
        return command ;
    }

    /**
     * ID  1E0 05 0F BF FE FF 7F
     *
     * @return
     */
    public static String getCommand1E0() {

        ACache aCache = ACache.get(App.getGlobalContext()) ;


        String command = "1E0050F" ;
        Param param = paramDaoUtil.queryParamName("ANTI") ;
//        String anti = aCache.getAsString("ANTI") ;
        String anti=param.getParamValue() ;

        if(anti.equals("关闭")){
            command = command + "7F" ;
        }
        if(anti.equals("开启")){
            command = command + "BF" ;
        }

        param =  paramDaoUtil.queryParamName("INDOOR") ;
        String indoor = param.getParamValue();
        if(indoor.equals("关闭")){
            command = command + "FD" ;
        }
        if(indoor.equals("开启")){
            command = command + "FE" ;
        }

        command = command+"FF7F" ;
        return command ;
    }


    /**
     *
     * @return
     */
    public static String getCommand1E5() {

//        ACache aCache = ACache.get(App.getGlobalContext()) ;

        String command="" ;
        //ac
        Param param = paramDaoUtil.queryParamName("AC") ;
        String ac = param.getParamValue();
         if(ac.equals("开启")){
             ac = "0" ;
         }
        if(ac.equals("关闭")){
            ac = "1" ;
        }

        //WINDLEFT
        Param param1 = paramDaoUtil.queryParamName("WINDLEFT") ;
        String windleft =param1.getParamValue() ;

        if(windleft.equals("自动")){
            windleft="1" ;
        }
        if(windleft.equals("手动")){
            windleft="0" ;
        }

        Param param2 = paramDaoUtil.queryParamName("WINDLEFTSPEED") ;
       // WINDLEFTSPEED
        String windleftspeed = param2.getParamValue() ;

        if(windleftspeed.equals("手动")){
            windleftspeed="0" ;
        }
        if(windleftspeed.equals("自动")){
            windleftspeed="1" ;
        }

        Param param3 = paramDaoUtil.queryParamName("WINDRIGHT") ;

        String  windright="1" ;
        //WINDRIGHT
//        String windright = param3.getParamValue() ;
//        if(windright.equals("自动")){
//            windright="1" ;
//        }
//        if(windright.equals("手动")){
//            windright="0" ;
//        }
        //WINDRIGHTSPEED

        String windrightspeed="1" ;
//        Param param4 = paramDaoUtil.queryParamName("WINDRIGHTSPEED") ;
//        String windrightspeed = param4.getParamValue();
//        if(windrightspeed.equals("自动")){
//            windrightspeed="1" ;
//        }
//        if(windrightspeed.equals("手动")){
//            windrightspeed="0" ;
//        }
        //ONOFF
        String onoff = "0";
//        Param param5 = paramDaoUtil.queryParamName("ONOFF") ;
//        String onoff = param5.getParamValue() ;
//        if(onoff.equals("开启")){
//            onoff="0" ;
//        }
//        if(onoff.equals("关闭")){
//            onoff="1" ;
//        }


        String result = ac+"1"+onoff+windright+windleft+ windleftspeed+windrightspeed+"1";


        command = command+binaryString2hexString(result);

        //FLOWMODEL
        Param param6 = paramDaoUtil.queryParamName("FLOWMODEL") ;
        String FLOWMODEL = param6.getParamValue() ;
//        String FLOWMODEL = aCache.getAsString("FLOWMODEL") ;

        if (FLOWMODEL.equals("集中")){
            command=command+"7A";
        }
        if (FLOWMODEL.equals("中等")){
            command=command+"6E";
        }
        if (FLOWMODEL.equals("扩散")){
            command=command+"62";
        }

        command = command+"CF" ;



        return command ;
    }

    /**
     *
     * 二进制字符串转16进制字符串
     *
     * @param bString
     * @return
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
            return null;
        }
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     *
     * 设置风向
     * @return
     */
    public static String getCommand0BB(int index) {

        String prewind = "OBB05" ;
        String suffwind= "006C0700" ;

        String mid = "" ;
        switch (index){
            case 0:
                mid = "92" ;
             break;
            case 1:
                mid = "93" ;
                break;
            case 2:
                mid = "94" ;
                break;
            case 3:
                mid = "96" ;
                break;
            case 4:
                mid = "98" ;
                break;
            case 5:
                mid = "99" ;
                break;
            case 6:
                mid = "9A" ;
                break;
            case 7:
                mid = "9C" ;
                break;
        }
        return prewind+mid+suffwind ;
    }



    /**
     *
     * 修改音量
     *
     * @param progress
     */
    public void updateVolume(int progress){
        AudioManager am = (AudioManager) App.getGlobalContext().getSystemService(Context.AUDIO_SERVICE) ;
        am.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0) ;
        int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC) ;
        am.setStreamVolume(AudioManager.STREAM_MUSIC,currentVolume,am.FLAG_SHOW_UI);
    }


    /**
     * 初始化日期
     * @param week
     * @param date
     */
    public  static void initWeekDate(TextView week, TextView date) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

        LogUtils.printI(CommonUtil.class.getSimpleName(), "initWeekDate---mYear="+mYear +", mMonth="+mMonth +", mDay"+mDay);

        if("1".equals(mWay)){
            mWay ="日";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }

        week.setText("星期"+mWay);

        date.setText(mYear+"/"+mMonth+"/"+mDay);

    }

    public static  String  getCheck(String senddata) {

        int x =0 ;
        for(int i = 4;i<senddata.length();i=i+2) {
            String se = senddata.substring(i,i+2) ;
            x = x + Integer.parseInt(se,16) ;

        }

        String check = Integer.toHexString(x);

        if(check.length()>2) {
            check = check.substring(check.length()-2,check.length()) ;
        }else if(check.length()==1) {
            check = "0"+check ;
        }

        return check ;

    }


}
