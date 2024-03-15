package com.android.launcher.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @description:
* @createDate: 2023/4/29
*/
public class StringUtils {

    /**
     * 将16进制转换为二进制
     * @return
     */
    public static synchronized String hexString2binaryString(String hex) {
// variable to store the converted
        // Binary Sequence
        StringBuilder binary =  new StringBuilder();

        // converting the accepted Hexadecimal
        // string to upper case
        hex = hex.toUpperCase();

        // initializing the HashMap class
        HashMap<Character, String> hashMap
                = new HashMap<Character, String>();

        // storing the key value pairs
        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        int i;
        char[] arrChr = hex.toCharArray();
        char ch;

        for (i = 0; i < arrChr.length; i++) {
            // extracting each character
            ch = arrChr[i];

            if (hashMap.containsKey(ch)) {
                binary.append(hashMap.get(ch));
            }

        }

        // returning the converted Binary
        return binary.toString();

    }

    //判定是否为十六进制数
    public static synchronized boolean isHex(String hex) {
        int digit;
        try {	//字符串直接转换为数字，存在字符时会自动抛出异常
            digit = Integer.parseInt(hex);
        }catch (Exception e) {	//字符串存在字母时，捕获异常并转换为数字
            digit = hex.charAt(0) - 'A' + 10;
        }
        return 0 <= digit && digit <= 15;
    }

        /**
     * 将一个字符串按照指定长度进行分割，并转换为 List
     *
     * @param input    待分割的字符串
     * @param perSize  分割的长度
     * @return         返回分割后的 List
     */
    public static List<String> splitStringToList(String input, int perSize) {
        List<String> list = new ArrayList<>();
        int len = input.length();
        for (int i = 0; i < len; i += perSize) {
            int endIndex = i + perSize;
            if (endIndex >= len) {
                endIndex = len;
            }
            String subStr = input.substring(i, endIndex);
            list.add(subStr);
        }
        return list;
    }

    public static String filterNULL(String data) {
        if(data == null){
            return "";
        }
        final String tag = "NULL";
        if(tag.equalsIgnoreCase(data)){
            return "";
        }
        return data;
    }


    public static synchronized String hexToString(String input) {
        // 确保输入的16进制字符串长度为偶数
        if (input.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hexadecimal string");
        }

        // 将16进制字符串转换为字节数组
        byte[] bytes = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                    + Character.digit(input.charAt(i+1), 16));
        }

        // 根据字节数组构建字符串并返回
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
