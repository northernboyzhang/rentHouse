package com.northernboy.renthouse.ui.manageHouse

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson

import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.centerToast
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.ManageHouseUpdateFragmentBinding
import com.northernboy.renthouse.view.HouseView

class ManageHouseUpdateFragment : BaseBindingFragment<ManageHouseUpdateFragmentBinding>(R.layout.manage_house_update_fragment) {

    private val args : ManageHouseUpdateFragmentArgs by navArgs()
    private lateinit var manageHouseUpdateViewModel: ManageHouseUpdateViewModel

    override fun initViewModel() {

        manageHouseUpdateViewModel = ViewModelProvider(this).get(ManageHouseUpdateViewModel::class.java)
        args.houseView
        val houseView = Gson().fromJson(args.houseView, HouseView::class.java)
        if(houseView.houseId != null){
            manageHouseUpdateViewModel.setHouseView(houseView)
        }
    }

    override fun subscribe() {

        manageHouseUpdateViewModel.houseView.observe(viewLifecycleOwner, Observer {
            dataBinding.item = it
        })
        dataBinding.manageHouseUpdateConfirm.setOnClickListener {
            manageHouseUpdateViewModel._houseView.value?.apply {
                houseAddress = dataBinding.manageHouseUpdateAddress.getContent()
                houseStatus = if(dataBinding.manageHouseUpdateSwitchStatus.isChecked) 1 else 0
                houseType = dataBinding.manageHouseUpdateType.getContent()
                houseRent = dataBinding.manageHouseUpdateRent.getContent()?.toFloat()
                houseCapacity = dataBinding.manageHouseUpdateCapacity.getSpinnerSelectedItem().toString().toInt()
                if(houseAddress.isNullOrEmpty() || houseStatus == null ||houseType.isNullOrEmpty() || houseRent == null || houseCapacity == null){
                    centerToast(requireContext(), getString(R.string.warn_input_error))
                }else{
                    manageHouseUpdateViewModel.updateHouse()
                    findNavController().navigateUp()
                    centerToast(requireContext(), getString(R.string.notify_success))
                }
            }
        }
    }

}
