package hr.ficko.reposearch.data.interactors

import androidx.annotation.WorkerThread

interface BaseInteractor<in RequestType, out ResponseType> {

    @WorkerThread
    fun execute(params: RequestType): ResponseType
}