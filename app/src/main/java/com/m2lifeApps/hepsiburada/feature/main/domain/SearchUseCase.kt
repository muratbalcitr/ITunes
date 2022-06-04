package com.m2lifeApps.hepsiburada.feature.main.domain

import androidx.paging.map
import com.m2lifeApps.data.Resource
import com.m2lifeApps.data.remote.request.ITuneRequest
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.data.repository.ApiRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    val apiRepository: ApiRepository
) {
    fun searchMovies(
        term: String,
        limit: Int? = 30,
        offset: Int? = null,
        media: String? = null,
    ): Observable<Resource<ITuneResponse>> {
        return apiRepository.searchMovies(
            term, limit, offset, media
        ).subscribeOn(Schedulers.io())
    }
}
