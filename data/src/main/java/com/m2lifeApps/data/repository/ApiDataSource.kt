package com.m2lifeApps.data.repository

import com.m2lifeApps.data.remote.ProjectService
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val projectService: ProjectService
) {

    fun searchMovies(
        keyword: String,
        limit: Int? = null,
        offset: Int? = null,
        media: String? = null,

    ) =
        projectService.searchMovies(keyword, limit, offset, media.toString())
}
