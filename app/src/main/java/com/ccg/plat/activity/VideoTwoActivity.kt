package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
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
    val context = this
    private var url = ""
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://siyou.nos-eastchina1.126.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubService::class.java)

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
        val listName = remember { mutableStateListOf<String>() }
        val listUrl = remember { mutableStateListOf<String>() }
        LaunchedEffect(Unit) {
            val kv = MMKV.defaultMMKV()
            val json = kv.decodeString(url)
            if (json.isNullOrEmpty()) {
                val data = retrofit.getListData(url)
                if (data.videoTag.isNotEmpty()) {
                    if (listName.isNotEmpty()) {
                        listName.clear()
                    }
                    listName.addAll(data.videoTag)
                    isLoading = false
                } else {
                    isLoading = true
                }
                if (data.videoUrl.isNotEmpty()) {
                    if (listUrl.isNotEmpty()) {
                        listUrl.clear()
                    }
                    listUrl.addAll(data.videoUrl)
                }
                kv.encode(url, GsonBuilder().create().toJson(data))
            } else {
                val saveData = GsonBuilder().create().fromJson(json, RoomListBean::class.java)
                if (saveData.videoTag.isNotEmpty()) {
                    if (listName.isNotEmpty()) {
                        listName.clear()
                    }
                    listName.addAll(saveData.videoTag)
                    isLoading = false
                } else {
                    isLoading = true
                }
                if (saveData.videoUrl.isNotEmpty()) {
                    if (listUrl.isNotEmpty()) {
                        listUrl.clear()
                    }
                    listUrl.addAll(saveData.videoUrl)
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
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                items(count = listName.size) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 10.dp).shadow(elevation = 1.dp, shape = RoundedCornerShape(1.dp)).padding(vertical = 10.dp).clickable {
                        val intent = Intent(context, VideoThreeActivity::class.java)
                        intent.putExtra("url", listUrl[it])
                        startActivity(intent)
                    }, contentAlignment = Alignment.Center) {
                        Text(text = listName[it])
                    }
                }
            }
        }
    }
}

