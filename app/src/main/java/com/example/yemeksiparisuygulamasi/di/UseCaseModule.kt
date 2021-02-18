package com.example.yemeksiparisuygulamasi.di

import com.example.yemeksiparisuygulamasi.domain.repository.MenuRepository
import com.example.yemeksiparisuygulamasi.domain.usecase.GetAllFoodsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun providesGetAllFoodsUseCase(repository: MenuRepository): GetAllFoodsUseCase {
        return GetAllFoodsUseCase(repository)
    }
}