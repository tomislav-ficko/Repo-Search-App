package hr.ficko.reposearch.data.network

import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException

class GitHubRepository {

    private val apiService: ApiService = AppModule.getApiService()

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
}

