package com.example.yemeksiparisuygulamasi.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.alertview_layout.view.*

@AndroidEntryPoint
class MenuFragment : BaseFragment<MenuViewModel, FragmentMenuBinding>() {
    override val layoutRes: Int = R.layout.fragment_menu
    override val viewModel: MenuViewModel by viewModels()
    private lateinit var navController: NavController
    override fun observeViewModel() {
        viewModel._foodList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                    binding.menuRecyclerView.setHasFixedSize(true)
                    binding.menuRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding.menuRecyclerView.adapter =
                        MenuAdapter(
                            this.requireContext(),
                            it.toData() as ArrayList<Food>,
                            object : MenuAdapter.MenuItemClickListener {
                                override fun clickRow(food: Food) {
                                    alertView(food)
                                }
                            })
                }
                is ResultData.Failed -> {
                    Toast.makeText(this.requireContext(), "Ürünler şu an alınamıyor lütfen daha sonra tekrar deneyiniz.", Toast.LENGTH_SHORT).show()
                }
                is ResultData.Loading -> {

                }
            }
        })

        viewModel.searchedFoodList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                    binding.menuRecyclerView.setHasFixedSize(true)
                    binding.menuRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding.menuRecyclerView.adapter =
                        MenuAdapter(
                            this.requireContext(),
                            it.toData() as ArrayList<Food>,
                            object : MenuAdapter.MenuItemClickListener {
                                override fun clickRow(food: Food) {
                                    alertView(food)
                                }
                            })
                }
                is ResultData.Failed -> {
                    Toast.makeText(this.requireContext(), "Aradığınız ürün bulunamadı.", Toast.LENGTH_SHORT).show()
                }
                is ResultData.Loading -> {

                }
            }
        })
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        viewModel.getAllFoods(this.requireContext())
        binding.menuSearchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.menuSearchView.query?.let { viewModel.searchFoodsWithKeyword(this@MenuFragment.requireContext(), it.toString()) }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                binding.menuSearchView.query?.let { viewModel.searchFoodsWithKeyword(this@MenuFragment.requireContext(), it.toString()) }
                return true
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun alertView(food :Food){
        val design = LayoutInflater.from(this.requireContext()).inflate(R.layout.alertview_layout, null)

        val alert = AlertDialog.Builder(this.requireContext())
            .setTitle("Ürün Detayı")
            .setView(design)

        var counter = 1
        design.order_counter_text.text = counter.toString()

        design.alert_text.text = "${food.name} - ${food.price} \u20ba"

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.image_path}"
        Picasso.get().load(url).into(design.alert_food_image)

        design.add_button.setOnClickListener {
            counter++
            design.order_counter_text.text = counter.toString()
        }
        design.remove_button.setOnClickListener {
            if(counter != 1) {
                counter--
                design.order_counter_text.text = counter.toString()
            }
        }

        alert.setPositiveButton("Sepete Ekle"){ dialogInterface, i->
            viewModel.addFoodsToBasket(this@MenuFragment.requireContext(),food,counter)
        }
        alert.setNegativeButton("İptal"){ dialogInterface, i->
            dialogInterface.dismiss()
        }
        alert.create().show()
    }
}