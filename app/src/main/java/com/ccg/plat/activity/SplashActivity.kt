package com.ccg.plat.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blankj.utilcode.util.*
import com.blankj.utilcode.util.PermissionUtils.FullCallback
import com.ccg.plat.Const
import com.ccg.plat.repository.GitHubService
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.google.android.exoplayer2.offline.Download
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/17 11:58
 */
class SplashActivity : ComponentActivity() {
    val context = this
    private val retrofit = Retrofit.Builder().baseUrl("http://101.42.171.191:8083/GameServer/houtai/").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
//    private val retrofit = Retrofit.Builder().baseUrl("https://siyou.nos-eastchina1.126.net/").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
    var title = mutableStateOf("1第一次进入如果出更新弹窗那就需要更新app,要么点立即更新去更新app,要么去群里下载最新的app\n\n" + "2软件安装需要两个权限读写权限和安装app的权限,只有都同意才能安装最新的app\n\n" + "3如果遇到问题加QQ群 463208733\n\n")
    var mTaskId = 0L
    var downloadProgress = mutableStateOf(0)
    var downloadUrl = mutableStateOf("")
    var apkFile = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    SplashUI()
                }
            }
        }
        Const.IS_VIP = !SPUtils.getInstance().getString("userInfo").isNullOrEmpty()
    }

    @Composable
    fun SplashUI() {
        var showProgress by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.let {
                Const.filePath = it.path
                apkFile = Const.filePath + "/xiaohuangren.apk"
            }
            val data = retrofit.getUpdataInfo()
            if (data.version > AppUtils.getAppVersionCode()) {
                downloadUrl.value = data.apkUrl
                showProgress = true
            } else {
                startActivity(Intent(context, MainActivity::class.java))
                finish()
            }
        }
        LaunchedEffect(downloadUrl.value) {
//            if (downloadUrl.value.isNotEmpty()) {
//                checkPermission(downloadUrl.value)
//            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)) {
            if (showProgress) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = if (100 == downloadProgress.value) {
                        "下载完成"
                    } else {
                        "下载中"
                    }, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(5.dp))
                    LinearProgressIndicator(progress = downloadProgress.value.toFloat() / 100, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "下载进度: ${downloadProgress.value}%", fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title.value)
        }
    }

    /**
     * 检查用户是否授权了存储权限
     * @param downloadUrl String
     */
    private fun checkPermission(downloadUrl: String) {
//        if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            downloadApp(downloadUrl)
//        } else {
//            PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(object : FullCallback {
//                override fun onGranted(granted: List<String>) {
//                    downloadApp(downloadUrl)
//                }
//
//                override fun onDenied(deniedForever: List<String>, denied: List<String>) {
//                    ToastUtils.showShort("请开启权限")
//                }
//            }).request()
//        }
    }







    override fun onDestroy() {
        super.onDestroy()
    }
}

