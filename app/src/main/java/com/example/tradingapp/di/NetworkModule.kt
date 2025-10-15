package com.example.tradingapp.di

import com.example.tradingapp.data.remote.api.MarketApiService
import com.example.tradingapp.data.repository.StockRepositoryImpl
import com.example.tradingapp.domain.repository.StockRepository
import com.example.tradingapp.domain.usecase.GetStockPricesUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.polygon.io"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideMarketApiService(retrofit: Retrofit): MarketApiService =
        retrofit.create(MarketApiService::class.java)

    @Provides
    @Singleton
    fun provideStockRepository(api: MarketApiService): StockRepository =
        StockRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetStockPricesUseCase(repo: StockRepository) =
        GetStockPricesUseCase(repo)
}
