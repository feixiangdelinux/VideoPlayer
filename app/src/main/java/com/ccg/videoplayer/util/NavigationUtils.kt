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
     * 去往欢迎界面
     */
    fun goSelectSubjectActivity(json: String = "") {
        CC.obtainBuilder("ComponentOne")
            .setActionName("VideoPlayActivity")
            .addParam("json",json)
            .build()
            .call()

    }
}