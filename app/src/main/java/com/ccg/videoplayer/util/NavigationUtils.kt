package com.ccg.videoplayer.util

import com.billy.cc.core.component.CC

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-20 下午11:25
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

    /**
     * 去往视频列表界面
     * @param json String
     */
    fun goVideoListActivity(json: String = "") {
        CC.obtainBuilder("ComponentTwo")
            .setActionName("VideoListActivity")
            .addParam("json",json)
            .build()
            .call()
    }


}