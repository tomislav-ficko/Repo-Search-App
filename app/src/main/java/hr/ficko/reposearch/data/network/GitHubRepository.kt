package hr.ficko.reposearch.data.network

import com.squareup.moshi.Moshi
import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GitHubRepository {

    private val apiService: ApiService

    init {
        apiService = getApiService()
    }

    fun execute(requestModel: RepositoryRequestModel): Response<RepositoryResponseModel> =
        apiService.getRepositorySearchData(
            repoName = requestModel.repoName
        ).execute()

    private fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .client(okHttpClient())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()

    private fun moshi(): Moshi = Moshi.Builder().build()
}