package com.android.launcher.can;

import com.android.launcher.util.LogUtils;

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
