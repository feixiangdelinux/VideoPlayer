package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.FileIOUtils
import com.ccg.plat.Const
import com.ccg.plat.entity.DownloadUrlBean
import com.ccg.plat.entity.RoomBean
import com.ccg.plat.repository.GitHubService
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/17 16:26
 */
class VideoThreeActivity : ComponentActivity() {
    private val context = this
    private val kv = MMKV.defaultMMKV()
    private var playNumber = 0
    private var url = ""
    var downloadProgress = mutableStateOf(0)
    var mTaskId = 0L
    var jsonFile = ""
    var isLoading = mutableStateOf(true)
    val uiListData = mutableStateListOf<RoomBean>()
    var timeStamp = 0L
    private val retrofit = Retrofit.Builder().baseUrl(Const.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)

    /**
     * 这是本地缓存的所有json的目录
     */
    val updateList: MutableList<DownloadUrlBean> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("url")?.run {
            url = this
        }
        timeStamp = intent.getLongExtra("timeStamp", 0L)
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
        var cIndex by remember { mutableStateOf(-1) }
        LaunchedEffect(Unit) {
            jsonFile = Const.filePath + "/${EncryptUtils.encryptMD5ToString(url)}"
            //先判断是否缓存过数据,缓存过且没有过期就用缓存数据,否则从新下载新json数据
            var json = kv.decodeString("updateList")
            if (!json.isNullOrEmpty()) {
                val tempData = GsonBuilder().create().fromJson<MutableList<DownloadUrlBean>>(json, object : TypeToken<MutableList<DownloadUrlBean>>() {}.type)
                updateList.addAll(tempData)
                json = ""
                tempData.clear()
            }
            var isDownload = true
            val iterator = updateList.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (item.url == url) {
                    if (item.timeStamp == timeStamp) {
                        isDownload = false
                    } else {
                        iterator.remove()
                    }
                }
            }
            if (isDownload) {
                Timber.e("AA1:  下载完成")
                val roomList = retrofit.getVideoFinalData(url)
                FileIOUtils.writeFileFromString(jsonFile, GsonBuilder().create().toJson(roomList))
                downloadProgress.value = 100
                updateList.add(DownloadUrlBean(url = url, filePath = jsonFile, timeStamp = timeStamp))
                kv.encode("updateList", GsonBuilder().create().toJson(updateList))
                updateList.clear()
                if (Const.finalVideoList.isNotEmpty()) {
                    Const.finalVideoList.clear()
                }
                Const.finalVideoList = roomList
                if (Const.finalVideoList.isNotEmpty()) {
                    if (uiListData.isNotEmpty()) {
                        uiListData.clear()
                    }
                    uiListData.addAll(Const.finalVideoList)
                    isLoading.value = false
                } else {
                    isLoading.value = true
                }
            } else {
                Timber.e("AA1:  用缓存")
                loadDataForStorage(jsonFile)
            }
        }
        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(progress = downloadProgress.value.toFloat() / 100, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 80.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("加载中...")
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    Text(text = "电影暂停就会显示收藏按钮,随机播放按钮\n", modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp), fontSize = 15.sp)
                }
                items(count = uiListData.size) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                cIndex = it
                                if (Const.IS_VIP) {
                                    val intent = Intent(context, VideoPlayerActivity::class.java)
                                    intent.putExtra("tag", 1)
                                    intent.putExtra("index", it)
                                    startActivity(intent)
                                } else {
                                    if (playNumber <= 10) {
                                        //可以正常播放
                                        playNumber += 1
                                        kv.encode("playNumber", playNumber)
                                        val intent = Intent(context, VideoPlayerActivity::class.java)
                                        intent.putExtra("tag", 1)
                                        intent.putExtra("index", it)
                                        startActivity(intent)
                                    } else {
                                        //提示不充钱每天智能看10次
                                        Toast
                                            .makeText(context, "不充钱每天只能看10次", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                            }, verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(10.dp))
                            AsyncImage(model = uiListData[it].pUrl, contentDescription = null, modifier = Modifier
                                .width(120.dp)
                                .wrapContentHeight()
                                .clip(shape = RoundedCornerShape(10)))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = uiListData[it].name, color = if (it == cIndex) {
                                Color.Blue
                            } else {
                                Color.Unspecified
                            }, modifier = Modifier
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

    /**
     * 从本地json文件中加载数据
     */
    private fun loadDataForStorage(filePash: String) {
        var json = FileIOUtils.readFile2String(filePash)
        if (Const.finalVideoList.isNotEmpty()) {
            Const.finalVideoList.clear()
        }
        Const.finalVideoList = GsonBuilder().create().fromJson<MutableList<RoomBean>>(json, object : TypeToken<MutableList<RoomBean>>() {}.type)
        json = ""
        if (Const.finalVideoList.isNotEmpty()) {
            if (uiListData.isNotEmpty()) {
                uiListData.clear()
            }
            uiListData.addAll(Const.finalVideoList)
            isLoading.value = false
        } else {
            isLoading.value = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Const.finalVideoList.isNotEmpty()) {
            Const.finalVideoList.clear()
        }
        url = ""
        mTaskId = 0L
        jsonFile = ""
        if (uiListData.isNotEmpty()) {
            uiListData.clear()
        }
    }
}

