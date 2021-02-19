package com.example.yemeksiparisuygulamasi.domain.repository

import android.content.Context
import com.example.yemeksiparisuygulamasi.domain.entity.Basket
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun addBasket(context: Context, food: Food, counter: Int): Flow<ResultData<Unit>>
    suspend fun removeBasket(context: Context, foodFromBasket: Basket): Flow<ResultData<Unit>>
    fun getBasket(context: Context):  Flow<ResultData<List<Basket>>>
}