package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.SPUtils;
import com.android.launcher.util.UnitUtils;

import org.greenrobot.eventbus.EventBus;

import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

/**
 * @date： 2024/1/3
 * @author: 78495
*/
public class MaintainTask extends TaskBase {

    public MaintainTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            CarTravelTable travelTable = CarTravelRepository.getInstance().getData(App.getGlobalContext(), AppUtils.getDeviceId(App.getGlobalContext()));
            if (travelTable == null) {
                return;
            }
            if (!FuncUtil.isSendMaintainMessage) {
                return;
            }

            float startDistance = 9500f;
            float endDistance = 10000f;

            float userMile = travelTable.getUserMile();
            String unit = " km";

            int unitType = SPUtils.getInt(context, SPUtils.SP_UNIT_TYPE, UnitType.getDefaultType());
            if (unitType == UnitType.MI.ordinal()) {
                startDistance = UnitUtils.kmToMi(startDistance);
                endDistance = UnitUtils.kmToMi(endDistance);
                userMile = UnitUtils.kmToMi(userMile);
                unit = " mi";
            }

            if (travelTable.getUserMile() > startDistance && travelTable.getUserMile() < endDistance) {
                float diff = BigDecimalUtils.sub(String.valueOf(userMile), String.valueOf(startDistance));
                FuncUtil.infoCount++;
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.NEED_MAINTAIN_MESSAGE);
                messageEvent.data = context.getResources().getString(R.string.only_remaining) + diff + unit + context.getResources().getString(R.string.timely_maintenance);
                EventBus.getDefault().post(messageEvent);

            } else if (travelTable.getUserMile() >= endDistance) {
                float diff = BigDecimalUtils.sub(String.valueOf(userMile), String.valueOf(endDistance));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.NEED_MAINTAIN_MESSAGE);
                FuncUtil.infoCount++;
                messageEvent.data = context.getResources().getString(R.string.exceed) + diff + unit + context.getResources().getString(R.string.timely_maintenance);
                EventBus.getDefault().post(messageEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
