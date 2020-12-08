package hr.ficko.reposearch.data.network

import hr.ficko.reposearch.data.models.RepositoryResponseModel
import hr.ficko.reposearch.other.Constants.PAGE_SIZE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    fun getRepositorySearchData(
        @Query("q") repoName: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsPerPage: Int = PAGE_SIZE
    ): Call<RepositoryResponseModel>

    @GET("/search/repositories")
    fun getRepositorySearchDataSortedByDate(
        @Query("q") repoNameAndSortingQualifier: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsPerPage: Int = PAGE_SIZE,
        @Query("sort") sortBy: String = "updated"
    ): Call<RepositoryResponseModel>

}