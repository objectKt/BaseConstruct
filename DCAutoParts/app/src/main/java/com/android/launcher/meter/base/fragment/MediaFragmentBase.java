package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.service.LivingService;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

/**
* @description: 多媒体
* @createDate: 2023/9/21
*/
public abstract class MediaFragmentBase extends MenuFragmentBase {

    protected ImageView playIV;
    protected TextView lyricTV;
    protected ImageView musicIV;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        try {
            initBlueTooth(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.MEDIA;
    }


    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if(event.type == MessageEvent.Type.ON_LEFT_KEY){
            onLeft();
        }else if(event.type == MessageEvent.Type.ON_RIGHT_KEY){
            onRight();
        }else if(event.type == MessageEvent.Type.UPDATE_LYRIC){
            if(event.data instanceof String){
                String lyric = (String) event.data;
                if(lyricTV!=null){
                    lyricTV.setText(StringUtils.filterNULL(lyric));
                }
                if(!LivingService.isPlayMusic){
                    LivingService.isPlayMusic = true;
                    if(playIV!=null){
                        playIV.setImageResource(R.drawable.ic_music_pause);
                    }
                }
            }
        }else if(event.type == MessageEvent.Type.BLUETOOTH_DISCONNECTED){
            if(lyricTV!=null){
                lyricTV.setText("");
            }
            if(playIV!=null){
                playIV.setImageResource(R.drawable.ic_music_play);
            }
        }else if(event.type == MessageEvent.Type.MUSIC_STOP){
            if(playIV!=null){
                playIV.setImageResource(R.drawable.ic_music_play);
            }
        }
    }

    private void initBlueTooth(View view) {
        musicIV = view.findViewById(R.id.musicIV);
       ImageView previousIV = view.findViewById(R.id.previousIV);
       ImageView nextIV = view.findViewById(R.id.nextIV);
        playIV = view.findViewById(R.id.playIV);
        playIV.setImageResource(R.drawable.ic_music_play);
        lyricTV = view.findViewById(R.id.lyricTV);
    }

    @Override
    protected void onOK() {
        super.onOK();
        if (playIV != null) {
            if (LivingService.isPlayMusic) {
                startTask(() -> {
                    String send = "AABB1400CCDD";
                    SendHelperUsbToRight.handler(send);
                });
                playIV.setImageResource(R.drawable.ic_music_pause);
            } else {

                startTask(() -> {
                    String send = "AABB1500CCDD";
                    SendHelperUsbToRight.handler(send);
                });
                playIV.setImageResource(R.drawable.ic_music_play);
            }
        }
    }

    @Override
    protected void onLeft() {
        super.onLeft();
        startTask(() -> {
            String send = "AABB1200CCDD" ;
            SendHelperUsbToRight.handler(send);
        });
    }

    @Override
    protected void onRight() {
        super.onRight();
        startTask(() -> {
            String send = "AABB1300CCDD" ;
            SendHelperUsbToRight.handler(send);
        });
    }
}
