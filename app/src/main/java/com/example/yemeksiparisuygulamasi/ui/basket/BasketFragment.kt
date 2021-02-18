package com.example.yemeksiparisuygulamasi.ui.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.databinding.FragmentBasketBinding
import com.example.yemeksiparisuygulamasi.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : BaseFragment<BasketViewModel, FragmentBasketBinding>(){

    override val layoutRes: Int = R.layout.fragment_basket
    override val viewModel: BasketViewModel by viewModels()

    override fun observeViewModel() {
        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textDashboard.text = it
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {

    }
}