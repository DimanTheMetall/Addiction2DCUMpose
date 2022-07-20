package com.example.addiction2dcumpose.dataClasses

import com.example.addiction2dcumpose.Constants

data class SearchSettings(
    val page: Int = 1,
    val limit: Int = Constants.ON_PAGE_LIMIT,
    val q: Int? = null,
    val type: MangaType? = null,
    val score: Int? = null,
    val minScore: Int? = null,
    val maxScore: Int? = null,
    val status: MangaStatus? = null,
    val sfw: Boolean = false,
    val genresInclude: List<Genre>? = null,
    val genresExclude: List<Genre>? = null,
    val sort: Sort? = null,
    val startDate: SearchDate? = null,
    val endDate: SearchDate? = null,
    val letters: String = ""
)
