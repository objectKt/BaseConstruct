package com.android.launcher.handler;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.entity.LampShowStatus;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 灯光状态
 */
public class HandlerLampStatus implements HandlerInteface {

    private LampShowStatus lampShowStatus = new LampShowStatus();

    @Override
    public void handlerData(final String msg) {

        LogUtils.printI(HandlerLampStatus.class.getSimpleName(), "handlerData----msg="+msg);

        try {
            disposeData(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disposeData(String msg) {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_LAMP);
        MessageEvent clearanceLampMessage = new MessageEvent(MessageEvent.Type.SHOW_CLEARANCE_LAMP);

        String lampSpin = msg ;

        if(lampSpin.equals("off")){

            clearanceLampMessage.data = false;
            EventBus.getDefault().post(clearanceLampMessage);

            lampShowStatus.setLightAutoShow(false);
            //前雾灯
            lampShowStatus.setFrontFogLampShow(false);
            //远光
            lampShowStatus.setHighBeamShow(false);
            //近光
            lampShowStatus.setDippedHeadlightShow(false);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        } else if(lampSpin.equals("width")){
            //                    示宽灯打开
            clearanceLampMessage.data = true;
            EventBus.getDefault().post(clearanceLampMessage);

            lampShowStatus.setLightAutoShow(false);
            //前雾灯
            lampShowStatus.setFrontFogLampShow(false);
            //后雾灯
            lampShowStatus.setRearFogLampShow(false);
            //远光
            lampShowStatus.setHighBeamShow(false);
            //近光
            lampShowStatus.setDippedHeadlightShow(false);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        } else if(lampSpin.equals("widthfogf")){
            //示宽灯+前雾灯

            clearanceLampMessage.data = true;
            EventBus.getDefault().post(clearanceLampMessage);

            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setFrontFogLampShow(true);
            lampShowStatus.setRearFogLampShow(false);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        } else if(lampSpin.equals("widthfogb")){
            //示宽灯+后雾灯
            clearanceLampMessage.data = true;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setRearFogLampShow(true);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        } else if(lampSpin.equals("lowwidth")){
            //近光+示宽灯
            clearanceLampMessage.data = true;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setRearFogLampShow(false);
            lampShowStatus.setFrontFogLampShow(false);
            lampShowStatus.setHighBeamShow(false);
            lampShowStatus.setDippedHeadlightShow(true);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        }else if(lampSpin.equals("lowwidthfogf")){
            //近光+前雾灯+示宽灯
            clearanceLampMessage.data = true;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setRearFogLampShow(false);
            lampShowStatus.setFrontFogLampShow(true);
            lampShowStatus.setHighBeamShow(false);
            lampShowStatus.setDippedHeadlightShow(true);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        }else if(lampSpin.equals("lowwidthfogb")){
            //近光+后雾灯+示宽灯
            clearanceLampMessage.data = true;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setRearFogLampShow(true);
            lampShowStatus.setFrontFogLampShow(false);
            lampShowStatus.setHighBeamShow(false);
            lampShowStatus.setDippedHeadlightShow(true);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        }else if(lampSpin.equals("low")){
            // 近光亮起
            clearanceLampMessage.data = false;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setRearFogLampShow(false);
            lampShowStatus.setFrontFogLampShow(false);
            lampShowStatus.setHighBeamShow(false);
            lampShowStatus.setDippedHeadlightShow(true);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        }else if(lampSpin.equals("lowfogf")){
            // 近光亮起+前雾灯亮起
            clearanceLampMessage.data = false;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setRearFogLampShow(false);
            lampShowStatus.setFrontFogLampShow(true);
            lampShowStatus.setHighBeamShow(false);
            lampShowStatus.setDippedHeadlightShow(true);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        }else if(lampSpin.equals("lowfogb")){
            // 近光亮起+前雾灯亮起
            clearanceLampMessage.data = false;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(false);
            lampShowStatus.setRearFogLampShow(false);
            lampShowStatus.setFrontFogLampShow(true);
            lampShowStatus.setHighBeamShow(false);
            lampShowStatus.setDippedHeadlightShow(true);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        }else if (lampSpin.equals("auto")){
            clearanceLampMessage.data = false;
            EventBus.getDefault().post(clearanceLampMessage);


            lampShowStatus.setLightAutoShow(true);
            lampShowStatus.setRearFogLampShow(false);
            lampShowStatus.setFrontFogLampShow(false);
            lampShowStatus.setHighBeamShow(false);
            lampShowStatus.setDippedHeadlightShow(false);
            messageEvent.data = lampShowStatus;
            EventBus.getDefault().post(messageEvent);

        }
    }
}
