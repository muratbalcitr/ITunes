package com.m2lifeApps.data.remote.request

data class ITuneRequest(
    val term: String,
    val limit: Int?,
    val offset: Int? = null,
    val media: String? = null
)
