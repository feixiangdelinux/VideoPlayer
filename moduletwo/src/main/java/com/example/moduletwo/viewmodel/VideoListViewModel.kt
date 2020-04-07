package com.example.moduletwo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ccg.libbase.BaseViewModelB
import com.example.moduletwo.entity.VideoBean
import com.example.moduletwo.repository.NetRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-5 下午2:07
 */
class VideoListViewModel : BaseViewModelB() {
    private val repository by lazy { NetRepository() }
    var uiData: MutableLiveData<MutableList<VideoBean>> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    fun initData(url: String) {
        addDisposable(
            repository.getListData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    uiData.postValue(it.data)
                }, {
                    errMsg.postValue("获取房间列表失败:  ${it.message}")
                    Timber.e("获取房间列表失败:  ${it.message}")
                })
        )
    }
}