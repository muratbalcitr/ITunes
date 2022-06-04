package com.m2lifeApps.hepsiburada.feature.main.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m2lifeApps.data.remote.request.ITuneRequest
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.data.repository.ApiRepository
import io.reactivex.rxjava3.schedulers.Schedulers

class ITuneItemDataSource(
    private val dataRepository: ApiRepository
) : PagingSource<ITuneRequest, ITuneResponse>() {
    override fun getRefreshKey(state: PagingState<ITuneRequest, ITuneResponse>): ITuneRequest? {
        return null
    }

    override suspend fun load(params: LoadParams<ITuneRequest>): LoadResult<ITuneRequest, ITuneResponse> {
        return try {
            var data: ITuneResponse? = null
              dataRepository
                .searchMovies(
                    params.key?.term.toString(),
                    params.key?.limit,
                    params.key?.offset,
                    params.key?.media,
                ).subscribeOn(Schedulers.io())
                .map { movies ->
                    data = movies.data!!
                }
            return LoadResult.Page(data = listOf(data!!),null,null)
        } catch (e: Exception) {
            LoadResult.Error(e.cause!!)
        }
    }

    // Method to map Movies to LoadResult object
    private fun toLoadResult(movies: ITuneResponse) =
        LoadResult.Page<ITuneRequest, ITuneResponse>(listOf(movies), null, null)
}
