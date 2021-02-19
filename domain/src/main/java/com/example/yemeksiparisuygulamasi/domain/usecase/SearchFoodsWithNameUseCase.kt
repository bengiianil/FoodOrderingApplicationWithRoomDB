package com.example.yemeksiparisuygulamasi.domain.usecase

import android.content.Context
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import com.example.yemeksiparisuygulamasi.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodsWithNameUseCase @Inject constructor(private val repository: MenuRepository) {
    operator fun invoke(context: Context, keyWord: String): Flow<ResultData<List<Food>>> {
        return repository.searchFood(context,keyWord)
    }
}