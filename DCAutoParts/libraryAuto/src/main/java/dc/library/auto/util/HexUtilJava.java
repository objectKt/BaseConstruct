package dc.library.auto.util;

import java.util.ArrayList;
import java.util.List;

public class HexUtilJava {

    public static synchronized String toHexString(byte[] arg, int length) {
        if (arg != null && arg.length != 0) {
            StringBuilder sb = new StringBuilder();
            char[] hexArray = "0123456789ABCDEF".toCharArray();
            for (int j = 0; j < length; ++j) {
                int v = arg[j] & 255;
                sb.append(hexArray[v >>> 4]).append(hexArray[v & 15]);
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 16位字符串转byte数组
     */
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static int isOdd(int num) {
        return num & 1;
    }

    /**
     * 数据转成二进制数据发送
     */
    public static byte[] toByteArrayTTLLD(String hexString) {
        if (hexString != null) {
            char[] NewArray = new char[1000];
            char[] array = hexString.toCharArray();
            int length = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] != ' ') {
                    NewArray[length] = array[i];
                    length++;
                }
            }
            int EvenLength = (length % 2 == 0) ? length : length + 1;
            if (EvenLength != 0) {
                int[] data = new int[EvenLength];
                data[EvenLength - 1] = 0;
                for (int i = 0; i < length; i++) {
                    if (NewArray[i] >= '0' && NewArray[i] <= '9') {
                        data[i] = NewArray[i] - '0';
                    } else if (NewArray[i] >= 'a' && NewArray[i] <= 'f') {
                        data[i] = NewArray[i] - 'a' + 10;
                    } else if (NewArray[i] >= 'A' && NewArray[i] <= 'F') {
                        data[i] = NewArray[i] - 'A' + 10;
                    }
                }
                byte[] byteArray = new byte[EvenLength / 2];
                for (int i = 0; i < EvenLength / 2; i++) {
                    byteArray[i] = (byte) (data[i * 2] * 16 + data[i * 2 + 1]);
                }
                return byteArray;
            }
        }
        return new byte[]{};
    }

    public static List<String> toHexStringBenzHandler(String hex) {
        ArrayList<String> arr = new ArrayList<String>();
        String h = hex.substring(12, 16);
        int hh = Integer.parseInt(h, 16);
        String hhx = Integer.toHexString(hh);
        String len = hex.substring(7, 8);
        arr.add(hhx);
        arr.add(len);
        String temp = "";
        for (int i = 16; i < hex.length(); i++) {
            if (i % 2 == 0) {//每隔两个
                temp = hex.charAt(i) + "";
            } else {
                temp = temp + hex.charAt(i);
            }
            if (temp.length() == 2) {
                arr.add(temp);
            }
        }
        return arr;
    }
}
