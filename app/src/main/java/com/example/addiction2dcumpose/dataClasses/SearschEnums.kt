package com.example.addiction2dcumpose.dataClasses

enum class MangaType(val typeName: String) {
    MANGA("manga"), NOVEL("novel"), LIGHT_NOVEL("lightnovel"),
    ONESHOT("oneshot"), DOUJIN("doujin"), MANHWA("manhwa"),
    MANHUA("manhua")

}
enum class MangaStatus(val statusName: String) {
    PUBLISHING("publishing"), COMPLETE ("complete"), HIATUS("hiatus"),
    DISCONTINUED("discontinued"), UPCOMING("upcoming")
}

enum class OrderBy (val orderedName: String){
    MAI_ID("mal_id"), TITLE("title"), START_DATE("start_date"),
    END_DATE("end_date"), CHAPTERS("chapters"), VOLUMES("volumes"),
    SCORE("score"), SCORED_BY("scored_by"), RANK("rank"),
    POPULARITY("popularity"), MEMBERS("members"), FAVORITES("favorites")
}

enum class Sort(val sortName: String){
    DESC("desc"), ASC("asc")
}







