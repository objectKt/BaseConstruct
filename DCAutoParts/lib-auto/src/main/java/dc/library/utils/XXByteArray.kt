package dc.library.utils

import org.apache.commons.lang3.StringUtils

/**
 * 字节数组工具类
 */
object XXByteArray {

    /**
     * 字节数组转带空格的十六进制字符串，且合并 FE
     * 例如：FF FF 00 01 00 01 00 01 00 01 64 21 26 45 00 1A 72 65 67 69 73 74 72 61 FE01 69 6F 6E 20 FE00 75 63 63 65 73 73 66 75 6C 3A 4F 4B B9 33 FF
     */
    fun bytesToSpaceHex(bytes: ByteArray): String {
        return XxBytesJava.h264Bytes2Hex(bytes)
    }

    /**
     * 转义 FE00 FE01 -> FE FF，无空格
     * 例如 FFFF0001000100010001642127E7001A7265676973747261FF696F6E20FE75636365737366756C3A4F4B26A0FF
     */
    fun feChangeFromHex(dataHex: String): String {
        val replaceFE01 = StringUtils.replace(dataHex, "E01", "F")
        val result = StringUtils.replace(replaceFE01, "E00", "E")
        return StringUtils.replace(result, " ", "")
    }

    /**
     * 字节数组 转 十六进制大写字符，无空格
     * 例如：FFFF000100010001000164212645001A7265676973747261FE016F6E20EE00636365737366756C3A4F4BB933FF
     */
    fun toHeX(bytes: ByteArray): String {
        return buildString {
            for (B in bytes) {
                append(String.format("%02X", B))
            }
        }
    }

    /**
     * 无汉字十六进制字符串 转 字节数组
     */
    fun fromHex(str: String): ByteArray {
        return ByteArray(str.length / 2) { str.substring(it * 2, it * 2 + 2).toInt(16).toByte() }
    }

    /**
     * Int型数值 转 4字节数组
     * shl(bits):左移运算符。
     * Kotlin的位运算符只能对Int和Long两种数据类型起作用。
     * 使用&&连接两个表达式时，会从左往右依次判定每个表达式的结果，当遇到某个表达式的结果为false时，则会直接返回整个表达式的结果为false，不会再执行接下来的表达式。
     * 使用and连接两个表达式时，会执行所有的表达式并收集结果，最后把and两边的结果再做逻辑与操作得出最终结果。
     * @param num Int
     * @return ByteArray
     */
    fun fromInt(num: Int): ByteArray {
        val byteArray = ByteArray(4)
        val highH = ((num shr 24) and 0xff).toByte()
        val highL = ((num shr 16) and 0xff).toByte()
        val lowH = ((num shr 8) and 0xff).toByte()
        val lowL = (num and 0xff).toByte()
        byteArray[0] = highH
        byteArray[1] = highL
        byteArray[2] = lowH
        byteArray[3] = lowL
        return byteArray
    }

    /**
     * 判断两个字节数组是否一样
     * kotlin数组提供了一个indices属性，这个属性可返回数组的索引区间
     * @param b1 ByteArray
     * @param b2 ByteArray
     * @return Boolean
     */
    fun equals(b1: ByteArray, b2: ByteArray): Boolean {
        if (b1.size == b2.size) {
            for (i in b1.indices) {
                if (b1[i] != b2[i]) {
                    return false
                }
            }
            return true
        } else {
            return false
        }
    }

    /**
     * 合并 两个字节数组
     * @param b1 ByteArray
     * @param b2 ByteArray
     * @return ByteArray
     */
    fun merge(b1: ByteArray?, b2: ByteArray?): ByteArray? {
        if (b1 == null) return b2
        if (b2 == null) return b1
        val b1Size = b1.size
        val b2Size = b2.size
        if (b1Size == 0) return b2
        if (b2Size == 0) return b1
        val result = ByteArray(b1Size + b2Size)
        System.arraycopy(b1, 0, result, 0, b1Size)
        System.arraycopy(b2, 0, result, b1Size, b2Size)
        return result
    }

    /**
     * 截取 字节数组
     * System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
     * src 原数组 / srcPos 原数组起始位置（从这个位置开始复制） / dest 目标数组 / destPos 目标数组粘贴的起始位置 / length 复制的个数
     * @param bytes  被截取数组
     * @param start  被截取数组开始截取位置
     * @param length 新数组的长度
     * @return ByteArray
     */
    fun split(bytes: ByteArray, start: Int, length: Int): ByteArray {
        val result = ByteArray(length)
        System.arraycopy(bytes, start, result, 0, length)
        return result
    }

    /**
     * 合并多个字节数组
     * @param first ByteArray
     * @param rest Array<out ByteArray?>
     * @return ByteArray
     */
    fun combineMultiBytes(first: ByteArray, vararg rest: ByteArray): ByteArray {
        var totalLength = first.size
        for (array in rest) {
            totalLength += array.size
        }
        val result = first.copyOf(totalLength)
        var offset = first.size
        for (array in rest) {
            System.arraycopy(array, 0, result, offset, array.size)
            offset += array.size
        }
        return result
    }

    // 高字节在前
    fun convertTwoSignInt(byteArray: ByteArray): Int = (byteArray[1].toInt() shl 8) or (byteArray[0].toInt() and 0xFF)

    fun convertTwoUnSignInt(byteArray: ByteArray): Int =
        (byteArray[3].toInt() shl 24) or (byteArray[2].toInt() and 0xFF) or (byteArray[1].toInt() shl 8) or (byteArray[0].toInt() and 0xFF)

    // 无符号
    fun convertFourUnSignInt(byteArray: ByteArray): Int = (byteArray[1].toInt() and 0xFF) shl 8 or (byteArray[0].toInt() and 0xFF)

    fun convertFourUnSignLong(byteArray: ByteArray): Long =
        ((byteArray[3].toInt() and 0xFF) shl 24 or (byteArray[2].toInt() and 0xFF) shl 16 or (byteArray[1].toInt() and 0xFF) shl 8 or (byteArray[0].toInt() and 0xFF)).toLong()

    // 16进制转10进制
    fun hexOf2BytesToInt(substring: String): Int {
        return substring.toInt(16)
    }

    // Int 数值转两字节 ByteArray
    fun intToByteArrayOf2Size(value: Int): ByteArray {
        return XxBytesJava.unsignedShortTo2Byte(value)
    }

    fun hexOf4BytesToInt(hex: String): Int {
        val b = fromHex(hex)
        return b[3].toInt() and 0xFF or (b[2].toInt() and 0xFF shl 8) or (b[1].toInt() and 0xFF shl 16) or (b[0].toInt() and 0xFF shl 24)
    }

    fun getBatteryDate() {
        // 生产日期 2 字节 比如 0x2068,其中日期为最低 5 为：0x2068&0x1f = 8 表示日期;月份 （0x2068>>5）&0x0f= 0x03 表示 3 月;
        // 年份就为 2000+ (0x2068>>9) = 2000 + 0x10 =2016
    }
}