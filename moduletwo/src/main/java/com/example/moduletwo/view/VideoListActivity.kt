package com.example.moduletwo.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.billy.cc.core.component.CCUtil
import com.ccg.libbase.BaseActivityB
import com.example.moduletwo.R
import com.example.moduletwo.adapter.VideoListAdapter
import com.example.moduletwo.databinding.ActivityVideoListBinding
import com.example.moduletwo.entity.RoomBean
import com.example.moduletwo.util.NavigationUtils
import com.example.moduletwo.viewmodel.VideoListViewModel
import com.google.gson.GsonBuilder
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :视频列表页面
 * 主要功能 :以列表形势显示所有视频
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:27
 *
 */
class VideoListActivity : BaseActivityB<VideoListViewModel>() {
    override fun providerVMClass(): Class<VideoListViewModel>? =
        VideoListViewModel::class.java
    private lateinit var binding: ActivityVideoListBinding
    private var url = ""
    private var adapter = VideoListAdapter()
    private val context =this
    override fun initView() {
        binding = DataBindingUtil.setContentView<ActivityVideoListBinding>(
            this,
            R.layout.activity_video_list
        )
    }

    override fun initData() {
        val json = CCUtil.getNavigateParam(
            this,
            "json",
            ""
        )
        Timber.e(json)
        val data =
            GsonBuilder().create().fromJson<RoomBean.DataBean>(json, RoomBean.DataBean::class.java)
        url = data.roomUrl
        binding.recyclerView.adapter = adapter
        getData()
    }

    override fun setListener() {
        adapter.setOnItemClickListener { adapter, view, position ->
            viewModel.uiData.value?.run {

                val clickData = this[position]
                val clipboardManagerOne = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboardManagerOne.setPrimaryClip(ClipData.newPlainText(null, clickData.getvUrl()))
                val json = GsonBuilder().create().toJson(clickData)
                NavigationUtils.goVideoPlayActivity(json)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.apply {
            uiData.observe(this@VideoListActivity, Observer {
                adapter.setNewData(it)
            })
            errMsg.observe(this@VideoListActivity, Observer {
                Timber.e("加载失败$it")
            })
        }
    }

    override fun getData() {
        super.getData()
        viewModel.initData(url)
    }
}
