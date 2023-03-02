package com.ccg.plat

import com.ccg.plat.entity.RoomBean

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/11/3 15:35
 */
class Const {
    companion object {
        var IS_VIP: Boolean = false
        /**
         * 文件下载路径
         */
        var filePath: String= ""

        /**
         * 播放列表数据
         */
        var finalVideoList: MutableList<RoomBean>  =ArrayList()
    }
}