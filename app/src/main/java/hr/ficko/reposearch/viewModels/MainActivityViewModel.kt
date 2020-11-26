package hr.ficko.reposearch.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ficko.reposearch.data.models.Repository
import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import hr.ficko.reposearch.data.network.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivityViewModel @ViewModelInject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    val repoLiveData: MutableLiveData<List<Repository>> = MutableLiveData()

    fun getSearchResults(repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = RepositoryRequestModel(repoName)
            handleResponse(gitHubRepository.execute(request))
        }
    }

    private fun handleResponse(response: Response<RepositoryResponseModel>) {
        response.body()?.let {
            repoLiveData.postValue(it.repoList)
        }
    }

}