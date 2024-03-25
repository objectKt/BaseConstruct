package dc.library.utils

object HexUtil {

    @Synchronized
    fun toHexString(arg: ByteArray?, length: Int): String {
        return if (arg != null && arg.isNotEmpty()) {
            val sb = StringBuffer()
            val hexArray = "0123456789ABCDEF".toCharArray()
            for (j in 0 until length) {
                val v = arg[j].toInt() and 255
                sb.append(hexArray[v ushr 4]).append(hexArray[v and 15])
            }
            val returnString: String = sb.toString()
            sb.setLength(0)
            returnString
        } else {
            ""
        }
    }

}