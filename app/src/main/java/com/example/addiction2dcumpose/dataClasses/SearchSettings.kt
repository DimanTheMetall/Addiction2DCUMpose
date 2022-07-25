package com.example.addiction2dcumpose.dataClasses

import com.example.addiction2dcumpose.Constants

data class SearchSettings(
    val page: Int = 1,
    val limit: Int = Constants.ON_PAGE_LIMIT,
    val q: String? = null,
    val type: MangaType? = null,
    val score: Int? = null,
    val minScore: Int? = null,
    val maxScore: Int? = null,
    val status: MangaStatus? = null,
    val sfw: Boolean = true,
    val genresInclude: List<Genre>? = null,
    val genresExclude: List<Genre>? = null,
    val sort: Sort? = null,
    val orderBy: OrderBy? = null,
    val startDate: SearchDate? = null,
    val endDate: SearchDate? = null,
    val letters: String = ""
) {
    fun getIncludeGenresAsString(): String? {
        return genreToString(genresInclude)
    }

    fun getExcludeGenresAsString(): String? {
        return genreToString(genresExclude)
    }

    fun risePage(): SearchSettings {
        return this.copy(page = page + 1)
    }

    private fun genreToString(genreList: List<Genre>?): String? {
        var result: String? = null
        return if (genreList.isNullOrEmpty()) {
            result
        } else {
            genreList.forEachIndexed { index, genre ->
                result += if (index == 0) {
                    genre.id
                } else {
                    ",${genre.id}"
                }
            }
            result
        }
    }
}



