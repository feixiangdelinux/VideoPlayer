package com.ccg.plat

import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/18 16:25
 */
class MyReceiver : JPushMessageReceiver(){
    private val TAG = "PushMessageReceiver"
    override fun onMessage(p0: Context?, customMessage: CustomMessage?) {
        super.onMessage(p0, customMessage)
        Log.e(TAG, "[onMessage] $customMessage")
    }

    override fun onNotifyMessageOpened(p0: Context?, message: NotificationMessage?) {
        super.onNotifyMessageOpened(p0, message)
        Log.e(TAG, "[onNotifyMessageOpened] " + message)
    }

    override fun onMultiActionClicked(p0: Context?, p1: Intent?) {
        super.onMultiActionClicked(p0, p1)
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮")
    }

    override fun onRegister(p0: Context?, registrationId: String?) {
        super.onRegister(p0, registrationId)
        Log.e(TAG, "[onRegister] " + registrationId);
    }
}