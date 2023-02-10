package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ccg.plat.Const
import com.ccg.plat.entity.VideoListBean
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
 * date : 2022/10/17 16:26
 */
class VideoThreeActivity : ComponentActivity() {
    val context = this
    private var url = ""
    private val retrofit = Retrofit.Builder().baseUrl("https://siyou.nos-eastchina1.126.net/").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
    val kv = MMKV.defaultMMKV()
    var playNumber = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("url")?.run {
            url = this
        }
        playNumber = kv.decodeInt("playNumber")
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
        val listName = remember { mutableStateListOf<VideoListBean.Data>() }
        LaunchedEffect(Unit) {
            val json = kv.decodeString(url)
            if (json.isNullOrEmpty()) {
                val data = retrofit.getVideoFinalData(url)
                if (data.data.isNotEmpty()) {
                    if (listName.isNotEmpty()) {
                        listName.clear()
                    }
                    listName.addAll(data.data)
                    isLoading = false
                } else {
                    isLoading = true
                }
                kv.encode(url, GsonBuilder().create().toJson(data))
            } else {
                val saveData = GsonBuilder().create().fromJson(json, VideoListBean::class.java)
                if (saveData.data.isNotEmpty()) {
                    if (listName.isNotEmpty()) {
                        listName.clear()
                    }
                    listName.addAll(saveData.data)
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
                item{
                    Text(text = "电影暂停就会显示收藏按钮,随机播放按钮\n", modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp), fontSize = 15.sp)
                }

                items(count = listName.size) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                if (Const.IS_VIP) {
                                    val intent = Intent(context, SimplePlayerActivity::class.java)
                                    intent.putExtra("tag", 0)
                                    intent.putExtra("key", url)
                                    intent.putExtra("index", it)
                                    startActivity(intent)

                                } else {
                                    if (playNumber <= 10) {
                                        //可以正常播放
                                        playNumber += 1
                                        kv.encode("playNumber", playNumber)
                                        val intent = Intent(context, SimplePlayerActivity::class.java)
                                        intent.putExtra("tag", 0)
                                        intent.putExtra("key", url)
                                        intent.putExtra("index", it)
                                        startActivity(intent)
                                    } else {
                                        //提示不充钱每天智能看10次
                                        Toast.makeText(context, "不充钱每天只能看10次", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }, verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(10.dp))
                            AsyncImage(model = listName[it].pUrl, contentDescription = null, modifier = Modifier
                                .width(120.dp)
                                .wrapContentHeight()
                                .clip(shape = RoundedCornerShape(10)))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = listName[it].name, modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight())
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
    }
}

