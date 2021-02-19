package com.example.yemeksiparisuygulamasi.domain.usecase

import android.content.Context
import com.example.yemeksiparisuygulamasi.domain.entity.Basket
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import com.example.yemeksiparisuygulamasi.domain.repository.BasketRepository
import com.example.yemeksiparisuygulamasi.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveFoodFromBasketUseCase @Inject constructor(private val repository: BasketRepository) {
    suspend operator fun invoke(context: Context, foodFromBasket: Basket): Flow<ResultData<Unit>> {
        return repository.removeBasket(context,foodFromBasket)
    }
}