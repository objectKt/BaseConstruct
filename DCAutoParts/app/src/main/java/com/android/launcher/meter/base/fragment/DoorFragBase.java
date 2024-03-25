package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
* @description: 车门
* @createDate: 2023/9/25
*/
public abstract class DoorFragBase extends MenuFragmentBase {

    private FrameLayout frame_door;
    private ImageView door_pg;

    private volatile boolean flg ;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        frame_door = view.findViewById(R.id.frame_door);
        frame_door.setVisibility(View.INVISIBLE);
        door_pg = view.findViewById(R.id.door_pg);
    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.DOOR;
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if(event.type == MessageEvent.Type.UPDATE_DOOR_HINT){
            try {
                try {
                    LogUtils.printI(TAG, "MessageEvent-----event="+event.type.name() +", data="+event.data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(event.data!=null){
                    String msgflg = (String) event.data;
                    try {
                        setDoorImge(msgflg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDoorImge(String msgflg) {


        switch (msgflg){
            case "55":
                FuncUtil.doorStatus = true;
                frame_door.setVisibility(View.INVISIBLE);
                flg = true ;
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CLOSE_DOOR_FRAG));
                break;
            case "56":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door56);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "59":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door59);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "65":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door65);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "95":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door95);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "66":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);

                door_pg.setImageResource(R.drawable.door66);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "96":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door96);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "99":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door99);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "5A":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);

                door_pg.setImageResource(R.drawable.door5a);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "A5":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);

                door_pg.setImageResource(R.drawable.doora5);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "69":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door69);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "A9":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);

                door_pg.setImageResource(R.drawable.doora9);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "9A":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);

                door_pg.setImageResource(R.drawable.door9a);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "AA":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);

                door_pg.setImageResource(R.drawable.dooraa);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "6A":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.door6a);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "A6":
                flg = false ;
                frame_door.setVisibility(View.VISIBLE);
                door_pg.setImageResource(R.drawable.doora6);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                break;
            case "hq55":
                if (flg){
                    frame_door.setVisibility(View.INVISIBLE);
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CLOSE_DOOR_FRAG));
                }
                break;
            case "hq5A":
                if (flg){
                    frame_door.setVisibility(View.VISIBLE);
                    door_pg.setImageResource(R.drawable.doorhq5a);
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                }
                break;
            case "hq59":
                if (flg){
                    frame_door.setVisibility(View.VISIBLE);
                    door_pg.setImageResource(R.drawable.doorhq59);
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                }
                break;
            case "hq56":
                if (flg){
                    frame_door.setVisibility(View.VISIBLE);
                    door_pg.setImageResource(R.drawable.doorhq56);
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_DOOR_FRAG));
                }
                break;
        }
    }

    @Override
    protected void onBack() {
        super.onBack();
        if(MeterActivity.currentMenuType == MenuType.DOOR){
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_HOME_FRAG));
        }
    }
}
