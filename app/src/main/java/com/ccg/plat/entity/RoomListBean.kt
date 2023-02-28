package com.ccg.plat.entity

data class RoomListBean(val timeStamp: Long, val `data`: MutableList<Data>) {
    data class Data(val videoTag: String, val videoUrl: String)
}