package com.northernboy.renthouse.ui.registerHouse

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.centerToast
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.RegisterHouseFragmentBinding
import com.northernboy.renthouse.ui.register.RegisterViewModel
import com.northernboy.renthouse.view.HouseView

class RegisterHouseFragment : BaseBindingFragment<RegisterHouseFragmentBinding>(R.layout.register_house_fragment),AdapterView.OnItemSelectedListener {

    private lateinit var registerHouseViewModel: RegisterHouseViewModel

    override fun initViewModel() {
        registerHouseViewModel = ViewModelProvider(this).get(RegisterHouseViewModel::class.java)
    }

    override fun subscribe() {
        dataBinding.apply {
            registerCapacity.inputTitleSpinner.text = getString(R.string.register_house_capacity)
            registerHouseAddress.inputTitle.text = getString(R.string.register_house_address)
            registerHouseRent.inputTitle.text = getString(R.string.register_house_rent)
            registerHouseRent.inputContent.hint = getString(R.string.register_house_rent_hint)
            registerHouseType.inputTitle.text = getString(R.string.register_house_type)

            registerHouseConfirm.setOnClickListener {
                val address = registerHouseAddress.inputContent.text
                val type = registerHouseType.inputContent.text
                val capacity = registerCapacity.inputSpinner.selectedItem
                val rent = registerHouseRent.inputContent.text
                if(address != null && type != null && capacity != null && rent != null){
                    registerHouseViewModel.registerHouse(HouseView().apply {
                        houseAddress = address.toString()
                        houseCapacity = capacity.toString().toInt()
                        houseRent = rent.toString().toFloat()
                        houseType = type.toString()
                    })
                    findNavController().navigateUp()
                }else{
                    centerToast(requireContext(), getString(R.string.warn_input_error))
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }


}
