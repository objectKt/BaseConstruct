package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.service.LivingService;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.DateUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

/**
 * 定时更新发动机转速和车速任务
 * @date： 2024/1/3
 * @author: 78495
*/
public class UpdateCarSpeedUITask extends TaskBase{


    public UpdateCarSpeedUITask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            MessageEvent carSpeedMessage = new MessageEvent(MessageEvent.Type.UPDATE_CAR_SPEED);
            EventBus.getDefault().post(carSpeedMessage);

            MessageEvent engineSpeedMessage = new MessageEvent(MessageEvent.Type.UPDATE_ENGINE_SPEED);
            EventBus.getDefault().post(engineSpeedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            float hour = DateUtils.computeHourNumber(LivingService.launchCarRunTime);

            LogUtils.printI(TAG, "startAttentionAidTask----hour=" + hour + ", launchCarRunTime=" + LivingService.launchCarRunTime);
            if (hour >= 3.0f) { //提示注意力辅助系统
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_teacup);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
                alertVo.setAlertMessage(context.getResources().getString(R.string.attention_assist_system_warn));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
