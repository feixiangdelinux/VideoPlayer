package com.ccg.videoplayer.view

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.AppUtils
import com.ccg.videoplayer.BuildConfig
import com.ccg.videoplayer.repository.GitHubService
import com.ccg.videoplayer.R
import com.ccg.videoplayer.util.writeFilePath
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.*


/**
 * @author : C4_雍和
 * 描述 :欢迎界面
 * 主要功能 :更新app
 * 维护人员 : C4_雍和
 * date : 20-3-20 下午11:56
 */
class SplashActivity : AppCompatActivity() {
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://siyou.nos-eastchina1.126.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    private val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkVersion()
    }

    /**
     * 检查app
     *
     */
    private fun checkVersion() {
        val service = retrofit.create(GitHubService::class.java)
        addDisposable(
            service.getUpdataInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.data.version == AppUtils.getAppVersionCode()) {
                        startActivity(Intent(context, MainActivity::class.java))
                        finish()
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("更新通知")
                            .setMessage(it.data.desc)
                            .setPositiveButton("立即更新") { _, _ ->
                                downloadApp(it.data.apkUrl)
                            }
                            .create().show()
                    }
                }, {
                    Timber.e("250:失败  ")
                })
        )
    }

    /**
     * 下载app
     */
    private fun downloadApp(apkUrl: String) {
        val service = retrofit.create(GitHubService::class.java)
        service.downloadApp(apkUrl).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.run {
                    getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.let {
                        var paths = it.path
                        if (!paths.endsWith("/")) {
                            paths = "$paths/"
                        }
                        this.writeFilePath(paths, "xiaohuangren.apk")
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
                                updateApp(paths + "xiaohuangren.apk")
                            }
                        } catch (e: IOException) {
                        }
                    }
                }
            }
        })
    }

    /**
     * 更新app
     */
    private fun updateApp(s: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            /* Android N 写法*/
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val contentUri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".fileProvider",
                File(s)
            )
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            /* Android N之前的老版本写法*/
            intent.setDataAndType(
                Uri.fromFile(File(s)),
                "application/vnd.android.package-archive"
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    /**
     * 添加订阅
     * @param disposable Disposable
     */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}