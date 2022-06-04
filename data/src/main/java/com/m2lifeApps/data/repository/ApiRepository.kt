package com.m2lifeApps.data.repository

import com.m2lifeApps.data.Resource
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.data.utils.toObservable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiDataSource: ApiDataSource
) {
    fun searchMovies(
        term: String,
        limit: Int? = 30,
        offset: Int? = null,
        media: String? = null
    ): Observable<Resource<ITuneResponse>> {
        return Observable.create { emitter ->
            apiDataSource.searchMovies(term, limit, offset, media)
                .toObservable(emitter)
        }
    }
}
