package com.ccg.plat.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.FullCallback
import com.blankj.utilcode.util.ToastUtils
import com.ccg.plat.repository.GitHubService
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.ccg.plat.util.ACache
import com.tencent.mmkv.MMKV
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.*

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/17 11:58
 */
class SplashActivity : ComponentActivity() {
    val context = this
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://siyou.nos-eastchina1.126.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    SplashUI()
                }
            }
        }
    }

    @Composable
    fun SplashUI() {
        Text(text = "第一次进入如果出更新弹窗那就需要更新app,要么点立即更新去更新app,要么去群里下载最新的app\n软件安装需要两个权限读写权限和安装app的权限,只有都同意才能安装最新的app")
        var showDialog by remember {
            mutableStateOf(false)
        }
        var title by remember {
            mutableStateOf("")
        }
        var downloadUrl by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            val time = ACache.get(context).getAsString("SplashTime")
            if (time.isNullOrEmpty()) {
                ACache.get(context).put("SplashTime", "true", ACache.TIME_DAY * 3)
                val kv = MMKV.defaultMMKV()
                kv.clearAll()
                val data = retrofit.getUpdataInfo()
                if (data.data.version == AppUtils.getAppVersionCode()) {
                    startActivity(Intent(context, MainActivity::class.java))
                    finish()
                } else {
                    showDialog = true
                    title = data.data.desc
                    downloadUrl = data.data.apkUrl
                }
            } else {
                startActivity(Intent(context, MainActivity::class.java))
                finish()
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = "更新通知")
                },
                text = {
                    Text(text = title)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            title = "开始下载..."
                            checkPermission(downloadUrl)
                        }
                    ) {
                        Text("立即更新")
                    }
                }
            )
        }
    }

    private fun checkPermission(downloadUrl: String) {
        if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) || PermissionUtils.isGranted(Manifest.permission.INSTALL_PACKAGES)) {
            downloadApp(downloadUrl)
        } else {
            PermissionUtils.permission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INSTALL_PACKAGES
            ).callback(object : FullCallback {
                override fun onGranted(granted: List<String>) {
                    //这里就是权限打开之后自己要操作的逻辑
                    downloadApp(downloadUrl)
                }

                override fun onDenied(deniedForever: List<String>, denied: List<String>) {
                    ToastUtils.showShort("请开启权限")
                }
            }).request()
        }
    }

    /**
     * 下载app
     */
    private fun downloadApp(apkUrl: String) {
        retrofit.downloadApp(apkUrl).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.run {
                    getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.let {
                        var paths = it.path
                        Timber.e("aa  " + paths + "xiaohuangren.apk")
                        if (!paths.endsWith("/")) {
                            paths = "$paths/"
                        }
                        try {
                            //判断文件夹是否存在
                            val files = File(paths)
                            //跟目录一个文件夹
                            if (!files.exists()) {
                                //不存在就创建出来
                                files.mkdirs()
                            }
                            //创建一个文件
                            val futureStudioIconFile = File(paths + "xiaohuangren.apk")
                            //初始化输入流
                            var inputStream: InputStream? = null
                            //初始化输出流
                            var outputStream: OutputStream? = null
                            try {
                                //设置每次读写的字节
                                val fileReader = ByteArray(4096)
                                var fileSizeDownloaded: Long = 0
                                //请求返回的字节流
                                inputStream = this.byteStream()
                                //创建输出流
                                outputStream = FileOutputStream(futureStudioIconFile)
                                //进行读取操作
                                while (true) {
                                    val read = inputStream!!.read(fileReader)
                                    if (read == -1) {
                                        break
                                    }
                                    //进行写入操作
                                    outputStream.write(fileReader, 0, read)
                                    fileSizeDownloaded += read.toLong()
                                }
                                //刷新
                                outputStream.flush()
                            } catch (e: IOException) {
                            } finally {
                                inputStream?.close()
                                outputStream?.close()
                                AppUtils.installApp(paths + "xiaohuangren.apk")
                            }
                        } catch (e: IOException) {
                        }
                    }
                }
            }
        })
    }

}

