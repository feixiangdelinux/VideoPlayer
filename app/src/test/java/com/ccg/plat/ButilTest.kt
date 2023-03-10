package com.ccg.plat

import com.blankj.utilcode.util.FileIOUtils
import com.ccg.plat.entity.SpiderInfo
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/3/3 14:13
 */
class ButilTest {
    val spiderInfo = SpiderInfo(name = "langyoueight", url = "http://7333dy.com", isMerge = false, isPlay = true)

    @Test
    fun cleaningData() {
        val fileStr = FileIOUtils.readFile2List("E:\\${spiderInfo.name}.json")
        println("aaa   " + fileStr.size)
        fileStr.clear()
    }

}