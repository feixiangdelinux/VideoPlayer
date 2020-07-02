package com.ccg.videoplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ccg.libbase.BaseViewModelB
import com.ccg.videoplayer.entity.AbaseBean
import com.ccg.videoplayer.repository.NetRepository

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:27
 */
class MainViewModel : BaseViewModelB() {
    private val repository by lazy { NetRepository() }
    var uiData: MutableLiveData<MutableList<AbaseBean>> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    fun initData() {

        val datas: MutableList<AbaseBean> = ArrayList()
        val tempDataOne = AbaseBean()
        tempDataOne.tag = 1
        tempDataOne.name = "小黄人播放器"
        datas.add(tempDataOne)
        uiData.postValue(datas)

    }
}