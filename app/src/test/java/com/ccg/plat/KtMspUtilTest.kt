package com.example.dataclean


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_疫染黎明
 * 描述 :msp的数据清洗工具类
 * 主要功能 :
 * 维护人员 : C4_疫染黎明
 * date : 20-1-2 上午10:51
 * KtZzhmtynUtilTest
 */
class KtMspUtilTest {
//    /**
//     * A去重复
//     */
//    @Test
//    fun cleaningData() {
//        //1加载json文件到内存中
////        val fileStr = KtStringUtil.getStrInFile("E:\\msp.json")
//        val fileStr = KtStringUtil.getStrInFile("/home/iori/msp.json")
//        //2把json转换成list
//        val listDatas = GsonBuilder().create()
//            .fromJson<List<VideoBean>>(fileStr, object : TypeToken<List<VideoBean>>() {}.type)
//        println("一共: " + listDatas.size)
//        //4对对象进行去重操作
//        val set = LinkedHashSet<VideoBean>()
//        val list = ArrayList<VideoBean>()
//        set.addAll(listDatas)
//        list.addAll(set)
//        val iterator = list.iterator()
//        while (iterator.hasNext()) {
//            val item = iterator.next()
//            if (item.getpUrl().isNullOrEmpty() || item.getvUrl().isNullOrEmpty()) {
//                iterator.remove()
//            }
//        }
//        //5把去重复的数据保存到文件中
//        println("去重复后: " + list.size)
////        KtStringUtil.saveAsFileWriter("E:\\msp1.json", GsonBuilder().create().toJson(list))
//        KtStringUtil.saveAsFileWriter("/home/iori/msp1.json", GsonBuilder().create().toJson(list))
//    }
//
//
//    /**
//     * B1把不能播放的数据删除并把最终结果保存到本地
//     */
//    @Test
//    fun cleaningDataTwo() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/iori/msp1.json")
//        val fileStrTwo = KtStringUtil.getStrInFile("/home/iori/msptext.json")
////        //2把json转换成list
//        val listDatasOne = GsonBuilder().create()
//            .fromJson<ArrayList<VideoBean>>(
//                fileStr,
//                object : TypeToken<ArrayList<VideoBean>>() {}.type
//            )
//        val listDatasTwo = GsonBuilder().create()
//            .fromJson<List<VideoInfo>>(fileStrTwo, object : TypeToken<List<VideoInfo>>() {}.type)
//        for ((aa, av) in listDatasOne.withIndex()) {
//            for ((bb, bv) in listDatasTwo.withIndex()) {
//                if (av.getvUrl() == bv.getvUrl()) {
//                    av.i = "1"
//                }
//            }
//        }
//        val it = listDatasOne.iterator()
//        while (it.hasNext()) {
//            val item = it.next()
//            if (item.i != "1") {
//                it.remove()
//            }
//        }
//        println("最终的: " + listDatasOne.size)
//        KtStringUtil.saveAsFileWriter(
//            "/home/iori/mspok.json",
//            GsonBuilder().create().toJson(listDatasOne)
//        )
//    }
//
//    /**
//     * B2把最终结果分解成几份
//     */
//    @Test
//    fun cleaningDataThree() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/iori/mspok.json")
//        val listDatasOne = GsonBuilder().create()
//            .fromJson<ArrayList<VideoBean>>(
//                fileStr,
//                object : TypeToken<ArrayList<VideoBean>>() {}.type
//            )
//        val zongList = KtStringUtil.averageAssign(listDatasOne, 2)
//        KtStringUtil.saveAsFileWriter(
//            "/home/iori/msp1.json",
//            GsonBuilder().create().toJson(zongList?.get(0))
//        )
//        KtStringUtil.saveAsFileWriter(
//            "/home/iori/msp2.json",
//            GsonBuilder().create().toJson(zongList?.get(1))
//        )
//    }
}