package com.ccg.videoplayer.util

import okhttp3.ResponseBody
import java.io.*

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-21 上午9:12
 */


/**
 *  把Retrofit2请求的结果保存到本地
 * @receiver ResponseBody
 * @param filePath String   文件保存路径
 * @param fileName String   需要保存的文件的文件名字
 */
fun ResponseBody.writeFilePath(filePath: String, fileName: String) {
    try {
        //判断文件夹是否存在
        val files = File(filePath)
        //跟目录一个文件夹
        if (!files.exists()) {
            //不存在就创建出来
            files.mkdirs()
        }
        //创建一个文件
        val futureStudioIconFile = File(filePath + fileName)
        //初始化输入流
        var inputStream: InputStream? = null
        //初始化输出流
        var outputStream: OutputStream? = null
        try {
            //设置每次读写的字节
            val fileReader = ByteArray(4096)
            var fileSizeDownloaded: Long = 0
            //请求返回的字节流
            inputStream = this.byteStream()
            //创建输出流
            outputStream = FileOutputStream(futureStudioIconFile)
            //进行读取操作
            while (true) {
                val read = inputStream!!.read(fileReader)
                if (read == -1) {
                    break
                }
                //进行写入操作
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read.toLong()
            }
            //刷新
            outputStream.flush()
        } catch (e: IOException) {
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
    }
}



/**
 *  把Retrofit2请求的结果保存到本地
 * @receiver ResponseBody
 * @param filePath String   文件保存路径
 * @param fileName String   需要保存的文件的文件名字
 */
fun ResponseBody.writeFile(files: File, fileName: String) {
    try {
        //判断文件夹是否存在
        //跟目录一个文件夹
        if (!files.exists()) {
            //不存在就创建出来
            files.mkdirs()
        }
        //创建一个文件
        val futureStudioIconFile = File(files.path + fileName)
        //初始化输入流
        var inputStream: InputStream? = null
        //初始化输出流
        var outputStream: OutputStream? = null
        try {
            //设置每次读写的字节
            val fileReader = ByteArray(4096)
            var fileSizeDownloaded: Long = 0
            //请求返回的字节流
            inputStream = this.byteStream()
            //创建输出流
            outputStream = FileOutputStream(futureStudioIconFile)
            //进行读取操作
            while (true) {
                val read = inputStream!!.read(fileReader)
                if (read == -1) {
                    break
                }
                //进行写入操作
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read.toLong()
            }
            //刷新
            outputStream.flush()
        } catch (e: IOException) {
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
    }
}