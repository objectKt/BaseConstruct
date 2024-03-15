package com.android.launcher.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.TextView;

import com.android.launcher.App;
import com.android.launcher.handler.HandlerAbsflg;
import com.android.launcher.handler.HandlerAlert;
import com.android.launcher.handler.HandlerAn;
import com.android.launcher.handler.HandlerBrake;
import com.android.launcher.handler.HandlerCMS;
import com.android.launcher.handler.HandlerCall;
import com.android.launcher.handler.HandlerCarSteady;
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

public class CommonUtil {

    public static MediaPlayer mediaPlayer;

    public static char[] ch = "0000000000000000".toCharArray();
    public static Map<String, String> map = new HashMap<String, String>() {{
        put("0", "13");
        put("1", "14");
        put("2", "12");
        put("3", "11");
        put("4", "7");
        put("5", "8");
        put("6", "6");
        put("7", "5");
        put("12", "9");
        put("13", "15");
        put("14", "10");
        put("15", "16");
    }};

    public static Map<String, String> airTemp = new HashMap<String, String>() {{
        put("28", "Lo");
        put("32", "16");
        put("3c", "17");
        put("46", "18");
        put("50", "19");
        put("5a", "20");
        put("64", "21");
        put("6e", "22");
        put("78", "23");
        put("82", "24");
        put("8c", "25");
        put("96", "26");
        put("a0", "27");
        put("aa", "28");
        put("b4", "Hi");
    }};

    public static Map<String, String> windMap = new HashMap<String, String>() {{
        put("2e", "1");
        put("38", "2");
        put("42", "3");
        put("4c", "4");
        put("60", "5");
        put("78", "6");
        put("c8", "7");
    }};

    public static Map<String, HandlerInteface> meterHandler = new HashMap<String, HandlerInteface>() {{
//        put("energySpeed",new HandlerEnergySpeed()) ;
//        put("carTemp",new HandlerCarTemp()) ;
        put("lamp", new HandlerLamp());
        put("waterTemp", new HandlerWaterTemp1());
        put("degree", new HandlerDegree());
//        put("carSpeed",new HandlerCarSpeed());
        put("lampStatus", new HandlerLampStatus());
        put("alert", new HandlerAlert());
        put("carSteady", new HandlerCarSteady());
//        put("emergencyFlash",new HandlerEmergencyFlash());
        put("yeshi", new HandlerYeshi());
//        put("widthLamp",new HandlerWidthLamp());
        put("CMS", new HandlerCMS());
        put("oil", new HandlerOil());
        put("safe", new HandlerSafe());
        put("brake", new HandlerBrake());
        put("absflg", new HandlerAbsflg());
        put("call", new HandlerCall());
        put("an", new HandlerAn());
        put("tiaodadeng", new HandlerTiaoDadeng());
        put("yuanguangzhishi", new HandlerYuanGuangZhiShi());
        put("youzhuanxiang", new HandlerYouZhuanXiang());
        put("zuozhuanxiang", new HandlerZouZhuanXiang());
        put("radarfront", new HandlerRadarFront());
//        put("radarback",new HandlerRadarBack()) ;
        put("taiya", new HandlerTaiYa());
        put("door", new HandlerDoor());
    }};

    public static String getTime(int progress) {// 将毫秒转换成00:00
        int sec = progress / 1000; // 获取秒
        int min = sec / 60; // 获取分
        sec = sec % 60;// 获取剩余秒数 (分:秒)
        return String.format("%02d:%02d", min, sec);
    }


    /**
     * 获取风速
     *
     * @param index
     * @return
     */
    public static String getWind(String index) {
        return windMap.get(index);
    }


    /**
     * 获取温度
     *
     * @param index
     * @return
     */
    public static String getTemp(String index) {
        return airTemp.get(index);
    }

    /**
     * 16进制字符串 转10进制
     *
     * @param hex
     * @return
     */
    public static int getHexToDecimal(String hex) {
        return Integer.parseInt(hex, 16);
    }

    /**
     * 获取稳定的数据 防止指针来回晃动
     *
     * @param hexToDecimal
     * @return
     */
    public static int getStandard(int hexToDecimal) {

        if (hexToDecimal > 100) {
            int aa = hexToDecimal / 100;
            int yu = hexToDecimal % 100;
            String finalSpeed = "";
            if (yu > 15) {
                finalSpeed = (aa + 1) + "00";
            } else {
                finalSpeed = aa + "15";
            }

            return Integer.parseInt(finalSpeed);
        } else {
            return hexToDecimal;
        }
    }


    /**
     * 16进制
     *
     * @param num
     * @return
     */
    public static String getHexData(String num) {

        String hexLen = Integer.toHexString(Integer.parseInt(num));
        StringBuffer b = new StringBuffer(hexLen);
        while (b.length() < 2) {
            b.insert(0, '0');
        }
        return b.toString();
    }

    /**
     * 16进制字符串转二进制字符串
     *
     * @param hexString
     * @return
     */
    public static String convertHexToBinary(String hexString) {
        long l = Long.parseLong(hexString, 16);
        String binaryString = Long.toBinaryString(l);
        int shouldBinaryLen = hexString.length() * 4;
        StringBuffer addZero = new StringBuffer();
        int addZeroNum = shouldBinaryLen - binaryString.length();
        for (int i = 1; i <= addZeroNum; i++) {
            addZero.append("0");
        }
        return addZero.toString() + binaryString;
    }


    public static Activity getCurrentActivity() {
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
     * 获取结果
     *
     * @param prepareString
     * @return
     */
    public static List<String> getLampResult(String prepareString) {

        List<String> result = new ArrayList<>();
        char[] parr = prepareString.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            char p = parr[i];

            if (!String.valueOf(c).equals(String.valueOf(p))) {

                if (String.valueOf(c).equals("0")) {
                    result.add(map.get(i + ""));
                    ch[i] = '1';
                } else {
                    ch[i] = '0';
                }
            }
        }
        return result;
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
     * 设置风向
     *
     * @return
     */
    public static String getCommand0BB(int index) {

        String prewind = "OBB05";
        String suffwind = "006C0700";

        String mid = "";
        switch (index) {
            case 0:
                mid = "92";
                break;
            case 1:
                mid = "93";
                break;
            case 2:
                mid = "94";
                break;
            case 3:
                mid = "96";
                break;
            case 4:
                mid = "98";
                break;
            case 5:
                mid = "99";
                break;
            case 6:
                mid = "9A";
                break;
            case 7:
                mid = "9C";
                break;
        }
        return prewind + mid + suffwind;
    }


    /**
     * 修改音量
     *
     * @param progress
     */
    public void updateVolume(int progress) {
        AudioManager am = (AudioManager) App.getGlobalContext().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, am.FLAG_SHOW_UI);
    }


    /**
     * 初始化日期
     *
     * @param week
     * @param date
     */
    public static void initWeekDate(TextView week, TextView date) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

        LogUtils.printI(CommonUtil.class.getSimpleName(), "initWeekDate---mYear=" + mYear + ", mMonth=" + mMonth + ", mDay" + mDay);

        if ("1".equals(mWay)) {
            mWay = "日";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }

        week.setText("星期" + mWay);

        date.setText(mYear + "/" + mMonth + "/" + mDay);

    }

    public static String getCheck(String senddata) {

        int x = 0;
        for (int i = 4; i < senddata.length(); i = i + 2) {
            String se = senddata.substring(i, i + 2);
            x = x + Integer.parseInt(se, 16);

        }

        String check = Integer.toHexString(x);

        if (check.length() > 2) {
            check = check.substring(check.length() - 2, check.length());
        } else if (check.length() == 1) {
            check = "0" + check;
        }

        return check;

    }


}
