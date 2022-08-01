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
    val sfw: Boolean = false,
    val genresInclude: List<Genre> = mutableListOf(),
    val genresExclude: List<Genre> = mutableListOf(),
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchSettings

        if (page != other.page) return false
        if (limit != other.limit) return false
        if (q != other.q) return false
        if (type != other.type) return false
        if (score != other.score) return false
        if (minScore != other.minScore) return false
        if (maxScore != other.maxScore) return false
        if (status != other.status) return false
        if (sfw != other.sfw) return false
        if (!genresInclude.deepEquals(other = other.genresInclude)) return false
        if (!genresExclude.deepEquals(other = other.genresExclude)) return false
        if (sort != other.sort) return false
        if (orderBy != other.orderBy) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (letters != other.letters) return false

        return true
    }

    override fun hashCode(): Int {
        var result = page
        result = 31 * result + limit
        result = 31 * result + (q?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (score ?: 0)
        result = 31 * result + (minScore ?: 0)
        result = 31 * result + (maxScore ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + sfw.hashCode()
        result = 31 * result + (genresInclude?.hashCode() ?: 0)
        result = 31 * result + (genresExclude?.hashCode() ?: 0)
        result = 31 * result + (sort?.hashCode() ?: 0)
        result = 31 * result + (orderBy?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + letters.hashCode()
        return result
    }


}

fun <T>List<T>.deepEquals(other: List<T>): Boolean = this.size==other.size && this.containsAll(other)



