package com.android.launcher.can;//package com.car.left.can;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//定速巡航
public class Can378 implements CanParent {

    //37880000000051000000

    //00 00 00 00 51 00 00 00 等待设定,没有开启的状态
    //00 3C 00 00 51 3C 00 00 没有开启的状态
    //00 5B 00 00 45 5B 00 00 正在定速巡航
    //00 5A 00 00 51 5A 00 00 取消定速巡航
    //80 3c 00 00 45 46 00 00 正在开启
    //00 46 00 00 45 46 00 00  开启状态, 两个46为车速
    //[378, 8, 80, 25, 00, 00, 63, 25, 00, 00] //63等待设定

    public static volatile String lastData = "";
    private String lastErrorStatus = "";
    private int lastSetSpeed = 0;

    @Override
    public void handlerCan(List<String> msg) {
        try {
            String sendData = String.join("", msg);
            if(lastData.equals(sendData)){
                return;
            }
            lastData = sendData;

            String errorStatus = msg.get(2);
            LogUtils.printI(Can378.class.getSimpleName(), "handlerCan-----" + msg +", errorStatus="+errorStatus);

            String onoroff = msg.get(6);
            if (onoroff.equals("51")) { //非定速巡航模式
//                if (isCloseCruiseControl(msg)) {
//                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_OFF));
//                } else if (isCancelCruiseControl(msg)) {
//                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_OFF));
//
                if (!lastErrorStatus.equals(errorStatus)) {
                    lastErrorStatus = errorStatus;
                    if ("0C".equalsIgnoreCase(errorStatus)) { //定速巡航停止运作
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_STOP));
                    } else if ("03".equals(errorStatus)) { //出现故障
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_STOP));
                    }else{
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_OFF));
                    }
                }else{
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_OFF));
                }
            } else if (onoroff.equals("45")) { // 定速巡航打开
                if ("0C".equalsIgnoreCase(errorStatus)) { //定速巡航停止运作
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_STOP));
                } else if ("03".equals(errorStatus)) { //出现故障
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_STOP));
                }else{
                    String speed = msg.get(3);
                    int ss = 0; //车速
                    try {
                        ss = Integer.parseInt(speed, 16);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_ON);
                    messageEvent.data = ss;
                    EventBus.getDefault().post(messageEvent);
                }


            }else if(onoroff.equals("63")){ //等待设定
                if ("0C".equalsIgnoreCase(errorStatus)) { //定速巡航停止运作
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_STOP));
                } else if ("03".equals(errorStatus)) { //出现故障
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_STOP));
                }else{
                    //判断是否是等待设定
                    String waitSetStatusTag = getWaitSetTag(msg.get(2));
                    if ("0".equals(waitSetStatusTag)) { //等待设定
                        disposeWaitSetup(msg, waitSetStatusTag);
                    } else {
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_OFF));
                    }
                }
            }else if(onoroff.equals("44")){ //限速模式  [378, 8, 00, 1E, 00, 00, 44, 1E, 00, 00]
                int speed = Integer.parseInt(msg.get(3), 16);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SPEED_LIMITED_MODE);
                messageEvent.data = speed;
                EventBus.getDefault().post(messageEvent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @description: 是否取消定速巡航
     * @createDate: 2023/6/1
     */
    private boolean isCancelCruiseControl(List<String> msg) {
        try {
            String leftFlag = msg.get(3);
            String rightFlag = msg.get(7);
            String flag = "5A";
            LogUtils.printI(Can378.class.getSimpleName(), "isCancelCruiseControl----leftFlag=" + leftFlag + ", rightFlag=" + rightFlag);
            if (flag.equalsIgnoreCase(leftFlag) && flag.equalsIgnoreCase(rightFlag)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @description: 定速巡航是否是关闭状态
     * @createDate: 2023/6/1
     */
    private boolean isCloseCruiseControl(List<String> msg) {
        try {
            String leftFlag = msg.get(3);
            String rightFlag = msg.get(7);
            String flag = "3C";
            LogUtils.printI(Can378.class.getSimpleName(), "isCloseCruiseControl----leftFlag=" + leftFlag + ", rightFlag=" + rightFlag);
            if (flag.equalsIgnoreCase(leftFlag) && flag.equalsIgnoreCase(rightFlag)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void disposeWaitSetup(List<String> msg, String waitSetStatusTag) {
        try {
            int setupSpeed = getSetupSpeed(msg);
            LogUtils.printI(Can378.class.getSimpleName(), "disposeWaitSetup---setupSpeed=" + setupSpeed + ", lastSetSpeed=" + lastSetSpeed + ", waitSetStatusTag" + waitSetStatusTag);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.CRUISE_CONTROL_WAIT_SET);
            messageEvent.data = setupSpeed;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 获取设置的巡航车速
     * @createDate: 2023/5/31
     */
    private int getSetupSpeed(List<String> msg) {
        try {
            String setupSpeedStr = msg.get(3);
            return Integer.parseInt(setupSpeedStr, 16);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @description: 获取等待设定的标志
     * @createDate: 2023/5/31
     */
    @Nullable
    private String getWaitSetTag(String status) {
        String waitSetStatusTag = null;
        try {
            Integer value = Integer.valueOf(status);
            String waitSetStatus = String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0');
            LogUtils.printI(Can378.class.getSimpleName(), "getWaitSetTag-----waitSetStatus=" + waitSetStatus);

            int waitSetIndex = waitSetStatus.length() - 1;
            waitSetStatusTag = waitSetStatus.substring(waitSetIndex);
            LogUtils.printI(Can378.class.getSimpleName(), "getWaitSetTag-----waitSetStatusTag=" + waitSetStatusTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return waitSetStatusTag;
    }

    public void clear() {
        lastData = "";
        lastErrorStatus = "";
        lastSetSpeed = 0;
    }
}
