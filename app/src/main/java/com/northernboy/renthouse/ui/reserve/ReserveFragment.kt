package com.northernboy.renthouse.ui.reserve

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.northernboy.renthouse.R
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.base.NViewAdapter
import com.northernboy.renthouse.databinding.ReserveFragmentBinding
import com.northernboy.renthouse.view.ReserveView

class ReserveFragment : BaseBindingFragment<ReserveFragmentBinding>(R.layout.reserve_fragment) {

    private lateinit var reserveViewModel: ReserveViewModel

    override fun initViewModel() {
        reserveViewModel = ViewModelProvider(this).get(ReserveViewModel::class.java)
    }

    override fun subscribe() {
        dataBinding.recyclerReserveView.apply {
            adapter = ReserveAdapter()
            layoutManager = LinearLayoutManager(context)
        }

        reserveViewModel.reserveViewList.observe(viewLifecycleOwner, Observer {
            val adapter = dataBinding.recyclerReserveView.adapter as ReserveAdapter
            adapter.submitList(it)
        })
    }
}
class ReserveAdapter: NViewAdapter<ReserveView>(DiffCallBack()){
    override fun touchEvent(binding: ViewDataBinding) {

    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.reserve
    }
}