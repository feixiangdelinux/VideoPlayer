package com.ccg.plat.entity

data class VideoListBean(
    val `data`: MutableList<Data>,
    val videoTag: String
) {
    data class Data(
        val e: String,
        val i: String,
        val id: Int,
        val name: String,
        val pUrl: String,
        val tags: String,
        val url: String,
        val vUrl: String
    )
}