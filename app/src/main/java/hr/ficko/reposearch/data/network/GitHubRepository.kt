package hr.ficko.reposearch.data.network

import com.squareup.moshi.Moshi
import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class GitHubRepository {

    private val apiService: ApiService

    init {
        apiService = getApiService()
    }

    fun execute(requestModel: RepositoryRequestModel): Response<RepositoryResponseModel>? {
        return try {
            apiService.getRepositorySearchData(
                repoName = requestModel.repoName,
                pageNumber = requestModel.pageNumber
            ).execute()
        } catch (e: UnknownHostException) {
            Timber.d("No internet connection!")
            Timber.d("Exception: " + e)
            null
        } catch (e: Exception) {
            Timber.d("Exception: " + e)
            throw e
        }
    }

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
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .build()

    private fun moshi(): Moshi = Moshi.Builder().build()
}

