package com.m2lifeApps.data.remote

import com.m2lifeApps.data.remote.response.ITuneResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface ProjectService {

    @POST(EndPoints.movies)
    fun searchMovies(
        @Query("term") keyword: String,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int? = null,
        @Query("media") media: String = "all",
    ): Single<ITuneResponse>

    object EndPoints {
        const val movies = "search"
    }
}
