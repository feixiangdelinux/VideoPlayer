package com.ccg.plat.entity

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/3/2 16:59
 */
data class DownloadUrlBean(
    //json文件对应的网络地址
    val url: String,
    //json文件存储在本地的路径
    val filePath: String,
    //json文件的时间戳(判断文件是否需要更新)
    val timeStamp: Long,
)