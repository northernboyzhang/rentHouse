package com.northernboy.renthouse.ui.home

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.rentLog
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.base.NViewAdapter
import com.northernboy.renthouse.databinding.FragmentHomeBinding
import com.northernboy.renthouse.view.HouseView

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel

    override fun initViewModel() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun subscribe(){
        dataBinding.recyclerHouseView.apply {
            adapter = HouseViewAdapter()
            layoutManager = LinearLayoutManager(context)
        }
        val adapter = dataBinding.recyclerHouseView.adapter as HouseViewAdapter
        homeViewModel.houseViewList.observe(viewLifecycleOwner, Observer {
            rentLog(it.size.toString())
            adapter.submitList(it)
        })
    }
}

class HouseViewAdapter : NViewAdapter<HouseView>(DiffCallBack()){

    override fun touchEvent(binding: ViewDataBinding) {

    }

    override fun getItemViewType(position: Int): Int {
        rentLog(getItem(position).toString())
        return R.layout.house_view
    }
}