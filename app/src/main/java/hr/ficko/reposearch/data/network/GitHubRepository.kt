package hr.ficko.reposearch.data.network

import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import hr.ficko.reposearch.other.ResponseValues.SORTED
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException

class GitHubRepository {

    private val apiService: ApiService = AppModule.getApiService()

    fun execute(request: RepositoryRequestModel): Response<RepositoryResponseModel>? {
        return try {
            val call = if (request.sortStatus == SORTED) {
                apiService.getRepositorySearchDataSortedByDate(
                    request.repoName,
                    request.pageNumber
                )
            } else {
                apiService.getRepositorySearchData(
                    request.repoName,
                    request.pageNumber
                )
            }
            call.execute()
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

