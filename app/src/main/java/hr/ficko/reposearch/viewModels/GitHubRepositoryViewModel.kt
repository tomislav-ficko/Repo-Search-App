package hr.ficko.reposearch.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ficko.reposearch.data.models.Repository
import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import hr.ficko.reposearch.data.network.GitHubRepository
import hr.ficko.reposearch.other.Constants.PAGE_SIZE
import hr.ficko.reposearch.other.ResponseValues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import kotlin.math.ceil

class GitHubRepositoryViewModel @ViewModelInject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    val networkErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val repoLiveData: MutableLiveData<List<Repository>> = MutableLiveData()
    val totalPagesLiveData: MutableLiveData<Int> = MutableLiveData()
    private var repoList: MutableList<Repository> = mutableListOf()
    private var lastSearchTerm: String = ""

    fun getSearchResults(repoName: String, pageNumber: Int, sortStatus: ResponseValues) {
        if (isNewSearchTerm(repoName)) {
            emptyPreviousResults()
            lastSearchTerm = repoName
        }
        val request = RepositoryRequestModel(repoName, pageNumber, sortStatus)
        executeRequestOnBackgroundThread(request)
    }

    private fun isNewSearchTerm(newSearchTerm: String): Boolean {
        if (lastSearchTerm.isEmpty() || lastSearchTerm != newSearchTerm) {
            return true
        }
        return false
    }

    private fun emptyPreviousResults() {
        repoList = mutableListOf()
    }

    private fun executeRequestOnBackgroundThread(request: RepositoryRequestModel) {
        viewModelScope.launch(Dispatchers.IO) {
            handleResponse(
                gitHubRepository.execute(request)
            )
        }
    }

    private fun handleResponse(response: Response<RepositoryResponseModel>?) {
        if (response == null) {
            networkErrorLiveData.postValue(true)
        } else {
            response.body()?.let {
                totalPagesLiveData.postValue(calculateTotalPages(it.totalRepoCount))
                repoList.addAll(it.repoList)
                repoLiveData.postValue(repoList)
            }
        }
    }

    private fun calculateTotalPages(totalRepoCount: Int): Int {
        val totalPages = ceil(totalRepoCount.toDouble() / PAGE_SIZE).toInt()
        Timber.d("%d pages available", totalPages)
        return totalPages
    }
}