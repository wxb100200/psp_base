package com.base.am.web

import com.avaje.ebean.Ebean
import com.avaje.ebean.config.GlobalProperties
import com.avaje.ebean.jaxrs.MarshalOptions
import com.avaje.ebean.text.json.JsonWriteOptions
import com.base.am.model.BaseEntity
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.apache.log4j.Logger

abstract class RecordsWrapper(val records:List<*>? = null ) {
    abstract fun toJsonString(recordsJson: String): String
    private val defaultPretty = GlobalProperties.getBoolean("ebean.json.pretty", true)

    @Throws(Exception::class)
    fun toJson(): String {

        if(records==null || records.isEmpty()){
            return toJsonString("")
        }
        val str = if (records[0] is BaseEntity) {
            //如果第一个数据是实体,那么用Ebean来输出Json
            var options: JsonWriteOptions? = MarshalOptions.removeJsonWriteOptions()
            val pathProperties = MarshalOptions.removePathProperties()

//            println("\t\t>>>> pathProperties: >>>>\t\t$pathProperties") // SecurityControlFilter中在request一开始,先将path清理掉,免得被其他请求串掉.
            if (options == null) {
                if (pathProperties != null) {
                    options = JsonWriteOptions()
                    options.pathProperties = pathProperties
                }
            } else if (options.pathProperties == null && pathProperties != null) {
                options = options.copy()
                options!!.pathProperties = pathProperties
            }

            records.map { obj ->
                val jsonStr: String
                try {
                    jsonStr = retry(3) {
                        jsonContext.toJsonString(obj, defaultPretty, options)
                    }
                } catch (exp: Exception) {
                    throw exp
                }

                jsonStr
            }.joinToString(",")
        } else if(records[0] is JsonElement) {
            // 如果第一个数据是JsonElement,那么直接输出
            records.map { it.toString() }.joinToString(",")
        } else {
            // 否则用gson进行转换输出成Json格式。
            records.map { gson.toJson(it) }.joinToString(",")
        }

        return toJsonString(str)
    }

    /**
     * 尝试执行指定次数的函数f()->T
     */
    inline fun <T> retry(times:Int, f:()->T):T{
        var exp:Exception?=null
        (1..times).forEach {
            try {
                return f()
            }catch(e:Exception){
                e.printStackTrace()
                exp = e
            }
        }
        throw exp!!
    }

    companion object {
        protected var jsonContext = Ebean.createJsonContext()
        protected var gson = Gson()
        private val log = Logger.getLogger(RecordsWrapper::class.java)
    }

}
