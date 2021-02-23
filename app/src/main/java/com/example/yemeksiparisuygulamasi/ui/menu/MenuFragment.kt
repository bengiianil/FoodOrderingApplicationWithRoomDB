package com.example.yemeksiparisuygulamasi.ui.menu


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.databinding.FragmentMenuBinding
import com.example.yemeksiparisuygulamasi.model.Food
import com.example.yemeksiparisuygulamasi.model.ResultData
import com.example.yemeksiparisuygulamasi.ui.menu.adapter.MenuAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alertview_layout.view.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.coroutines.FlowPreview

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        binding.menuToolbar.setTitle("Menü")
        (activity as AppCompatActivity).setSupportActionBar(binding.menuToolbar)

        binding.menuToolbar.setTitleTextColor(this.requireContext().getColor(R.color.white))
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.foodList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.menuRecyclerView.setHasFixedSize(true)
                binding.menuRecyclerView.layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL)
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
            if (it != null) {
                binding.menuRecyclerView.setHasFixedSize(true)
                binding.menuRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
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
                }
                is ResultData.Failed -> {
                }
                is ResultData.Loading -> {

                }
            }
        })
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        observeViewModel()
        viewModel.getAllFoods(this.requireContext())
        binding.crossIcon.setOnClickListener {
            hideKeyboard()
            binding.searchEditText.isEnabled = false
            binding.menuToolbar.setTitle("Menü")
            binding.searchIcon.visibility = View.VISIBLE
            binding.crossIcon.visibility = View.INVISIBLE
            binding.searchEditText.setText("")
        }
        binding.searchIcon.setOnClickListener {
            openKeyboard()
            binding.searchEditText.isEnabled = true
            binding.menuToolbar.setTitle("")
            binding.searchEditText.requestFocus()
            binding.searchIcon.visibility = View.INVISIBLE
            binding.crossIcon.visibility = View.VISIBLE
        }
        binding.searchEditText.setOnClickListener {
            binding.searchEditText.setText("")
            binding.searchEditText.isEnabled = false
        }
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.searchEditText.text?.let {
                    viewModel.searchFoodsWithKeyword(this@MenuFragment.requireContext(), it.toString())
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
            Toast.makeText(this.requireContext(), "${food.name} Sepete Eklendi.", Toast.LENGTH_SHORT).show()
            viewModel.addFoodsToBasket(this@MenuFragment.requireContext(), food, counter)
        }
        alert.setNegativeButton("İptal") { dialogInterface, i ->
            Toast.makeText(this.requireContext(), "Seçim İptal Edildi.", Toast.LENGTH_SHORT).show()
            dialogInterface.dismiss()
        }
        alert.create().show()
    }

    private fun openKeyboard() {
        val imm: InputMethodManager? = this.requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = this.requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.requireActivity().currentFocus
        if (view == null) {
            view = View(this.requireActivity())
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}