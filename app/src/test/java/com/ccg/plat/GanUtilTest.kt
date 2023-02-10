package com.example.dataclean


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-3 下午2:10
 */
class GanUtilTest {
//    /**
//     * A完整数据并去重复/home/ccg
//     */
//    @Test
//    fun cleaningData() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/gan.json")
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
//                    bv.tags = av.tags
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
//        KtStringUtil.saveAsFileWriter("/home/ccg/gan1.json", GsonBuilder().create().toJson(list))
//    }
//
//
//    /**
//     * B1把不能播放的数据删除并把最终结果保存到本地
//     */
//    @Test
//    fun cleaningDataTwo() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/gan1.json")
//        val fileStrTwo = KtStringUtil.getStrInFile("/home/ccg/GanText.json")
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
//            "/home/ccg/ganok.json",
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
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/ganok.json")
//        val listDatasOne = GsonBuilder().create()
//            .fromJson<ArrayList<VideoBean>>(
//                fileStr,
//                object : TypeToken<ArrayList<VideoBean>>() {}.type
//            )
//        val zongList = KtStringUtil.averageAssign(listDatasOne, 7)
//        val aa = FinalVideoBean()
//        aa.timeStamp = System.currentTimeMillis()
//        aa.data = zongList!![0]
//        KtStringUtil.saveAsFileWriter(
//            "/home/ccg/gan1.json",
//            GsonBuilder().create().toJson(aa)
//        )
//        val bb = FinalVideoBean()
//        bb.timeStamp = System.currentTimeMillis()
//        bb.data = zongList[1]
//        KtStringUtil.saveAsFileWriter(
//            "/home/ccg/gan2.json",
//            GsonBuilder().create().toJson(bb)
//        )
//        val cc = FinalVideoBean()
//        cc.timeStamp = System.currentTimeMillis()
//        cc.data = zongList[2]
//        KtStringUtil.saveAsFileWriter(
//            "/home/ccg/gan3.json",
//            GsonBuilder().create().toJson(cc)
//        )
//        val dd = FinalVideoBean()
//        dd.timeStamp = System.currentTimeMillis()
//        dd.data = zongList[3]
//        KtStringUtil.saveAsFileWriter(
//            "/home/ccg/gan4.json",
//            GsonBuilder().create().toJson(dd)
//        )
//        val ee = FinalVideoBean()
//        ee.timeStamp = System.currentTimeMillis()
//        ee.data = zongList[4]
//        KtStringUtil.saveAsFileWriter(
//            "/home/ccg/gan5.json",
//            GsonBuilder().create().toJson(ee)
//        )
//        val ff = FinalVideoBean()
//        ff.timeStamp = System.currentTimeMillis()
//        ff.data = zongList[5]
//        KtStringUtil.saveAsFileWriter(
//            "/home/ccg/gan6.json",
//            GsonBuilder().create().toJson(ff)
//        )
//        val gg = FinalVideoBean()
//        gg.timeStamp = System.currentTimeMillis()
//        gg.data = zongList[6]
//        KtStringUtil.saveAsFileWriter(
//            "/home/ccg/gan7.json",
//            GsonBuilder().create().toJson(gg)
//        )
//    }
//
//    /**
//     * B2把最终结果分解成几份
//     */
//    @Test
//    fun cleaningDataFour() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzz.json")
//        val listDatasOne = GsonBuilder().create()
//            .fromJson<ArrayList<VideoBean>>(
//                fileStr,
//                object : TypeToken<ArrayList<VideoBean>>() {}.type
//            )
//        val zongList = KtStringUtil.averageAssign(listDatasOne, 10)
//        for ((index, e) in zongList!!.withIndex()) {
//            KtStringUtil.saveAsFileWriter(
//                "/home/ccg/buzz$index.txt",
//                GsonBuilder().create().toJson(e)
//            )
//        }
//    }
}