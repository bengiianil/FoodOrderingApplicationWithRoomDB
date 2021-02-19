package com.example.yemeksiparisuygulamasi.domain.usecase

import android.content.Context
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import com.example.yemeksiparisuygulamasi.domain.repository.BasketRepository
import com.example.yemeksiparisuygulamasi.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFoodToBasketUseCase @Inject constructor(private val repository: BasketRepository) {
    suspend operator fun invoke(context: Context, food: Food, counter: Int): Flow<ResultData<Unit>> {
        return repository.addBasket(context, food, counter)
    }
}