package test;

import android.os.Handler;
import android.os.Looper;

import com.android.launcher.MessageEvent;
import com.android.launcher.type.SteerWheelKeyType;

import org.greenrobot.eventbus.EventBus;

public class TechMenuTest {

   private static long startTime = 5000;

    public static void start(){

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_LEFT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_LEFT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);


        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);


        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);


        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);


        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);


        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_LEFT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);


        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);

        startTime += 1000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
                EventBus.getDefault().post(messageEvent);
            }
        }, startTime);
    }
}
