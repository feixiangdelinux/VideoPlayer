package com.ccg.videoplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ccg.libbase.BaseViewModelB
import com.ccg.videoplayer.entity.RoomBean
import com.ccg.videoplayer.repository.NetRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:27
 */
class MainViewModel : BaseViewModelB() {
    private val repository by lazy { NetRepository() }
    var uiData: MutableLiveData<RoomBean> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    fun initData() {
        addDisposable(
            repository.getListData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    uiData.postValue(it)
                }, {
                    Timber.e("获取房间列表失败:  ${it.message}")
                })
        )
    }
}