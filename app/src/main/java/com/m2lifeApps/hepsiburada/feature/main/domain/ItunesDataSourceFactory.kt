package com.m2lifeApps.hepsiburada.feature.main.domain

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.m2lifeApps.data.remote.request.ITuneRequest
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.data.repository.ApiRepository
import javax.inject.Inject

class ItunesDataSourceFactory @Inject constructor(
    private val dataRepository: ApiRepository
) {

    fun getItuneList(pageSize: ITuneRequest): LiveData<PagingData<ITuneResponse>> {
         return Pager(
            config = PagingConfig(
                pageSize = pageSize.limit?:20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ITuneItemDataSource(dataRepository) }
        ).liveData
    }
}
