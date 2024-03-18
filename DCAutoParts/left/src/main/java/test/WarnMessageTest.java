package test;

import android.os.Handler;
import android.os.Looper;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.can.Can379;
import com.android.launcher.can.CanParent;
import com.android.launcher.handler.HandlerDoor;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.type.CarType;
import com.android.launcher.type.GearsType;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

/**
 * @date： 2023/11/30
 * @author: 78495
*/
public class WarnMessageTest {

    public static void start(){

        long startTime = 3000;

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_brake_oil);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_40);

                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime + 2000);

//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo = new AlertVo();
//                alertVo.setAlertImg(R.drawable.ic_car_key);
//                alertVo.setAlertMessage(AlertMessage.ALERT_2F8_2_40);
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo;
//                EventBus.getDefault().post(messageEvent);
//            }
//        }, 3000);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo = new AlertVo();
//                alertVo.setAlertImg(R.drawable.ic_antifreeze_solution);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_10);
//
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo;
//                EventBus.getDefault().post(messageEvent);
//            }
//        }, 3000);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo = new AlertVo();
//                alertVo.setAlertImg(R.drawable.ic_fuel_filter);
//                alertVo.setAlertColor(AlertMessage.TEXT_WHITE + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_33D_01);
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo;
//                EventBus.getDefault().post(messageEvent);
//            }
//        }, 3000);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo2 = new AlertVo();
//                alertVo2.setAlertImg(R.drawable.ic_air_filter);
//                alertVo2.setAlertColor(AlertMessage.TEXT_WHITE + "");
//                alertVo2.setAlertMessage(AlertMessage.ALERT_33D_40);
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo2;
//                EventBus.getDefault().post(messageEvent);
//            }
//        }, 3000);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo4 = new AlertVo();
//                alertVo4.setAlertImg(R.drawable.ic_fuel_tank_cap);
//                alertVo4.setAlertColor(AlertMessage.TEXT_WHITE + "");
//                alertVo4.setAlertMessage(AlertMessage.ALERT_33D_04);
//                MessageEvent  messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo4;
//                EventBus.getDefault().post(messageEvent);
//            }
//        }, 4000);
////
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo3 = new AlertVo();
//                alertVo3.setAlertImg(R.drawable.ic_water_temp_high);
//                alertVo3.setAlertColor(AlertMessage.TEXT_WHITE + "");
//                alertVo3.setAlertMessage(AlertMessage.ALERT_33D_80);
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo3;
//                EventBus.getDefault().post(messageEvent);
//            }
//        }, 4000);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.ic_glass_of_water);
//                alertVo.setAlertColor(AlertMessage.TEXT_WHITE+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_20);
//
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo;
//                EventBus.getDefault().post(messageEvent);
//            }
//        },3100);
//
////
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_10);
//
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                messageEvent.data = alertVo;
//                EventBus.getDefault().post(messageEvent);
//            }
//        },3100);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                HandlerDoor door = (HandlerDoor) CommonUtil.meterHandler.get("door");
//                door.handlerData("96");
//            }
//        }, 6000);
//
        testGears();
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                HandlerDoor door = (HandlerDoor) CommonUtil.meterHandler.get("door");
//                door.handlerData("55");
//            }
//        },8000);


        //测试底盘升降
        testCan379S500();
//        testCan379S600();
//        testCan379Other();

//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
//                messageEvent.data = SteerWheelKeyType.KEY_BACK.ordinal();
//                EventBus.getDefault().post(messageEvent);
//            }
//        },12000);

//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
//                messageEvent.data = SteerWheelKeyType.KEY_BACK.ordinal();
//                EventBus.getDefault().post(messageEvent);
//            }
//        },16000);

//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
//                messageEvent.data = SteerWheelKeyType.KEY_BACK.ordinal();
//                EventBus.getDefault().post(messageEvent);
//            }
//        },20000);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
//                messageEvent.data = SteerWheelKeyType.KEY_BACK.ordinal();
//                EventBus.getDefault().post(messageEvent);
//            }
//        },24000);
    }

    private static void testCan379Other() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "10", "00", "00", "00", "00", "00", "00", "00");

                MeterActivity.carType = CarType.S300.ordinal();
                Can379 canParent = (Can379) FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 6000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "01", "01", "00", "00", "00", "00", "00", "00");

                CanParent canParent = FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 10000);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "02", "01", "00", "00", "00", "00", "00", "00");

                CanParent canParent = FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 14000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "04", "01", "00", "00", "00", "00", "00", "00");

                CanParent canParent = FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 18000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "00", "01", "00", "00", "00", "00", "00", "00");

                CanParent canParent = FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 22000);

    }

    private static void testCan379S600() {
        //[379, 2, 00, 01, 00, 00, 00, 00, 00, 00]
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MeterActivity.carType = CarType.S600.ordinal();
                List<String> list = Arrays.asList("379", "2", "10", "00", "00", "00", "00", "00", "00", "00");

                Can379 canParent = (Can379) FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 6000);

        //[379, 2, 00, 01, 00, 00, 00, 00, 00, 00]
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "10", "01", "00", "00", "00", "00", "00", "00");

                Can379 canParent = (Can379) FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 8000);

    }

    private static void testCan379S500() {
        //[379, 2, 00, 01, 00, 00, 00, 00, 00, 00]
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "10", "00", "00", "00", "00", "00", "00", "00");

                Can379 canParent = (Can379) FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 6000);

        //[379, 2, 00, 01, 00, 00, 00, 00, 00, 00]
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "10", "01", "00", "00", "00", "00", "00", "00");

                Can379 canParent = (Can379) FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 7000);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = Arrays.asList("379", "2", "10", "00", "00", "00", "00", "00", "00", "00");

                Can379 canParent = (Can379) FuncUtil.canHandler.get("379");
                canParent.handlerCan(list);
            }
        }, 10000);
    }

    private static void testGears() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                HandlerDoor door = (HandlerDoor) CommonUtil.meterHandler.get("door");
                door.handlerData("66");
            }
        }, 5000);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
                messageEvent.data = GearsType.TYPE_N;
                EventBus.getDefault().post(messageEvent);
            }
        }, 3000);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
                messageEvent.data = GearsType.TYPE_R;
                EventBus.getDefault().post(messageEvent);
            }
        }, 5000);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
                messageEvent.data = GearsType.TYPE_D;
                EventBus.getDefault().post(messageEvent);
            }
        }, 7000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
                messageEvent.data = GearsType.TYPE_P;
                EventBus.getDefault().post(messageEvent);
            }
        }, 9000);
    }
}
