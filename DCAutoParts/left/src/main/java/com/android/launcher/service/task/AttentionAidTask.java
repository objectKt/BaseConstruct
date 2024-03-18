package com.android.launcher.service.task;

import android.content.Context;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.service.LivingService;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.DateUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

/**
 * 注意力辅助提示
 * @date： 2024/1/3
 * @author: 78495
*/
public class AttentionAidTask extends TaskBase{

    public AttentionAidTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
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
