package lib.dc.utils;

public class UtilByteArrayJava {

    private static final String TAG = "UtilByteArrayJava";

    /**
     * Lin 氛围灯数据校验码
     * 校验方法：将校验对象的各字节作带进位二进制加法(每当结果大于等于 256 时就减去 255)
     * 并将所得最终的和逐位取反，以该结果作为要发送的校验和。
     */
    public static byte checkLinData(byte[] data) {
        int sum = 0;
        for (byte b : data) {
            // 将当前字节的值加到总和中，确保我们处理的是无符号值
            sum += b & 0xFF;
            // 如果和大于等于256（即高于一个字节能表示的最大值），则减去255
            if (sum >= 256) {
                sum -= 255;
            }
        }
        // 将最终的和逐位取反，通过两次按位取反操作来实现，因为byte是有符号的
        return (byte) (~sum & 0xFF);
    }

    /**
     * MCU 数据校验码 commandId + data
     * 校验和
     */
    public static byte checkMcuSum(byte[] buffer) {
        byte checksum = buffer[0];
        for (int i = 0; i < buffer.length - 1; i++) {
            checksum = (byte) (checksum + buffer[i]);
        }
        return checksum;
    }

    /**
     * CAN 数据校验码
     */
    public static byte checkCanSum(byte[] buffer) {
        int length = buffer.length;
        byte check = buffer[1];
        for (int i = 2; i < length - 1; i++) {
            check += buffer[i];
        }
        return (byte) (check ^ 0xff);
    }

    public static String toBinaryString(byte b) {
        return UtilByteArray.INSTANCE.toBinaryString(b);
    }
}