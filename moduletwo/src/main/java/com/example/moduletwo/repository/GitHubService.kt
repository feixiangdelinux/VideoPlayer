package com.example.moduletwo.repository


import com.example.moduletwo.entity.FinalVideoBean
import com.example.moduletwo.entity.RoomBean
import io.reactivex.Observable
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
    @GET
    fun getListData(@Url url: String): Observable<FinalVideoBean>
    @GET("/21/room.json")
    fun getRoomListData(): Observable<RoomBean>
}