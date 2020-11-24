package hr.ficko.reposearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hr.ficko.reposearch.data.MainRepository
import hr.ficko.reposearch.data.interactors.Interactors
import hr.ficko.reposearch.data.interactors.RepositoryInteractor
import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel

class MainActivityViewModel @Inject constructor(
    val mainRepository: MainRepository,
    val repositoryInteractor: RepositoryInteractor
) : BaseViewModel<RepositoryState>() {

    private fun getRepositories(repoName: String) {
        val request = RepositoryRequestModel(repoName)
        handleResponse(repositoryInteractor.execute(request))
    }

    private fun handleResponse(response: LiveData<RepositoryResponseModel>) {

    }

}