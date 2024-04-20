package lib.dc.utils

import com.dc.android.launcher.util.HUtils
import org.apache.commons.lang3.StringUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object UtilString {

    fun replaceBlanks(str: String): String = HUtils.Str.replaceBlanks(str)

    fun currentDataTimeYMDHM(): String {
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val str = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss"))
        return StringUtils.substring(str, 2, 16)
    }
}