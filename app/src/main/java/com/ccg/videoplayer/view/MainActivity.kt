package com.ccg.videoplayer.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ccg.videoplayer.GitHubService
import com.ccg.videoplayer.R
import com.ccg.videoplayer.entity.VideoBean
import com.ccg.videoplayer.util.NavigationUtils
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://siyou.nos-eastchina1.126.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    private val context = this
    var userList: MutableList<VideoBean> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.main_tv_click).setOnClickListener {
            NavigationUtils.goSelectSubjectActivity()
            val random = Random()
            val num = random.nextInt(userList.size + 1)
            val dat=userList[num]
            dat.vUrl="https://d1.xia12345.com/d/62/2018/06/VwsLD2jY.mp4"
            val ssss = GsonBuilder().create().toJson(dat)
            Timber.e(ssss)
            NavigationUtils.goSelectSubjectActivity(ssss)
        }
        /**
         * 联网请求网络并随机跳转到播放页面
         */

        val service = retrofit.create(GitHubService::class.java)
        addDisposable(
            service.getTemp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userList.clear()
                    userList.addAll(it)
                }, {})
        )
    }

    /**
     * 添加订阅
     * @param disposable Disposable
     */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
