package com.example.yemeksiparisuygulamasi.ui.menu

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.databinding.FragmentMenuBinding
import com.example.yemeksiparisuygulamasi.model.Food
import com.example.yemeksiparisuygulamasi.model.ResultData
import com.example.yemeksiparisuygulamasi.ui.common.BaseFragment
import com.example.yemeksiparisuygulamasi.ui.menu.adapter.MenuAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alertview_layout.view.*
import kotlinx.coroutines.FlowPreview

class MenuFragment : BaseFragment<MenuViewModel, FragmentMenuBinding>() {
    override val layoutRes: Int = R.layout.fragment_menu
    override lateinit var viewModel: MenuViewModel
    private lateinit var navController: NavController
    override fun observeViewModel() {
        viewModel._foodList.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                binding.menuRecyclerView.setHasFixedSize(true)
                binding.menuRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.menuRecyclerView.adapter =
                    MenuAdapter(
                        this.requireContext(),
                        it as ArrayList<Food>,
                        object : MenuAdapter.MenuItemClickListener {
                            override fun clickRow(food: Food) {
                                alertView(food)
                            }
                        })
            }

        })

        viewModel.searchedFoodList.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                binding.menuRecyclerView.setHasFixedSize(true)
                binding.menuRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.menuRecyclerView.adapter =
                    MenuAdapter(
                        this.requireContext(),
                        it as ArrayList<Food>,
                        object : MenuAdapter.MenuItemClickListener {
                            override fun clickRow(food: Food) {
                                alertView(food)
                            }
                        })
            }
        })

        viewModel.addedFoodToBasket.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Success -> {
                    // Todo: ekleme başarılı
                }
                is ResultData.Failed -> {

                }
                is ResultData.Loading -> {

                }
            }
        })
    }

    @FlowPreview
    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        navController = Navigation.findNavController(view)
        viewModel.getAllFoods(this.requireContext())
        binding.crossIcon.setOnClickListener {
            binding.searchEditText.isEnabled = false
            binding.searchIcon.visibility = View.VISIBLE
            binding.crossIcon.visibility = View.INVISIBLE
            binding.searchEditText.setText("")
        }
        binding.searchIcon.setOnClickListener {
            binding.searchEditText.isEnabled = true
            binding.searchEditText.requestFocus()
            binding.searchIcon.visibility = View.INVISIBLE
            binding.crossIcon.visibility = View.VISIBLE
        }
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.searchEditText.text?.let {
                    viewModel.searchFoodsWithKeyword(
                        this@MenuFragment.requireContext(),
                        it.toString()
                    )
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun alertView(food: Food) {
        val design =
            LayoutInflater.from(this.requireContext()).inflate(R.layout.alertview_layout, null)

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
            if (counter != 1) {
                counter--
                design.order_counter_text.text = counter.toString()
            }
        }

        alert.setPositiveButton("Sepete Ekle") { dialogInterface, i ->
            viewModel.addFoodsToBasket(this@MenuFragment.requireContext(), food, counter)
        }
        alert.setNegativeButton("İptal") { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        alert.create().show()
    }
}