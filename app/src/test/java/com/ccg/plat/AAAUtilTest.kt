package com.ccg.plat

import android.text.TextUtils.replace
import com.ccg.plat.entity.VideoBean
import com.ccg.plat.entity.VideoInfo
import com.ccg.plat.util.KtStringUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2020/9/25 13:46
 */
class AAAUtilTest {
    //    val name = "acb"
//    val name = "aqdav"
    val name = "hbsy"
//    val name = "aqdtv"
//    val name = "buzz"

    //    val name = "se"
    val isLinux = false

    private fun getVideoType(name: String): Int {
        return if (name in arrayOf("aqdtv", "ck", "hbsy")) {
            1
        } else {
            //不需要合并的
            -1
        }
    }

    /**
     * A完整数据并去重复/home/ccg
     */
    @Test
    fun cleaningData() {
        val startTime = System.currentTimeMillis()
        //1加载json文件到内存中
        val fileStr = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/$name.json")
        } else {
            KtStringUtil.getStrInFile("E:\\$name.json")
        }

        //2把json转换成list
        val listDatas = GsonBuilder().disableHtmlEscaping().create().fromJson<ArrayList<VideoBean>>(fileStr, object : TypeToken<ArrayList<VideoBean>>() {}.type)
        println("原始数据一共: " + listDatas.size)
        //3原始数据去重复
        val setOne = LinkedHashSet<VideoBean>()
        val listOne = ArrayList<VideoBean>()
        setOne.addAll(listDatas)
        listOne.addAll(setOne)
        listDatas.clear()
        println("原始数据去重后一共: " + listOne.size)
        val lexing = getVideoType(name)
        if (lexing == -1) {
            println("不需要数据拼接")
            //7去掉不完整的数据
            val iterator = listOne.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (item.getpUrl().isNullOrEmpty() || item.getvUrl().isNullOrEmpty()) {
                    iterator.remove()
                }
            }
            //8把去重复的数据保存到文件中
            println("去重复后: " + listOne.size)
            if (isLinux) {
                KtStringUtil.saveAsFileWriter("/home/ccg/" + name + "1.json", GsonBuilder().disableHtmlEscaping().create().toJson(listOne))
            } else {
                KtStringUtil.saveAsFileWriter("E:\\" + name + "1.json", GsonBuilder().disableHtmlEscaping().create().toJson(listOne))
            }
            val endTime = System.currentTimeMillis()
            println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
        } else {
            println("需要数据拼接")
            //4对数据进行分组，一组视频地址，一组缩略图
            val listVideo = ArrayList<VideoBean>()
            val listPic = ArrayList<VideoBean>()
            for (videoUrlData in listOne) {
                if (videoUrlData.getvUrl().isNotEmpty()) {
                    listVideo.add(videoUrlData)
                } else if (videoUrlData.getpUrl().isNotEmpty()) {
                    listPic.add(videoUrlData)
                }
            }
            listOne.clear()
            println("视频地址一共: " + listVideo.size)
            //5把缩略图合并到视频地址中
            for (index in listVideo.indices) {
                for (pictureUrlData in listPic) {
                    if (lexing == 1) {
                        if (listVideo[index].url == pictureUrlData.url) {
                            if (listVideo[index].name.isEmpty()) {
                                listVideo[index].name = pictureUrlData.name
                            }
                            if (listVideo[index].tags.isEmpty()) {
                                listVideo[index].tags = pictureUrlData.tags
                            }
                            listVideo[index].setpUrl(pictureUrlData.getpUrl())
                            continue
                        }
                    }
                }
                if (index % 10000 == 0) {
                    println("当前遍历到第: $index")
                }
            }
            listPic.clear()
            //6对完整的数据进行去重操作
            val set = LinkedHashSet<VideoBean>()
            val list = ArrayList<VideoBean>()
            set.addAll(listVideo)
            list.addAll(set)
            listVideo.clear()
            //7去掉不完整的数据
            val iterator = list.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (item.getpUrl().isNullOrEmpty() || item.getvUrl().isNullOrEmpty()) {
                    iterator.remove()
                }
            }
            //8把去重复的数据保存到文件中
            println("去重复后: " + list.size)
            for (videoUrlData in list) {
                videoUrlData.setvUrl(videoUrlData.getvUrl().replace("\\/","/"))
            }
            if (isLinux) {
                KtStringUtil.saveAsFileWriter("/home/ccg/" + name + "1.json", GsonBuilder().disableHtmlEscaping().create().toJson(list))
            } else {
                KtStringUtil.saveAsFileWriter("E:\\" + name + "1.json", GsonBuilder().disableHtmlEscaping().create().toJson(list))
            }
            val endTime = System.currentTimeMillis()
            println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
        }
    }

    /**
     * B1把不能播放的数据删除并把最终结果保存到本地
     */
    @Test
    fun cleaningDataTwo() {
        //1加载json文件到内存中
        val fileStr = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/" + name + "1.json")
        } else {
            KtStringUtil.getStrInFile("E:\\" + name + "1.json")
        }
        val fileStrTwo = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/" + name + "Text.json")
        } else {
            KtStringUtil.getStrInFile("E:\\" + name + "Text.json")

        }
        //2把json转换成list
        val listDatasOne = GsonBuilder().disableHtmlEscaping().create().fromJson<ArrayList<VideoBean>>(fileStr, object : TypeToken<ArrayList<VideoBean>>() {}.type)
        val listDatasTwo = GsonBuilder().disableHtmlEscaping().create().fromJson<List<VideoInfo>>(fileStrTwo, object : TypeToken<List<VideoInfo>>() {}.type)
        for (videoUrlData in listDatasOne) {
            for (pictureUrlData in listDatasTwo) {
                if (videoUrlData.getvUrl() == pictureUrlData.getvUrl()) {
                    videoUrlData.i = "1"
                }
            }
        }
        val it = listDatasOne.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item.i != "1") {
                it.remove()
            }
        }
        for (videoUrlData in listDatasOne) {
            videoUrlData.url=""
        }
        println("最终的: " + listDatasOne.size)
        if (isLinux) {
            KtStringUtil.saveAsFileWriter("/home/ccg/" + name + "ok.json", GsonBuilder().disableHtmlEscaping().create().toJson(listDatasOne))
        } else {
            KtStringUtil.saveAsFileWriter("E:\\" + name + "ok.json", GsonBuilder().disableHtmlEscaping().create().toJson(listDatasOne))
        }
    }

    /**
     * B2把最终结果分解成几份
     */
    @Test
    fun cleaningDataThree() {
        //1加载json文件到内存中
        val fileStr = if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/" + name + "ok.json")
        } else {
            KtStringUtil.getStrInFile("E:\\" + name + "ok.json")
        }
        val listDatasOne = GsonBuilder().disableHtmlEscaping().create().fromJson<ArrayList<VideoBean>>(fileStr, object : TypeToken<ArrayList<VideoBean>>() {}.type)
        val videoTag: MutableList<String> = ArrayList()
        val videoUrl: MutableList<String> = ArrayList()
        for (i in listDatasOne) {
            if (!videoTag.contains(i.tags)) {
                videoTag.add(i.tags)
            }
        }
        for (i in videoTag.indices) {
            val tempList: MutableList<VideoBean> = ArrayList()
            for (j in listDatasOne) {
                if (videoTag[i] == j.tags) {
                    tempList.add(j)
                }
            }
            val ssss = VideoListBean()
            ssss.videoTag = videoTag[i]
            ssss.data = tempList
            val videoU = if (isLinux) {
                "/home/ccg/$i.json"
            } else {
                "E:\\新建文件夹\\$i.json"
            }
            KtStringUtil.saveAsFileWriter(videoU, GsonBuilder().disableHtmlEscaping().create().toJson(ssss))
            videoUrl.add("https://siyou.nos-eastchina1.126.net/21/$name/$i.json")
        }
        val secon = SecondListBean()
        secon.videoTag = videoTag
        if (isLinux) {
            KtStringUtil.saveAsFileWriter("/home/ccg/index.json", GsonBuilder().disableHtmlEscaping().create().toJson(secon))
        } else {
            KtStringUtil.saveAsFileWriter("E:\\新建文件夹\\index.json", GsonBuilder().disableHtmlEscaping().create().toJson(secon))
        }
        println("完成    $name")
    }
}