package com.android.launcher.handler;

import android.os.CountDownTimer;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

// 门处理
public class HandlerDoor implements HandlerInteface {

    public volatile boolean flg;

    private void sendMessage(int resId, boolean isShow) {
        AlertVo alertVo = new AlertVo();
        alertVo.setType(AlertVo.Type.DOOR);
        alertVo.setAlertImg(resId);
        alertVo.setShow(isShow);
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
        messageEvent.data = alertVo;
        EventBus.getDefault().post(messageEvent);
    }

    public CountDownTimer timer;

    @Override
    public void handlerData(final String msg) {
        LogUtils.printI(HandlerDoor.class.getSimpleName(), "msg=" + msg);
        switch (msg) {
            case "55":
                sendMessage(0, false);
                flg = true;
                break;
            case "56":
                flg = false;
                sendMessage(R.drawable.door56, true);
                break;
            case "59":
                flg = false;
                sendMessage(R.drawable.door59, true);
                break;
            case "65":
                flg = false;
                sendMessage(R.drawable.door65, true);
                break;
            case "95":
                flg = false;
                sendMessage(R.drawable.door95, true);
                break;
            case "66":
                flg = false;
                sendMessage(R.drawable.door66, true);
                break;
            case "96":
                flg = false;
                sendMessage(R.drawable.door96, true);
                break;
            case "99":
                flg = false;
                sendMessage(R.drawable.door99, true);
                break;
            case "5A":
                flg = false;
                sendMessage(R.drawable.door5a, true);
                break;
            case "A5":
                flg = false;
                sendMessage(R.drawable.doora5, true);
                break;
            case "69":
                flg = false;
                sendMessage(R.drawable.door69, true);
                break;
            case "A9":
                flg = false;
                sendMessage(R.drawable.doora9, true);
                break;
            case "9A":
                flg = false;
                sendMessage(R.drawable.door9a, true);
                break;
            case "AA":
                flg = false;
                sendMessage(R.drawable.dooraa, true);
                break;
            case "6A":
                flg = false;
                sendMessage(R.drawable.door6a, true);
                break;
            case "A6":
                flg = false;
                sendMessage(R.drawable.doora6, true);
                break;
            case "hq55":
                if (flg) {
                    sendMessage(0, false);
                }
                break;
            case "hq5A":
                if (flg) {
                    sendMessage(R.drawable.doorhq5a, false);
                }
                break;
            case "hq59":
                if (flg) {
                    sendMessage(R.drawable.doorhq59, false);
                }
                break;
            case "hq56":
                if (flg) {
                    sendMessage(R.drawable.doorhq56, false);
                }
                break;
            default:
                break;
        }
    }
}
