package com.northernboy.renthouse.ui.bbs

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.northernboy.renthouse.R
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.base.NViewAdapter
import com.northernboy.renthouse.databinding.FragmentBbsBinding
import com.northernboy.renthouse.databinding.PostViewBinding
import com.northernboy.renthouse.databinding.PostViewBindingImpl
import com.northernboy.renthouse.view.PostView

class BbsFragment : BaseBindingFragment<FragmentBbsBinding>(R.layout.fragment_bbs) {

    private lateinit var bbsViewModel: BbsViewModel

    override fun initViewModel() {
        bbsViewModel = ViewModelProvider(this).get(BbsViewModel::class.java)
    }

    override fun subscribe() {
        dataBinding.recyclerPostView.layoutManager = LinearLayoutManager(context)
        dataBinding.recyclerPostView.adapter = PostViewAdapter()

        bbsViewModel.postViewList.observe(viewLifecycleOwner, Observer {
            val adapter = dataBinding.recyclerPostView.adapter as PostViewAdapter
            adapter.submitList(it)
        })
    }
}

class PostViewAdapter : NViewAdapter<PostView>(DiffCallBack()) {
    override fun touchEvent(binding: ViewDataBinding) {
        binding.root.setOnClickListener {
            val postViewDataBinding = binding as PostViewBinding
            val action = BbsFragmentDirections.actionNavigationBbsToReplyFragment(Gson().toJson(postViewDataBinding.item))
            binding.root.findNavController().navigate(action)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.post_view
    }
}