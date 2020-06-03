package com.northernboy.renthouse.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider

import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.centerToast
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.RegisterFragmentBinding
import com.northernboy.renthouse.view.UsrView

class RegisterFragment : BaseBindingFragment<RegisterFragmentBinding>(R.layout.register_fragment), AdapterView.OnItemSelectedListener {

    private lateinit var usrView: UsrView
    private lateinit var registerViewModel: RegisterViewModel

    override fun initViewModel() {
        usrView = getUsrView()?: UsrView()
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun subscribe() {

      //  setupGenderSpinner()

        val editableFactory = Editable.Factory.getInstance()
        dataBinding.apply {
            register.setOnClickListener {
                if(usrView.usrId != null){
                    if( usrView.identityNo != null && usrView.password != null){
                        registerViewModel.register(
                            registerName.inputContent.text.toString(),
                            registerUsrAddress.inputContent.text.toString(),
                            getGender(),
                            registerPhone.inputContent.text.toString()
                        )
                    }else{
                        centerToast(requireContext(), getString(R.string.warn_id_password_not_null))
                    }

                }else{
                    centerToast(requireContext(), getString(R.string.warn_login_first))
                }

            }
            registerIdentity.apply {
                inputTitle.text = getString(R.string.register_identity_no)
                inputContent.text = editableFactory.newEditable(usrView.identityNo)
            }
            registerName.apply {
                inputTitle.text = getString(R.string.register_name)
                inputContent.text = editableFactory.newEditable(usrView.name)
            }
            registerPassword.apply {
                inputTitle.text = getString(R.string.register_password)
                inputContent.text = editableFactory.newEditable(usrView.password)
            }
            registerPhone.apply {
                inputTitle.text = getString(R.string.register_phone)
                inputContent.text = editableFactory.newEditable(usrView.phone)
            }
            registerUsrAddress.apply {
                inputTitle.text = getString(R.string.register_usr_address)
                inputContent.text = editableFactory.newEditable(usrView.usrAddress)
            }
            registerGender.text = getString(R.string.register_gender)
        }
    }

    private fun getGender(): Int {

        return if( dataBinding.spinnerGender.selectedItem.toString() == getString(R.string.male)){
            1
        }else{
            0
        }
    }

    private fun setupGenderSpinner(){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dataBinding.spinnerGender.adapter = it
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        if(usrView.gender != null){
            parent?.setSelection(usrView.gender!!)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}

