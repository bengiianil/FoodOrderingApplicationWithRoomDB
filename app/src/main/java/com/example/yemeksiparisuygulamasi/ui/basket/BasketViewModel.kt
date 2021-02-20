package com.example.yemeksiparisuygulamasi.ui.basket

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yemeksiparisuygulamasi.data.remote.BasketRemoteDataSourceImpl
import com.example.yemeksiparisuygulamasi.model.Basket
import com.example.yemeksiparisuygulamasi.model.ResultData
import com.example.yemeksiparisuygulamasi.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BasketViewModel : BaseViewModel() {
    private val _removedFoodToBasket = MutableLiveData<ResultData<Unit>>()
    val removedFoodToBasket: MutableLiveData<ResultData<Unit>>
        get() = _removedFoodToBasket

    private val _foodsListFromBasket = MutableLiveData<List<Basket>>()
    val foodsListFromBasket: MutableLiveData<List<Basket>>
        get() = _foodsListFromBasket

    fun getFoodsFromBasket(context:Context){
        viewModelScope.launch(Dispatchers.IO) {
            BasketRemoteDataSourceImpl().getBasket(context).collect {
                _foodsListFromBasket.postValue(it)
            }
        }
    }

    fun deleteFoodsFromBasket(context:Context, foodFromBasket: Basket){
        viewModelScope.launch(Dispatchers.IO) {
            BasketRemoteDataSourceImpl().removeBasket(context,foodFromBasket).collect {
                handleTask(it) {
                    _removedFoodToBasket.postValue(it)
                }
            }
        }
    }
}