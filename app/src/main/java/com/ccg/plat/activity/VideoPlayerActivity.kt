package com.ccg.plat.activity

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.blankj.utilcode.util.ScreenUtils
import com.ccg.plat.Const
import com.ccg.plat.R
import com.ccg.plat.entity.RoomBean
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.tencent.mmkv.MMKV
import timber.log.Timber
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager


/**
 * @author : C4_雍和
 * 描述 :视频播放页面
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/1/31 11:03
 */
class VideoPlayerActivity : Activity() {
    val context = this
    private lateinit var videoPlayer: StandardGSYVideoPlayer
    private lateinit var funLl: LinearLayout
    private lateinit var randomButton: Button
    private lateinit var collectionButton: Button
    private lateinit var deleteCollectionButton: Button
    private lateinit var orientationUtils: OrientationUtils
    val kv = MMKV.defaultMMKV()
    //收藏列表数据
    var collectionData: MutableList<RoomBean> = ArrayList()
    //播放列表数据
    var playData: MutableList<RoomBean> = ArrayList()
    var index = 0
    /**
     * 0是播放视频列表
     * 1是播放收藏列表
     */
    var tag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ccc = WindowCompat.getInsetsController(window, window.decorView)
        ccc.hide(WindowInsetsCompat.Type.statusBars())
        setContentView(R.layout.activity_simple_play)
        index = intent.getIntExtra("index", 0)
        tag = intent.getIntExtra("tag", 0)
        playData.addAll(Const.finalVideoList)
        init()
    }

    private fun init() {
        videoPlayer = findViewById<StandardGSYVideoPlayer>(R.id.video_player)
        funLl = findViewById<LinearLayout>(R.id.fun_ll)
        randomButton = findViewById<Button>(R.id.random_video_btn)
        collectionButton = findViewById<Button>(R.id.collection_video_btn)
        deleteCollectionButton = findViewById<Button>(R.id.collection_delete_btn)
        if (tag == 1) {
            collectionButton.visibility = View.VISIBLE
            deleteCollectionButton.visibility = View.GONE
            val json = kv.decodeString("collection_key")
            if (json.isNullOrEmpty()) {
            } else {
                collectionData.addAll(GsonBuilder().create().fromJson<MutableList<RoomBean>>(json, object : TypeToken<MutableList<RoomBean>>() {}.type))
            }
        } else if (tag == 2) {
            collectionButton.visibility = View.GONE
            deleteCollectionButton.visibility = View.VISIBLE
        }
        videoPlayer.setUp(getVideoData().vUrl, true, getVideoData().name)
        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        videoPlayer.titleTextView.visibility = View.VISIBLE
        videoPlayer.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, videoPlayer)
        videoPlayer.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()
        }
        videoPlayer.setIsTouchWiget(true)
        videoPlayer.backButton.setOnClickListener {
            if (ScreenUtils.isLandscape()) {
                ScreenUtils.setPortrait(context)
            }
            onBackPressed()
        }
        videoPlayer.isNeedOrientationUtils = true
        videoPlayer.startPlayLogic()
        videoPlayer.setGSYStateUiListener {
            if (it == 5) {
                funLl.visibility = View.VISIBLE
            } else {
                funLl.visibility = View.GONE
            }
        }
        //收藏按钮的点击事件
        collectionButton.setOnClickListener {
            if (Const.IS_VIP) {
                if (!collectionData.contains(getVideoData())) {
                    collectionData.add(getVideoData())
                    kv.encode("collection_key", GsonBuilder().create().toJson(collectionData))
                }
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "你不是VIP用户", Toast.LENGTH_SHORT).show()
            }
        }
        //删除收藏按钮的点击事件
        deleteCollectionButton.setOnClickListener {
            Const.finalVideoList.remove(getVideoData())
            kv.encode("collection_key", GsonBuilder().create().toJson(Const.finalVideoList))
            Toast.makeText(context, "收藏已删除", Toast.LENGTH_SHORT).show()
        }
        //随机播放按钮的点击事件
        randomButton.setOnClickListener {
            videoPlayer.onVideoPause()
            index = (0 until playData.size).random()
            videoPlayer.setUp(getVideoData().vUrl, true, getVideoData().name)
            videoPlayer.startPlayLogic()
        }
        videoPlayer.setOnClickListener {  }
    }

    private fun getVideoData(): RoomBean {
        return playData[index]
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (orientationUtils != null) orientationUtils.releaseListener()
    }

    override fun onBackPressed() {
        videoPlayer.setVideoAllCallBack(null)
        super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            if (ScreenUtils.isLandscape()) {
                ScreenUtils.setPortrait(context)
            }
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}