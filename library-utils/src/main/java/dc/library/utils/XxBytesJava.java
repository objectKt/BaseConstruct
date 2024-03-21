package dc.library.utils;

import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.Charset;

public class XxBytesJava {

    public static String h264Bytes2Hex(byte[] data) {
        if (data == null) {
            return "";
        }
        String HEXES = "0123456789ABCDEF";
        final StringBuilder hex = new StringBuilder(2 * data.length);
        for (byte b : data) {
            char left = HEXES.charAt((b & 0xF0) >> 4);
            char right = HEXES.charAt((b & 0x0F));
            if (left == 'F' && right == 'E') {
                hex.append(left).append(right);
            } else {
                hex.append(left).append(right).append(" ");
            }
        }
        return hex.toString();
    }

    /**
     * 将Base16进制数字解密成明文（包括中文）
     */
    public static String decodeHex2ChineseStr(String hexStr, Charset charsetName) {
        String tmpStr;
        if (hexStr.length() <= 4) {
            return hexStr;
        }
        try {
            if ((hexStr.length()) % 2 != 0) {
                hexStr += "00";
            }
            int byteLength = hexStr.length() / 2;
            byte[] bytes = new byte[byteLength];
            int temp;
            for (int i = 0; i < byteLength; i++) {
                int tmpInt = Integer.parseInt(new BigInteger(hexStr.charAt(2 * i) + "", 16).toString(10));
                int tmpInt2 = Integer.parseInt(new BigInteger(hexStr.charAt(2 * i + 1) + "", 16).toString(10));
                temp = tmpInt * 16 + tmpInt2;
                bytes[i] = (byte) (temp < 128 ? temp : temp - 256);
            }
            tmpStr = new String(bytes, charsetName);
            return tmpStr;
        } catch (Exception ex) {
            Log.e("error", "decodeHexToStr try error");
        }
        return "-1";
    }

    public static String hexStringSplitSpace(String msgHex, boolean change) {
        StringBuilder builder = new StringBuilder();
        int length = msgHex.length() / 2;
        for (int i = 0; i < length; ++i) {
            String twoCharPart = msgHex.substring(i * 2, i * 2 + 2);
            if (change) {
                // 需要转义
                if (i >= 2 && i < length - 1) {
                    // 处理  FF -> FE 01  FE -> FE 00
                    if (twoCharPart.equalsIgnoreCase("FE")) {
                        builder.append("FE00");
                        continue;
                    }
                    if (twoCharPart.equalsIgnoreCase("FF")) {
                        builder.append("FE01");
                        continue;
                    }
                }
            }
            builder.append(twoCharPart);
        }
        return builder.toString();
    }

    //TODO CRC16 校验结果
    private static int crcResolve(int crc, int b, int p) {
        for (int i = 0; i < 8; i++) {
            int f = (crc & 0x8000) >> 8;
            int g = (b & 0x80);
            if ((f ^ g) != 0) {
                crc <<= 1;
                crc ^= p;
            } else {
                crc <<= 1;
            }
            b <<= 1;
        }
        return crc;
    }

    /**
     * 获取 CRC 校验值
     */
    public static int crcCheckInt(byte[] bytes) {
        int crc = 0xA000;
        int p = 0xA001;
        for (byte b : bytes) {
            crc = crcResolve(crc, b, p);
        }
        return ~crc;
    }

    //TODO Int 数值转 2 字节数组
    public static byte[] unsignedShortTo2Byte(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    public static byte[] chars2Bytes(char[] arraySn) {
        byte[] bytes = new byte[arraySn.length];
        for (int i = 0; i < arraySn.length; ++i) {
            bytes[i] = (byte) ((int) arraySn[i] - 48);
        }
        return bytes;
    }

    //必须把我们要的值弄到最低位去，有人说不移位这样做也可以， result[0] = (byte)(i  & 0xFF000000);
    //，这样虽然把第一个字节取出来了，但是若直接转换为byte类型，会超出byte的界限，出现error。
    // 再提下数//之间转换的原则（不管两种类型的字节大小是否一样，原则是不改变值，内存内容可能会变，
    // 比如int转为//float肯定会变）所以此时的int转为byte会越界，只有int的前三个字节都为0的时候转byte才不会越界。
    // 虽//然 result[0] = (byte)(i  & 0xFF000000); 这样不行，
    // 但是我们可以这样 result[0] = (byte)((i  & 0xFF000000) >>24);
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[3] = (byte) ((i >> 24) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[0] = (byte) (i & 0xFF);
        return result;
    }
}