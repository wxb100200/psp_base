package com.base.am.util

import cn.hillwind.common.util.gson.parseJson
import java.io.FileInputStream
import java.nio.charset.Charset

/**
 * 文件工具类
 */
object FileUtil{

    /**
     * 筛选文件里面需要匹配的记录
     * path:文件路径
     * reg:匹配文件中包含的内容
     */
    @JvmStatic
    fun filterRecord(path:String,reg:String):List<String>?{
        return FileInputStream(path).reader(Charsets.UTF_8).readLines().filter { it.contains(reg)}
    }

    /**
     * 读取文件数据，返回一个字符串
     */
    @JvmStatic
    fun fileToString(path: String):String{
        return FileInputStream(path).reader(Charsets.UTF_8).readText()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val str=fileToString("D:\\data.txt").parseJson()


    }

}
