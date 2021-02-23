package com.example.yemeksiparisuygulamasi.ui.menu

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemeksiparisuygulamasi.data.local.FoodDatabase
import com.example.yemeksiparisuygulamasi.data.remote.BasketRemoteDataSource
import com.example.yemeksiparisuygulamasi.data.remote.MenuRemoteDataSource
import com.example.yemeksiparisuygulamasi.model.Food
import com.example.yemeksiparisuygulamasi.model.FoodRoom
import com.example.yemeksiparisuygulamasi.model.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {
    private val _foodList = MutableLiveData<List<Food>>()
    val foodList: MutableLiveData<List<Food>>
        get() = _foodList

    private val _searchedFoodList = MutableLiveData<List<Food>>()
    val searchedFoodList: MutableLiveData<List<Food>>
        get() = _searchedFoodList

    private val _addedFoodToBasket = MutableLiveData<ResultData<Unit>>()
    val addedFoodToBasket: MutableLiveData<ResultData<Unit>>
        get() = _addedFoodToBasket

    @FlowPreview
    fun getAllFoods(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            MenuRemoteDataSource().getAllFoods(context).collect {
                if (it != null) {
                    insertFoodsToRoomDB(context,it)
                    _foodList.postValue(it)
                }
                else {
                    getFoodsFromRoomDB(context)
                }
            }
        }
    }

    fun searchFoodsWithKeyword(context: Context, keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            MenuRemoteDataSource().searchFood(context, keyword).collect {
                _foodList.postValue(it)
            }
        }
    }

    fun addFoodsToBasket(context: Context, food: Food, counter: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            BasketRemoteDataSource().addBasket(context, food, counter).collect {
                _addedFoodToBasket.postValue(it)
            }
        }
    }

    private fun insertFoodsToRoomDB(context: Context, it: List<Food>) {
        viewModelScope.launch(Dispatchers.IO) {
            _foodList.postValue(it)
            val dao = FoodDatabase(context).foodDao()
            dao.deleteAllFoods()
            val foodRoomList: ArrayList<FoodRoom> = arrayListOf()
            it.forEach {
                foodRoomList.add(FoodRoom(it.name, it.image_path, it.price.toString()))
            }
            var i = 0
            while (i < it.size) {
                it[i].id = it[i].id
                i += 1
            }
        }
    }

    private fun getFoodsFromRoomDB(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val dao = FoodDatabase(context).foodDao()
            val roomList = dao.getAllFoods()
            val list: ArrayList<Food> = arrayListOf()
            roomList.forEach {
                list.add(Food(it.foodId, it.foodName!!, it.foodImagePath!!, it.foodPrice!!.toInt()))
            }
            _foodList.postValue(list)
        }
    }
}