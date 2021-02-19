package com.example.yemeksiparisuygulamasi.data.datasource

import android.content.Context
import com.example.yemeksiparisuygulamasi.domain.entity.Basket
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import kotlinx.coroutines.flow.Flow

interface BasketRemoteDataSource {
    suspend fun addBasket(context: Context, food: Food, counter: Int): Flow<ResultData<Unit>>
    suspend fun removeBasket(context: Context, foodFromBasket: Basket): Flow<ResultData<Unit>>
    suspend fun getBasket(context: Context): Flow<List<Basket>?>
}