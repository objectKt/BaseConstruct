package com.android.launcher.usbdriver;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.airsystem.AirCommandUtils;
import com.android.launcher.can.Can1E5;
import com.android.launcher.can.Can20b;
import com.android.launcher.can.CanParent;
import com.android.launcher.entity.BinaryEntity;
import com.android.launcher.entity.NavEntity;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.service.LivingService;
import com.android.launcher.status.DriveModeStatus;
import com.android.launcher.type.LanguageType;
import com.android.launcher.type.UnitType;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LanguageUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;
import com.android.launcher.util.StringUtils;
import com.android.launcher.util.UnitUtils;
import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BenzHandlerData {

    private static final String TAG = BenzHandlerData.class.getSimpleName();

    public static StringBuffer pre = new StringBuffer();
    //    public static String CHAR = "AA0000" ;
    public static StringBuffer sbttl = new StringBuffer();
    public static SendHelperUsbToOne sendHelperUsbToOne = new SendHelperUsbToOne();
    private static ExecutorService executors = Executors.newFixedThreadPool(1);
    static Calendar calendar = Calendar.getInstance();
    public static String time;

    /**
     * 右侧发送来的数据
     *
     * @param text
     */
    public static void handlerFromRight(String text) throws Exception {
        Log.i("hufei", "执行了 handlerFromRight");
        sbttl.append(text);
        String dat = sbttl.toString();
//        AABB06AA000008000001E52828BF42BB416ECFCCDD

        if (dat.contains("AABB") && dat.contains("CCDD")) {
            LogUtils.printI(TAG, "handlerFromRight---data=" + dat);
//                Log.i("shuju",dat+"-------------") ;
            if (dat.endsWith("CCDD")) {
//                    System.out.println("====1==========" + dat);
                String[] str = dat.split("CCDD");
//                    Log.i("shuju",Arrays.toString(str)+"---------1----------");
                for (String s : str) {

                    if (!s.equals("")) {
//                            Log.i("shuju",s+"-------1------") ;
                        if (s.contains("AABB")) {
                            int inde = s.indexOf("AABB");
                            String da = s.substring(inde, s.length()).replaceAll("AABB", "");
                            handlerTTLData(da);
                        }
                    }
                }
                sbttl.setLength(0);
            } else {
                String[] str = dat.split("CCDD");
                for (int i = 0; i < str.length - 1; i++) {
                    String s = str[i];
                    if (!s.equals("")) {
                        if (s.contains("AABB")) {
                            int inde = s.indexOf("AABB");

                            String da = s.substring(inde, s.length()).replaceAll("AABB", "");
                            handlerTTLData(da);
                        }
                    }
                }
                sbttl.setLength(0);
                sbttl.append(str[str.length - 1]);
            }
        }
    }

    /**
     * 右侧 数据处理
     *
     * @param data
     */
    private static void handlerTTLData(final String data) {


        String id = data.substring(0, 2);
        Log.i("shuju", data + "-------1----------" + id);

        switch (id) {
            case "01":
                FuncUtil.BLUETOOTHCONNCTED = true;
                break;
            case "02":
                // blueToothclose
                FuncUtil.BLUETOOTHCONNCTED = false;
                break;
            case "03": //来电提示
                String jsonString = data.substring(2, data.length() - 1);
//                    String number = FastJsonUtils.fromString(jsonString,"number");
//                ACache.get(App.getGlobalContext()).put("Phone", jsonString);
//                EventBusMeter.getInstance().postSticky(new MessageWrap("call", jsonString));
                break;
            case "04": // 主驾气流类型设置
                setupMainDriverAirflow(data);

                break;
            case "05": // 副驾气流类型设置
                setupCopilotAirflow(data);
                break;
            case "06": // 主驾风速设置
                setupMainDriverWindLevel(data);
                break;
            case "07": // 驾驶模式
                disposeDriveMode(data);

                break;
            case "08": // 设置内部外部照明
                final String command8 = data.substring(2, data.length());

                byte[] bytes8 = FuncUtil.toByteArray(command8);
                MUsb1Receiver.write(bytes8);

                break;
            case "09": // 主驾温度设置
                setupMainDriverTempSize(data);
                break;
            case "10":
//                    String guaduan =sendToAssistant.getC() ;
//                    String number = FastJsonUtils.fromString(jsonString,"number");
//                    ACache.get(App.getGlobalContext()).put("Phone",jsonString);
//                EventBusMeter.getInstance().postSticky(new MessageWrap("call", "hide"));
                break;
            case "11":

                Log.i("shijian", data + "--------------------");
                String timedata = data.substring(2, data.length());
                if (timedata.length() == 14) {
//                    FuncUtil.currentTime = timedata ;
//                    18353158873
                    SendHelperUsbToRight.bandFlg = true;
                    try {
                        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(timedata);
                        calendar.setTime(date);

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);

                        int minute = calendar.get(Calendar.MINUTE);

                        int second = calendar.get(Calendar.SECOND);

                        String s = "";

                        String m = "";

                        String h = "";

                        if (hour < 10) {

                            h = "0" + hour;

                        } else {

                            h = hour + "";

                        }

                        if (minute < 10) {

                            m = "0" + minute;

                        } else {

                            m = minute + "";

                        }

                        if (second < 10) {

                            s = "0" + second;

                        } else {

                            s = second + "";

                        }

                        time = h + ":" + m;
//                        textView.setText(time);
//                        MeterActivity.localMeterActivity.timeok.setText(time);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case "12":
//              Log.i("shuju",data+"--------------------") ;
                SendHelperUsbToRight.handler("AABB000000CCDD");
                break;
            case "13": //
                final String command13 = data.substring(2, data.length());
                Log.i("shuju13", command13 + "--------------------");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] byte13 = FuncUtil.toByteArray(command13);
                        MUsb1Receiver.write(byte13);
                    }
                }).start();
                break;
            case "14": // 后空调控制参数

                try {
                    String command14 = data.substring(2);
                    command14 = "AA000006000000BC" + command14 + "0000";
                    Log.i("shuju14", command14 + "--------------------");

                    byte[] byte14 = FuncUtil.toByteArray(command14);
                    MUsb1Receiver.write(byte14);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "15": // logo

                String command15 = data.substring(2, data.length());
                ACache cache1 = ACache.get(App.getGlobalContext());
                if (command15.equals("01")) {
                    Intent intent = new Intent("xy.android.setcustlogoani");
                    intent.putExtra("filepath", "/sdcard/Pictures/benzlogo.png");
                    intent.putExtra("xy_type", 1);
                    App.getGlobalContext().sendBroadcast(intent);
                    cache1.remove("logo");
                }
                if (command15.equals("02")) {
                    Intent intent = new Intent("xy.android.setcustlogoani");
                    intent.putExtra("filepath", "/sdcard/Pictures/benzlogo2.png");
                    intent.putExtra("xy_type", 1);
                    App.getGlobalContext().sendBroadcast(intent);
                    cache1.put("logo", "1");
                }

                executors.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            FuncUtil.sendShellCommand("reboot");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case "16": // 前后空调开关
                disposeFrontBackSwitch(data);
                break;
            case "17": //导航
                disposeMapData(data);
                break;
            case "18":
                setupCopilotWindLevel(data);
                break;
            case "19": //设置副驾温度
                setupCopilotTempSize(data);
                break;
            case "20": //气流模式
                setupAirflowMode(data);
                break;
            case "21":

                try {
                    String rightDeviceId = data.substring(2).toLowerCase();
                    LogUtils.printI(BenzHandlerData.class.getSimpleName(), "rightDeviceId=" + rightDeviceId);
                    SPUtils.putString(App.getGlobalContext(), SPUtils.SP_RIGHT_DEVICE_ID, rightDeviceId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "22":
                try {
                    String rightTime = data.substring(2);
//                    LogUtils.printI(BenzHandlerData.class.getSimpleName(), "rightTime=" + rightTime);
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.CALIBRATION_TIME);
                    messageEvent.data = rightTime;
                    EventBus.getDefault().post(messageEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "23":
                disposeCan35dD5Data(data);
                break;
            case "24":
                disposeAutoLockData(data);
                break;
            case "25":
                disposeCarAssistData(data);
                break;
            case "26":
                disposeCarBgLightingData(data);
                break;
            case "27":
                disposeInnerLightingData(data);
                break;
            case "28":
                disposeOuterLightingData(data);
                break;
            case "29":
                disposeEnvirLightingData(data);
                break;
            case "30":
                disposeAlarmVolumeData(data);
                break;
            case "31":
                disposeHeadrestSwitchData(data);
                break;
            case "32": //设置原车公里数
                disposeOriginalCarKM(data);
                break;
            case "33": //头枕放倒
                disposeHeadrestData();
                break;
            case "34": //安全带辅助
                disposeSafeBeltData(data);
                break;
            case "35": //设置车辆型号
                disposeCarTypeData(data);
                break;
            case "36": //设置里程单位，km/h或者mi/h
                disposeSetUnitData(data);
                break;
            case "37": //切换语言
                disposeLanguageTypeData(data);
                break;
            case "38": //歌曲标题

                break;
            case "39": //歌词
                disposeLyric(data);
                break;
            case "40": //蓝牙断开
                disposeBluetoothDisconnected();
                break;
            case "41": //蓝牙连接
                disposeBluetoothConnected();
                break;
//            case "49":
//                disposeSetRearHatchCoverData(data);
//                break;
            case "50": //can1dc
                disposeCan1DCData(data);
                break;
            case "51": //空调开启和关闭
                disposeACData(data);
                break;
            case "52": //主驾自动

                break;
            case "53": //副驾自动

                break;
            case "54": //设置自动空调
                disposeSetAutoACData(data);
                break;
            case "55": //设置空调压缩机状态
                disposeSetAcCompressorData(data);
                break;
            case "56": //驾驶员风向自动
                disposeSetDriverWinddirAutoData(data);
                break;
            case "57": //副驾驶风向自动
                disposeSetFrontSeatWinddirAutoData(data);
                break;
            case "58": //主驾风速自动
                disposeSetDriverWindAutoData(data);
                break;
            case "59": //副驾风速自动
                disposeSetFrontSeatWindAutoData(data);
                break;
            case "60": //初始话空调配置
                disposeInit1E5Data(data);
                break;
            case "61": //设置气流模式
                disposeAirflowPatternData(data);
                break;
            case "62": //音乐暂停
                disposeMusicStop();
                break;
            case "63":
                disposeMapNavData(data);
                break;
            case "64":
                disposeStopMapNav(data);
                break;
            case "65":
                disposeDownloadMapCity(data);
                break;
            case "66":
                disposeDownloadMapProvince(data);
                break;
            case "67":
                disposeDownloadMapProvinceCitySize(data);
                break;
            case "68":
                disposeCancelDownloadMap();
                break;
            case "69":
                disposeRadarOnData(data);
                break;
            case "70":
                disposeEspOffData(data);
                break;
            case "71":
                disposeChassisLiftData();
                break;
            case "72":
                disposeDayRunLightOnData(data);
                break;
            case "73":
                disposeONKeyData(data);
                break;
            case "74":
                disposeSeatAdjustmentOnData(data);
                break;
            case "75":
                disposeOperateOriginMeterData(data);
                break;
            default:
                break;
        }
    }

    //打开操作原车仪表开关
    private static void disposeOperateOriginMeterData(String data) {
        try {
            String onStatus = data.substring(2);
            LogUtils.printI(TAG, "disposeOperateOriginMeterData---onStatus=" + onStatus);

            if (BinaryEntity.Value.NUM_1.getValue().equals(onStatus)) {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_ORIGIN_METER_ON, true);
                LivingService.operateOriginMeter = true;
            } else {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_ORIGIN_METER_ON, false);
                LivingService.operateOriginMeter = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //座椅功能调节开关
    private static void disposeSeatAdjustmentOnData(String data) {
        try {
            String onStatus = data.substring(2);
            LogUtils.printI(TAG, "disposeSeatAdjustmentOnData---onStatus=" + onStatus);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.OPEN_SEAT_FUNCTION_ADJUSTMENT);
            if (BinaryEntity.Value.NUM_1.getValue().equals(onStatus)) {
                messageEvent.data = true;
            } else {
                messageEvent.data = false;
            }
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置ON键开关状态
    private static void disposeONKeyData(String data) {
        try {
            String onStatus = data.substring(2);
            LogUtils.printI(TAG, "disposeONKeyData---onStatus=" + onStatus);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.OPEN_O_N_KEY);
            if (BinaryEntity.Value.NUM_1.getValue().equals(onStatus)) {
                messageEvent.data = true;
            } else {
                messageEvent.data = false;
            }
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置日间行驶灯开关
    private static void disposeDayRunLightOnData(String data) {
        try {
            String onStatus = data.substring(2);
            LogUtils.printI(TAG, "disposeDayRunLightOnData---onStatus=" + onStatus);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.OPEN_DAY_RUN_LIGHT);
            if (BinaryEntity.Value.NUM_1.getValue().equals(onStatus)) {
                messageEvent.data = true;
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_DAY_RUN_LIGHT_ON, true);
            } else {
                messageEvent.data = false;
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_DAY_RUN_LIGHT_ON, false);
            }
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeChassisLiftData() {
        try {
            LogUtils.printI(TAG, "disposeChassisLiftData---");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理ESP开关
    private static void disposeEspOffData(String data) {
        try {
            String offStatus = data.substring(2);
            LogUtils.printI(TAG, "disposeRadarOnData---offStatus=" + offStatus);
            if (BinaryEntity.Value.NUM_1.getValue().equals(offStatus)) {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_ESP_OFF, true);
            } else {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_ESP_OFF, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理雷达开关
    private static void disposeRadarOnData(String data) {
        try {
            String onStatus = data.substring(2, data.length());
            LogUtils.printI(TAG, "disposeRadarOnData---onStatus=" + onStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeCancelDownloadMap() {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.CANCEL_DOWNLOAD_OFFLINE_MAP);
        EventBus.getDefault().post(messageEvent);
    }

    //下载离线地图 省的城市数量
    private static void disposeDownloadMapProvinceCitySize(String data) {
        try {
            String citySizeHex = data.substring(2);
            int citySize = Integer.parseInt(citySizeHex, 16);
            LogUtils.printI(TAG, "disposeDownloadMapProvinceCitySize---citySizeHex=" + citySizeHex + ", citySize=" + citySize);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.DOWNLOAD_OFFLINE_MAP_PROVINCE_CITY_SIZE);
            messageEvent.data = citySize;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下载离线地图 省
    private static void disposeDownloadMapProvince(String data) {
        try {
            String hexProvince = data.substring(2);
            String province = StringUtils.hexToString(hexProvince);

            LogUtils.printI(TAG, "disposeDownloadMapProvince---hexProvince=" + hexProvince + ", province=" + province);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.DOWNLOAD_OFFLINE_MAP_PROVINCE);
            messageEvent.data = province;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下载离线地图 市
    private static void disposeDownloadMapCity(String data) {
        try {
            String hexCity = data.substring(2);
            String city = StringUtils.hexToString(hexCity);

            LogUtils.printI(TAG, "disposeDownloadMapCity---hexCity=" + hexCity + ", city=" + city);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.DOWNLOAD_OFFLINE_MAP_CITY);
            messageEvent.data = city;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //停止地图导航
    private static void disposeStopMapNav(String data) {
        LogUtils.printI(TAG, "disposeStopMapNav---data=" + data);
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STOP_MAP_NAV);
        EventBus.getDefault().post(messageEvent);
    }

    //处理导航数据
    private static void disposeMapNavData(String data) {
        try {
            String navData = data.substring(2);
            LogUtils.printI(TAG, "disposeMapNavData---navData=" + navData);
            String[] arr = navData.split("A");
            String lat = arr[0];
            String lng = arr[1];
            String strategyConvert = arr[2];
            String selectRouteId = arr[3];

            String latitude = lat.replace("D", ".");
            String longitude = lng.replace("D", ".");

            NavEntity navEntity = new NavEntity(latitude, longitude, Integer.valueOf(strategyConvert), Integer.valueOf(selectRouteId));


            LogUtils.printI(TAG, "disposeMapNavData---latitude=" + latitude + ", longitude=" + longitude);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.START_MAP_NAV);
            messageEvent.data = navEntity;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //后舱盖高度限制
//    private static void disposeSetRearHatchCoverData(String data) {
//        try {
//            String status = data.substring(2);
//            LogUtils.printI(TAG, "disposeSetRearHatchCoverData---status=" + status);
//            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_REAR_HATCH_COVER);
//            messageEvent.data = status;
//            EventBus.getDefault().post(messageEvent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private static void disposeMusicStop() {
        LogUtils.printI(TAG, "disposeMusicStop---");
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.MUSIC_STOP);
        EventBus.getDefault().post(messageEvent);
    }

    private static void disposeBluetoothConnected() {
        LogUtils.printI(TAG, "disposeBluetoothConnected---蓝牙连接");
        FuncUtil.BLUETOOTHCONNCTED = true;
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.BLUETOOTH_CONNECTED);
        EventBus.getDefault().post(messageEvent);
    }


    private static void disposeBluetoothDisconnected() {
        FuncUtil.BLUETOOTHCONNCTED = false;
        LogUtils.printI(TAG, "disposeBluetoothDisconnected---蓝牙断开");
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.BLUETOOTH_DISCONNECTED);
        EventBus.getDefault().post(messageEvent);
    }


    //歌词显示
    private static void disposeLyric(String data) {
        try {
            String d = data.substring(2, data.length());
            LogUtils.printI(TAG, "disposeLyric---d=" + d);
            String lyric = StringUtils.hexToString(d);
            LogUtils.printI(TAG, "disposeLyric---lyric=" + lyric);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_LYRIC);
            messageEvent.data = lyric;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeAirflowPatternData(String data) {
        try {
            LogUtils.printI(TAG, "disposeAirflowPatternData----data=" + data);
            data = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 16);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(18, 20);

            LogUtils.printI(TAG, "disposeAirflowPatternData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", data=" + data);
            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + data + end;
            LogUtils.printI(TAG, "disposeAirflowPatternData   ----command=" + command + ", length=" + command.length());
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeInit1E5Data(String data) {
        try {
            LogUtils.printI(TAG, "disposeInit1E5Data----data=" + data);
            String data1e5 = data.substring(2);

            String tag = "1e5";

            LogUtils.printI(TAG, "disposeInit1E5Data----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", data1e5=" + data1e5);
            final String command = Can1E5.COMMAND_1E5_STAT + tag + data1e5;
            LogUtils.printI(TAG, "disposeInit1E5Data   ----command=" + command + ", length=" + command.length());
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeSetFrontSeatWindAutoData(String data) {
        try {
            LogUtils.printI(TAG, "disposeSetFrontSeatWindAutoData----data=" + data);
            String isOpen = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 14);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);

            LogUtils.printI(TAG, "disposeSetFrontSeatWindAutoData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", isOpen=" + isOpen + ", status=" + status);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(16);
            if (BinaryEntity.Value.NUM_1.getValue().equals(isOpen)) {
                binaryEntity.setB2(BinaryEntity.Value.NUM_1);
            } else {
                binaryEntity.setB2(BinaryEntity.Value.NUM_0);
            }

            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + binaryEntity.getHexData() + end;
            LogUtils.printI(TAG, "disposeSetFrontSeatWindAutoData   ----binaryEntity=" + binaryEntity.toString() + ", command=" + command);
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeSetDriverWindAutoData(String data) {
        try {
            LogUtils.printI(TAG, "disposeSetDriverWindAutoData----data=" + data);
            String isOpen = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 14);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);

            LogUtils.printI(TAG, "disposeSetDriverWindAutoData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", isOpen=" + isOpen + ", status=" + status);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(16);
            if (BinaryEntity.Value.NUM_1.getValue().equals(isOpen)) {
                binaryEntity.setB1(BinaryEntity.Value.NUM_1);
            } else {
                binaryEntity.setB1(BinaryEntity.Value.NUM_0);
            }

            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + binaryEntity.getHexData() + end;
            LogUtils.printI(TAG, "disposeSetDriverWindAutoData----binaryEntity=" + binaryEntity.toString() + ", command=" + command);
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //副驾驶风向自动
    private static void disposeSetFrontSeatWinddirAutoData(String data) {
        try {
            LogUtils.printI(TAG, "disposeSetFrontSeatWinddirAutoData----data=" + data);
            String isOpen = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 14);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);

            LogUtils.printI(TAG, "disposeSetFrontSeatWinddirAutoData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", isOpen=" + isOpen + ", status=" + status);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(16);
            if (BinaryEntity.Value.NUM_1.getValue().equals(isOpen)) {
                binaryEntity.setB4(BinaryEntity.Value.NUM_1);
            } else {
                binaryEntity.setB4(BinaryEntity.Value.NUM_0);
            }

            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + binaryEntity.getHexData() + end;
            LogUtils.printI(TAG, "disposeSetFrontSeatWinddirAutoData----binaryEntity=" + binaryEntity.toString() + ", command=" + command);
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //主驾风向自动
    private static void disposeSetDriverWinddirAutoData(String data) {
        try {
            LogUtils.printI(TAG, "disposeSetDriverWinddirAutoData----data=" + data);
            String isOpen = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 14);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);

            LogUtils.printI(TAG, "disposeSetDriverWinddirAutoData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", isOpen=" + isOpen + ", status=" + status);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(16);
            if (BinaryEntity.Value.NUM_1.getValue().equals(isOpen)) {
                binaryEntity.setB3(BinaryEntity.Value.NUM_1);
            } else {
                binaryEntity.setB3(BinaryEntity.Value.NUM_0);
            }

            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + binaryEntity.getHexData() + end;
            LogUtils.printI(TAG, "disposeSetDriverWinddirAutoData----binaryEntity=" + binaryEntity.toString() + ", command=" + command);
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeSetAcCompressorData(String data) {
        try {
            LogUtils.printI(TAG, "disposeSetAcCompressorData----data=" + data);
            String isOpen = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 14);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);

            LogUtils.printI(TAG, "disposeSetAcCompressorData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", isOpen=" + isOpen + ", status=" + status);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(16);
            if (BinaryEntity.Value.NUM_0.getValue().equals(isOpen)) {
                binaryEntity.setB7(BinaryEntity.Value.NUM_0);
            } else {
                binaryEntity.setB7(BinaryEntity.Value.NUM_1);
            }

            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + binaryEntity.getHexData() + end;
            LogUtils.printI(TAG, "disposeSetAcCompressorData----binaryEntity=" + binaryEntity.toString() + ", command=" + command);
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void disposeSetAutoACData(String data) {
        try {
            LogUtils.printI(TAG, "disposeSetAutoACData----data=" + data);
            String isOpen = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 14);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);

            LogUtils.printI(TAG, "disposeSetAutoACData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", isOpen=" + isOpen + ", status=" + status);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(16);
            if (BinaryEntity.Value.NUM_0.getValue().equals(isOpen)) {
                binaryEntity.setB1(BinaryEntity.Value.NUM_0);
                binaryEntity.setB2(BinaryEntity.Value.NUM_0);
                binaryEntity.setB3(BinaryEntity.Value.NUM_0);
                binaryEntity.setB4(BinaryEntity.Value.NUM_0);
            } else {
                binaryEntity.setB1(BinaryEntity.Value.NUM_1);
                binaryEntity.setB2(BinaryEntity.Value.NUM_1);
                binaryEntity.setB3(BinaryEntity.Value.NUM_1);
                binaryEntity.setB4(BinaryEntity.Value.NUM_1);
            }

            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + binaryEntity.getHexData() + end;
            LogUtils.printI(TAG, "disposeSetAutoACData----binaryEntity=" + binaryEntity.toString() + ", command=" + command);
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeACData(String data) {
        try {
            LogUtils.printI(TAG, "disposeACData----data=" + data);
            String isOpen = data.substring(2);

            String tag = Can1E5.AIR_SYS_STATE_VALUE.substring(0, 3);
            String start = Can1E5.AIR_SYS_STATE_VALUE.substring(4, 14);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);

            LogUtils.printI(TAG, "disposeACData----can1e5=" + Can1E5.AIR_SYS_STATE_VALUE + ", isOpen=" + isOpen + ", status=" + status);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            String end = Can1E5.AIR_SYS_STATE_VALUE.substring(16);
            if (BinaryEntity.Value.NUM_0.getValue().equals(isOpen)) {
                binaryEntity.setB5(BinaryEntity.Value.NUM_0);
            } else {
                binaryEntity.setB5(BinaryEntity.Value.NUM_1);
            }

            final String command = Can1E5.COMMAND_1E5_STAT + tag + start + binaryEntity.getHexData() + end;
            LogUtils.printI(TAG, "disposeACData----binaryEntity=" + binaryEntity.toString() + ", command=" + command);
            if (!TextUtils.isEmpty(command)) {
                executeCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disposeCan1DCData(String data) {
        try {
            String d = data.substring(2, data.length());
            LogUtils.printI(TAG, "disposeCan1DCData----data=" + d);

//            String d1 = d.substring(0,2); //主驾温度
//            String d2 = d.substring(2,4); //副驾温度
//            String d3 = d.substring(4,6); //主驾风速
//            String d4 = d.substring(6,8); //副驾风速
//            String d5 = d.substring(10,12);
//            String d6 = d.substring(12,14);
//
//            setupMainDriverTempSize();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void disposeMapData(String data) {
        String d = data.substring(2, data.length());
        LogUtils.printI(TAG, "disposeMapData---d=" + d);
        try {
            MeterActivity.drivingDirection = d;
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_DRIVER_DIRECTION);
            messageEvent.data = d;
            EventBus.getDefault().post(messageEvent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void disposeLanguageTypeData(String data) {
        try {
            String typeStr = data.substring(3);
            LogUtils.printI(TAG, "disposeLanguageTypeData---data=" + data + ", typeStr=" + typeStr);
            int type = Integer.parseInt(typeStr);
            if (type >= 0) {
                if (type == LanguageType.EN.ordinal()) {
                    SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_UNIT_TYPE, UnitType.MI.ordinal());
                } else if (type == LanguageType.ZH.ordinal()) {
                    SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_UNIT_TYPE, UnitType.KM.ordinal());
                } else if (type == LanguageType.SYSTEM.ordinal()) {
                    if (LanguageUtils.isCN()) {
                        SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_UNIT_TYPE, UnitType.KM.ordinal());
                    } else {
                        SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_UNIT_TYPE, UnitType.MI.ordinal());
                    }
                }
                SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_SELECT_LANGUAGE, type);
//                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CHANGE_LANGUAGE));

                new Thread(() -> {
                    try {
                        Thread.sleep(300);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FuncUtil.sendShellCommand("reboot");
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description:
     * @createDate: 2023/9/12
     */
    private static void disposeSetUnitData(String data) {
        try {
            String typeStr = data.substring(2);
            LogUtils.printI(TAG, "disposeSetUnitData---type=" + typeStr);
            SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_UNIT_TYPE, Integer.parseInt(typeStr));
            new Thread(() -> {
                try {
                    Thread.sleep(300);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                FuncUtil.sendShellCommand("reboot");
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @description: 处理车辆型号
     * @createDate: 2023/7/17
     */
    private static void disposeCarTypeData(String data) {
        try {
            String typeStr = data.substring(2).substring(1);
            LogUtils.printI(TAG, "disposeCarTypeData---type=" + typeStr);
            MeterActivity.carType = Integer.valueOf(typeStr);
            SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_CAR_TYPE, MeterActivity.carType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @description: 处理安全带辅助
     * @createDate: 2023/7/17
     */
    private static void disposeSafeBeltData(String data) {
        try {
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SAFE_BELT_SWITCH);
            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeSafeBeltData---status=" + status);
            if ("01".equals(status)) {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_SAFE_BELT_SWITCH, true);
                messageEvent.data = true;
            } else {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_SAFE_BELT_SWITCH, false);
                messageEvent.data = false;
            }
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 恢复头枕放倒
     * @createDate: 2023/7/17
     */
    private static void disposeResumeHeadrestData() {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.RESYME_HEADREST_DOWN);
        EventBus.getDefault().post(messageEvent);
    }

    /**
     * @description: 处理头枕放倒
     * @createDate: 2023/7/17
     */
    private static void disposeHeadrestData() {
        try {
            boolean isOpen = SPUtils.getBoolean(App.getGlobalContext(), SPUtils.SP_HEADREST_DOWN, true);
            LogUtils.printI(TAG, "disposeHeadrestData---isOpen=" + isOpen);
            if (isOpen) {
                int count = SPUtils.getInt(App.getGlobalContext(), SPUtils.SP_HEADREST_DOWN_COUNT, 0);
                LogUtils.printI(TAG, "disposeHeadrestData---count=" + count);
                SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_HEADREST_DOWN_COUNT, ++count);

                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_HEADREST_DOWN);
                EventBus.getDefault().post(messageEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置原车公里数
     * @createDate: 2023/7/15
     */
    private static void disposeOriginalCarKM(String data) {
        try {
            String kmData = data.substring(2);
            LogUtils.printI(TAG, "disposeOriginalCarKM---kmData=" + kmData);
            String[] kmArr = kmData.split("E");
            final String km;
            if (kmArr.length == 1) {
                km = kmArr[0];
            } else if (kmArr.length == 2) {
                km = kmArr[0] + "." + kmArr[1];
            } else {
                km = "";
            }
            if (!TextUtils.isEmpty(km)) {
                new Thread(() -> {
                    try {
//                        Mile mile = FuncUtil.mileDaoUtil.queryMile();
//                        mile.setTotleMile(km);

                        CarTravelTable travelTable = CarTravelRepository.getInstance().getData(App.getGlobalContext(), AppUtils.getDeviceId(App.getGlobalContext()));
                        if (travelTable != null) {
                            float kmFloat = Float.parseFloat(km);
                            int unitType = SPUtils.getInt(App.getGlobalContext(), SPUtils.SP_UNIT_TYPE, UnitType.getDefaultType());
                            if (unitType == UnitType.MI.ordinal()) {
                                kmFloat = UnitUtils.miToKm(kmFloat);
                            }
                            travelTable.setTotalMile(kmFloat);
                            CarTravelRepository.getInstance().updateData(App.getGlobalContext(), travelTable);

                            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_TOTAL_MILE);
                            messageEvent.data = travelTable;
                            EventBus.getDefault().post(messageEvent);
                        }
//                        LogUtils.printI(TAG, "disposeOriginalCarKM---mile="+mile);
//                        FuncUtil.mileDaoUtil.insert(mile);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @description: 设置头枕放倒开关
     * @createDate: 2023/6/13
     */
    private static void disposeHeadrestSwitchData(String data) {
        try {


            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeHeadrestData---status=" + status);
            if ("01".equals(status)) {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_HEADREST_DOWN, true);
            } else if ("00".equals(status)) {
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_HEADREST_DOWN, false);

                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.RESYME_HEADREST_DOWN);
                EventBus.getDefault().post(messageEvent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置警报音量大小
     * @createDate: 2023/6/10
     */
    private static void disposeAlarmVolumeData(String data) {
        try {
            String volumeStr = data.substring(2);
            LogUtils.printI(TAG, "disposeAlarmVolumeData---volumeStr=" + volumeStr);

            int volume = Integer.parseInt(volumeStr);

            if (volume > 100) {
                volume = 100;
            } else if (volume < 0) {
                volume = 0;
            }
            SPUtils.putInt(App.getGlobalContext(), SPUtils.SP_CAR_ALARM_VOLUME, volume);

            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.UPDATE_ALARM_VOLUME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置环境照明
     * @createDate: 2023/6/6
     */
    private static void disposeEnvirLightingData(String data) {
        try {
            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeEnvirLightingData---status=" + status);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_CAR_ENVIR_LIGHTING);
            messageEvent.data = status;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置外部照明
     * @createDate: 2023/6/6
     */
    private static void disposeOuterLightingData(String data) {
        try {
            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeOuterLightingData---status=" + status);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_CAR_OUTER_LIGHTING);
            messageEvent.data = status;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置内部照明
     * @createDate: 2023/6/6
     */
    private static void disposeInnerLightingData(String data) {
        try {
            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeInnerLightingData---status=" + status);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_CAR_INNER_LIGHTING);
            messageEvent.data = status;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置背景照明
     * @createDate: 2023/6/6
     */
    private static void disposeCarBgLightingData(String data) {
        try {
            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeCarBgLightingData---status=" + status);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_CAR_BG_LIGHTING);
            messageEvent.data = status;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置上下车辅助状态
     * @createDate: 2023/6/6
     */
    private static void disposeCarAssistData(String data) {
        try {
            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeCarAssistData---status=" + status);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_CAR_ASSIST);
            messageEvent.data = status;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置自动落锁开关状态
     * @createDate: 2023/6/6
     */
    private static void disposeAutoLockData(String data) {
        try {
            String status = data.substring(2);
            LogUtils.printI(TAG, "disposeAutoLockData---status=" + status);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_AUTO_LOCK);
            messageEvent.data = status;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 设置锁车时自动合拢， 后视镜下降, 后舱盖高度限制
     * @createDate: 2023/6/2
     */
    private static void disposeCan35dD5Data(String data) {
        try {
            //d5 第二个数
            String can35dD5 = data.substring(2);

            LogUtils.printI(TAG, "disposeCan35dD5Data---can35dD5=" + can35dD5);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_CAN35D_D5);
            messageEvent.data = can35dD5;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 处理前后空调开关
     * @createDate: 2023/6/1
     */
    private static void disposeFrontBackSwitch(String data) {
        try {
            final String comm1 = data.substring(2);

            LogUtils.printI(TAG, "disposeFrontBackSwitch----comm1=" + comm1);
            List<String> comm = Can1E5.E5;
            if (comm != null && comm.size() > 0) {
                String bindata = CommonUtil.convertHexToBinary(comm.get(7));
                LogUtils.printI(TAG, "disposeFrontBackSwitch----bindata=" + bindata);
                String bin = "";
                if (comm1.equals("00")) {
                    bin = "0" + bindata.substring(1);
                }
                if (comm1.equals("01")) {
                    bin = "1" + bindata.substring(1);
                }

                String hex = CommonUtil.binaryString2hexString(bin).toUpperCase();


                final String command16 = "AA000008000001E5" + comm.get(2) + comm.get(3) + comm.get(4) + comm.get(5) + comm.get(6) + hex + comm.get(8) + comm.get(9);

                LogUtils.printI(TAG, "disposeFrontBackSwitch----hex=" + hex + ", command16=" + command16);
                byte[] bytes16 = FuncUtil.toByteArray(command16);
                MUsb1Receiver.write(bytes16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 处理驾驶模式
     * @createDate: 2023/6/1
     */
    private static void disposeDriveMode(String data) {
        //01 经典 M 00 舒适 02 C  运动 s
        try {
            ACache cache = ACache.get(App.getGlobalContext());
            String command = data.substring(2, data.length());

            LogUtils.printI(TAG, "disposeDriveMode---command=" + command);
            String driveMode = null;
            if (command.equals("01")) {
                driveMode = DriveModeStatus.M.getValue();
            }
            if (command.equals("02")) {
                driveMode = DriveModeStatus.S.getValue();
            }
            if (command.equals("00")) {
                driveMode = DriveModeStatus.C.getValue();
            }
            cache.put("CMS", driveMode);

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_DRIVE_MODE);
            messageEvent.data = driveMode;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @description: 设置气流模式
     * @createDate: 2023/5/2
     */
    private static void setupAirflowMode(String data) {
        String mode = "0";
        if (data.length() > 2) {
            mode = data.substring(2);
        }
        LogUtils.printI(BenzHandlerData.class.getName(), "setupAirflowMode----mode=" + mode);
        final String command = AirCommandUtils.buildAirflowMode(Can1E5.AIR_SYS_STATE_VALUE, mode);
        if (!TextUtils.isEmpty(command)) {
            executeCommand(command);
        }
    }

    /**
     * @description: 主驾温度设置
     * @createDate: 2023/5/1
     */
    private static void setupMainDriverTempSize(String data) {
        String size = "0";
        if (data.length() > 2) {
            size = data.substring(2);
        }
        LogUtils.printI(BenzHandlerData.class.getName(), "setupCopilotTempSize----size=" + size);
        final String command = AirCommandUtils.buildMainDriverTempSize(Can20b.AIR_SYS_STATE_VALUE_20B, size);
        if (!TextUtils.isEmpty(command)) {
            executeCommand(command);
        }
    }

    /**
     * @description: 副驾温度大小
     * @createDate: 2023/5/1
     */
    private static void setupCopilotTempSize(String data) {
        String size = "0";
        if (data.length() > 2) {
            size = data.substring(2);
        }
        LogUtils.printI(BenzHandlerData.class.getName(), "setupCopilotTempSize----size=" + size);
        String command = AirCommandUtils.buildCopilotTempSize(Can20b.AIR_SYS_STATE_VALUE_20B, size);
        if (!TextUtils.isEmpty(command)) {
            executeCommand(command);
        }
    }

    /**
     * @description: 设置副驾风速等级
     * @createDate: 2023/5/1
     */
    private static void setupCopilotWindLevel(String data) {
        String level = "0";
        if (data.length() > 2) {
            level = data.substring(2);
        }
        LogUtils.printI(BenzHandlerData.class.getName(), "setupCopilotWindLevel----windLevel=" + level);
        final String command = AirCommandUtils.buildCopilotWindLevel(Can20b.AIR_SYS_STATE_VALUE_20B, level);
        if (!TextUtils.isEmpty(command)) {
            executeCommand(command);
        }
    }


    /**
     * @description: 设置主驾风速等级
     * @createDate: 2023/5/1
     */
    private static void setupMainDriverWindLevel(String data) {
        String level = "0";
        if (data.length() > 2) {
            level = data.substring(2);
        }
        LogUtils.printI(BenzHandlerData.class.getName(), "setupMainDriverWindLevel----windLevel=" + level);
        String command = AirCommandUtils.buildMainDriverWindLevel(Can20b.AIR_SYS_STATE_VALUE_20B, level);
        if (!TextUtils.isEmpty(command)) {
            executeCommand(command);
        }
    }

    /**
     * @description: 设置主驾气流模式
     * @createDate: 2023/5/1
     */
    private static void setupMainDriverAirflow(String data) {
        String type = "0";
        if (data.length() > 2) {
            type = data.substring(2);
        }
        LogUtils.printI(BenzHandlerData.class.getName(), "setupMainDriverAirflow----type=" + type);
        final String command = AirCommandUtils.buildMainDriverAirflow(Can1E5.AIR_SYS_STATE_VALUE, type);
        if (!TextUtils.isEmpty(command)) {
            executeCommand(command);
        }
    }

    /**
     * @description: 设置副驾气流模式
     * @createDate: 2023/5/1
     */
    private static void setupCopilotAirflow(String data) {
        String type = "0";
        if (data.length() > 2) {
            type = data.substring(2);
        }
        LogUtils.printI(BenzHandlerData.class.getName(), "setupCopilotAirflow----type=" + type);
        final String command = AirCommandUtils.buildCopilotAirflow(Can1E5.AIR_SYS_STATE_VALUE, type);
        if (!TextUtils.isEmpty(command)) {
            executeCommand(command);
        }
    }

    private static synchronized void executeCommand(final String command) {
        byte[] bytes5 = FuncUtil.toByteArray(command.toUpperCase());
        MUsb1Receiver.write(bytes5);
    }


    public static void handlerCan(String da) {
        pre.append(da);
        String ss = pre.toString();
        if (ss.endsWith("AA0000")) {
            String[] datastring = ss.split("AA0000");
            for (String str : datastring) {
                String canstr = "AA0000" + str;
                if (canstr.length() == 32 && canstr.startsWith("AA0000")) {
                    List<String> data = toHexString(canstr);
                    CanParent canParent = FuncUtil.canHandler.get(data.get(0));
                    if (canParent != null && FuncUtil.SENDFLG) {
                        canParent.handlerCan(data);
                    }
//                    System.out.println("candata================2================"+data);
                }
            }
            pre.setLength(0);
            pre.append("AA0000");
        } else {
            String[] datastring = ss.split("AA0000");
            String dataString2 = "AA0000" + datastring[datastring.length - 1];
            if (dataString2.length() == 32) {
                for (int i = 0; i < datastring.length; i++) {
                    String canstr = "AA0000" + datastring[i];
                    if (canstr.length() == 32 && canstr.startsWith("AA0000")) {
                        List<String> data = toHexString(canstr);

                        CanParent canParent = FuncUtil.canHandler.get(data.get(0));
                        if (canParent != null && FuncUtil.SENDFLG) {
                            canParent.handlerCan(data);
                        }
//                            System.out.println("candata================2================"+data);
                    }
                }
                pre.setLength(0);
            } else {
                for (int i = 0; i < datastring.length - 1; i++) {
                    String canstr = "AA0000" + datastring[i];
                    if (canstr.length() == 32 && canstr.startsWith("AA0000")) {
                        List<String> data = toHexString(canstr);
                        CanParent canParent = FuncUtil.canHandler.get(data.get(0));
                        if (canParent != null && FuncUtil.SENDFLG) {
                            canParent.handlerCan(data);
                        }
//                            System.out.println("candata================3================"+data);
                    }
                }
                pre.setLength(0);
                pre.append(dataString2);
            }
        }
    }

    private static List<String> toHexString(String hex) {
        ArrayList<String> arr = new ArrayList<String>();

        String h = hex.substring(12, 16);

        int hh = Integer.parseInt(h, 16);
        String hhx = Integer.toHexString(hh);

        String len = hex.substring(7, 8);

        arr.add(hhx);
        arr.add(len);
        String temp = "";
        for (int i = 16; i < hex.length(); i++) {

            if (i % 2 == 0) {//每隔两个
                temp = hex.charAt(i) + "";
            } else {
                temp = temp + hex.charAt(i);
            }

            if (temp.length() == 2) {
                arr.add(temp);
            }
        }
        return arr;
    }


}
