package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.TimeUtils
import com.ccg.plat.entity.BillBean
import com.ccg.plat.repository.GitHubService
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.ccg.plat.util.JpushUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    val kv = MMKV.defaultMMKV()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    var registrationId = ""
    var currentBillData = ArrayList<BillBean>()
    private val retrofit = Retrofit.Builder().baseUrl("http://101.42.171.191:8083/GameServer/houtai/").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
    var isVip = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val json = kv.decodeString("BillActivity")
        if (json.isNullOrEmpty()) {
        } else {
            val saveData = GsonBuilder().create().fromJson<MutableList<BillBean>>(json, object : TypeToken<MutableList<BillBean>>() {}.type)
            if (saveData.isNotEmpty()) {
                if (currentBillData.isNotEmpty()) {
                    currentBillData.clear()
                }
                currentBillData.addAll(saveData)
            }
        }
        setContent {
            VideoPlayerTheme {
                LaunchedEffect(Unit) {
                    val data = retrofit.getAgentInfo()
                    isVip = if (data.isEmpty()) {
                        false
                    } else {
                        data.contains(JPushInterface.getRegistrationID(context))
                    }
                }
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    var input by remember { mutableStateOf("") }
                    var cdsfsd by remember { mutableStateOf(false) }
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "输入用户名:")
                            TextField(value = input, onValueChange = {
                                input = it.replace("\\s*".toRegex(), "")
                            })
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = {
                            if (isVip) {
                                if (input.trim().isNotEmpty()) {
                                    registrationId = input.trim()
                                    MainScope().launch(Dispatchers.IO) {
                                        JpushUtil.testSendPush(errMsg = errMsg, registrationId = input.trim(), alert = "您已开通VIP", title = "充值成功")
                                    }
                                } else {
                                    Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "你不是代理商无法操作", Toast.LENGTH_SHORT).show()
                            }

                        }, modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)) {
                            Text(text = "开通VIP")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = {
                            if (isVip) {
                                if (input.trim().isNotEmpty()) {
                                    registrationId = input.trim()
                                    MainScope().launch(Dispatchers.IO) {
                                        JpushUtil.testSendPush(errMsg = errMsg, registrationId = input.trim(), alert = "您已关闭VIP", title = "关闭VIP")
                                    }
                                } else {
                                    Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "你不是代理商无法操作", Toast.LENGTH_SHORT).show()
                            }

                        }, modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)) {
                            Text(text = "关闭VIP")
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = cdsfsd, onCheckedChange = {
                                cdsfsd = it
                            })
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = if (cdsfsd) {
                                "上报开通记录"
                            } else {
                                "不上报开通记录"
                            })
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {
                            if (isVip) {
                                val intent = Intent(context, BillActivity::class.java)
                                if (input.trim().isNotEmpty() && cdsfsd) {
                                    intent.putExtra("registrationId", input.trim())
                                }
                                startActivity(intent)
                            } else {
                                Toast.makeText(context, "你不是代理商无法操作", Toast.LENGTH_SHORT).show()
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)) {
                            Text(text = "查看开通记录")
                        }
                    }
                }
            }
        }
        errMsg.observe(context) {
            it?.run {
                Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
                if (it.contains("成功")) {
                    if (it.contains("开通VIP")) {
                        currentBillData.add(0, BillBean(t = TimeUtils.getNowString(), n = registrationId, o = true))
                        kv.encode("BillActivity", GsonBuilder().create().toJson(currentBillData))
                    } else if (it.contains("关闭VIP")) {
                        currentBillData.add(0, BillBean(t = TimeUtils.getNowString(), n = registrationId, o = false))
                        kv.encode("BillActivity", GsonBuilder().create().toJson(currentBillData))
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        currentBillData.clear()
    }
}