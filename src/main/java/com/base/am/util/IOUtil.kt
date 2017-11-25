package com.base.am.util

import cn.hillwind.common.util.BASE64Codec

import java.io.*
import java.nio.charset.Charset
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * IO工具类。用于处理IO相关的操作。
 * 特别包含了加载配置文件的操作。
 */
object IOUtil {

    val UTF8 = "utf-8"

    enum class FileType private constructor(var size: Long, var text: String) {
        Image(512 * 1024, "512K"), // 512k
        NormalFile(10 * 1024 * 1024, "10M"), // 10M
        LargeFile(20 * 1024 * 1024, "20M")   // 20M
    }

    /**
     * 检查文件大小

     * @param file 文件
     * *
     * @param type 文件类型
     * *
     * @return true - 大小合适； false - 超过大小限制.
     */
    fun checkFileSize(file: File, type: FileType): Boolean {
        return file.length() <= type.size
    }

    /**
     * config 设置为默认访问权限，以允许同一个包下面的其它类来修改这个config,方便测试。
     */
    internal var config: Properties? = null
    private var configLoaded = false
    private val ConfigLock = 0

    private var tempFolder: File? = null

    private val LOCK = 0

    /**
     * 取得系统默认临时目录。

     * @return
     */
    @JvmStatic
    fun getTempFolder(): File {
        if (tempFolder == null) {
            synchronized (LOCK) {
                if (tempFolder == null) {
                    try {
                        val tempFile = File.createTempFile("IOUtil", "tmp")
                        tempFolder = tempFile.parentFile
                        tempFile.delete()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }

                }
            }
        }
        return tempFolder!!
    }

    @JvmStatic
    fun createTempFile(fileName: String): File {
        return createTempFile("IOUtil", fileName)
    }

    @JvmStatic
    fun createTempFile(prefix: String, suffix: String): File {
        try {
            val f = File.createTempFile(prefix, suffix)
            f.deleteOnExit()
            return f
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }

    }

    @JvmStatic
    fun getFile(fileName: String): File {
        return File(fileName)
    }

    /**
     * 获取配置文件。
     * 应该在classpath中包含一个config.xml的配置文件。
     */
    @JvmStatic
    fun getConfig(): Properties {
        if (config == null && !configLoaded) {
            synchronized (ConfigLock) {
                if (!configLoaded) {
                    try {
                        config = loadPropertiesFromXML("/config.xml")
                    } finally {
                        configLoaded = true
                    }
                }
            }
        }
        return config!!
    }

    /**
     * 读取一个文件。
     */
    @JvmStatic
    fun openFile(file: File): InputStream {
        try {
            val `is` = FileInputStream(file)
            return `is`
        } catch (exp: Exception) {
            throw RuntimeException("openFile error, file: " + file, exp)
        }

    }

    /**
     * 读取一个文件。
     */
    @JvmStatic
    fun openFile(name: String): InputStream {
        try {
            return openFile(File(name))
        } catch (exp: Exception) {
            throw RuntimeException("openFile error, file: " + name, exp)
        }

    }

    /**
     * 读取一个文本文件。返回字符串文本。可以指定字符集。
     */
    @JvmStatic
    fun readAsText(fileName: String, charsetName: String): String {
        var `is`: InputStream? = null
        try {
            `is` = openFile(fileName)
            return readAsText(`is`, charsetName)
        } catch (exp: Exception) {
            throw RuntimeException("readTextFile error, file: " + fileName, exp)
        } finally {
            close(`is`)
        }
    }

    @JvmStatic
    fun readAsBase64(file: File, offset: Int, length: Int): String {
        val content = read(file, offset, length)
        return String(BASE64Codec.encode(content))
    }

    @JvmStatic
    fun readAsBase64(file: File, offset: Double, length: Double): String {
        val content = read(file, offset.toInt(), length.toInt())
        return String(BASE64Codec.encode(content))
    }

    @JvmStatic
    fun read(file: File, offset: Int, length: Int): ByteArray {
        var `is`: BufferedInputStream? = null

        val var6: ByteArray
        try {
            `is` = BufferedInputStream(FileInputStream(file))
            var exp = ByteArray(length)
            `is`.skip(offset.toLong())
            val len = `is`.read(exp)
            if (len < length) {
                exp = Arrays.copyOfRange(exp, 0, len)
            }

            var6 = exp
        } catch (var10: Exception) {
            throw RuntimeException("writeFile error, file: " + file, var10)
        } finally {
            close(`is`)
        }

        return var6
    }

    /**
     * 写文本文件。

     * @param fileName 文件名称。
     * *
     * @param content  内容。
     */
    @JvmStatic
    fun writeAsText(fileName: String, content: String) {
        writeAsText(File(fileName), content)
    }

    /**
     * 写文本文件。

     * @param file    文件。
     * *
     * @param content 内容。
     */
    @JvmStatic
    fun writeAsText(file: File, content: String) {
        var osw: OutputStreamWriter? = null
        try {
            osw = OutputStreamWriter(FileOutputStream(file), IOUtil.UTF8)
            osw.write(content, 0, content.length)
        } catch (exp: Exception) {
            throw RuntimeException("writeTextFile error, file: " + file, exp)
        } finally {
            close(osw)
        }
    }

    /**
     * 写文件。

     * @param file 文件
     * *
     * @param b    Buffer 大小
     */
    @JvmStatic
    fun write(file: File, b: ByteArray) {
        var os: BufferedOutputStream? = null
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            os.write(b)
        } catch (exp: Exception) {
            throw RuntimeException("writeFile error, file: " + file, exp)
        } finally {
            close(os)
        }
    }

    /**
     * 写文件。

     * @param file       文件
     * *
     * @param is         内容
     * *
     * @param bufferSize Buffer 大小
     */
    @JvmStatic
    @JvmOverloads fun write(file: File, `is`: InputStream, bufferSize: Int = 1024 * 100) {
        val fos=FileOutputStream(file)
        write(fos,`is`,bufferSize)
        close(fos)
    }

    /**
     * 输入输出。将is流中的内容输出到os中去。

     * @param os         输出流。
     * *
     * @param is         输入流。
     * *
     * @param bufferSize Buffer大小。
     */
    @JvmStatic
    fun write(os: OutputStream, `is`: InputStream, bufferSize: Int) {
        `is`.copyTo(os,bufferSize)
    }

    /**
     * 输入输出。将is流中的内容输到os中去。
     * copy 函数，不会自动关闭os，以备后续继续copy，要求调用者自行关闭

     * @param os         输出流。
     * *
     * @param is         输入流。
     * *
     * @param bufferSize Buffer大小。
     */
    @JvmStatic
    @JvmOverloads fun copy(os: OutputStream, `is`: InputStream, bufferSize: Int = 1024 * 1024) {
        `is`.copyTo(os,bufferSize)
        os.flush()
    }

    @JvmStatic
    @JvmOverloads fun getPrintWriter(os: OutputStream, charset: String = UTF8): PrintWriter {
        try {
            return PrintWriter(BufferedWriter(OutputStreamWriter(os, charset)))
        } catch (ex: UnsupportedEncodingException) {
            return PrintWriter(BufferedWriter(OutputStreamWriter(os)))
        }

    }

    /**
     * 读取一个字符串流。返回字符串文本。可以指定字符集。

     * @param charsetName 字符集，可以为null.
     */
    @JvmStatic
    fun readAsText(`is`: InputStream, charsetName: String?): String {
        return `is`.reader(Charset.forName(charsetName)).readText()
    }

    @JvmStatic
    fun read(file: File): ByteArray {
        return read(openFile(file))
    }


    /**
     * 读取一个流。返回包含全部内容的byte[]。
     * 因为本方法会读取全部内容到内存中，故此方法只能在已知文件不是很大的情况下使用。
     */
    @JvmStatic
    fun read(`is`: InputStream): ByteArray {
        val bufferList = ArrayList<ByteArray>()
        val lengthList = ArrayList<Int>()
        var total = 0
        try {
            do {
                val buffer = ByteArray(10000)
                var length: Int
                length = `is`.read(buffer)
                if (length == -1) {
                    break
                }
                total += length
                bufferList.add(buffer)
                lengthList.add(length)
            } while (true)
            val result = ByteArray(total)
            var offset = 0
            for (i in bufferList.indices) {
                System.arraycopy(bufferList[i], 0, result, offset, lengthList[i])
                offset += lengthList[i]
            }
            return result
        } catch (exp: Exception) {
            throw RuntimeException("readInputStream error!", exp)
        }

    }

    /**
     * 读取某个网页的内容

     * @param url         地址，可以是http/https
     * *
     * @param charsetName 字符集，可以为null，使用系统默认字符集
     */
    @JvmStatic
    fun readURL(url: String, charsetName: String): String {
        try {
            val u = java.net.URL(url)
            return readAsText(u.openStream(), charsetName)
        } catch (exp: Exception) {
            throw RuntimeException("readURL error!", exp)
        }

    }

    /**
     * 从classpath中读取一个name的资源文件。
     */
    @JvmStatic
    fun getResourceAsStream(name: String): InputStream {
        try {
            val `is` = IOUtil::class.java.getResourceAsStream(name)
            return `is`
        } catch (exp: Exception) {
            exp.printStackTrace()
            throw RuntimeException("getResouce error, file: " + name, exp)
        }

    }

    /**
     * 从classpath中读取一个name的属性文件。
     */
    @JvmStatic
    fun loadProperties(name: String): Properties {
        val p = Properties()
        try {
            val `is` = IOUtil::class.java.getResourceAsStream(name)
            p.load(`is`)
            return p
        } catch (exp: Exception) {
            exp.printStackTrace()
            throw RuntimeException("Load Properties file error: " + name, exp)
        }

    }

    /**
     * 从classpath中读取一个name的XML格式的属性文件。
     */
    @JvmStatic
    fun loadPropertiesFromXML(name: String): Properties {
        val p = Properties()
        try {
            val `is` = IOUtil::class.java.getResourceAsStream(name)
            p.loadFromXML(`is`)
            return p
        } catch (exp: Exception) {
            exp.printStackTrace()
            throw RuntimeException("Load Properties file error: " + name, exp)
        }

    }

    @JvmStatic
    fun close(resource: Closeable?) {
        try {
            resource?.close()
        } catch (exp: IOException) {
            exp.printStackTrace()
        }

    }

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        if (args.size > 0) {
            // System.out.println(readURL(args[0], null));
        }
    }

    /**
     * 把字节数组保存为一个文件

     * @EditTime 2007-8-13 上午11:45:56
     */
    @JvmStatic
    fun writeAsBytes(b: ByteArray, outputFile: String): File {
        var stream: BufferedOutputStream? = null
        var file: File? = null
        try {
            file = File(outputFile)
            val fstream = FileOutputStream(file)
            stream = BufferedOutputStream(fstream)
            stream.write(b)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            }
        }
        return file!!
    }

    @JvmStatic
    fun getErrorStackTrace(th: Throwable): String {
        val sw = StringWriter()
        th.printStackTrace(PrintWriter(sw))
        return sw.toString()
    }

    @Throws(Exception::class)
    @JvmStatic
    fun zipFile(filePath: Array<String>?, fileName: Array<String>?): InputStream {
        if (null == filePath || null == fileName || filePath.size != fileName.size) {
            throw RuntimeException("filePath and fileName not be null,filePath.length unequ")
        }
        val bufferLength = 1024 * 1024
        val buffer = ByteArray(bufferLength)
        var length: Int

        val zipFile = File.createTempFile("zip", ".zip")
        println(zipFile.length())

        val out = FileOutputStream(zipFile)
        val zipOut = ZipOutputStream(out, Charset.forName("gb2312"))
        var file: File
        var `in`: InputStream
        for (i in fileName.indices) {
            zipOut.putNextEntry(ZipEntry(fileName[i]))
            file = File(filePath[i])
            if (!file.exists()) {
                throw RuntimeException("file " + filePath[i] + "not exists")
            }
            FileInputStream(file).copyTo(zipOut,bufferLength)
            zipOut.flush()
        }
        zipOut.close()
        println(zipFile.length())
        return FileInputStream(zipFile)
    }

    @JvmStatic
    fun alloc(file: File, length: Long) {
        val buffer = ByteArray(1048576)
        var os: BufferedOutputStream? = null

        try {
            os = BufferedOutputStream(FileOutputStream(file))
            var exp = length

            do {
                val batchLength = Math.min(exp, buffer.size.toLong()).toInt()
                os.write(buffer, 0, batchLength)
                exp -= batchLength.toLong()
            } while (exp > 0L)

            os.flush()
        } catch (var10: Exception) {
            var10.printStackTrace()
            throw RuntimeException("writeFile error, file: " + file, var10)
        } finally {
            close(os)
        }
    }

    @JvmStatic
    fun alloc(file: File, length: Double) {
        val buffer = ByteArray(1048576)
        var os: BufferedOutputStream? = null

        try {
            os = BufferedOutputStream(FileOutputStream(file))
            var exp = length.toLong()

            do {
                val batchLength = Math.min(exp, buffer.size.toLong()).toInt()
                os.write(buffer, 0, batchLength)
                exp -= batchLength.toLong()
            } while (exp > 0L)

            os.flush()
        } catch (var10: Exception) {
            var10.printStackTrace()
            throw RuntimeException("writeFile error, file: " + file, var10)
        } finally {
            close(os)
        }
    }

    @JvmStatic
    fun write(file: File, offset: Int, content: String) {
        write(file, offset, BASE64Codec.decode(content.toByteArray()))
    }

    @JvmStatic
    fun write(file: File, offset: Double, content: String) {
        write(file, offset.toInt(), BASE64Codec.decode(content.toByteArray()))
    }

    @JvmStatic
    fun write(file: File, offset: Int, content: ByteArray?) {
        synchronized (file) {
            var raf: RandomAccessFile? = null

            try {
                raf = RandomAccessFile(file, "rw")
                raf.seek(offset.toLong())
                raf.write(content)
            } catch (var11: Exception) {
                var11.printStackTrace()
            } finally {
                close(raf)
            }

        }
    }

    @JvmStatic
    fun write(array: ByteArray, offset: Int, content: ByteArray) {
        synchronized (array) {
            System.arraycopy(content, 0, array, offset, content.size)
        }
    }


}
