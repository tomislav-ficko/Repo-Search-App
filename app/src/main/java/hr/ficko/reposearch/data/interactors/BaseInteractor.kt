package hr.ficko.reposearch.data.interactors

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import hr.ficko.reposearch.data.models.RepositoryResponseModel

interface BaseInteractor<in RequestType, out ResponseType> {

    @WorkerThread
    fun execute(params: RequestType): LiveData<RepositoryResponseModel>
}