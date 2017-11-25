package com.base.am.resources

import com.base.am.util.IOUtil
import com.base.am.web.RecordsWrapper
import java.util.Arrays

/**
 * 带有分页信息的RecordsWrapper,主要用于和ExtJs配合使用
 */
class PagableRecordsWrapper(records: List<*>?) : RecordsWrapper(records) {

    //记录总数
    var total = 0
        private set

    //状态码
    var status = -1
        private set

    //返回的错误消息
    var message: String? = null
        private set

    //元数据,也是为了配合ExtJs使用
    var metaData = "\"metaData\":false"
        private set

    //是否成功
    var isSuccess = false
        private set

    override fun toJsonString(recordsJson: String): String {
        return """{"status":$status,"success":$isSuccess, "message":"${escape(message)}",$metaData,"total":$total,"records":[ $recordsJson ]}"""
    }

    fun toJsonString(recordsJson: String = "", withBracket: Boolean = false ): String {
        var recordsJson2 = recordsJson.trim()
        if (withBracket && recordsJson2.length > 0) {
            return toJsonString(recordsJson2.substring(1, recordsJson2.length - 1))
        }
        return toJsonString(recordsJson2)
    }

    companion object {

        /**
         * 将字符串中的回车,换行,双引号等会引起json格式错误的字符予以替换.
         */
        @JvmStatic
        fun escape(str: String?): String? {
            if (str == null) return null
            return str.replace("\n".toRegex(), "\\\\n").replace("\r".toRegex(), "").replace("\"".toRegex(), "\\\"")
        }

        /**
         * 成功,但是没有数据需要返回
         */
        @JvmStatic
        fun empty(): PagableRecordsWrapper {
            return ok(null, 0)
        }

        /**
         * 成功,且只有一条数据返回
         */
        @JvmStatic
        fun single(obj: Any): PagableRecordsWrapper {
            return ok(Arrays.asList(*arrayOf(obj)), 1)
        }

        /**
         * 成功
         */
        @JvmStatic
        fun ok(list: List<Any>?, total: Int = list?.size ?: 0): PagableRecordsWrapper {
            return multiple(list, total, true, 0, null)
        }

        /**
         * 错误
         */
        @JvmStatic
        fun error(errorCode: Int, errorMessage: String): PagableRecordsWrapper {
            return multiple(null, 0, false, errorCode, errorMessage)
        }

        /**
         * 错误
         */
        @JvmStatic
        fun error(errorCode: Int, exception: Throwable): PagableRecordsWrapper {
            return multiple(null, 0, false, errorCode, exception.message + "\n\n错误详情：\n" + IOUtil.getErrorStackTrace(exception))
        }

        /**
         * 基本方法
         */
        private fun multiple(list: List<Any>?, total: Int, success: Boolean, errorCode: Int, errorMessage: String?): PagableRecordsWrapper {
            val wrapper = PagableRecordsWrapper(if (success) list else null)
            wrapper.isSuccess = success
            if (success) {
                wrapper.status = 0
                wrapper.total = if (list == null) 0 else total
            } else {
                wrapper.status = errorCode
                wrapper.message = errorMessage
                wrapper.metaData = """"metaData":{"status":${wrapper.status},"message":"${escape(wrapper.message)}"}"""
            }
            return wrapper
        }
    }


}
