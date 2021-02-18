package com.example.yemeksiparisuygulamasi.di

import com.example.yemeksiparisuygulamasi.data.datasource.MenuRemoteDataSource
import com.example.yemeksiparisuygulamasi.data.datasource.remote.MenuRemoteDataSourceImpl
import com.example.yemeksiparisuygulamasi.data.repository.MenuRepositoryImpl
import com.example.yemeksiparisuygulamasi.domain.repository.MenuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesMenuRepository(
        remoteDataSource: MenuRemoteDataSource
    ): MenuRepository {
        return MenuRepositoryImpl(
            remoteDataSource
        )
    }

    @Provides
    fun providesMenuRemoteDataSource(): MenuRemoteDataSource {
        return MenuRemoteDataSourceImpl()
    }
}