package com.m2lifeApps.hepsiburada.feature.main.domain

import com.m2lifeApps.data.remote.response.ITuneResponse

sealed class SearchViewEvent {
    data class SelectItem(val item: ITuneResponse.Result) : SearchViewEvent()
}
