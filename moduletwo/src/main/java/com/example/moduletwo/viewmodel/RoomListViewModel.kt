package com.example.moduletwo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ccg.libbase.BaseViewModelB
import com.ccg.libbase.util.ACache
import com.example.moduletwo.entity.RoomBean
import com.example.moduletwo.repository.NetRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-2 下午4:53
 */
class RoomListViewModel : BaseViewModelB() {
    private val repository by lazy { NetRepository() }
    var uiData: MutableLiveData<RoomBean> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    fun initData() {
        val json = ACache.get().getAsString("RoomListActivity")
        if (json.isNullOrEmpty()) {
            addDisposable(
                repository.getRoomListData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        uiData.postValue(it)
                        val jsonData = GsonBuilder().create().toJson(it)
                        ACache.get().put("RoomListActivity", jsonData, ACache.TIME_DAY)
                    }, {
                        Timber.e("获取房间列表失败:  ${it.message}")
                    })
            )
        } else {
            val data = GsonBuilder().create().fromJson<RoomBean>(json, RoomBean::class.java)
            uiData.postValue(data)
        }
    }
}