package com.android.launcher.can;

import android.util.Log;

import com.android.launcher.entity.BinaryEntity;
import com.android.launcher.util.LogUtils;

import java.util.List;

//车钥匙启动车关闭车
public class Can4B implements CanParent {

    @Override
    public void handlerCan(List<String> msg) {

        Log.i("can4B",msg.toString()+"=============") ;

        BinaryEntity binaryEntity = new BinaryEntity(msg.get(2));
        LogUtils.printI(Can4B.class.getSimpleName(),"msg="+msg+", d1="+binaryEntity);

        BinaryEntity binaryEntity5 = new BinaryEntity(msg.get(5));
        LogUtils.printI(Can4B.class.getSimpleName(),"msg="+msg+", d4="+binaryEntity5);
    }
}
