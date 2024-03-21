package test;

import android.os.Handler;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.type.SteerWheelKeyType;

import org.greenrobot.eventbus.EventBus;

import static test.DataTestManager.sendKey;

public class SportMeterTest {
    static long startTime = 3000;

    public static void start(){

        classicToSport();

//        menu();
    }

    private static void menu() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        },startTime);
        startTime += 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        },startTime);
        startTime += 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        },startTime);
        startTime += 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        },startTime);
        startTime += 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        },startTime);
        startTime += 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        },startTime);
        startTime += 1000;

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
//                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
//                EventBus.getDefault().post(messageEvent);
//            }
//        },startTime);
//        startTime += 1000;
    }

    private static void classicToSport() {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                startTime += 1000;
                sendKey(SteerWheelKeyType.KEY_OK);


                startTime += 1000;
                sendKey(SteerWheelKeyType.KEY_LEFT);


                startTime += 2000;
                sendKey(SteerWheelKeyType.KEY_OK);
            }
        }).start();

    }



}
