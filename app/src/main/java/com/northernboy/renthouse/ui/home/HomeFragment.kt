package com.northernboy.renthouse.ui.home

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.opengl.Visibility
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.centerToast
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.Utils.rentLog
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.base.NViewAdapter
import com.northernboy.renthouse.databinding.FragmentHomeBinding
import com.northernboy.renthouse.databinding.HouseViewBinding
import com.northernboy.renthouse.view.HouseView

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel

    override fun initViewModel() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun subscribe(){
        dataBinding.recyclerHouseView.apply {
            adapter = HouseViewAdapter{houseId ->
                val usrView = getUsrView()
                if(usrView?.usrId != null){
                    homeViewModel.reserve(houseId, usrView.usrId!!)
                    centerToast(context, "预约已发送")
                }else{
                    centerToast(context, "请先登录")
                }
            }
        }
        val adapter = dataBinding.recyclerHouseView.adapter as HouseViewAdapter
        homeViewModel.houseViewList.observe(viewLifecycleOwner, Observer {
            rentLog(it.size.toString())
            adapter.submitList(it)
        })
    }
}

class HouseViewAdapter(val reserve:(houseId: Int) -> Unit) : NViewAdapter<HouseView>(DiffCallBack()){

    override fun touchEvent(binding: ViewDataBinding) {
        val houseViewDataBinding = binding as HouseViewBinding
       houseViewDataBinding.order.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToOrderFragment(Gson().toJson(houseViewDataBinding.item))
            houseViewDataBinding.root.findNavController().navigate(action)
        }
        houseViewDataBinding.reserve.setOnClickListener {
            houseViewDataBinding.item?.houseId?.let { houseId -> reserve(houseId) }
        }
        houseViewDataBinding.expandMore.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
            val vis = houseViewDataBinding.funcs.visibility
            val rotateValue: Float
            if(vis == View.GONE){
                houseViewDataBinding.funcs.visibility = View.VISIBLE
                rotateValue = 180f
            }else{
                //houseViewDataBinding.funcs.visibility = View.INVISIBLE
                houseViewDataBinding.funcs.visibility = View.GONE
                rotateValue = 0f
            }
            it.animate().apply {
                rotation(rotateValue)
                duration = 500
            }.start()
        }
    }

    override fun getItemViewType(position: Int): Int {
        rentLog(getItem(position).toString())
        return R.layout.house_view
    }
}