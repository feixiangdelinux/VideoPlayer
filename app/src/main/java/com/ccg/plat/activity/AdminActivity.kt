package com.ccg.plat.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.ccg.plat.ui.theme.VideoPlayerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/11/3 14:19
 */
class AdminActivity : ComponentActivity() {
    val context = this
    private val appKey = "759c31f0637969e398f43d6b"
    private val masterSecret = "756a2cce84a38f669415679e"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    var input by remember { mutableStateOf("") }
                    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().height(55.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "输入用户名:")
                            TextField(value = input, onValueChange = {
                                input = it
                            })
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = {
                            if (input.trim().isNotEmpty()) {
                                MainScope().launch(Dispatchers.IO) {
                                    testSendPush(registrationId = input)
                                }
                            }
                        }, modifier = Modifier.fillMaxWidth().height(55.dp)) {
                            Text(text = "开通VIP")
                        }
                    }
                }
            }
        }
    }

    private fun testSendPush(registrationId: String) {
        val clientConfig = ClientConfig.getInstance()
        val jpushClient = JPushClient(masterSecret, appKey, null, clientConfig)
        val payload = buildPushObject_android_and_ios(registrationId)
        try {
            Timber.e("准备推送")
            val result = jpushClient.sendPush(payload)
            Timber.e("准备完成")
        } catch (e: APIConnectionException) {
            Timber.e("出错1")
        } catch (e: APIRequestException) {
            Timber.e("出错2" + e.message)
            Timber.e("出错2" + e.errorMessage)
        }
    }

    private fun buildPushObject_android_and_ios(registrationId: String): PushPayload {
//        return PushPayload.newBuilder().setPlatform(Platform.android())
//            .setAudience(Audience.registrationId(registrationId))
//            .setAudience(
//                Audience.newBuilder().addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
//                    .build()
//            )
//            .setNotification(Notification.newBuilder().setAlert("testing alert content").build())
//            .setOptions(Options.newBuilder().setTimeToLive(600).build())
//            .build()


        return PushPayload.newBuilder().setPlatform(Platform.android_ios())
            .setAudience(Audience.registrationId(registrationId))
//            .setAudience(Audience.all())

            .setNotification(
                Notification.newBuilder()
                    .setAlert("您已开通VIP")
                    .addPlatformNotification(
                        AndroidNotification.newBuilder()
                            .setTitle("充值成功").build()
                    )
                    .addPlatformNotification(
                        IosNotification.newBuilder()
                            .incrBadge(1)
                            .addExtra("extra_key", "extra_value").build()
                    )
                    .build()
            )

            .setOptions(
                Options.newBuilder()
                    .setApnsProduction(false)
                    .setTimeToLive(43200)
                    .build()
            )
            .build();
    }
}