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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ccg.plat.entity.RoomInfoBean
import com.ccg.plat.repository.GitHubService
import com.ccg.plat.ui.theme.VideoPlayerTheme
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
    private val retrofit = Retrofit.Builder().baseUrl("http://101.42.171.191:8083/GameServer/houtai/").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)

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
        val downloadUrl = remember { mutableStateListOf<RoomInfoBean>() }
        var cIndex by remember { mutableStateOf(-1) }
        LaunchedEffect(Unit) {
            val data = retrofit.getRoomListData()
            if (data.isNotEmpty()) {
                if (downloadUrl.isNotEmpty()) {
                    downloadUrl.clear()
                }
                downloadUrl.addAll(data)
                isLoading = false
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
                items(count = downloadUrl.size) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            cIndex = it
                            val intent = Intent(context, VideoTwoActivity::class.java)
                            intent.putExtra("url", downloadUrl[it].roomUrl)
                            startActivity(intent)
                        }) {
                        Text(
                            text = downloadUrl[it].roomName, color = if (it == cIndex) {
                                Color.Blue
                            } else {
                                Color.Unspecified
                            }, modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp), fontSize = 20.sp
                        )
                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
    }
}

