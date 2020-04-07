package com.example.moduletwo.util

import com.billy.cc.core.component.CC

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-5 下午6:05
 */
object NavigationUtils {
    /**
     * 去往视频播放界面
     * @param json String
     */
    fun goVideoPlayActivity(json: String = "") {
        CC.obtainBuilder("ComponentOne")
            .setActionName("VideoPlayActivity")
            .addParam("json",json)
            .build()
            .call()
    }
}