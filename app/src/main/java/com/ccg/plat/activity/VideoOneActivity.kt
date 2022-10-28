package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.ccg.plat.entity.RoomBean
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
 * date : 2022/10/17 15:52
 */
class VideoOneActivity : ComponentActivity() {
    val context = this
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://siyou.nos-eastchina1.126.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val downloadUrl = remember { mutableStateListOf<RoomBean.Data>() }
        LaunchedEffect(Unit) {
            val kv = MMKV.defaultMMKV()
            val json = kv.decodeString("VideoOneActivity")
            if (json.isNullOrEmpty()) {
                val data = retrofit.getRoomListData()
                if (data.data.isNotEmpty()) {
                    if (downloadUrl.isNotEmpty()) {
                        downloadUrl.clear()
                    }
                    downloadUrl.addAll(data.data)
                    isLoading = false
                } else {
                    isLoading = true
                }
                kv.encode("VideoOneActivity", GsonBuilder().create().toJson(data))
            } else {
                val saveData = GsonBuilder().create().fromJson(json, RoomBean::class.java)
                if (saveData.data.isNotEmpty()) {
                    if (downloadUrl.isNotEmpty()) {
                        downloadUrl.clear()
                    }
                    downloadUrl.addAll(saveData.data)
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
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                items(items = downloadUrl) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 10.dp).shadow(elevation = 1.dp, shape = RoundedCornerShape(1.dp)).padding(vertical = 10.dp).clickable {
                        val intent = Intent(context, VideoTwoActivity::class.java)
                        intent.putExtra("url", it.roomUrl)
                        startActivity(intent)
                    }, contentAlignment = Alignment.Center) {
                        Text(text = it.roomName)
                    }
                }
            }
        }


    }
}

