package dc.library.utils

object ByteArrayUtil {

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
}