package hr.ficko.reposearch.data.interactors

import androidx.lifecycle.LiveData
import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import hr.ficko.reposearch.data.network.ApiService
import javax.inject.Inject

class RepositoryInteractor @Inject constructor(
    private val apiService: ApiService
) : Interactors.RepositoryInteractor {

    override fun execute(params: RepositoryRequestModel): LiveData<RepositoryResponseModel> =
        apiService.getRepositorySearchData(
            repoName = params.repoName
        )
}