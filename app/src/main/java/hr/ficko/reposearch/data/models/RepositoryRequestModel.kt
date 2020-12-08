package hr.ficko.reposearch.data.models

import hr.ficko.reposearch.other.ResponseValues

data class RepositoryRequestModel(
    val repoName: String,
    val pageNumber: Int,
    val sortStatus: ResponseValues
)