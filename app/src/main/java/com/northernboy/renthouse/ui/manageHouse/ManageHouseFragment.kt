package com.northernboy.renthouse.ui.manageHouse

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.northernboy.renthouse.R
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.base.NViewAdapter
import com.northernboy.renthouse.databinding.ManageHouseBinding
import com.northernboy.renthouse.databinding.ManageHouseFragmentBinding
import com.northernboy.renthouse.databinding.ManageHouseNewBinding
import com.northernboy.renthouse.view.HouseView

class ManageHouseFragment : BaseBindingFragment<ManageHouseFragmentBinding>(R.layout.manage_house_fragment) {

    private lateinit var manageHouseViewModel: ManageHouseViewModel


    override fun initViewModel() {
        manageHouseViewModel = ViewModelProvider(this).get(ManageHouseViewModel::class.java)
    }

    override fun subscribe() {
        dataBinding.recyclerManageHouse.apply {
            adapter = ManageHouseAdapter()
        }
        manageHouseViewModel.houseViewList.observe(viewLifecycleOwner, Observer {
            val adapter = dataBinding.recyclerManageHouse.adapter as ManageHouseAdapter
            val newIt = it?.plus(HouseView())
            adapter.submitList(newIt)
        })
    }


}
class ManageHouseAdapter: NViewAdapter<HouseView>(DiffCallBack()){

    var isNull = false

    override fun touchEvent(binding: ViewDataBinding) {
        var dataBinding : ViewDataBinding
        if(isNull){
            dataBinding = binding as ManageHouseNewBinding
            dataBinding.manageHouseNew.setOnClickListener {
                dataBinding.root.findNavController().navigate(R.id.navigation_register_house)
            }
        }else{
            dataBinding = binding as ManageHouseBinding
            dataBinding.manageHouseMore.setOnClickListener {
                val action = ManageHouseFragmentDirections.actionNavigationManageOwnHouseToMangeHouseUpdateFragment(dataBinding.item.toString())
                dataBinding.root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
       return if(position+1 == itemCount){
           isNull = true
            R.layout.manage_house_new
        }else{
            R.layout.manage_house
        }
    }
}