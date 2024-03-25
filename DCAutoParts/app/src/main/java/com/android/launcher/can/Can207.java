package com.android.launcher.can;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @date： 2023/12/29
 * @author: 78495
*/
public class Can207 implements CanParent {


    private static final String TAG = Can207.class.getSimpleName();

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String data = msg.get(4);
        if (!lastData.equals(data)) {
            LogUtils.printI(TAG, "msg="+msg);
            lastData = data;

        }


    }
}
