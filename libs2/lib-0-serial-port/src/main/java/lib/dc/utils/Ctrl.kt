package lib.dc.utils

import com.alibaba.fastjson.JSONObject
import com.drake.logcat.LogCat

interface Ctrl {

    object Func {

        @Suppress("UNCHECKED_CAST")
        fun mapToJSONObject(map: Map<String, Any>): JSONObject {
            val jsonObject = JSONObject()
            for ((key, value) in map) {
                when (value) {
                    is String, is Int, is Double, is Long, is Boolean -> jsonObject[key] = value
                    is Float -> jsonObject[key] = value.toDouble()
                    is Map<*, *> -> jsonObject.putAll(mapToJSONObject(value as Map<String, Any>))
                    else -> {}
                }
            }
            return jsonObject
        }
    }

    object Log {

        fun e(e: Exception, message: String = "") {
            LogCat.e("报错：$message ${e.message}\n${e.stackTrace}")
        }

        /**
         * 专门打印 CAN 通信相关日志
         */
        fun infoCan(fromWhere: String, buffer: ByteArray) {
            val message = UtilByteArray.toHeXLog(buffer)
            val msgMapper = mapOf("Class = " to fromWhere, "INFO CAN日志 = " to message)
            LogCat.json(json = Func.mapToJSONObject(msgMapper), type = LogCat.Type.INFO)
        }

        /**
         * 专门打印 CAN 通信相关日志
         */
        fun debugCan(fromWhere: String, message: String) {
            val msgMapper = mapOf("Class = " to fromWhere, "DEBUG CAN日志 = " to message)
            LogCat.json(json = Func.mapToJSONObject(msgMapper), type = LogCat.Type.DEBUG)
        }

        /**
         * 专门打印 TTL 通信相关日志
         */
        fun infoTTL(fromWhere: String, message: String) {
            val msgMapper = mapOf("Class = " to fromWhere, "INFO CAN日志 = " to message)
            LogCat.json(json = Func.mapToJSONObject(msgMapper), type = LogCat.Type.INFO)
        }

        /**
         * 专门打印 TTL 通信相关日志
         */
        fun debugTTL(fromWhere: String, message: String) {
            val msgMapper = mapOf("Class = " to fromWhere, "DEBUG CAN日志 = " to message)
            LogCat.json(json = Func.mapToJSONObject(msgMapper), type = LogCat.Type.DEBUG)
        }

        /**
         * 专门打印 LIN 通信相关日志
         */
        fun infoLin(fromWhere: String, message: String) {
            val msgMapper = mapOf("Class = " to fromWhere, "INFO CAN日志 = " to message)
            LogCat.json(json = Func.mapToJSONObject(msgMapper), type = LogCat.Type.INFO)
        }

        /**
         * 专门打印 LIN 通信相关日志
         */
        fun debugLin(fromWhere: String, message: String) {
            val msgMapper = mapOf("Class = " to fromWhere, "DEBUG CAN日志 = " to message)
            LogCat.json(json = Func.mapToJSONObject(msgMapper), type = LogCat.Type.DEBUG)
        }
    }

}