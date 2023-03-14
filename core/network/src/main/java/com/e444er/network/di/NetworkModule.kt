package com.e444er.network.di

import com.e444er.network.AuthenticationInterceptor
import com.e444er.network.BASE_URL
import com.e444er.network.BuildConfig
import com.e444er.network.NetworkDataSource
import com.e444er.network.retrofit.MovieRetrofitApi
import com.e444er.network.retrofit.RetrofitNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getAuthInterceptor(): Interceptor =
        AuthenticationInterceptor(BuildConfig.TMBD_API_KEY)

    @Provides
    @Singleton
    fun getOkHttpClient(interceptor: Interceptor): OkHttpClient
            = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun getRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    internal fun movieApi(
        retrofit: Retrofit
    ): MovieRetrofitApi = retrofit.create(MovieRetrofitApi::class.java)

    @Provides
    internal fun retrofitNetwork(
        movieRetrofitApi: MovieRetrofitApi
    ) : NetworkDataSource = RetrofitNetwork(movieRetrofitApi)
}