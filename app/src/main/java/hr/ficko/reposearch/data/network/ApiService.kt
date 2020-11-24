package hr.ficko.reposearch.data.network

import androidx.lifecycle.LiveData
import hr.ficko.reposearch.data.models.RepositoryResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

@Dao
interface RepositoryDao {

    @GET("/search/repositories")
    fun getRepositorySearchData(
        @Query("q") repoName: String
    ): LiveData<RepositoryResponseModel>
}