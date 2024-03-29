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
import androidx.compose.ui.graphics.Color
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
import com.ccg.plat.entity.DownloadUrlBean
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
 *      val dataDES = "据此前报道，去年5月，芬兰和瑞典同时申请加入北约。俄罗斯总统普京去年5月就芬兰和瑞典决定申请加入北约的决定表示，俄罗斯与芬兰和瑞典之间“不存在问题”，因此芬兰和瑞典加入北约不对俄构成直接威胁，但北约军事基础设施在两国领土上的扩张必然会引起俄方回应。"
val keyDES = "6841028304055607"
val bytesDataDES: ByteArray = dataDES.toByteArray()
val bytesKeyDES: ByteArray = keyDES.toByteArray()
val str3 = EncryptUtils.encrypt3DES2HexString(bytesDataDES, bytesKeyDES, "DESede/ECB/NoPadding", null)
Log.e("bb", str3)
val str4 = EncryptUtils.decryptHexString3DES(str3, bytesKeyDES, "DESede/ECB/NoPadding", null)
Log.e("bb", ConvertUtils.bytes2String(str4))
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
    /**
     * 这是本地缓存的所有json的目录
     */
    val updateList: MutableList<DownloadUrlBean> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Aria.download(context).register()
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

    /**
     * 下载json数据到手机中
     */
    private fun downloadJsonFile() {
        if (FileUtils.isFileExists(jsonFile)) {
            FileUtils.delete(jsonFile)
        }
        PermissionUtil.checkPermission {
            mTaskId = Aria.download(context).load(url).setFilePath(jsonFile).create()
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
                downloadJsonFile()
            } else {
                loadDataForStorage(jsonFile)
            }
        }
        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        progress = downloadProgress.value.toFloat() / 100, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 80.dp)
                    )
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
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
                            AsyncImage(
                                model = uiListData[it].pUrl, contentDescription = null, modifier = Modifier
                                    .width(120.dp)
                                    .wrapContentHeight()
                                    .clip(shape = RoundedCornerShape(10))
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = uiListData[it].name, color = if (it == cIndex) {
                                    Color.Blue
                                } else {
                                    Color.Unspecified
                                }, modifier = Modifier
                                    .weight(1f)
                                    .wrapContentHeight()
                            )
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
     *
     */
    @Download.onTaskComplete
    fun taskComplete(task: DownloadTask) {
        downloadProgress.value = 100
        Aria.download(this).load(mTaskId).cancel(false)
        updateList.add(DownloadUrlBean(url = url, filePath = jsonFile, timeStamp = timeStamp))
        kv.encode("updateList", GsonBuilder().create().toJson(updateList))
        updateList.clear()
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
        mTaskId = 0L
        jsonFile = ""
        if (uiListData.isNotEmpty()) {
            uiListData.clear()
        }
        Aria.download(this).load(mTaskId).cancel(true)
        Aria.download(context).unRegister()
    }
}

