package com.android.launcher.can;

import com.android.launcher.util.LogUtils;
import com.android.launcher.util.StringUtils;

import java.util.List;

//车钥匙启动车关闭车
public class Can1 implements CanParent {

    public static volatile boolean isClose = false;

    public static volatile String lastStatus = "";

    @Override
    public void handlerCan(List<String> msg) {

        try {
            String dat = msg.get(2) ;

            //0: 拔出钥匙，1: 插入钥匙， 2:打开车上电源， C: 发动汽车 。 5->4变化：重启车机， D,F状态未知
            String status = dat.substring(1);

            if(!lastStatus.equals(status)){
                lastStatus = status;
                String binaryString = StringUtils.hexString2binaryString(status);
                LogUtils.printI(Can1.class.getSimpleName(), "carKeyStatus----msg="+msg +", status="+status +", binaryString="+binaryString);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
