package com.ccg.videoplayer

import android.app.Application
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-21 上午12:47
 */
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化Timber(代替log打印的工具类)
        Timber.plant(Timber.DebugTree())
    }
}