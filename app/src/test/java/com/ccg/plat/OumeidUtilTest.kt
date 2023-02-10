package com.example.dataclean


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-17 下午3:46
 */
class OumeidUtilTest {
//    /**
//     * B1把不能播放的数据删除并把最终结果保存到本地
//     */
//    @Test
//    fun cleaningDataTwo() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/oumeid.json")
////        val fileStr = KtStringUtil.getStrInFile("E:\\oumeid.json")
//        val fileStrTwo = KtStringUtil.getStrInFile("/home/ccg/oumeidText.json")
////        val fileStrTwo = KtStringUtil.getStrInFile("E:\\oumeidtext.json")
////        //2把json转换成list
//        val listDatasOne = GsonBuilder().create()
//            .fromJson<ArrayList<VideoBean>>(
//                fileStr,
//                object : TypeToken<ArrayList<VideoBean>>() {}.type
//            )
//        val listDatasTwo = GsonBuilder().create()
//            .fromJson<List<VideoInfo>>(fileStrTwo, object : TypeToken<List<VideoInfo>>() {}.type)
//        for (videoUrlData in listDatasOne) {
//            for (pictureUrlData in listDatasTwo) {
//                if (videoUrlData.getvUrl() == pictureUrlData.getvUrl()) {
//                    videoUrlData.i = "1"
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
//        KtStringUtil.saveAsFileWriter("/home/ccg/oumeidok.json", GsonBuilder().create().toJson(listDatasOne))
////        KtStringUtil.saveAsFileWriter("E:\\oumeidok.json", GsonBuilder().create().toJson(listDatasOne))
//    }
//
//    /**
//     * B2把最终结果分解成几份
//     */
//    @Test
//    fun cleaningDataThree() {
//        //1加载json文件到内存中
//        val fileStr = KtStringUtil.getStrInFile("/home/ccg/oumeidok.json")
////        val fileStr = KtStringUtil.getStrInFile("E:\\oumeidok.json")
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
////            val videoU = "E:\\新建文件夹\\$i.json"
//            KtStringUtil.saveAsFileWriter(videoU, GsonBuilder().create().toJson(ssss))
//            videoUrl.add("https://siyou.nos-eastchina1.126.net/21/oumeid/$i.json")
//        }
//        val secon = SecondListBean()
//        secon.videoTag = videoTag
//        secon.videoUrl = videoUrl
//        KtStringUtil.saveAsFileWriter("/home/ccg/index.json", GsonBuilder().create().toJson(secon))
////        KtStringUtil.saveAsFileWriter("E:\\新建文件夹\\index.json", GsonBuilder().create().toJson(secon))
//    }

}