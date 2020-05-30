package com.northernboy.renthouse.ui

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.northernboy.renthouse.R
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.FragmentLoginBinding
import com.northernboy.renthouse.main.UsrViewModel

class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    lateinit var usrViewModel: UsrViewModel
    override fun initViewModel() {
        usrViewModel = ViewModelProvider(requireActivity()).get(UsrViewModel::class.java)
    }

    override fun subscribe() {
        dataBinding.loginRegister.setOnClickListener {
            usrViewModel.login(
                dataBinding.loginId.text.toString(),
                dataBinding.loginPassword.text.toString()
            )
            findNavController().navigateUp()
        }
    }

}