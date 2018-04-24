package com.taurus.hackweekdemo.network

import com.taurus.hackweekdemo.BuildConfig
import com.taurus.hackweekdemo.core.extensions.debug
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal class NetworkModule {

    companion object {
        const val CONNECT_TIME_OUT = 10L
        const val READ_TIME_OUT = 15L
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okhttpBuilder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        debug { okhttpBuilder.addInterceptor(loggingInterceptor) }
        return okhttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.API_URL)
                    .build()

    @Provides
    @Singleton
    fun provideCarFeedsGuidService(retrofit: Retrofit) = retrofit.create(CarFeedsGuidService::class.java)

    @Provides
    @Singleton
    fun provideDummyPhotoProvider(): DummyPhotoProvider = DummyPhotoProviderImpl()

    @Provides
    @Singleton
    fun provideWatchlistService(
            guidService: CarFeedsGuidService,
            dummyPhotoProvider: DummyPhotoProvider
    ) = CarFeedsService(guidService, dummyPhotoProvider)

}
