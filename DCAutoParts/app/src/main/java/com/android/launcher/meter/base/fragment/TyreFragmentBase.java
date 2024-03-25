package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.entity.CarTyre;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.util.FastJsonUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description: 胎压
* @createDate: 2023/9/22
*/
@Deprecated
public abstract class TyreFragmentBase extends MenuFragmentBase {

    private TextView rearLeftTV, rearRightTV, frontLeftTV, frontRightTV;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TextView titleTV = view.findViewById(R.id.titleTV) ;
        titleTV.setText(getContext().getResources().getString(R.string.tire_pressure_display));
        rearRightTV = view.findViewById(R.id.rearRightTV) ;
        rearLeftTV = view.findViewById(R.id.rearLeftTV) ;
        frontRightTV = view.findViewById(R.id.frontRightTV) ;
        frontLeftTV = view.findViewById(R.id.frontLeftTV) ;
    }

    @Override
    protected void initData() {
        try {
            String data = SPUtils.getString(App.getGlobalContext(), SPUtils.SP_TAIYAI, null);
            LogUtils.printI(TAG, "data="+data);
            if(!TextUtils.isEmpty(data)){
                List<String> message = FastJsonUtils.fromList(data,String.class) ;
                String fl = message.get(5) ;
                String fr =  message.get(6) ;
                String bl =  message.get(7) ;
                String br =  message.get(8) ;

                String  fll =  (Integer.parseInt(fl,16)- 70)+"" ;
                String  frr =(Integer.parseInt(fr,16)- 70)+"" ;
                String bll = (Integer.parseInt(bl,16)- 70)+"" ;
                String brr = (Integer.parseInt(br,16)- 70)+"" ;
                Map<String,String> tempMap = new HashMap<>() ;
                tempMap.put("fl",fll);
                tempMap.put("fr",frr);
                tempMap.put("bl",bll);
                tempMap.put("br",brr);
                updateData(tempMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.TYRE;
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if (event.type == MessageEvent.Type.CAR_TYRE) {
            try {
                LogUtils.printI(TAG, "MessageEvent-----event=" + event.type.name() + ", data=" + event.data);
                if (event.data != null) {
                    CarTyre carTyre = (CarTyre) event.data;
                    updateData(carTyre.getTempMap());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(event.type == MessageEvent.Type.UPDATE_TAIYAI){
            updateTaiYa(event);
        }
    }

    /**
     * @description: 更新胎压数据
     * @createDate: 2023/8/23
     */
    private void updateTaiYa(MessageEvent event) {
        try {
            LogUtils.printI(TAG, "onMessageEvent---type="+event.type.name() +" ,data="+event.data);
            String data = (String) event.data;
            List<String> message = FastJsonUtils.fromList(data,String.class) ;
            String fl = message.get(5) ;
            String fr =  message.get(6) ;
            String bl =  message.get(7) ;
            String br =  message.get(8) ;

            String  fll =  (Integer.parseInt(fl,16)- 70)+"" ;
            String  frr =(Integer.parseInt(fr,16)- 70)+"" ;
            String bll = (Integer.parseInt(bl,16)- 70)+"" ;
            String brr = (Integer.parseInt(br,16)- 70)+"" ;
            Map<String,String> tempMap = new HashMap<>() ;
            tempMap.put("fl",fll);
            tempMap.put("fr",frr);
            tempMap.put("bl",bll);
            tempMap.put("br",brr);
            updateData(tempMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData(Map<String, String> params) {
        try {
            if (params != null) {
                if (params.size() > 0) {
                    Log.i("taiya", params.toString() + "----params----2---");
                    frontLeftTV.setText(getContext().getResources().getString(R.string.front_left)+"       "+params.get("fl"));
                    frontRightTV.setText(params.get("fr")+"       "+getContext().getResources().getString(R.string.front_right));
                    rearLeftTV.setText(getContext().getResources().getString(R.string.rear_left)+"       "+params.get("bl"));
                    rearRightTV.setText(params.get("br")+"        "+getContext().getResources().getString(R.string.rear_right));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onBack() {
        super.onBack();
        if(MeterActivity.currentMenuType == MenuType.TYRE){
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_MAINTAIN_HOME_FRAG));
        }
    }
}
