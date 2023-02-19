package com.ccg.plat.repository

import com.ccg.plat.entity.RoomBean
import com.ccg.plat.entity.RoomListBean
import com.ccg.plat.entity.UpdateBean
import com.ccg.plat.entity.VideoListBean
import okhttp3.ResponseBody
import retrofit2.Call
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

    @GET("/21/roomlist.json")
    suspend fun getRoomListData(): RoomBean

    @GET("/21/agentinfo.json")
    suspend fun getAgentInfo(): MutableList<String>

    @GET
    suspend fun getListData(@Url url: String): RoomListBean

    @GET
    suspend fun getVideoFinalData(@Url url: String): VideoListBean
}