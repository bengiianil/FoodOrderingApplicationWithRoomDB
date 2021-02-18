package com.example.yemeksiparisuygulamasi.domain.repository

import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getAllFood(): Flow<ResultData<List<Food>>>
}