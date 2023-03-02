package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ccg.plat.entity.RoomListBean
import com.ccg.plat.repository.GitHubService
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.google.gson.GsonBuilder
import com.tencent.mmkv.MMKV
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/17 16:15
 */
class VideoTwoActivity : ComponentActivity() {
    private val context = this
    private var url = ""
    private val retrofit = Retrofit.Builder().baseUrl("https://siyou.nos-eastchina1.126.net/").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
    private val kv = MMKV.defaultMMKV()

    /**
     * 0用户第一次进入,没有缓存,需要缓存数据
     * 1用户已经缓存过数据,不需要缓存
     * 2用户缓存的数据已经过期,需要重新下载
     */
    private var state = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("url")?.run {
            url = this
        }
        setContent {
            VideoPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    VideoListUI(url)
                }
            }
        }
    }

    @Composable
    fun VideoListUI(url: String) {
        var isLoading by remember { mutableStateOf(true) }
        val listName = remember { mutableStateListOf<RoomListBean.Data>() }
        LaunchedEffect(Unit) {
            val data = retrofit.getListData(url)
            if (data.data.isNotEmpty()) {
                isLoading = false
                listName.addAll(data.data)
                //获取本地存储的数据,如果存储的数据timeStamp跟后台返回的一样说明数据没有发生变化,不用更新数据,如果发生了变化,更新数据
                val json = kv.decodeString(url)
                if (json.isNullOrEmpty()) {
                    //如果本地没有缓存,则缓存
                    state = 0
                    kv.encode(url, GsonBuilder().create().toJson(data))
                } else {
                    //如果存储的数据timeStamp跟后台返回的一样说明数据没有发生变化,不用更新数据,如果发生了变化,更新数据
                    val saveData = GsonBuilder().create().fromJson<RoomListBean>(json, RoomListBean::class.java)
                    if (saveData.timeStamp == data.timeStamp) {
                        state = 1
                    } else {
                        state = 2
                        //如果存储的数据timeStamp跟后台返回的不一样,则更新数据
                        kv.encode(url, GsonBuilder().create().toJson(data))
                    }
                }
            } else {
                isLoading = true
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
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                items(count = listName.size) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            val intent = Intent(context, VideoThreeActivity::class.java)
                            intent.putExtra("url", listName[it].videoUrl)
                            intent.putExtra("state", state)
                            startActivity(intent)
                        }) {
                        Text(text = listName[it].videoTag, modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp), fontSize = 20.sp)
                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
    }
}

