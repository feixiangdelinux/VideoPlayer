package com.ccg.plat.util

import androidx.lifecycle.MutableLiveData
import cn.jiguang.common.ClientConfig
import cn.jiguang.common.resp.APIConnectionException
import cn.jiguang.common.resp.APIRequestException
import cn.jpush.api.JPushClient
import cn.jpush.api.push.model.Options
import cn.jpush.api.push.model.Platform
import cn.jpush.api.push.model.PushPayload
import cn.jpush.api.push.model.audience.Audience
import cn.jpush.api.push.model.notification.AndroidNotification
import cn.jpush.api.push.model.notification.IosNotification
import cn.jpush.api.push.model.notification.Notification
import com.blankj.utilcode.util.TimeUtils
import com.ccg.plat.entity.BillBean
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/2/16 14:33
 */
object JpushUtil {
    fun testSendPush(errMsg: MutableLiveData<String>, registrationId: String, alert: String, title: String) {
        val appKey = "759c31f0637969e398f43d6b"
        val masterSecret = "756a2cce84a38f669415679e"
        val clientConfig = ClientConfig.getInstance()
        val jpushClient = JPushClient(masterSecret, appKey, null, clientConfig)
        val payload = buildPush(registrationId, alert, title)
        try {
            val result = jpushClient.sendPush(payload)
            if (alert.contains("开通VIP")) {
                errMsg.postValue("开通VIP成功")
            } else if (alert.contains("关闭VIP")) {
                errMsg.postValue("关闭VIP成功")
            }
        } catch (e: APIConnectionException) {
            errMsg.postValue("出错1")
        } catch (e: APIRequestException) {
            errMsg.postValue("出错2")
        }
    }
    private fun buildPush(registrationId: String, alert: String, title: String): PushPayload {
        return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.registrationId(registrationId)).setNotification(Notification.newBuilder().setAlert(alert).addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build()).addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra("extra_key", "extra_value").build()).build()).setOptions(Options.newBuilder().setApnsProduction(false).setTimeToLive(43200).build()).build();
    }

}