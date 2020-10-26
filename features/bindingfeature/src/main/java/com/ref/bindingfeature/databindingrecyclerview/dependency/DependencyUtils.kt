package com.ref.bindingfeature.databindingrecyclerview.dependency

import colruyt.android.dsa.rayon.network.GitUserServiceAPI
import colruyt.android.dsa.rayon.network.NetworkUtil
import com.ref.bindingfeature.databindingrecyclerview.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object DependencyUtils {

    private fun provideNetworkURL(): String = NetworkUtil.NETWORK_BASE_URL

    private fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY)

    private fun provideOKHttp(logger: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        with(okHttpClient) {
            addInterceptor(logger)
            callTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        }
        return okHttpClient.build()
    }


     private fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(provideNetworkURL()).client(provideOKHttp(
            provideHttpLogger())).addConverterFactory(GsonConverterFactory.create())
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient().build())
            .build()

    }

    private fun provideAPIService() :GitUserServiceAPI{

       return  provideRetrofitInstance().create(GitUserServiceAPI::class.java)
    }

    fun provideUserRepository() = UserRepository(provideAPIService())

}