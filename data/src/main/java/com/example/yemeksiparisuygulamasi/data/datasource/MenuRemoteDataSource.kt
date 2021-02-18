package com.example.yemeksiparisuygulamasi.data.datasource

import com.example.yemeksiparisuygulamasi.domain.entity.Food
import kotlinx.coroutines.flow.Flow

interface MenuRemoteDataSource {
    suspend fun getAllFoods(): Flow<List<Food>>
}