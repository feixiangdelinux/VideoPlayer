package com.ccg.plat.entity

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/2/18 7:03
 * @property name String    爬虫标识
 * @property isMerge Boolean    是否需要合并
 * @property url String     爬虫对应的网址
 * @property folder String     爬虫爬取的结果所放的目录
 * @property cleanNumber Int    数据需要处理的次数
 * @constructor
 */
data class ScrapyBean(val name: String, val isMerge: Boolean, val url: String, val folder: String, val cleanNumber: Int)
