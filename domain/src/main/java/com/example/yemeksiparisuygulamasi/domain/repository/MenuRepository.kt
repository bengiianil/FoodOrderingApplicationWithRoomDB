package com.example.yemeksiparisuygulamasi.domain.repository

import android.content.Context
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getAllFood(context: Context): Flow<ResultData<List<Food>>>
}