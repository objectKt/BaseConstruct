package test;

import android.os.Handler;
import android.os.Looper;

import com.android.launcher.MessageEvent;
import com.android.launcher.type.SteerWheelKeyType;

import org.greenrobot.eventbus.EventBus;

/**
 * 时速校准测试
 * @date： 2023/11/22
 * @author: 78495
*/
public class ClassicSpeedAdjustMenuTest {

    public static void start(){
        long startTime = 3000;

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
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_LEFT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_LEFT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_BACK.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime += 1000);
    }
}
