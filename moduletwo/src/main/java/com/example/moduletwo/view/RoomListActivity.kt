package com.example.moduletwo.view

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ccg.libbase.BaseActivityB
import com.example.moduletwo.R
import com.example.moduletwo.adapter.RoomListAdapter
import com.example.moduletwo.databinding.ActivityVideoListBinding
import com.example.moduletwo.util.NavigationUtils
import com.example.moduletwo.viewmodel.RoomListViewModel
import com.google.gson.GsonBuilder
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :视频房间列表
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-2 下午4:52
 */
class RoomListActivity : BaseActivityB<RoomListViewModel>() {
    override fun providerVMClass(): Class<RoomListViewModel>? =
        RoomListViewModel::class.java

    private lateinit var binding: ActivityVideoListBinding
    private var adapter = RoomListAdapter()
    private val context = this
    override fun initView() {
        binding = DataBindingUtil.setContentView<ActivityVideoListBinding>(
            this,
            R.layout.activity_video_list
        )
    }

    override fun initData() {
        binding.recyclerView.adapter = adapter
        getData()
    }

    override fun setListener() {
        adapter.setOnItemClickListener { adapter, view, position ->
            viewModel.uiData.value?.run {
                val clickData = data[position]
                val json = GsonBuilder().create().toJson(clickData)
                val intent=Intent(context,VideoListActivity::class.java)
                intent.putExtra("json",json)
                startActivity(intent)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.apply {
            uiData.observe(context, Observer {
                adapter.setNewData(it.data)
            })
            errMsg.observe(context, Observer {
                Timber.e("加载失败$it")
            })
        }
    }

    override fun getData() {
        super.getData()
        viewModel.initData()
    }
}
