package lib.dc.utils

import com.dc.android.launcher.util.HUtils

/**
 * 字节数组工具类
 */
@Suppress("MemberVisibilityCanBePrivate")
object UtilByteArray {

    fun checkLinData(data: ByteArray): Byte = UtilByteArrayJava.checkLinData(data)
    fun checkMcuData(data: ByteArray): Byte = UtilByteArrayJava.checkMcuSum(data)
    fun toHexFromByte(byte: Byte): String = HUtils.ByteX.byteToHex(byte)
    fun toIntFromByte(byte: Byte): Int = HUtils.ByteX.byteToUInt(byte)
    fun toHeXX(bytes: ByteArray): String = HUtils.ByteX.bytesToHeXX(bytes)
    fun fromHex(str: String): ByteArray = HUtils.ByteX.heXXToBytes(str)
    fun toBinaryString(byte: Byte): String = HUtils.ByteX.byteToBinaryString(byte)

    /**
     * 字节数组 转 十六进制大写字符，空格间隔显示
     * 用于 Log 打印，不用于传输
     */
    @Suppress("LocalVariableName")
    fun toHeXLog(bytes: ByteArray?): String {
        bytes?.let {
            return buildString {
                for (B in it) {
                    append(String.format("%02X ", B))
                }
            }
        }
        return ""
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

    fun twoBytesToInt(high: Byte, low: Byte): Int = (high.toInt() shl 8) or (low.toInt() and 0xFF)

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

    fun hexOf4BytesToInt(hex: String): Int {
        val b = fromHex(hex)
        return b[3].toInt() and 0xFF or (b[2].toInt() and 0xFF shl 8) or (b[1].toInt() and 0xFF shl 16) or (b[0].toInt() and 0xFF shl 24)
    }
}