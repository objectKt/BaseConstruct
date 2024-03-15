package com.android.launcher.can;

import java.util.List;

public class Can39f implements CanParent {

    private static final String TAG = Can39f.class.getSimpleName();

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String d2 = msg.get(4);
        String d1 = msg.get(3);
        String d0 = msg.get(2);
        if (!lastData.equals(d2)) {
            String time = Integer.parseInt(d0, 16) + ":" + Integer.parseInt(d1, 16) + ":" + Integer.parseInt(d2, 16);
            lastData = d2;
        }
    }
}
