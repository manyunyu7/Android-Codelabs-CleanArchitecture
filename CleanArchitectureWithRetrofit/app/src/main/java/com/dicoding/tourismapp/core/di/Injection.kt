package com.dicoding.tourismapp.core.di

import android.content.Context

import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import com.dicoding.tourismapp.core.data.source.local.room.TourismDatabase

import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.data.source.remote.RemoteDataSource
import com.dicoding.tourismapp.core.data.source.remote.network.ApiConfig
import com.dicoding.tourismapp.core.domain.ITourismRepository
import com.dicoding.tourismapp.core.domain.TourismInteractor
import com.dicoding.tourismapp.core.domain.TourismUseCase
import com.dicoding.tourismapp.core.utils.AppExecutors
import com.dicoding.tourismapp.core.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): ITourismRepository {
        val database = TourismDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.tourismDao())
        val appExecutors = AppExecutors()

        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideUseCase(context: Context) : TourismUseCase{
        val repository = provideRepository(context)
        return TourismInteractor(repository)
    }
}
