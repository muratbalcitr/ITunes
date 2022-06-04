package com.m2lifeApps.hepsiburada.feature.detail.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.hepsiburada.core.extensions.Event
import com.m2lifeApps.hepsiburada.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    val useCase: SearchDetailUseCase
) : BaseViewModel() {

    private val _iTuneDetail = MutableLiveData<ITuneResponse.Result>()
    val iTuneDetail: LiveData<ITuneResponse.Result> = _iTuneDetail

    private val _event = MutableLiveData<Event<SearchDetailViewEvent>>()
    val event: LiveData<Event<SearchDetailViewEvent>> = _event

     fun setDetail(value: ITuneResponse.Result) = _iTuneDetail.postValue(value)
}
