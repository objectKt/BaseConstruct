package com.android.launcher.handler;

import android.util.Log;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.entity.CarTyre;
import com.android.launcher.util.FastJsonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description: 胎压
* @createDate: 2023/6/25
*/
public class HandlerTaiYa implements HandlerInteface {

    private void showTaiya(String msg) {
        List<String> message = FastJsonUtils.fromList(msg,String.class) ;
        String fl = message.get(5) ;
        String fr =  message.get(6) ;
        String bl =  message.get(7) ;
        String br =  message.get(8) ;

        String  fll =  (Integer.parseInt(fl,16)- 70)+"" ;
        String  frr =(Integer.parseInt(fr,16)- 70)+"" ;
        String bll = (Integer.parseInt(bl,16)- 70)+"" ;
        String brr = (Integer.parseInt(br,16)- 70)+"" ;

        Log.i("taiya",message+"-------");
//        if (MeterActivity.viewId == CommonUtil.FRAGMENT_MAIN_TAIN_TAiYA){
            Map<String,String> tempMap = new HashMap<>() ;
            tempMap.put("fl",fll);
            tempMap.put("fr",frr);
            tempMap.put("bl",bll);
            tempMap.put("br",brr);
            Log.i("taiya",tempMap.toString()+"----1---");

//            MeterActivity.meterMidParent.updateDate(tempMap);

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.CAR_TYRE);
            messageEvent.data = new CarTyre(tempMap);
            EventBus.getDefault().post(messageEvent);
//        }

    }


    @Override
    public void handlerData(final String msg) {

        try {
            showTaiya(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
