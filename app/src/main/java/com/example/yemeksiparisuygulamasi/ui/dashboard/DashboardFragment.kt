package com.example.yemeksiparisuygulamasi.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.databinding.FragmentDashboardBinding
import com.example.yemeksiparisuygulamasi.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>(){

    override val layoutRes: Int = R.layout.fragment_dashboard
    override val viewModel: DashboardViewModel by viewModels()

    override fun observeViewModel() {
        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textDashboard.text = it
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {

    }
}