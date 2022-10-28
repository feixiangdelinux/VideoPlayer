package com.ccg.plat.entity

data class RoomBean(
    val `data`: List<Data>,
    val timeStamp: Int
) {
    data class Data(
        val explain: String,
        val roomName: String,
        val roomUrl: String
    )
}