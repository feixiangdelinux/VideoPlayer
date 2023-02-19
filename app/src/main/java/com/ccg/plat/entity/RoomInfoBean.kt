package com.ccg.plat.entity

/**
 *
 * @property explain String
 * @property roomName String
 * @property roomUrl String
 * @property tag String
 * @property timeStamp String
 * @constructor
 */
data class RoomInfoBean(
    val explain: String,
    val roomName: String,
    val roomUrl: String,
    val tag: String,
    val timeStamp: Long
)