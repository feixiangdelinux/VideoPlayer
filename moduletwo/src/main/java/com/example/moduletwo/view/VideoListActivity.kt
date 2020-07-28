package com.example.moduletwo.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ccg.libbase.BaseActivityB
import com.example.moduletwo.R
import com.example.moduletwo.adapter.VideoListAdapter
import com.example.moduletwo.databinding.ActivityVideoListBinding
import com.example.moduletwo.util.FinalProvider
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
    private var adapter = VideoListAdapter()
    private val context = this
    override fun initView() {
        binding = DataBindingUtil.setContentView<ActivityVideoListBinding>(
            this,
            R.layout.activity_video_list
        )
    }

    override fun initData() {
        binding.recyclerView.adapter = adapter
        adapter.setNewData(FinalProvider.getFinalData())
    }

    override fun setListener() {
        adapter.setOnItemClickListener { _, _, position ->
            FinalProvider.getFinalData().run {
                val clickData = this[position]
                val json = GsonBuilder().create().toJson(clickData)
                NavigationUtils.goVideoPlayActivity(json)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.apply {
            uiData.observe(context, Observer {

            })
            errMsg.observe(context, Observer {
                Timber.e("加载失败$it")
            })
        }
    }

    override fun getData() {
        super.getData()
//        viewModel.initData()
    }
}
