//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.android.launcher.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.android.launcher.App;
import com.android.launcher.can.Can003;
import com.android.launcher.can.Can005;
import com.android.launcher.can.Can1;
import com.android.launcher.can.Can105;
import com.android.launcher.can.Can139;
import com.android.launcher.can.Can17a;
import com.android.launcher.can.Can180;
import com.android.launcher.can.Can181;
import com.android.launcher.can.Can187;
import com.android.launcher.can.Can1E5;
import com.android.launcher.can.Can203;
import com.android.launcher.can.Can207;
import com.android.launcher.can.Can20b;
import com.android.launcher.can.Can247;
import com.android.launcher.can.Can29;
import com.android.launcher.can.Can2ee;
import com.android.launcher.can.Can2f3;
import com.android.launcher.can.Can2f8;
import com.android.launcher.can.Can2f9;
import com.android.launcher.can.Can2fa;
import com.android.launcher.can.Can2fb;
import com.android.launcher.can.Can2ff;
import com.android.launcher.can.Can305;
import com.android.launcher.can.Can30d;
import com.android.launcher.can.Can319;
import com.android.launcher.can.Can33d;
import com.android.launcher.can.Can375;
import com.android.launcher.can.Can378;
import com.android.launcher.can.Can379;
import com.android.launcher.can.Can37d;
import com.android.launcher.can.Can380;
import com.android.launcher.can.Can39d;
import com.android.launcher.can.Can3e1;
import com.android.launcher.can.Can3ed;
import com.android.launcher.can.Can45;
import com.android.launcher.can.Can4B;
import com.android.launcher.can.Can69;
import com.android.launcher.can.Can39f;
import com.android.launcher.can.CanParent;
import com.android.launcher.can.Canbb;
import com.android.launcher.can.Canbc;
import com.android.launcher.can.Canf8;
import com.android.launcher.dao.MileDaoUtil;
import com.android.launcher.dao.MileSetDaoUtil;
import com.android.launcher.dao.MileStartDaoUtil;
import com.android.launcher.dao.ParamDaoUtil;
import com.android.launcher.entity.Mile;
import com.android.launcher.entity.MileSet;
import com.android.launcher.entity.MileStart;
import com.android.launcher.entity.Param;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.service.LivingService;
import com.android.launcher.ttl.SerialHelperttlLd;
import com.android.launcher.ttl.SerialHelperttlLd3;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.dc.auto.library.launcher.util.ACache;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FuncUtil {


    public static boolean SHUANGSHAN = false;

    @Deprecated
    public static boolean ZUOXIZNG = false;
    public static boolean YOUXIZNG = false;
    public static boolean ALERTHIDDEN = false;
    //是否发送
    public static boolean SENDFLG = true;
    public static boolean TTLFLG = false;

    //系统音量
    public static AudioManager audioManager;

    //蓝牙是否连接
    public static boolean BLUETOOTHCONNCTED = false;

    //FM
    public static int playFMstation = 0;
    //媒体播放是在右侧还是左侧
    public static int playMediaStation = 0;  //0 表示左侧  1表示右侧

    //呼叫状态  主叫  mainCall  被叫  beCalled
//    public static final String MAINCALL = "main" ;
//    public static final String BECALLED = "becalled" ;

    //    //通话中
    public static String PHONESTATUS = "";

    public static String yuanguang = "";
    public static boolean jinguang;


    // 信息条数
    public static int infoCount = 0;

    //门窗是否关好
    public static boolean doorStatus = false;


    //串口
    public static SerialHelperttlLd serialHelperttl;
    public static SerialHelperttlLd3 serialHelperttl3;

    public static MileStartDaoUtil mileStartDaoUtil = new MileStartDaoUtil(App.getGlobalContext());
    public static MileStart mileStart;

    public static MileSetDaoUtil mileSetDaoUtil = new MileSetDaoUtil(App.getGlobalContext());
    public static MileSet mileSet;

    public static MileDaoUtil mileDaoUtil = new MileDaoUtil(App.getGlobalContext());
    public static Mile mile;

    public static ParamDaoUtil paramDaoUtil = new ParamDaoUtil(App.getGlobalContext());
    public static List<Param> params;

    public static ACache aCache = ACache.get(App.getGlobalContext());


//    public static String currentTime ="20220101080808";


    public static String currentSpeed;

    @Deprecated
    public static boolean showDoor = true;
    public static boolean usbStatus = true;
    public static String configUsbToRight = "0";
    public static boolean degree;

    //是否发送保养的信息
    public static boolean isSendMaintainMessage = true;


    public FuncUtil() {
    }

    public static Activity getCurrentActivity() {

        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录

            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath, "left2.apk"));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
        }
    }

    public static int HexToInt(String inHex) {
        return Integer.parseInt(inHex, 16);
    }


    public static byte[] toByteArray(String arg) {
        if (arg != null) {
            char[] NewArray = new char[1000];
            char[] array = arg.toCharArray();
            int length = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] != ' ') {
                    NewArray[length] = array[i];
                    length++;
                }
            }
            int EvenLength = (length % 2 == 0) ? length : length + 1;
            if (EvenLength != 0) {
                int[] data = new int[EvenLength];
                data[EvenLength - 1] = 0;
                for (int i = 0; i < length; i++) {
                    if (NewArray[i] >= '0' && NewArray[i] <= '9') {
                        data[i] = NewArray[i] - '0';
                    } else if (NewArray[i] >= 'a' && NewArray[i] <= 'f') {
                        data[i] = NewArray[i] - 'a' + 10;
                    } else if (NewArray[i] >= 'A' && NewArray[i] <= 'F') {
                        data[i] = NewArray[i] - 'A' + 10;
                    }
                }
                byte[] byteArray = new byte[EvenLength / 2];
                for (int i = 0; i < EvenLength / 2; i++) {
                    byteArray[i] = (byte) (data[i * 2] * 16 + data[i * 2 + 1]);
                }
                return byteArray;
            }
        }
        return new byte[]{};
    }


    /**
     * 获取 当前时间
     *
     * @return
     */
    public static String getCurrentDate() {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dfs.format(new Date());
    }


    /**
     * 计算两个时间的时间差
     *
     * @param currentDate
     * @param startTime
     * @return
     */
    public static String getTwoDateDifferenceFomatter(String currentDate, String startTime) {

        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long diff = (sd.parse(currentDate).getTime() - sd.parse(startTime).getTime()) / 1000;

            Long mss = Math.abs(diff);
            long days = mss / (1000 * 60 * 60 * 24);

            long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

            long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);


            hours = days * 24 + hours;

            return hours + ":" + minutes;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "0:0";

    }


    /**
     * 更改时间
     *
     * @param dateTime
     */
    public static void setSystemTime(String dateTime) {
        DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        DataOutputStream dos = null;
        try {
            Date date = parseFormat.parse(dateTime);
            Process process = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(process.getOutputStream());
            if (Build.VERSION.SDK_INT >= 25) {
                dateTime = new SimpleDateFormat("MMddHHmmyyyy.ss").format(date); //Android 7.1
                dos.writeBytes("date " + dateTime + "\n");
                dos.writeBytes("busybox hwclock -w \n");
                dos.writeBytes("exit\n");
                dos.flush();

            } else {
                dateTime = new SimpleDateFormat("yyyyMMdd.HHmmss").format(date); //低于Android 7.1
                dos.writeBytes("date -s " + dateTime + "\n");
                dos.writeBytes("clock -w\n");
                dos.writeBytes("exit\n");
                dos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                dos = null;
            }
        }
    }


    public static void sendShellCommand(String cmd) {
        Runtime mRuntime = Runtime.getRuntime(); //执行命令的方法
        try {
            //Process中封装了返回的结果和执行错误的结果
            Process mProcess = mRuntime.exec(cmd); //加入参数
            OutputStream os = mProcess.getOutputStream();
            os.write("\n".getBytes());
            os.flush();
            os.close();
            //使用BufferReader缓冲各个字符，实现高效读取
            //InputStreamReader将执行命令后得到的字节流数据转化为字符流
            //mProcess.getInputStream()获取命令执行后的的字节流结果
            BufferedReader mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            //实例化一个字符缓冲区
            StringBuffer mRespBuff = new StringBuffer();
            //实例化并初始化一个大小为1024的字符缓冲区，char类型
            char[] buff = new char[1024];
            int ch = 0;
            //read()方法读取内容到buff缓冲区中，大小为buff的大小，返回一个整型值，即内容的长度
            //如果长度不为null
            while ((ch = mReader.read(buff)) != -1) {
                //就将缓冲区buff的内容填进字符缓冲区
                mRespBuff.append(buff, 0, ch);
            }
            //结束缓冲
            mReader.close();
            Log.i("shell", "shellExec: " + mRespBuff);
            //弹出结果
            Log.i("benzleftshell", "执行命令: " + cmd + "执行成功");

        } catch (IOException e) {
            // 异常处理
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String getCarSpeed(String hexSpeed) {

        String hexs = hexSpeed.substring(1, hexSpeed.length());
        String speed = hexSpeed.substring(1, 3);
        String speedlast = hexSpeed.substring(3, 4);

        Integer speedi = Integer.parseInt(speed, 16);
        if (Integer.parseInt(speedlast, 16) > 10) {
            speedi = speedi + 1;
        }


        return speedi + "";
    }


    /**
     * 乘法
     *
     * @param speed
     * @param bigDecimal
     * @return
     */
    public static BigDecimal multiply(BigDecimal speed, BigDecimal bigDecimal) {
        return speed.multiply(bigDecimal);
    }


    /**
     * 设置屏幕亮度
     *
     * @param context
     * @param brightness 范围 15- 255
     */
    public static void saveBrightness(Context context, int brightness) {

        try {
//            Log.i("AAAAA",brightness+"--------------") ;
            int bright = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            if (bright != brightness) {
                Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
                Settings.System.putInt(App.getGlobalContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                context.getContentResolver().notifyChange(uri, null);


                if (brightness == 255) {
                    //通知右侧屏幕进行屏幕亮度调节
                    String send = "AABB2300CCDD";
                    Log.i("BIGHT", brightness + "==============1");
                    SendHelperUsbToRight.handler(send);
                    MeterActivity.isNightMode = false;
                }
                if (brightness == 15) {
                    //通知右侧屏幕进行屏幕亮度调节
                    String send = "AABB2400CCDD";
                    Log.i("BIGHT", brightness + "==============2");
                    SendHelperUsbToRight.handler(send);
                    MeterActivity.isNightMode = true;
                }


            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * 加
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
        return num1.add(num2);
    }


    public static Map<String, CanParent> canHandler = new HashMap<String, CanParent>() {{
        put("1", new Can1());
        put("2ee", new Can2ee());
        put("2f3", new Can2f3());
        put("2f8", new Can2f8());
        put("2f9", new Can2f9());
        put("2fa", new Can2fa());
        put("2fb", new Can2fb());
        put("2ff", new Can2ff());
        put("3ed", new Can3ed());
        put("17a", new Can17a());
        put("20b", new Can20b());
        put("29", new Can29());
        put("30d", new Can30d());
        put("33d", new Can33d());
        put("37d", new Can37d());
        put("45", new Can45());
        put("69", new Can69());
        put("105", new Can105());
        put("139", new Can139());
        put("180", new Can180());
        put("181", new Can181());
        put("203", new Can203());
        put("247", new Can247());
        put("305", new Can305());
        put("319", new Can319());
        put("375", new Can375());
        put("379", new Can379());
        put("bb", new Canbb());
        put("bc", new Canbc());
        put("f8", new Canf8());
        put("4b", new Can4B());
        put("1e5", new Can1E5());
        put("378", new Can378());
        put("380", new Can380());
        put("5", new Can005());
        put("3e1", new Can3e1());
        put("187", new Can187());
        put("207", new Can207());
        put("39d", new Can39d());
        put("39f", new Can39f());
        put("3", new Can003());
    }};


    public static String getMileJustInTime(String speed) {
        BigDecimal currentspeed = new BigDecimal(speed);
        BigDecimal currentTime = new BigDecimal("0.0000055556");

        BigDecimal currentmile = multiply(currentspeed, currentTime);

        return currentmile.toString();
    }

    /**
     * 获取里程
     *
     * @param miles
     * @return
     */
    public static String currentMile = "0";

    public static Map<String, String> getResultMap(List<String> miles) {
        Map<String, String> map = new HashMap<>();
        for (String mile : miles) {
            BigDecimal m = new BigDecimal(currentMile);
            BigDecimal m1 = new BigDecimal(mile);

            BigDecimal re = m.add(m1);
            currentMile = re.toString();
        }

        BigDecimal realMile = new BigDecimal(currentMile).setScale(4, BigDecimal.ROUND_HALF_UP);

        map.put("realMile", realMile.toString());

        if (realMile.floatValue() > 0.00f) {
            LivingService.launchCarRunMile = BigDecimalUtils.add(String.valueOf(LivingService.launchCarRunMile), String.valueOf(realMile.floatValue()));
            LogUtils.printI(FuncUtil.class.getSimpleName(), "launchCarRunMile=" + LivingService.launchCarRunMile);

            QtripUtils.computeMile(map, realMile);
        }
        currentMile = "0";
        return map;
    }

    public static void initMileAndParam() {
        // 初始化参数
//        paramDaoUtil.deleteAll();
        params = paramDaoUtil.queryAll();

        Log.i("Params", params.size() + "---------------");
        if (params.size() < 1) {
            List<Param> params = new ArrayList<>();

            Param param = new Param();
            param.setParamName("LAMP");
            param.setParamValue("0秒");
            params.add(param);


            Param param1 = new Param();
            param1.setParamName("LAMPBACK");
            param1.setParamValue("0秒");
            params.add(param1);


            Param param2 = new Param();
            param2.setParamName("CMS");
            param2.setParamValue("C");
            params.add(param2);


            Param param3 = new Param();
            param3.setParamName("COMMAND");
            param3.setParamValue("中");
            params.add(param3);


            Param param4 = new Param();
            param4.setParamName("AUDIOLEVEL");
            param4.setParamValue("7");
            params.add(param4);


            Param param5 = new Param();
            param5.setParamName("UNIT");
            param5.setParamValue("km/h");
            params.add(param5);

            Param param6 = new Param();
            param6.setParamName("SHOWLEVEL");
            param6.setParamValue("255");
            params.add(param6);

            Param param7 = new Param();
            param7.setParamName("REAR");
            param7.setParamValue("关闭");
            params.add(param7);

            Param param8 = new Param();
            param8.setParamName("ANTI");
            param8.setParamValue("关闭");
            params.add(param8);

            Param param9 = new Param();
            param9.setParamName("INDOOR");
            param9.setParamValue("关闭");
            params.add(param9);

            Param param10 = new Param();
            param10.setParamName("MIRROR");
            param10.setParamValue("关闭");
            params.add(param10);

            Param param11 = new Param();
            param11.setParamName("GETTINGOFF");
            param11.setParamValue("关闭");
            params.add(param11);

            Param param12 = new Param();
            param12.setParamName("FLOWMODEL");
            param12.setParamValue("中等");
            params.add(param12);

            Param param121 = new Param();
            param121.setParamName("FLOWMODELMAIN");
            param121.setParamValue("中等");
            params.add(param121);


            Param param13 = new Param();
            param13.setParamName("AIRWINDFRONT");
            param13.setParamValue("2");
            params.add(param13);


            Param param14 = new Param();
            param14.setParamName("AIRWINDMAIN");
            param14.setParamValue("2");
            params.add(param14);

            Param param15 = new Param();
            param15.setParamName("AIRTEMP");
            param15.setParamValue("Lo");
            params.add(param15);

            Param param16 = new Param();
            param16.setParamName("AIRTEMPFRONT");
            param16.setParamValue("Lo");
            params.add(param16);

            Param param17 = new Param();
            param17.setParamName("WINDLEFTSPEED");
            param17.setParamValue("自动");
            params.add(param17);

            Param param18 = new Param();
            param18.setParamName("WINDRIGHTSPEED");
            param18.setParamValue("自动");
            params.add(param18);

            Param param19 = new Param();
            param19.setParamName("ESP");
            param19.setParamValue("关闭");
            params.add(param19);

            Param param191 = new Param();
            param191.setParamName("ASSZHI");
            param191.setParamValue("适中");
            params.add(param191);

            Param param20 = new Param();
            param20.setParamName("ASSATT");
            param20.setParamValue("标准");
            params.add(param20);

            Param param201 = new Param();
            param201.setParamName("AC");
            param201.setParamValue("开启");
            params.add(param201);


            Param param202 = new Param();
            param202.setParamName("WINDLEFT");
            param202.setParamValue("自动");
            params.add(param202);


            Param param21 = new Param();
            param21.setParamName("CARSETSPEED");
            param21.setParamValue("150km/h");
            params.add(param21);

            paramDaoUtil.insertMult(params);
        }


        // 初始化里程
        mile = mileDaoUtil.queryMile();
//        mileDaoUtil.deleteAll();
        if (mile == null) {
            Mile mile = new Mile();
            mile.setCreateDate(FuncUtil.getCurrentDate());
            mile.setCurrentStatus("Y");
            mile.setTotleMile("0");
            mile.setUserChangeDate(FuncUtil.getCurrentDate());
            mile.setUserMile("0");
            mileDaoUtil.insert(mile);
        }
        // 初始化启动后里程

        mileStart = mileStartDaoUtil.queryMileStart();

        if (mileStart == null) {

            MileStart mileStart = new MileStart();
            mileStart.setStartMile("0");
            mileStart.setStartTime(FuncUtil.getCurrentDate());
            mileStartDaoUtil.insert(mileStart);
        } else {
            mileStart.setStartMile("0");
            mileStart.setStartTime(FuncUtil.getCurrentDate());
            mileStartDaoUtil.insert(mileStart);
        }

        //初始化复位后里程

        mileSet = mileSetDaoUtil.queryMileSet();

        if (mileSet == null) {
            MileSet mileSet = new MileSet();
            mileSet.setSetMile("0");
            mileSet.setSetTime(FuncUtil.getCurrentDate());
            mileSetDaoUtil.insert(mileSet);
        }


        //驾驶模式
        String cms = aCache.getAsString("CMS");
        if (cms == null) {
            aCache.put("CMS", "S");
        }

        audioManager = (AudioManager) App.getGlobalContext().getSystemService(Context.AUDIO_SERVICE);
        int AUDIOLEVEL = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);
    }


    public static void initSerialHelper() {
        Log.i("serial", "======================开始串口");
        try {
            initSerialHelper1();
            initSerialHelper3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initSerialHelper3() throws IOException {
        serialHelperttl3 = new SerialHelperttlLd3("/dev/ttyS3", 9600);
        serialHelperttl3.openLLd();
    }

    public static void initSerialHelper1() throws IOException {
        serialHelperttl = new SerialHelperttlLd("/dev/ttyS1", 115200);
        serialHelperttl.openLLd();
    }

    public static void setBluetooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
            setScanMode.setAccessible(true);
            setDiscoverableTimeout.invoke(adapter, 0);
            setScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 0);
            Log.i("App", "open Bluetooth search");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("App", "setDiscoverableTimeout failure:" + e.getMessage());
        }
    }

    /**
     * 获取油耗的百分比 ，
     *
     * @param munis
     * @return
     */
    public static float getOilPrecent(int munis) {

//        if (munis < 210) {
//            munis = munis + 1;
//        } else {
//            munis = munis + 9;
//        }
//        if (munis < 22) { //油表视图左边距离
//            munis = 22;
//        }
//        if (munis > 426) { //油表视图右边距离
//            munis = 426;
//        }

//        munis = munis - 22;

        float result = BigDecimalUtils.div(Integer.toString(munis), Integer.toString(CarConstants.OIL_MAX_VALUE), 4);

        LogUtils.printI(FuncUtil.class.getSimpleName(), "getOilPrecent---munis=" + munis + ", result=" + result);

        float finalresult = BigDecimalUtils.sub("1", Float.toString(result));

        LogUtils.printI(FuncUtil.class.getSimpleName(), "getOilPrecent---finalresult=" + finalresult);
        return finalresult;
    }

}
