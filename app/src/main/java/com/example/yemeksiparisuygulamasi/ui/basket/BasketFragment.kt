package com.example.yemeksiparisuygulamasi.ui.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.databinding.FragmentBasketBinding
import com.example.yemeksiparisuygulamasi.domain.entity.Basket
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import com.example.yemeksiparisuygulamasi.ui.basket.adapter.BasketAdapter
import com.example.yemeksiparisuygulamasi.ui.common.BaseFragment
import com.example.yemeksiparisuygulamasi.ui.menu.adapter.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : BaseFragment<BasketViewModel, FragmentBasketBinding>(){

    override val layoutRes: Int = R.layout.fragment_basket
    override val viewModel: BasketViewModel by viewModels()

    override fun observeViewModel() {
        viewModel.addedFoodToBasket.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                   println(it.data)
                }
                is ResultData.Failed -> {
                    println(it.error)
                }
                is ResultData.Loading -> {

                }
            }
        })


        viewModel.foodsListFromBasket.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                    binding.basketRecyclerView.setHasFixedSize(true)
                    binding.basketRecyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    binding.basketRecyclerView.adapter =
                        BasketAdapter(
                            this.requireContext(),
                            it.toData() as ArrayList<Basket>,
                            object : BasketAdapter.BasketItemClickListener {
                                override fun delete(foodFromBasket: Basket) {
                                    viewModel.deleteFoodsFromBasket(this@BasketFragment.requireContext(),foodFromBasket)
                                }
                            })
                }
                is ResultData.Failed -> {
                    println(it.error)
                }
                is ResultData.Loading -> {

                }
            }
        })

        viewModel.removedFoodToBasket.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                    viewModel.getFoodsFromBasket(this.requireContext())
                }
                is ResultData.Failed -> {
                    println(it.error)
                }
                is ResultData.Loading -> {

                }
            }
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFoodsFromBasket(this.requireContext())
        //viewModel.addFoodsToBasket(this.requireContext(), Food(213,"yemek","image",43),3)
        //viewModel.deleteFoodsFromBasket(this.requireContext(), Basket( Food(213,"yemek","image",43),1))
    }
}