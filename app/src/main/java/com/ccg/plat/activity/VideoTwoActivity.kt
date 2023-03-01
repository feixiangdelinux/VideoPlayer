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
    private val retrofit = Retrofit.Builder().baseUrl("https://siyou.nos-eastchina1.126.net/").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)

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

