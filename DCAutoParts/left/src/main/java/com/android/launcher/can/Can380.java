package com.android.launcher.can;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.entity.EngineOilMenuEntity;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @description: 发动机油液位
 * @createDate: 2023/8/17
 */
public class Can380 implements  CanParent{

    private static final String TAG = Can380.class.getSimpleName();

    public static volatile String lastData = "";
    public static volatile String lastCheckOilLevelData = "";

    //机油液位正常
    public static final String NORMAL_LABEL = "38080713060C020400C5";
    //机油液位过高
    public static final String TOO_HIGH_LABEL = "38080713060C020700C2";

    private MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_ENGINE_OIL_STATUS);

    @Override
    public void handlerCan(List<String> msg) {

        String data = String.join("", msg);
        try {
            if(!lastData.equals(data)){
                lastData = data;
                LogUtils.printI(TAG, "handlerCan---msg="+msg +", lastData="+lastData +", data="+data);

                String data1 = msg.get(2);
                String data2 = msg.get(3);
                String data3 = msg.get(4);

                if("05".equalsIgnoreCase(data1) && "13".equalsIgnoreCase(data2) && "03".equalsIgnoreCase(data3)){
                    //下一次加油时检查发动机油液位
                    AlertVo alertVo = new AlertVo() ;
                    alertVo.setAlertImg(R.drawable.ic_oil_pressure);
                    alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.check_oil_level));
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                    messageEvent.data = alertVo;
                    EventBus.getDefault().post(messageEvent);
                }else{
                    EngineOilMenuEntity engineOilMenuEntity = new EngineOilMenuEntity();
                    engineOilMenuEntity.setData(data);
                    engineOilMenuEntity.setStatus1(data1);
                    engineOilMenuEntity.setStatus2(data2);
                    engineOilMenuEntity.setStatus3(data3);
                    messageEvent.data = engineOilMenuEntity;
                    EventBus.getDefault().post(messageEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
