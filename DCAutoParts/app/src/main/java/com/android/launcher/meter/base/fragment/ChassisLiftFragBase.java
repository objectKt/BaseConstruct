package com.android.launcher.meter.base.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

/**
* @description: 底盘升降
* @createDate: 2023/9/25
*/
@Deprecated
public abstract class ChassisLiftFragBase extends MenuFragmentBase {

    private ImageView hintIconIV;
    private TextView infoTV;

    private AlertVo mAlertVo;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        hintIconIV = view.findViewById(R.id.hintIconIV);
        infoTV = view.findViewById(R.id.infoTV);
    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.CHASSIS_LIFT;
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
         if(event.type == MessageEvent.Type.CAN379_VIEW_UPDATE){
            try {
                LogUtils.printI(TAG, "event="+event.type.name() +", data="+event.data);
                if(event.data!=null && event.data instanceof AlertVo){
                    AlertVo alertVo = (AlertVo) event.data;
                    updateData(alertVo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateData(AlertVo alertVo){
        mAlertVo = alertVo;
        try {
            if(mAlertVo != null && hintIconIV!=null){
                hintIconIV.setImageResource(alertVo.getAlertImg());
                infoTV.setTextColor(Color.parseColor(alertVo.getAlertColor()));
                infoTV.setText(alertVo.getAlertMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
