package com.example.yemeksiparisuygulamasi.di

import com.example.yemeksiparisuygulamasi.domain.repository.BasketRepository
import com.example.yemeksiparisuygulamasi.domain.repository.MenuRepository
import com.example.yemeksiparisuygulamasi.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun providesGetAllFoodsUseCase(repository: MenuRepository): GetAllFoodsUseCase {
        return GetAllFoodsUseCase(repository)
    }

    @Provides
    fun providesSearchFoodWithKeywordUseCase(repository: MenuRepository): SearchFoodsWithNameUseCase {
        return SearchFoodsWithNameUseCase(repository)
    }

    @Provides
    fun providesAddFoodToBasketUseCase(repository: BasketRepository): AddFoodToBasketUseCase {
        return AddFoodToBasketUseCase(repository)
    }

    @Provides
    fun providesRemoveFoodFromBasketUseCase(repository: BasketRepository): RemoveFoodFromBasketUseCase {
        return RemoveFoodFromBasketUseCase(repository)
    }

    @Provides
    fun providesGetAllFoodsFromBasketUseCase(repository: BasketRepository): GetFoodsFromBasketUseCase {
        return GetFoodsFromBasketUseCase(repository)
    }
}