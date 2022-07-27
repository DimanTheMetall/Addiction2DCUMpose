package com.example.addiction2dcumpose.dataClasses

interface SettingsType {
    val settingsName: String
    fun getTypeName(): String
}

enum class MangaType(override val settingsName: String) : SettingsType {
    MANGA("manga"), NOVEL("novel"), LIGHT_NOVEL("lightnovel"),
    ONESHOT("oneshot"), DOUJIN("doujin"), MANHWA("manhwa"),
    MANHUA("manhua");

    override fun getTypeName(): String = "Type"
}

enum class MangaStatus(override val settingsName: String): SettingsType {
    PUBLISHING("publishing"), COMPLETE("complete"), HIATUS("hiatus"),
    DISCONTINUED("discontinued"), UPCOMING("upcoming");

    override fun getTypeName(): String = "Status"
}

enum class OrderBy(override val settingsName: String): SettingsType {
    MAI_ID("mal_id"), TITLE("title"), START_DATE("start_date"),
    END_DATE("end_date"), CHAPTERS("chapters"), VOLUMES("volumes"),
    SCORE("score"), SCORED_BY("scored_by"), RANK("rank"),
    POPULARITY("popularity"), MEMBERS("members"), FAVORITES("favorites");

    override fun getTypeName(): String = "Order By"
}

enum class Sort(override val settingsName: String): SettingsType {
    DESC("desc"), ASC("asc");

    override fun getTypeName(): String = "Sort"
}

enum class GenresFilter(override val settingsName: String): SettingsType {
    GENRES("genres"), EXPLICIT_GENRES("explicit_genres"), THEMES("themes"),
    DEMOGRAPHICS("demographics");

    override fun getTypeName(): String = "Genres Filter"
}







