package com.base.am.web.pspWeb

import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

/**
 * Created by wxb on 2017/1/5.
 */
object QRcodefind{
    var index=0

    fun findQRcode(){
        var file= File("D:/bullzip")
        var files=file.listFiles().forEach {
            testDecode(it)
        }

    }
    fun testDecode(file: File) {
        val image: BufferedImage
        index++
        println(file.name)
        try {
            image = ImageIO.read(file)
            val source = BufferedImageLuminanceSource(image)
            val binarizer = HybridBinarizer(source)
            val binaryBitmap = BinaryBitmap(binarizer)
            val hints = HashMap<DecodeHintType, Any>()
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8")
//            hints.put(DecodeHintType.TRY_HARDER, true)
            val result = MultiFormatReader().decode(binaryBitmap, hints)// 对图像进行解码
            println("${index},fileName:${file.name},content:${result.text}")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}

fun main(args: Array<String>) {
    QRcodefind.findQRcode()
}