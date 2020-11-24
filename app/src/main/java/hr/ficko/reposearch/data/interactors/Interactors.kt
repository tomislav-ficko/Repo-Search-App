package hr.ficko.reposearch.data.interactors

import hr.ficko.reposearch.data.models.RepositoryRequestModel
import hr.ficko.reposearch.data.models.RepositoryResponseModel

interface Interactors {
    interface RepositoryInteractor : BaseInteractor<RepositoryRequestModel, RepositoryResponseModel>
}