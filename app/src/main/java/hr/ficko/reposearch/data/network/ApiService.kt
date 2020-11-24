package hr.ficko.reposearch.data.network

import hr.ficko.reposearch.data.models.RepositoryResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    fun getRepositorySearchData(
        @Query("q") repoName: String
    ): RepositoryResponseModel
}