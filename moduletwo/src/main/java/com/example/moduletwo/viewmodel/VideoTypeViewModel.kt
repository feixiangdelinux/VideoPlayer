package com.example.moduletwo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ccg.libbase.BaseViewModelB
import com.ccg.libbase.util.ACache
import com.example.moduletwo.entity.FinalVideoBean
import com.example.moduletwo.entity.VideoBean
import com.example.moduletwo.repository.NetRepository
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-27 上午10:40
 */
class VideoTypeViewModel : BaseViewModelB() {
    private val repository by lazy { NetRepository() }
    var uiData: MutableLiveData<MutableList<String>> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    val allData: MutableList<VideoBean> = ArrayList()
    fun initData(url: String) {
        val json = ACache.get().getAsString(url)
        if (json.isNullOrEmpty()) {
            addDisposable(
                repository.getListData(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        allData.clear()
                        allData.addAll(it.data)
                        val typeDatas: MutableList<String> = ArrayList()
                        for (i in it.data) {
                            if (!typeDatas.contains(i.tags)) {
                                typeDatas.add(i.tags)
                            }
                        }
                        uiData.postValue(typeDatas)
                        val jsonData = GsonBuilder().create().toJson(it)
                        ACache.get().put(url, jsonData, ACache.TIME_DAY)
                    }, {
                        errMsg.postValue("获取房间列表失败:  ${it.message}")
                        Timber.e("获取房间列表失败:  ${it.message}")
                    })
            )
        } else {
            val jsonDatas =
                GsonBuilder().create().fromJson<FinalVideoBean>(json, FinalVideoBean::class.java)
            allData.clear()
            allData.addAll(jsonDatas.data)
            val typeDatas: MutableList<String> = ArrayList()
            for (i in jsonDatas.data) {
                if (!typeDatas.contains(i.tags)) {
                    typeDatas.add(i.tags)
                }
            }
            uiData.postValue(typeDatas)
        }
    }
}