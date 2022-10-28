package com.ccg.plat

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.tencent.mmkv.MMKV
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/18 16:55
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化Timber(代替log打印的工具类)
        Timber.plant(Timber.DebugTree())
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
        MMKV.initialize(this)
    }
}