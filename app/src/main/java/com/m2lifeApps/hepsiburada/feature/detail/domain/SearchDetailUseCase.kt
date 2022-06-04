package com.m2lifeApps.hepsiburada.feature.detail.domain

import com.m2lifeApps.data.repository.ApiRepository
import javax.inject.Inject

class SearchDetailUseCase @Inject constructor(
    val apiRepository: ApiRepository
)
