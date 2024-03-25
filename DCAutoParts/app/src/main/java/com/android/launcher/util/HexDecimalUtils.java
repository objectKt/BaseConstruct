package com.android.launcher.util;

/**
* @description:
* @createDate: 2023/8/26
*/
public class HexDecimalUtils {

    public static synchronized String xor(String hex1, String hex2){
        // 将16进制转换为10进制
        int dec1 = Integer.parseInt(hex1, 16);
        int dec2 = Integer.parseInt(hex2, 16);

        // 执行异或运算
        int result = dec1 ^ dec2;

        // 将结果转换为16进制
        String hexResult = Integer.toHexString(result);

        return hexResult;
    }

}
