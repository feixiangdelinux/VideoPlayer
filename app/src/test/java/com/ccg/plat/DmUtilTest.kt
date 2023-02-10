package com.example.dataclean


import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2020/9/7 15:27
 */
class DmUtilTest {
//    /**
//     * A完整数据并去重复/home/ccg
//     */
//    @Test
//    fun cleaningData() {
//        val startTime = System.currentTimeMillis()
//        //1加载json文件到内存中
////        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzz.json")
//        val fileStr = KtStringUtil.getStrInFile("E:\\dm.json")
//        //2把json转换成list
//        val listDatas = GsonBuilder().create()
//            .fromJson<List<VideoBean>>(fileStr, object : TypeToken<List<VideoBean>>() {}.type)
//        println("原始数据一共: " + listDatas.size)
//        val setOne = LinkedHashSet<VideoBean>()
//        val listOne = ArrayList<VideoBean>()
//        setOne.addAll(listDatas)
//        listOne.addAll(setOne)
//        println("原始数据去重后一共: " + listOne.size)
//        //3把不完整的数据填写完整
//        for (videoUrlData in listOne) {
//            for (pictureUrlData in listOne) {
//                if (videoUrlData.id != pictureUrlData.id
//                    && videoUrlData.url == pictureUrlData.url
//                    && videoUrlData.getpUrl().isNullOrEmpty()
//                    && videoUrlData.getvUrl().isNotEmpty()
//                    && pictureUrlData.getvUrl().isNullOrEmpty()
//                    && pictureUrlData.getpUrl().isNotEmpty()
//                ) {
//                    videoUrlData.setpUrl(pictureUrlData.getpUrl())
//                    videoUrlData.tags = pictureUrlData.tags
//                    videoUrlData.name = pictureUrlData.name
//                    pictureUrlData.setvUrl(videoUrlData.getvUrl())
//                }
//            }
//        }
//        //4对对象进行去重操作
//        val set = LinkedHashSet<VideoBean>()
//        val list = ArrayList<VideoBean>()
//        set.addAll(listOne)
//        list.addAll(set)
//        val iterator = list.iterator()
//        while (iterator.hasNext()) {
//            val item = iterator.next()
//            if (item.getpUrl().isNullOrEmpty() || item.getvUrl().isNullOrEmpty()) {
//                iterator.remove()
//            }
//        }
//        //5把去重复的数据保存到文件中
//        println("数据合并后去重: " + list.size)
////        KtStringUtil.saveAsFileWriter("/home/ccg/buzz1.json", GsonBuilder().create().toJson(list))
//        KtStringUtil.saveAsFileWriter("E:\\dm1.json", GsonBuilder().create().toJson(list))
//        val endTime = System.currentTimeMillis()
//        println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
//    }
//
//
//    /**
//     * B1把不能播放的数据删除并把最终结果保存到本地
//     */
//    @Test
//    fun cleaningDataTwo() {
//        //1加载json文件到内存中
////        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzz1.json")
//        val fileStr = KtStringUtil.getStrInFile("E:\\dm1.json")
////        val fileStrTwo = KtStringUtil.getStrInFile("/home/ccg/buzzText.json")
//        val fileStrTwo = KtStringUtil.getStrInFile("E:\\dmText.json")
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
////        KtStringUtil.saveAsFileWriter("/home/ccg/buzzok.json", GsonBuilder().create().toJson(listDatasOne))
//        KtStringUtil.saveAsFileWriter("E:\\dmok.json", GsonBuilder().create().toJson(listDatasOne))
//    }
//
//    /**
//     * B2把最终结果分解成几份
//     */
//    @Test
//    fun cleaningDataThree() {
//        //1加载json文件到内存中
////        val fileStr = KtStringUtil.getStrInFile("/home/ccg/buzzok.json")
//        val fileStr = KtStringUtil.getStrInFile("E:\\dmok.json")
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
////            val videoU = "/home/ccg/$i.json"
//            val videoU = "E:\\新建文件夹\\$i.json"
//            KtStringUtil.saveAsFileWriter(videoU, GsonBuilder().create().toJson(ssss))
//            videoUrl.add("https://siyou.nos-eastchina1.126.net/21/dm/$i.json")
//        }
//        val secon = SecondListBean()
//        secon.videoTag = videoTag
//        secon.videoUrl = videoUrl
////        KtStringUtil.saveAsFileWriter("/home/ccg/index.json", GsonBuilder().create().toJson(secon))
//        KtStringUtil.saveAsFileWriter("E:\\新建文件夹\\index.json", GsonBuilder().create().toJson(secon))
//    }
}