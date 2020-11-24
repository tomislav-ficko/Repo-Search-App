package hr.ficko.reposearch.data.interactors

import androidx.lifecycle.LiveData
import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel

interface Interactors {
    interface RepositoryInteractor :
        BaseInteractor<RepositoryRequestModel, LiveData<RepositoryResponseModel>>
}