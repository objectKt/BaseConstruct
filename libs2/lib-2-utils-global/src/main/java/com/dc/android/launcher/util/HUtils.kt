package com.dc.android.launcher.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date

interface HUtils {

    object ResX {
        fun getDrawable(@DrawableRes resId: Int): Drawable = AppCompatResources.getDrawable(AppGet.appContext(), resId)!!
        fun getColor(@ColorRes resId: Int): Int = AppGet.appContext().getColor(resId)
        fun getString(@StringRes resId: Int): String = AppGet.appContext().getString(resId)
        fun getIntArray(@ArrayRes resId: Int): IntArray = AppGet.appContext().resources.getIntArray(resId)
        fun getStringArray(@ArrayRes resId: Int): Array<out String> = AppGet.appContext().resources.getStringArray(resId)
    }

    object MathX {
        /* 随机选择 eg：mutableListOf("0", "1", "2", "3", "4", "5", "6", "7", "8") */
        fun randomSelect(lst: List<String>): String {
            var result = ""
            lst.shuffled().take(1).forEach { result = it }
            return result
        }

        fun formatNumber(number: Int): String = if (number in 0..9) "0$number" else number.toString()
    }

    // import com.ta pa doo.alerter.Alerter
    object StringX {
        /* Iterable 类型和 Array 类型 */
        /* 将一个可迭代对象（如列表、集合或数组）中的所有元素转换成一个由指定分隔符分隔的字符串 */
        fun listToString(strLst: List<String>, sep: String = "") = strLst.joinToString(separator = sep)

        fun utf8StringToHex(utf8String: String): String = utf8String.map { it.code.toString(16).padStart(2, '0') }.joinToString(separator = "")

        /* 解决中文字符串的显示乱码问题(MySQL出现过) */
        fun handleChineseCharsets(content: String): String = URLDecoder.decode(URLEncoder.encode(content, "ISO-8859-1"), "UTF-8")
    }

    object ByteX {
        /* 字节数组 转16进制字符串，没有空格，XX 大写, 用于传输 */
        fun bytesToHeXX(bytes: ByteArray): String = buildString { bytes.forEach { append(String.format("%02X", it)) } }

        /* 无汉字16进制字符串转  字节数组 */
        fun heXXToBytes(str: String): ByteArray = ByteArray(str.length / 2) { str.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

        /* 单字节  转十进制数字，输出转换后的无符号整数值 */
        fun byteToUInt(byte: kotlin.Byte): Int = (byte.toInt() and 0xFF)

        /* 单字节  转16进制字符 */
        fun byteToHex(byte: kotlin.Byte): String = String.format("%02X", byte)

        /* 单字节  转二进制字符串 */
        fun byteToBinaryString(byte: kotlin.Byte): String = Integer.toBinaryString(byte.toInt()).padStart(8, '0')

        /* 4字节长度：16进制字符串 转整数 */
        fun hexOf4BytesToInt(hex: String): Int = heXXToBytes(hex).let { it[3].toInt() and 0xFF or (it[2].toInt() and 0xFF shl 8) or (it[1].toInt() and 0xFF shl 16) or (it[0].toInt() and 0xFF shl 24) }

        // 2字节长度：16进制字符串 转整数
        fun hexOf2BytesToInt(strHex: String): Int = strHex.toInt(16)

        /* 字节流转 Bitmap */
        fun byteArrayToBitmap(bytesArray: ByteArray?): Bitmap? = bytesArray?.let { BitmapFactory.decodeStream(ByteArrayInputStream(it)) }

        /* char 字母数组 转化为 16进制字符串 */
        fun charsToHex(chars: CharArray): String = bytesToHeXX(charsToBytes(chars))

        private fun charsToBytes(chars: CharArray): ByteArray {
            val bytes = ByteArray(chars.size)
            for (i in chars.indices) {
                bytes[i] = (chars[i].code - 48).toByte()
            }
            return bytes
        }

        fun hexToString(hex: String): String {
            val finalString = StringBuilder()
            val tempString = StringBuilder()
            var i = 0
            while (i < hex.length - 1) {
                val output = hex.substring(i, i + 2)
                val decimal = output.toInt(16)
                finalString.append(decimal.toChar())
                tempString.append(decimal)
                i += 2
            }
            return finalString.toString()
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
         * 将Base16进制数字解密成明文（包括中文）
         * public static String decodeHex2ChineseStr(String hexStr, Charset charsetName) {
         *         String tmpStr;
         *         if (hexStr.length() <= 4) {
         *             return hexStr;
         *         }
         *         try {
         *             if ((hexStr.length()) % 2 != 0) {
         *                 hexStr += "00";
         *             }
         *             int byteLength = hexStr.length() / 2;
         *             byte[] bytes = new byte[byteLength];
         *             int temp;
         *             for (int i = 0; i < byteLength; i++) {
         *                 int tmpInt = Integer.parseInt(new BigInteger(hexStr.charAt(2 * i) + "", 16).toString(10));
         *                 int tmpInt2 = Integer.parseInt(new BigInteger(hexStr.charAt(2 * i + 1) + "", 16).toString(10));
         *                 temp = tmpInt * 16 + tmpInt2;
         *                 bytes[i] = (byte) (temp < 128 ? temp : temp - 256);
         *             }
         *             tmpStr = new String(bytes, charsetName);
         *             return tmpStr;
         *         } catch (Exception ex) {
         *             Log.e("error", "decodeHexToStr try error");
         *         }
         *         return "-1";
         *     }
         *
         *         ODO CRC16 校验结果
         *     private static int crcResolve(int crc, int b, int p) {
         *         for (int i = 0; i < 8; i++) {
         *             int f = (crc & 0x8000) >> 8;
         *             int g = (b & 0x80);
         *             if ((f ^ g) != 0) {
         *                 crc <<= 1;
         *                 crc ^= p;
         *             } else {
         *                 crc <<= 1;
         *             }
         *             b <<= 1;
         *         }
         *         return crc;
         *     }
         *
         *         /**
         *      * 获取 CRC 校验值
         *      */
         *     public static int crcCheckInt(byte[] bytes) {
         *         int crc = 0xA000;
         *         int p = 0xA001;
         *         for (byte b : bytes) {
         *             crc = crcResolve(crc, b, p);
         *         }
         *         return ~crc;
         *     }
         *         ODO Int 数值转 2 字节数组
         *     public static byte[] unsignedShortTo2Byte(int s) {
         *         byte[] targets = new byte[2];
         *         targets[0] = (byte) (s >> 8 & 0xFF);
         *         targets[1] = (byte) (s & 0xFF);
         *         return targets;
         *     }
         *
         *     public static byte[] chars2Bytes(char[] arraySn) {
         *         byte[] bytes = new byte[arraySn.length];
         *         for (int i = 0; i < arraySn.length; ++i) {
         *             bytes[i] = (byte) ((int) arraySn[i] - 48);
         *         }
         *         return bytes;
         *     }
         *
         */
    }

    object FileX {

        fun getFileSize(file: File?): Int {
            val fis: FileInputStream
            return try {
                fis = FileInputStream(file)
                fis.available()
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }

        // 保存圖片到應用目錄下
        // 使用协程保存Bitmap并获取路径
        suspend fun saveBitmapToJpg(context: Context, bitmap: Bitmap?, filename: String): String? {
            if (bitmap == null) {
                return ""
            }
            return withContext(Dispatchers.IO) {
                val wrapper = ContextWrapper(context)
                val directory = wrapper.getDir("XXConst.Val.FILE_PATH_SAVED", Context.MODE_PRIVATE)
                val file = File(directory, "$filename.jpg")
                var outputStream: FileOutputStream? = null
                try {
                    outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    file.absolutePath
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                } finally {
                    try {
                        outputStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        suspend fun saveJSONObjectToFile(context: Context, jsonString: String, filename: String): String? {
            return withContext(Dispatchers.IO) {
                val wrapper = ContextWrapper(context)
                val directory = wrapper.getDir("XXConst.Val.FILE_PATH_SAVED", Context.MODE_PRIVATE)
                val file = File(directory, "$filename.json")
                try {
                    FileOutputStream(file).use { outputStream ->
                        outputStream.write(jsonString.toByteArray())
                    }
                    file.absolutePath
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            }
        }

        fun writeToFile(filePath: String?, content: String?, append: Boolean) {
            val file = File(filePath)
            val fileWriter: FileWriter
            try {
                if (!file.exists()) {
                    println("创建文件" + file.createNewFile())
                }
                fileWriter = FileWriter(filePath, append)
                fileWriter.write(content)
                fileWriter.flush()
                fileWriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun deleteFilesInFolder(directoryPath: String) {
            val directory = File(directoryPath)
            if (directory.exists() && directory.isDirectory) {
                val files = directory.listFiles()
                if (files != null) {
                    for (file in files) {
                        if (file.isFile) {
                            file.delete()
                        } else if (file.isDirectory) {
                            deleteFilesInFolder(file.absolutePath)
                        }
                    }
                }
            }
        }
    }

    object Str {
        fun replaceBlanks(str: String): String = str.replace(" ", "")
    }

    object DateX {
        /* 指定日期转时间戳 */
        fun dataTimeToSecondsTimeStamp(time: String?, pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
            try {
                val date: LocalDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern))
                val off: OffsetDateTime = date.atZone(ZoneId.of("UTC+8")).toOffsetDateTime()
                return off.toEpochSecond()
            } catch (e: Exception) {
                Log.e("Date", "报错：dataTimeToSecondsTimeStamp ${e.message} ${e.stackTrace}")
            }
            return 0L
        }

        /* 时间戳转 16进制 */
        fun timestampToHex(): String = needSureDoubleSize(java.lang.Long.toHexString(System.currentTimeMillis()).uppercase())

        /* 16进制转时间戳 */
        fun hexToTimestamp(hexString: String): Long = java.lang.Long.parseLong(hexString, 16)

        fun timestampToClock(): String = getCurrentTime("HH:mm:ss  yy-MM-dd")

        private fun needSureDoubleSize(content: String): String = if (content.length % 2 != 0) "0$content" else content

        // 将十进制字符串转换为十六进制字符串
        /**
         * 時間戳轉日期
         */
        @SuppressLint("SimpleDateFormat")
        fun timestampToDate(timestamp: Long): String {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(Date(timestamp))
        }

        private fun getCurrentTime(pat: String): String {
            val currentDateTime: LocalDateTime = LocalDateTime.now()
            return currentDateTime.format(DateTimeFormatter.ofPattern(pat))
        }

        fun getWeekDaysArray(): Array<String> {
            return arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        }

        fun getWeekDaysMapValue(week: String): Int {
            return when (week) {
                "周日" -> 7
                "周一" -> 1
                "周二" -> 2
                "周三" -> 3
                "周四" -> 4
                "周五" -> 5
                "周六" -> 6
                else -> -1
            }
        }

        fun currentDateTimeDigit(): String = getCurrentTime("yyyyMMddHHmmss")
        fun currentDateTime(): String = getCurrentTime("yyyy-MM-dd HH:mm:ss")
    }

    @SuppressLint("StaticFieldLeak")
    object AppGet {
        private var context: Context? = null

        @JvmStatic
        fun appContext(): Context {
            if (context == null) {
                throw RuntimeException("Please call [HUtils.AppGet.init(context)] first")
            } else {
                return context as Context
            }
        }

        @JvmStatic
        fun init(context: Context) {
            if (AppGet.context != null) return
            AppGet.context = context.applicationContext
        }
    }

    object StringUtil {

//        fun hexToJsonString(hex: String): String {
//            return XxBytesJava.decodeHex2ChineseStr(hex, Charsets.UTF_8)
//        }

        /**
         * JSON String 转 Hex
         */
//        fun jsonToHex(jsonStr: String): String {
//            return ByteArrayUtil.toHeX(jsonStr.toByteArray(Charsets.UTF_8))
//        }
    }
}