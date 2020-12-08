package hr.ficko.reposearch.data.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .client(okHttpClient())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .build()

    fun moshi(): Moshi = Moshi.Builder().build()
}