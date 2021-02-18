package com.example.yemeksiparisuygulamasi.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.databinding.FragmentHomeBinding
import com.example.yemeksiparisuygulamasi.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val layoutRes: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    override fun observeViewModel() {
        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {

    }
}