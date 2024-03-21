package com.android.launcher.service.task;

import android.content.Context;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.util.AppUtils;

import org.greenrobot.eventbus.EventBus;

import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

/**
 * 启动后数据更新显示
 * @date： 2024/1/9
 * @author: 78495
*/
public class LauncherAfterUpdateTask extends TaskBase{

    public LauncherAfterUpdateTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            CarTravelTable travelTable = CarTravelRepository.getInstance().getData(context, AppUtils.getDeviceId(context));
            if(travelTable!=null){
                sendLaunchAfterUpdate(travelTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendLaunchAfterUpdate(CarTravelTable travelTable) {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_LAUNCH_AFTER);
        messageEvent.data = travelTable;
        EventBus.getDefault().post(messageEvent);
    }
}
