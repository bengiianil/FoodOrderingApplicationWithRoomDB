package com.example.yemeksiparisuygulamasi.data.local

import androidx.room.*
import com.example.yemeksiparisuygulamasi.model.Food
import com.example.yemeksiparisuygulamasi.model.FoodRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert
    suspend fun insertAll(foods: List<FoodRoom>) : List<Long>

    @Query("SELECT * FROM FoodRoom")
    suspend fun getAllFoods() : List<FoodRoom>

    @Query("DELETE FROM FoodRoom")
    suspend fun deleteAllFoods()
}