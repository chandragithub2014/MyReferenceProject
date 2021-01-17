package com.ref.hiltfeaturemodule.di


import com.ref.hiltfeaturemodule.network.NetworkUtil
import com.ref.hiltfeaturemodule.network.PlaceHolderServiceAPI
import com.ref.hiltfeaturemodule.repository.CoroutineHelper
import com.ref.hiltfeaturemodule.repository.PlaceHolderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(ActivityComponent::class)
@Module
object NetworkModule {


    @Provides
    fun  provideNetworkURL():String = NetworkUtil.NETWORK_BASE_URL

    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOKHttp(logger: HttpLoggingInterceptor) : OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        with(okHttpClient){
            addInterceptor(logger)
            callTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        }
        return okHttpClient.build()

    }

    @Provides
    fun  provideRetrofit(baseUrl : String,okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(
        GsonConverterFactory.create()).build()

    @Provides
    fun provideAPIService(retrofit: Retrofit): PlaceHolderServiceAPI = retrofit.create(PlaceHolderServiceAPI::class.java)


    @Provides
    fun provideCoroutineDispatcher() = CoroutineHelper().fetchCoroutineDispatcher()

    @Provides
    fun provideRepository(apiService: PlaceHolderServiceAPI) = PlaceHolderRepository(apiService)
}