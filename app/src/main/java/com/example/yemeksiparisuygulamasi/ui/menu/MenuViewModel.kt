package com.example.yemeksiparisuygulamasi.ui.menu

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yemeksiparisuygulamasi.data.remote.BasketRemoteDataSource
import com.example.yemeksiparisuygulamasi.data.remote.MenuRemoteDataSource
import com.example.yemeksiparisuygulamasi.model.Food
import com.example.yemeksiparisuygulamasi.model.ResultData
import com.example.yemeksiparisuygulamasi.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.collect

class MenuViewModel : BaseViewModel() {

    private val foodList = MutableLiveData<List<Food>>()
    val _foodList: MutableLiveData<List<Food>>
        get() = foodList

    private val _searchedFoodList = MutableLiveData<List<Food>>()
    val searchedFoodList: MutableLiveData<List<Food>>
        get() = _searchedFoodList

    private val _addedFoodToBasket = MutableLiveData<ResultData<Unit>>()
    val addedFoodToBasket: MutableLiveData<ResultData<Unit>>
        get() = _addedFoodToBasket

    @FlowPreview
    fun getAllFoods(context:Context) {
        viewModelScope.launch(Dispatchers.IO) {
            MenuRemoteDataSource().getAllFoods(context).collect {
                _foodList.postValue(it)
            }
        }
    }

    fun searchFoodsWithKeyword(context:Context, keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            MenuRemoteDataSource().searchFood(context,keyword).collect {
                _foodList.postValue(it)
            }
        }
    }

    fun addFoodsToBasket(context:Context, food: Food, counter: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            BasketRemoteDataSource().addBasket(context, food, counter).collect {
                handleTask(it) {
                    _addedFoodToBasket.postValue(it)
                }
            }
        }
    }
}