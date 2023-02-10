package com.example.dataclean


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-9-4 上午11:54
 */
class SundkiUtilTest {
//    /**
//     * A完整数据并去重复/home/ccg
//     */
//    @Test
//    fun cleaningData() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/sundki.json")
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
//        KtStringUtil.saveAsFileWriter("/home/ccg/sundki1.json", GsonBuilder().create().toJson(list))
//    }
//
//    /**
//     * B2把最终结果分解成几份
//     */
//    @Test
//    fun cleaningDataThree() {
//        //1加载json文件到内存中
////        val fileStr = KtStringUtil.getStrInFile("/home/ccg/sundkiok.json")
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/sundki1.json")
//        val listDatasOne = GsonBuilder().create().fromJson<ArrayList<VideoBean>>(
//            fileStr,
//            object : TypeToken<ArrayList<VideoBean>>() {}.type
//        )
//        val videoTag: MutableList<String> = ArrayList()
//        val videoUrl: MutableList<String> = ArrayList()
//        for (i in listDatasOne) {
//            if (!videoTag.contains(i.tags)) {
//                videoTag.add(i.tags)
//            }
//        }
//        for (i in videoTag.indices) {
//            val tempList: MutableList<VideoBean> = ArrayList()
//            for (j in listDatasOne) {
//                if (videoTag[i] == j.tags) {
//                    tempList.add(j)
//                }
//            }
//            val ssss = VideoListBean()
//            ssss.videoTag = videoTag[i]
//            ssss.data = tempList
//            val videoU = "/home/ccg/$i.json"
//            KtStringUtil.saveAsFileWriter(videoU, GsonBuilder().create().toJson(ssss))
//            videoUrl.add(videoU)
//        }
//        val secon = SecondListBean()
//        secon.videoTag = videoTag
//        secon.videoUrl = videoUrl
//        KtStringUtil.saveAsFileWriter("/home/ccg/index.json", GsonBuilder().create().toJson(secon))
//
////https://siyou.nos-eastchina1.126.net/21/sundki/index.json
////https://siyou.nos-eastchina1.126.net/21/sundki/1.json
//
////        val zongList = KtStringUtil.averageAssign(listDatasOne, 3)
////        val aa = FinalVideoBean()
////        aa.timeStamp = System.currentTimeMillis()
////        aa.data = zongList!![0]
////        KtStringUtil.saveAsFileWriter(
////            "/home/ccg/buzz1.json",
////            GsonBuilder().create().toJson(aa)
////        )
////        val bb = FinalVideoBean()
////        bb.timeStamp = System.currentTimeMillis()
////        bb.data = zongList[1]
////        KtStringUtil.saveAsFileWriter(
////            "/home/ccg/buzz2.json",
////            GsonBuilder().create().toJson(bb)
////        )
////        val cc = FinalVideoBean()
////        cc.timeStamp = System.currentTimeMillis()
////        cc.data = zongList[2]
////        KtStringUtil.saveAsFileWriter(
////            "/home/ccg/buzz3.json",
////            GsonBuilder().create().toJson(cc)
////        )
//    }

}