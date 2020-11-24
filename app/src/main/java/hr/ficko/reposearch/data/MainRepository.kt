package hr.ficko.reposearch.data

import com.squareup.moshi.Moshi
import hr.ficko.reposearch.data.network.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainRepository {

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .client(okHttpClient())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    fun moshi(): Moshi = Moshi.Builder().build()
}