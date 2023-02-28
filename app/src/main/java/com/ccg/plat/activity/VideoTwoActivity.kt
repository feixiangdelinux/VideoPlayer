package com.ccg.plat.activity

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arialyy.annotations.Download
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


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
    var filePath = ""
    var mTaskId = 0L
    var downloadProgress = mutableStateOf(0)
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

        LinearProgressIndicator(progress = downloadProgress.value.toFloat() / 100, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight())

//        if (isLoading) {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    CircularProgressIndicator()
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Text("加载中")
//                }
//            }
//        } else {
//            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//                items(count = listName.size) {
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .clickable {
//                            val intent = Intent(context, VideoThreeActivity::class.java)
//                            intent.putExtra("url", listUrl[it])
//                            startActivity(intent)
//                        }) {
//                        Text(text = listName[it],modifier = Modifier.padding(start = 20.dp,top= 15.dp,bottom= 15.dp),fontSize = 20.sp)
//                        Divider(thickness = 1.dp)
//                    }
//                }
//            }
//        }
    }




}

