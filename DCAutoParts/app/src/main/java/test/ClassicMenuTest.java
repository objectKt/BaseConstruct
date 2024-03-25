package test;

import android.os.Handler;
import android.os.Looper;

import com.android.launcher.MessageEvent;
import com.android.launcher.type.SteerWheelKeyType;

import org.greenrobot.eventbus.EventBus;

public class ClassicMenuTest {

    public static void start(){
        long startTime = 5000;

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 2000);
//
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 2000);
//
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 2000);
//
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);
//
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
//                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
//                EventBus.getDefault().post(messageEvent);
//            }
//        }, startTime += 1000);


    }
}
