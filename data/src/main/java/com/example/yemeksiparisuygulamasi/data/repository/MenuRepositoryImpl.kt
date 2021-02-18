package com.example.yemeksiparisuygulamasi.data.repository

import android.content.Context
import com.example.yemeksiparisuygulamasi.data.datasource.MenuRemoteDataSource
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import com.example.yemeksiparisuygulamasi.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(private val dataSource: MenuRemoteDataSource) :
    MenuRepository {
    override fun getAllFood(context: Context): Flow<ResultData<List<Food>>> = flow {
        emit(ResultData.Loading())
        val commentsList = dataSource.getAllFoods(context)
        commentsList.collect {
            if (it != null){
                emit(ResultData.Success(it))
            }
            else{
                emit(ResultData.Failed())
            }
        }
    }
}