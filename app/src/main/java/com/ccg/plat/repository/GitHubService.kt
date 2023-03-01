package com.ccg.plat.repository

import com.ccg.plat.entity.*
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/17 11:56
 */
interface GitHubService {
    @GET("/1/update.json")
    suspend fun getUpdataInfo(): UpdateBean

    @GET("/1/roomlist.json")
    suspend fun getRoomListData(): MutableList<RoomInfoBean>

    @GET("/21/agentinfo.json")
    suspend fun getAgentInfo(): MutableList<String>

    @GET
    suspend fun getVideoFinalData(@Url url: String): MutableList<RoomBean>

    @GET
    suspend fun getListData(@Url url: String): RoomListBean
}