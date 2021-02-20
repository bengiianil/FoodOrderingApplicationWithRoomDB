package com.example.yemeksiparisuygulamasi.ui.basket

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemeksiparisuygulamasi.data.remote.BasketRemoteDataSource
import com.example.yemeksiparisuygulamasi.model.Basket
import com.example.yemeksiparisuygulamasi.model.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {
    private val _removedFoodToBasket = MutableLiveData<ResultData<Unit>>()
    val removedFoodToBasket: MutableLiveData<ResultData<Unit>>
        get() = _removedFoodToBasket

    private val _foodsListFromBasket = MutableLiveData<List<Basket>>()
    val foodsListFromBasket: MutableLiveData<List<Basket>>
        get() = _foodsListFromBasket

    fun getFoodsFromBasket(context:Context){
        viewModelScope.launch(Dispatchers.IO) {
            BasketRemoteDataSource().getBasket(context).collect {
                _foodsListFromBasket.postValue(it)
            }
        }
    }

    fun deleteFoodsFromBasket(context:Context, foodFromBasket: Basket){
        viewModelScope.launch(Dispatchers.IO) {
            BasketRemoteDataSource().removeBasket(context,foodFromBasket).collect {
                _removedFoodToBasket.postValue(it)
            }
        }
    }
}