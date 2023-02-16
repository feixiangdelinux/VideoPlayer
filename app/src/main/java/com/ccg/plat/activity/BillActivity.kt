package com.ccg.plat.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import cn.jpush.android.api.JPushInterface
import com.ccg.plat.entity.BillBean
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.ccg.plat.util.JpushUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/2/16 13:39
 */
class BillActivity : ComponentActivity() {
    val context = this
    val errMsg: MutableLiveData<String> = MutableLiveData()
    private var registrationId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("registrationId")?.run {
            registrationId = this
        }
        setContent {
            VideoPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    VideoListUI()
                }
            }
        }
    }

    @Composable
    fun VideoListUI() {
        var isLoading by remember { mutableStateOf(true) }
        val listName = remember { mutableStateListOf<BillBean>() }
        var mOpenNum by remember { mutableStateOf(0) }
        var mCloseNum by remember { mutableStateOf(0) }
        LaunchedEffect(Unit) {
            val kv = MMKV.defaultMMKV()
            val json = kv.decodeString("BillActivity")
            if (json.isNullOrEmpty()) {
                isLoading = false
            } else {
                val saveData = GsonBuilder().create().fromJson<MutableList<BillBean>>(json, object : TypeToken<MutableList<BillBean>>() {}.type)
                if (saveData.isNotEmpty()) {
                    if (listName.isNotEmpty()) {
                        listName.clear()
                    }
                    var openNum = 0
                    var closeNum = 0
                    saveData.forEach {
                        if (it.o) {
                            openNum += 1
                        } else {
                            closeNum += 1
                        }
                    }
                    mOpenNum = openNum
                    mCloseNum = closeNum
                    listName.addAll(saveData)
                    if (registrationId.isNotEmpty()) {
                        MainScope().launch(Dispatchers.IO) {
                            JpushUtil.testSendPush(errMsg = errMsg, registrationId = registrationId, alert = "${JPushInterface.getRegistrationID(context)} 开通 $openNum 关闭 $closeNum", title = "上报账单")
                        }
                    }
                    isLoading = false
                } else {
                    isLoading = true
                }
            }
        }
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("加载中")
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(5.dp))
                Text("开通 $mOpenNum 位用户   关闭 $mCloseNum 位用户", modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), textAlign = TextAlign.Center, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(5.dp))
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    items(count = listName.size) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()) {
                            val colot = if (listName[it].o) {
                                Color.Black
                            } else {
                                Color.Red
                            }
                            val conTime = if (listName[it].o) {
                                "开通时间"
                            } else {
                                "关闭时间"
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(text = "$it")
                                Spacer(modifier = Modifier.width(15.dp))
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()) {
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(text = "用户名: ${listName[it].n}", color = colot)
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(text = "$conTime: ${listName[it].t}", color = colot)
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                            Divider(thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}