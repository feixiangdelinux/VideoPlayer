package com.ccg.plat

import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.CmdMessage
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.blankj.utilcode.util.SPUtils
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/18 16:25
 */
class MyReceiver : JPushMessageReceiver(){
    override fun onNotifyMessageArrived(p0: Context?, message: NotificationMessage?) {
        super.onNotifyMessageArrived(p0, message)
        if(message?.notificationTitle=="充值成功"){
            Timber.e("充值成功")
            SPUtils.getInstance().put("userInfo","VIP")
            Const.IS_VIP =true
        }
    }
}