package com.ccg.plat.entity

data class UpdateBean(
    val `data`: Data,
    val timeStamp: Int
) {
    data class Data(
        val apkUrl: String,
        val desc: String,
        val version: Int
    )
}