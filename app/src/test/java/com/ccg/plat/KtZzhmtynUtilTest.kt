package com.example.dataclean


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_疫染黎明
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_疫染黎明
 * date : 20-1-7 上午9:19
 */
class KtZzhmtynUtilTest {
//    /**
//     * A完整数据并去重复
//     */
//    @Test
//    fun cleaningData() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/iori/zzhmtyn.json")
//        //2把json转换成list
//        val listDatas = GsonBuilder().create()
//            .fromJson<List<VideoBean>>(fileStr, object : TypeToken<List<VideoBean>>() {}.type)
//        println("一共: " + listDatas.size)
//        //3把不完整的数据填写完整
//        for ((aa, av) in listDatas.withIndex()) {
//            for ((bb, bv) in listDatas.withIndex()) {
//                if (av.id != bv.id
//                    && av.name == bv.name
//                    && av.getpUrl().isNullOrEmpty()
//                    && av.getvUrl().isNotEmpty()
//                    && bv.getvUrl().isNullOrEmpty()
//                    && bv.getpUrl().isNotEmpty()
//                ) {
//                    av.setpUrl(bv.getpUrl())
//                    av.tags = bv.tags
//                    bv.setvUrl(av.getvUrl())
//                }
//            }
//        }
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
//        KtStringUtil.saveAsFileWriter("/home/iori/zzhmtyn1.json", GsonBuilder().create().toJson(list))
//    }
//    /**
//     * B1把不能播放的数据删除并把最终结果保存到本地
//     */
//    @Test
//    fun cleaningDataTwo() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/iori/zzhmtyn1.json")
//        val fileStrTwo = KtStringUtil.getStrInFile("/home/iori/zzhmtyntext.json")
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
//            "/home/iori/zzhmtynok.json",
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
//        val fileStr = KtStringUtil.getStrInFile("/home/iori/zzhmtynok.json")
//        val listDatasOne = GsonBuilder().create()
//            .fromJson<ArrayList<VideoBean>>(
//                fileStr,
//                object : TypeToken<ArrayList<VideoBean>>() {}.type
//            )
//        val zongList = KtStringUtil.averageAssign(listDatasOne, 2)
//        KtStringUtil.saveAsFileWriter(
//            "/home/iori/jjok1.json",
//            GsonBuilder().create().toJson(zongList?.get(0))
//        )
//        KtStringUtil.saveAsFileWriter(
//            "/home/iori/jjok2.json",
//            GsonBuilder().create().toJson(zongList?.get(1))
//        )
//    }
}