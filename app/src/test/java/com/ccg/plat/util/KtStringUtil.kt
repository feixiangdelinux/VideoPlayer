package com.ccg.plat.util

import com.ccg.plat.entity.VideoBean
import java.io.*

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/11/7 10:19
 */
class KtStringUtil {
    companion object {
        fun getStrInFile(filePash: String): String {
            val encoding = "UTF-8"
            val file = File(filePash)
            val filelength = file.length()
            val filecontent = ByteArray(filelength.toInt())
            try {
                val `in` = FileInputStream(file)
                `in`.read(filecontent)
                `in`.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return try {
                String(filecontent)
            } catch (e: UnsupportedEncodingException) {
                System.err.println("The OS does not support $encoding")
                e.printStackTrace()
                ""
            }

        }


        fun saveAsFileWriter(fileName: String, contentStr: String) {
            var fwriter: FileWriter? = null
            try {
                // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
                fwriter = FileWriter(fileName)
                fwriter.write(contentStr)
            } catch (ex: IOException) {
                ex.printStackTrace()
            } finally {
                try {
                    fwriter!!.flush()
                    fwriter.close()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }

            }
        }

        /**
         * 将一个list平均分成num份
         * @param list List<VideoBean>
         * @param num Int
         * @return ArrayList<ArrayList<VideoBean>>
         */
        fun splitList(list: List<VideoBean>, num: Int): ArrayList<ArrayList<VideoBean>> {
            val listSize = list.size
            var stringlist = ArrayList<VideoBean>()
            val stringListHashMap = ArrayList<ArrayList<VideoBean>>()
            for ((aa, av) in list.withIndex()) {
                stringlist.add(av)
                if (((aa + 1) % num == 0) || (aa + 1 == listSize)) {
                    stringListHashMap.add(stringlist)
                    stringlist = ArrayList()
                }
            }
            return stringListHashMap
        }


        fun <T> averageAssign(
            source: List<T>,
            n: Int
        ): List<List<T>>? {
            val result: MutableList<List<T>> =
                java.util.ArrayList()
            var remainder = source.size % n //(先计算出余数)
            val number = source.size / n //然后是商
            var offset = 0 //偏移量
            for (i in 0 until n) {
                var value: List<T>? = null
                if (remainder > 0) {
                    value = source.subList(i * number + offset, (i + 1) * number + offset + 1)
                    remainder--
                    offset++
                } else {
                    value = source.subList(i * number + offset, (i + 1) * number + offset)
                }
                result.add(value)
            }
            return result
        }


    }
}