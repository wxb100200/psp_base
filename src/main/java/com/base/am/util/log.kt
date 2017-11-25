@file:Suppress("KDocUnresolvedReference", "unused", "DEPRECATION")

package com.base.am.util

import org.apache.log4j.Level
import java.util.*
import kotlin.reflect.KClass


/**
 * 保存日志到数据库的服务
 */
interface IDbLogService {
    fun save2db(name:String ,message :String?, remark:String?,syncTime:Long?=null)
}

class Logger protected constructor(val logger: org.apache.log4j.Logger, val name: String) {

    fun debug(message: Any) {
        logger.log(FQCN,Level.DEBUG,message,null)
    }

    fun info(message: Any) {
        logger.log(FQCN,Level.INFO,message,null)
    }

    fun warn(message: Any) {
        logger.log(FQCN,Level.WARN,message,null)
    }

    /**
     * 同时输出到日志及数据库
     * @param message
     */
    fun warn2db(message: Any?) {
        save2db(name, if (message == null) "" else message.toString(), "warn")
        logger.log(FQCN,Level.WARN,message,null)
    }

    fun error(message: Any) {
        logger.log(FQCN,Level.ERROR,message,null)
    }

    fun error(message: Any, t: Throwable) {
        logger.log(FQCN,Level.ERROR,message,t)
    }

    /**
     * 同时保存到数据库和日志文件
     * @param message
     */
    fun error2db(message: Any?) {
        save2db(name, if (message == null) "" else message.toString(), "error")
        logger.log(FQCN,Level.ERROR,message,null)
    }

    /**
     * 同时保存到数据库和日志文件
     * @param message
     * *
     * @param t
     */
    fun error2db(message: Any?, t: Throwable?) {
        save2db(name, (if (message == null) "" else message.toString()) + " : " + if (t == null) "" else t.message, "error")
        logger.log(FQCN,Level.ERROR,message,t)
    }

    companion object {
        // 这个参数非常重要,决定了log4j是不是能够打印出正确的行号和文件名称
        private val FQCN = Logger::class.java.name

        @JvmStatic
        fun getLogger(name: String): Logger {
            return Logger(org.apache.log4j.Logger.getLogger(name), name)
        }

        @JvmStatic
        fun getLogger(clazz: Class<*>): Logger {
            return Logger(org.apache.log4j.Logger.getLogger(clazz), clazz.name)
        }

        @JvmStatic
        fun getLogger(clazz: KClass<*>): Logger {
            return Logger(org.apache.log4j.Logger.getLogger(clazz.java), clazz.java.name)
        }

        private fun save2db(name: String, message: String?, remark: String?) {
            provider?.save2db(name,message,remark)
        }

        val provider by lazy {
            ServiceLoader.load(IDbLogService::class.java)?.first()
        }

    }

}
