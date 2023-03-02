package com.ccg.plat.entity

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/3/1 17:34
 */
data class SpiderInfo(
    //爬虫的名称
    val name: String,
    //爬取下来的数据是否需要合并
    val isMerge: Boolean,
    //是否需要验证videoUrl的有效性
    val isPlay: Boolean)
