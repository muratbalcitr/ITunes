package com.m2lifeApps.hepsiburada.feature.main.domain

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.m2lifeApps.data.Status
import com.m2lifeApps.data.remote.request.ITuneRequest
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.hepsiburada.core.common.SearchPositionalDataSource
import com.m2lifeApps.hepsiburada.core.common.UiThreadExecutor
import com.m2lifeApps.hepsiburada.core.extensions.Event
import com.m2lifeApps.hepsiburada.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val useCase: SearchUseCase,
    val factory: ItunesDataSourceFactory
) : BaseViewModel() {

    private val _iTuneResponse = MutableLiveData<ITuneResponse>()
    val iTuneResponse: LiveData<ITuneResponse> = _iTuneResponse

    private val _event = MutableLiveData<Event<SearchViewEvent>>()
    val event: LiveData<Event<SearchViewEvent>> = _event

    private val _isMoreLoading = MutableLiveData<Boolean>()
    val isMoreLoading: LiveData<Boolean> = _isMoreLoading

    val term = MutableLiveData<String>("star")
    val take = MutableLiveData(20)
    var searchResponsePagedLiveData = MutableLiveData<PagedList<ITuneResponse.Result>>()

    fun setMoreLoading(value: Boolean) = _isMoreLoading.postValue(value)

    init {

    }

    fun searchMovies(
        request: ITuneRequest
    ) {
        val response = factory.getItuneList(request)
        Timber.e(response.value.toString())
    }

    fun searchMovies(
        term: String,
        limit: Int,
        offset: Int? = null,
        media: String? = null
    ) {
        setLoading(true)
        useCase.searchMovies(term, limit, offset, media)
            .subscribe {

                when (it.status) {
                    Status.SUCCESS -> {
                        setLoading(false)
                        setMoreLoading(false)
                        it.data.let { res ->
                            _iTuneResponse.postValue(res)
                        }
                        it.data?.results?.apply {

                            if (isNotEmpty()) {
                                val pagedListConfig = PagedList.Config.Builder()
                                    .setPageSize(limit)
                                    .build()

                                val pagedList = PagedList.Builder(
                                    SearchPositionalDataSource(this),
                                    pagedListConfig
                                )
                                    .setNotifyExecutor(UiThreadExecutor())
                                    .setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                                    .build()

                                searchResponsePagedLiveData.postValue(pagedList)
                            }
                        }
                    }
                    Status.ERROR -> {
                        setLoading(false)
                        setMoreLoading(false)
                        Timber.e(it.error)
                    }
                    Status.LOADING -> {
                    }
                }
            }.let {
                disposable.add(it)
            }
    }

    fun selectMovies(item: ITuneResponse.Result) {
        _event.postValue(Event(SearchViewEvent.SelectItem(item)))
    }
}
