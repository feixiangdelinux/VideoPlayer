package com.ccg.videoplayer.repository

import com.ccg.videoplayer.entity.UpdateBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-21 上午12:20
 */
interface GitHubService {
    @GET("/21/update.json")
    fun getUpdataInfo(): Observable<UpdateBean>

    @GET
    fun downloadApp(@Url fileUrl: String): Call<ResponseBody>
}