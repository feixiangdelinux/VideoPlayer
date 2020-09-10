package com.example.moduletwo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ccg.libbase.BaseViewModelB
import com.ccg.libbase.util.ACache
import com.example.moduletwo.entity.FinalListBean
import com.example.moduletwo.repository.NetRepository
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :VideoTypeActivity
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-5 下午2:07
 */
class VideoListViewModel : BaseViewModelB() {
    private val repository by lazy { NetRepository() }
    var uiData: MutableLiveData<MutableList<FinalListBean.DataBean>> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()

    fun initData(url: String) {
        val json = ACache.get().getAsString(url)
        if (json.isNullOrEmpty()) {
            addDisposable(
                repository.getVideoFinalData(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        uiData.postValue(it.data)
                        val jsonData = GsonBuilder().create().toJson(it)
                        ACache.get().put(url, jsonData, ACache.TIME_DAY)
                    }, {
                        errMsg.postValue("获取房间列表失败:  ${it.message}")
                        Timber.e("获取房间列表失败:  ${it.message}")
                    })
            )
        } else {
            val jsonDatas =
                GsonBuilder().create().fromJson<FinalListBean>(json, FinalListBean::class.java)
            uiData.postValue(jsonDatas.data)
        }
    }
}