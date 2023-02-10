package com.example.dataclean


import com.ccg.plat.SecondListBean
import com.ccg.plat.VideoListBean
import com.ccg.plat.entity.VideoBean
import com.ccg.plat.entity.VideoInfo
import com.ccg.plat.util.KtStringUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :BaihuzuUtilTest
 * 主要功能 :baihuzu
 * 维护人员 : C4_雍和
 * date : 20-9-24 下午3:43
 *
 */
class AcbUtilTest {
//    val name = "acb"
//    val isLinux = false
//    /**
//     * A完整数据并去重复/home/ccg
//     */
//    @Test
//    fun cleaningData() {
//        val startTime = System.currentTimeMillis()
//        //1加载json文件到内存中
//        val fileStr = if (isLinux) {
//            KtStringUtil.getStrInFile("/home/ccg/$name.json")
//        } else {
//            KtStringUtil.getStrInFile("E:\\$name.json")
//        }
//        //2把json转换成list
//        val listDatas = GsonBuilder().create().fromJson<ArrayList<VideoBean>>(
//            fileStr,
//            object : TypeToken<ArrayList<VideoBean>>() {}.type
//        )
//        println("原始数据一共: " + listDatas.size)
//        //3原始数据去重复
//        val setOne = LinkedHashSet<VideoBean>()
//        val listOne = ArrayList<VideoBean>()
//        setOne.addAll(listDatas)
//        listOne.addAll(setOne)
//        listDatas.clear()
//        println("原始数据去重后一共: " + listOne.size)
//        if (isLinux) {
//            KtStringUtil.saveAsFileWriter(
//                "/home/ccg/" + name + "1.json",
//                GsonBuilder().create().toJson(listOne)
//            )
//        } else {
//            KtStringUtil.saveAsFileWriter(
//                "E:\\" + name + "1.json",
//                GsonBuilder().create().toJson(listOne)
//            )
//        }
//        val endTime = System.currentTimeMillis()
//        println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
//    }
//
//    /**
//     * B1把不能播放的数据删除并把最终结果保存到本地
//     */
//    @Test
//    fun cleaningDataTwo() {
//        //1加载json文件到内存中
//        val fileStr = if (isLinux) {
//            KtStringUtil.getStrInFile("/home/ccg/aqdtv1.json")
//        } else {
//            KtStringUtil.getStrInFile("E:\\" + name + "1.json")
//        }
//        val fileStrTwo = if (isLinux) {
//            KtStringUtil.getStrInFile("/home/ccg/" + name + "Text.json")
//        } else {
//            KtStringUtil.getStrInFile("E:\\" + name + "Text.json")
//
//        }
//        //2把json转换成list
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
//        if (isLinux) {
//            KtStringUtil.saveAsFileWriter(
//                "/home/ccg/" + name + "ok.json",
//                GsonBuilder().create().toJson(listDatasOne)
//            )
//        } else {
//            KtStringUtil.saveAsFileWriter(
//                "E:\\" + name + "ok.json",
//                GsonBuilder().create().toJson(listDatasOne)
//            )
//        }
//    }
//
//    /**
//     * B2把最终结果分解成几份
//     */
//    @Test
//    fun cleaningDataThree() {
//        //1加载json文件到内存中
//        val fileStr = if (isLinux) {
//            KtStringUtil.getStrInFile("/home/ccg/" + name + "ok.json")
//        } else {
//            KtStringUtil.getStrInFile("E:\\" + name + "ok.json")
//        }
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
//            val videoU = if (isLinux) {
//                "/home/ccg/$i.json"
//            } else {
//                "E:\\新建文件夹\\$i.json"
//            }
//            KtStringUtil.saveAsFileWriter(videoU, GsonBuilder().create().toJson(ssss))
//            videoUrl.add("https://siyou.nos-eastchina1.126.net/21/$name/$i.json")
//        }
//        val secon = SecondListBean()
//        secon.videoTag = videoTag
//        secon.videoUrl = videoUrl
//        if (isLinux) {
//            KtStringUtil.saveAsFileWriter(
//                "/home/ccg/index.json",
//                GsonBuilder().create().toJson(secon)
//            )
//        } else {
//            KtStringUtil.saveAsFileWriter(
//                "E:\\新建文件夹\\index.json",
//                GsonBuilder().create().toJson(secon)
//            )
//        }
//        println("完成    $name")
//    }
}