package com.example.yemeksiparisuygulamasi.data.datasource.remote

import com.example.yemeksiparisuygulamasi.data.datasource.MenuRemoteDataSource
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowViaChannel
import javax.inject.Inject

class MenuRemoteDataSourceImpl @Inject constructor() :
    MenuRemoteDataSource {
    @FlowPreview
    override suspend fun getAllFoods(): Flow<List<Food>> {
        return flowViaChannel { flowChannel ->
            flowChannel.sendBlocking(arrayListOf())
        }
    }
}