package com.android.launcher.can;

import java.util.List;

public class Can003 implements CanParent {

    private static final String TAG = Can003.class.getSimpleName();

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String d0Hex = msg.get(2);
        if (!lastData.equals(d0Hex)) {
//            BinaryEntity binaryEntity = new BinaryEntity(d0Hex);
//            LogUtils.printI(TAG, "msg="+msg +",d0Hex=" + d0Hex +", d0="+Integer.parseInt(d0Hex,16) +", binaryEntity="+binaryEntity.getBinaryData());
            lastData = d0Hex;
        }
    }
}
