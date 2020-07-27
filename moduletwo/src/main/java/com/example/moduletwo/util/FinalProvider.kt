package com.example.moduletwo.util

import com.example.moduletwo.entity.VideoBean

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-27 上午11:57
 */
object FinalProvider {
    private val finalData: MutableList<VideoBean> = ArrayList()
    fun setFinalData(mList: MutableList<VideoBean>) {
        clearFinalData()
        finalData.addAll(mList)
    }

    fun getFinalData(): MutableList<VideoBean> {
        return finalData
    }

    private fun clearFinalData() {
        finalData.clear()
    }
}