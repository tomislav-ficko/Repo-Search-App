package hr.ficko.reposearch.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryResponseModel(
    @Json(name = "total_count") val totalRepoCount: Int,
    @Json(name = "items") val repoList: List<Repository>
)

@JsonClass(generateAdapter = true)
data class Repository(
    @Json(name = "name") val repoName: String,
    @Json(name = "updated_at") val updatedAt: String,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "description") val description: String?
)

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "login") val ownerName: String
)