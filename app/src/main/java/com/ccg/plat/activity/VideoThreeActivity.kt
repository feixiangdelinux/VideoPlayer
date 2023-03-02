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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils
import com.ccg.plat.Const
import com.ccg.plat.entity.RoomBean
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.ccg.plat.util.PermissionUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV

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
    private var state = 0
    var downloadProgress = mutableStateOf(0)
    var mTaskId = 0L
    var jsonFile = ""
    var isLoading = mutableStateOf(true)
    val listName = mutableStateListOf<RoomBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Aria.download(context).register()
        intent.getStringExtra("url")?.run {
            url = this
        }
        intent.getIntExtra("state", 0).run {
            state = this
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
        LaunchedEffect(Unit) {
            jsonFile = Const.filePath + "/${EncryptUtils.encryptMD5ToString(url)}"
            val json = kv.decodeString(url)
            val tiaojianOne = state == 0
            val tiaojianTwo = state == 2
            val tiaojianThree = json.isNullOrEmpty()
            if (tiaojianOne || tiaojianTwo || tiaojianThree) {
                //需要重新下载
                if (FileUtils.isFileExists(jsonFile)) {
                    FileUtils.delete(jsonFile)
                }
                PermissionUtil.checkPermission {
                    mTaskId = Aria.download(context).load(url).setFilePath(jsonFile).create()
                }
            } else {
                //加载本地缓存的数据
                loadDataForStorage(json!!)
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
                    Text("加载中")
                }
            }

        } else {
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                item {
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
                                    intent.putExtra("index", it)
                                    startActivity(intent)

                                } else {
                                    if (playNumber <= 10) {
                                        //可以正常播放
                                        playNumber += 1
                                        kv.encode("playNumber", playNumber)
                                        val intent = Intent(context, SimplePlayerActivity::class.java)
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
            if (listName.isNotEmpty()) {
                listName.clear()
            }
            listName.addAll(Const.finalVideoList)
            isLoading.value = false
        } else {
            isLoading.value = true
        }
    }

    /**
     * 下载中
     * @param task DownloadTask
     */
    @Download.onTaskRunning
    fun running(task: DownloadTask) {
        val len = task.fileSize
        if (len != 0L) {
            downloadProgress.value = task.percent
        }
    }

    /**
     * 下载结束
     * @param task DownloadTask
     */
    @Download.onTaskComplete
    fun taskComplete(task: DownloadTask) {
        downloadProgress.value = 100
        Aria.download(this).load(mTaskId).cancel(false)
        kv.encode(url, jsonFile)
        loadDataForStorage(jsonFile)
    }

    @Download.onTaskFail
    fun taskFail(task: DownloadTask?) {
        if (FileUtils.isFileExists(jsonFile)) {
            FileUtils.delete(jsonFile)
        }
        jsonFile = jsonFile.substring(0, jsonFile.length - 1)
        mTaskId = Aria.download(context).load(url).setFilePath(jsonFile).create()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (Const.finalVideoList.isNotEmpty()) {
            Const.finalVideoList.clear()
        }
        url = ""
        state = 0
        mTaskId = 0L
        jsonFile = ""
        if (listName.isNotEmpty()) {
            listName.clear()
        }
        Aria.download(this).load(mTaskId).cancel(true)
        Aria.download(context).unRegister()
    }
}

