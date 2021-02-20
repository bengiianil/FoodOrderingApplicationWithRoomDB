package com.example.yemeksiparisuygulamasi.ui.basket

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.databinding.FragmentBasketBinding
import com.example.yemeksiparisuygulamasi.model.Basket
import com.example.yemeksiparisuygulamasi.model.ResultData
import com.example.yemeksiparisuygulamasi.ui.basket.adapter.BasketAdapter
import com.example.yemeksiparisuygulamasi.ui.common.BaseFragment

class BasketFragment : BaseFragment<BasketViewModel, FragmentBasketBinding>() {

    override val layoutRes: Int = R.layout.fragment_basket
    override lateinit var viewModel: BasketViewModel

    override fun observeViewModel() {
        viewModel.foodsListFromBasket.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                var sumPrice = 0
                binding.noProductInBasketText.visibility = View.INVISIBLE
                binding.basketRecyclerView.visibility = View.VISIBLE
                binding.basketRecyclerView.setHasFixedSize(true)
                binding.basketRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                binding.basketRecyclerView.adapter =
                    BasketAdapter(
                        this.requireContext(),
                        it as ArrayList<Basket>,
                        object : BasketAdapter.BasketItemClickListener {
                            override fun delete(foodFromBasket: Basket) {
                                viewModel.deleteFoodsFromBasket(
                                    this@BasketFragment.requireContext(),
                                    foodFromBasket
                                )
                            }
                        })
                binding.basketRecyclerView.visibility = View.VISIBLE
                binding.noProductInBasketText.visibility = View.GONE

                it.forEach {
                    sumPrice += (it.food.price * it.orderQuantity)
                }
                binding.sumBasketPriceText.text = sumPrice.toString()
            }
            else{
                binding.noProductInBasketText.visibility = View.VISIBLE
                binding.basketRecyclerView.visibility = View.INVISIBLE
            }
        })

        viewModel.removedFoodToBasket.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                    viewModel.getFoodsFromBasket(this.requireContext())
                }
                is ResultData.Failed -> {

                }
                is ResultData.Loading -> {

                }
            }
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(BasketViewModel::class.java)
        viewModel.getFoodsFromBasket(this.requireContext())
    }
}