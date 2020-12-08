package hr.ficko.reposearch.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.ficko.reposearch.BuildConfig
import hr.ficko.reposearch.data.network.ApiService
import hr.ficko.reposearch.ui.MainAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        @ApiUrl apiUrl: String,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun okHttpClient(loggingInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .build()

    @Singleton
    @Provides
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun moshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun loggingInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        Timber.d(String.format("Sending request to " + request.url))

        val response = chain.proceed(request)
        Timber.d(String.format("Response: " + response.code + " " + response.message))

        response
    }

    @Singleton
    @Provides
    fun mainAdapter(): MainAdapter = MainAdapter()

    @Provides
    @Singleton
    @ApiUrl
    fun apiUrl(): String = BuildConfig.API_URL
}
