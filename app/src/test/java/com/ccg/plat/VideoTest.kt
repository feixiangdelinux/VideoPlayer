package com.ccg.plat

import com.ccg.plat.entity.ScrapyBean
import com.ccg.plat.entity.VideoBean
import com.ccg.plat.util.KtStringUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Test

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/2/18 7:26
 */
class VideoTest {
//    private fun getVideoType() {
//        val listOne = ArrayList<ScrapyBean>()
//        listOne.add(ScrapyBean(name = "se", isMerge = false, url = "https://94se.net"))
//
//    }

    val scrapyData = ScrapyBean(name = "se", isMerge = false, url = "https://94se.net", folder = "C:\\新建文件夹\\", cleanNumber = 1)
    val isLinux = false
    val filePash = "E:\\"

    fun getFilePath(isLinux: Boolean, prefix: String, name: String): String {
        return if (isLinux) {
            KtStringUtil.getStrInFile("/home/ccg/${name}.json")
        } else {
            KtStringUtil.getStrInFile("${prefix}.${name}.json")
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
            KtStringUtil.getStrInFile("/home/ccg/${scrapyData.name}.json")
        } else {
            KtStringUtil.getStrInFile("${scrapyData.folder}${scrapyData.name}.json")
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
        if (scrapyData.isMerge) {
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
                    if (scrapyData.isMerge) {
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
                videoUrlData.setvUrl(videoUrlData.getvUrl().replace("\\/", "/"))
            }
            if (isLinux) {
                KtStringUtil.saveAsFileWriter("/home/ccg/${scrapyData.name}1.json", GsonBuilder().disableHtmlEscaping().create().toJson(list))
            } else {
                KtStringUtil.saveAsFileWriter("${scrapyData.folder}${scrapyData.name}1.json", GsonBuilder().disableHtmlEscaping().create().toJson(list))
            }
            val endTime = System.currentTimeMillis()
            println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
        } else {
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
                KtStringUtil.saveAsFileWriter("/home/ccg/${scrapyData.name}1.json", GsonBuilder().disableHtmlEscaping().create().toJson(listOne))
            } else {
                KtStringUtil.saveAsFileWriter("${scrapyData.folder}${scrapyData.name}1.json", GsonBuilder().disableHtmlEscaping().create().toJson(listOne))

            }
            val endTime = System.currentTimeMillis()
            println("耗时：  " + (endTime - startTime) / 1000 / 60 + " 分钟")
        }
    }

}