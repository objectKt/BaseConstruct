package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.can.Can105;
import com.android.launcher.entity.EngineOilMenuEntity;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
* @description: 发动机机油液位
* @createDate: 2023/9/22
*/
@Deprecated
public abstract class EngineOilFragBase extends MenuFragmentBase {

    public boolean isNormal = false;
    public boolean isTest = false;
    public boolean toHigh = false;
    public boolean toLow = false;

    private TextView messageTV;
    private Button startBt;

    private  boolean carRun = false;
    //等待时间是否充足
    private  boolean waitTimeFinish = true;

    private boolean isTestRunning = false;

    //机油液位正常
    private static final String NORMAL_LABEL = "38080713060C020400C5";
    //机油液位过高
    private static final String TOO_HIGH_LABEL = "38080713060C020700C2";

    private Handler handler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                updata();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        messageTV = view.findViewById(R.id.messageTV);
        startBt = view.findViewById(R.id.startBt);

        messageTV.setVisibility(View.GONE);
        startBt.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initData() {
        updata();
        isTest = false;
    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.ENGINE_OIL;
    }

    private void updata() {
        Can105 can105 = (Can105) FuncUtil.canHandler.get("105");

        long interval = (System.currentTimeMillis() - can105.stopTime) / 1000;

        LogUtils.printI(TAG, "carRun="+carRun +", waitTimeFinish="+waitTimeFinish +", isNormal="+isNormal +", isTest="+isTest +", interval="+interval);
        if(can105.realSpeed > 0|| can105.run){
            messageTV.setText("发动机机油液位\n" +
                    "在发动机运行时\n" +
                    "请勿检查");
            finish();
        }else if(interval < 6){
            messageTV.setText("发动机机油液位等待时间不足");
            finish();
        }else if(isNormal){
            messageTV.setText("发动机机油液位正常");
        }else if(toHigh){
            messageTV.setText("发动机机油液位过高");
            toHigh = false;
        }else if(toLow){
            messageTV.setText("发动机机油液位过低");
            toLow = false;
        }else if(isTest){
            messageTV.setText("发动机机油液位\n" +
                    "测量进行中\n" +
                    "只有当车辆水平时\n" +
                    "测量结果才准确");
        }else{
            messageTV.setText("发动机机油液位\n测试失败，请重试");
        }
    }

    public void finish() {
        isTest = false;
        handler.postDelayed(() -> {
            try {
                startBt.setVisibility(View.VISIBLE);
                messageTV.setVisibility(View.GONE);
                isTestRunning = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 3000);
    }

    @Override
    protected void onOK() {
        super.onOK();
        if(MeterActivity.currentMenuType == MenuType.ENGINE_OIL) {
            if(!isTest){
                startBt.setVisibility(View.GONE);
                messageTV.setVisibility(View.VISIBLE);
                messageTV.setText("");
//                if( MeterActivity.localMeterActivity!=null){
//                    MeterActivity.localMeterActivity.startTestEngineOil();
//                }
            }
        }
    }

    public void startTest() {
        handler.sendMessage(new Message());
        handler.postDelayed(() -> {
            try {
                isTest = false;
                if(!isNormal){
                    handler.sendMessage(new Message());
                    handler.postDelayed(() -> {
                        try {
                            startBt.setVisibility(View.VISIBLE);
                            messageTV.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 10000);
    }


    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if(event.type == MessageEvent.Type.UPDATE_ENGINE_OIL_STATUS){
            try {
                try {
                    LogUtils.printI(TAG, "MessageEvent-----event="+event.type.name() +", data="+event.data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(event.data!=null){
                    EngineOilMenuEntity entity = (EngineOilMenuEntity) event.data;
                    if(entity.getStatus1().equals("10") && entity.getStatus2().equals("12")){ //机油液位正在测量
                        isTest = true;
                        isNormal = false;
                        startTest();
                    }else if(entity.getData().equalsIgnoreCase(NORMAL_LABEL)){
                        isNormal = true;
                        isTest = false;
                        handler.sendMessage(new Message());

                        finish();
                    } else if (entity.getData().equalsIgnoreCase(TOO_HIGH_LABEL)) {
                        isNormal = false;
                        toHigh = true;
                        isTest = false;
                        handler.sendMessage(new Message());

                        finish();
                    }else if (entity.getData().startsWith("38080713060C02")) {
                        isNormal = false;
                        toHigh = false;
                        toLow = true;
                        isTest = false;
                        handler.sendMessage(new Message());

                        finish();
                    } else if (entity.getStatus1().equals("10") && entity.getStatus2().equalsIgnoreCase("0F")) { //取消发送数据

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onBack() {
        super.onBack();
        if(MeterActivity.currentMenuType == MenuType.ENGINE_OIL) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_MAINTAIN_HOME_FRAG));
        }
    }
}
