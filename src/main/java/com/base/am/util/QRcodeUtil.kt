package com.base.am.util

import com.alibaba.fastjson.JSONObject
import com.google.zxing.*
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.HybridBinarizer

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.FileSystems
import java.util.HashMap

/**
 * 二维码工具类
 */
object QRcodeUtil {
     var width = 200// 图像宽度
     var height = 200// 图像高度
     var format = "png"// 图像类型

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        testEncode()
        testDecode()
    }

    /**
     * 生成图像

     * @throws WriterException
     * *
     * @throws IOException
     */
    @Throws(WriterException::class, IOException::class)
    fun testEncode() {
        val filePath = "D://"
        val fileName = "zxing.png"
        val json = JSONObject()
        json.put(
                "zxing",
                "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing")
        json.put("author", "shihy")
        val content = json.toJSONString()// 内容
        val hints = HashMap<EncodeHintType, Any>()
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8")
        val bitMatrix = MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints)// 生成矩阵
        val path = FileSystems.getDefault().getPath(filePath, fileName)
        MatrixToImageWriter.writeToPath(bitMatrix, format, path)// 输出图像
        println("输出成功.")
    }

    /**
     * 解析图像
     */
    fun testDecode() {
        val filePath = "D://zxing.png"
        val image: BufferedImage
        try {
            image = ImageIO.read(File(filePath))
            val source = BufferedImageLuminanceSource(image)
            val binarizer = HybridBinarizer(source)
            val binaryBitmap = BinaryBitmap(binarizer)
            val hints = HashMap<DecodeHintType, Any>()
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8")
            val result = MultiFormatReader().decode(binaryBitmap, hints)// 对图像进行解码
            val content = JSONObject.parseObject(result.text)
            println("图片中内容：  ")
            println("author： " + content.getString("author"))
            println("zxing：  " + content.getString("zxing"))
            println("图片中格式：  ")
            println("encode： " + result.barcodeFormat)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }

    }
}
