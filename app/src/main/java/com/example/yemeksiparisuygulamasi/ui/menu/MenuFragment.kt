package com.example.yemeksiparisuygulamasi.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.ui.menu.adapter.MenuAdapter
import com.example.yemeksiparisuygulamasi.databinding.FragmentMenuBinding
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import com.example.yemeksiparisuygulamasi.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<MenuViewModel, FragmentMenuBinding>() {
    override val layoutRes: Int = R.layout.fragment_menu
    override val viewModel: MenuViewModel by viewModels()
    private lateinit var navController: NavController
    override fun observeViewModel() {
        viewModel.restaurantList.observe(viewLifecycleOwner, Observer {

            when (it) {
                is ResultData.Success -> {
                    binding.menuRecyclerView.setHasFixedSize(true)
                    binding.menuRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding.menuRecyclerView.adapter =
                        MenuAdapter(
                            this.requireContext(),
                            it.toData() as ArrayList<Food>,
                            1,
                            object : MenuAdapter.ItemClickListener {
                                override fun clickRow(position: Int) {

                                }
                            })
                }
                is ResultData.Failed -> {

                }
                is ResultData.Loading -> {

                }
            }
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        viewModel.getAllFoods(this.requireContext())
    }
}