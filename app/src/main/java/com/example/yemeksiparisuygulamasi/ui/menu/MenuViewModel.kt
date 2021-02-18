package com.example.yemeksiparisuygulamasi.ui.menu

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import com.example.yemeksiparisuygulamasi.domain.usecase.GetAllFoodsUseCase
import com.example.yemeksiparisuygulamasi.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.collect

class MenuViewModel @ViewModelInject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase
) : BaseViewModel() {

    private val _restaurantList = MutableLiveData<ResultData<List<Food>>>()
    val restaurantList: LiveData<ResultData<List<Food>>>
        get() = _restaurantList

    fun getAllFoods(context:Context){
        viewModelScope.launch(Dispatchers.IO) {
            getAllFoodsUseCase.invoke(context).collect { it ->
                handleTask(it) {
                    _restaurantList.postValue(it)
                }
            }
        }
    }
}