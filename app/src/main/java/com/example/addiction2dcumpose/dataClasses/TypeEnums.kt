package com.example.addiction2dcumpose.dataClasses

enum class ScoreDialogType(val scoreName: String) {
    SCORE("Score"), MAX_SCORE("Max score"), MIN_SCORE("Min score");
}

enum class DateDialogType(val dateName: String) {
    START_DATE("Start date"), END_DATE("End date")
}

enum class MangaItemType(val typeName: String) {
    GENRES("genres"), AUTHORS("authors"), THEMES("themes"), SERIALIZATIONS("serializations")
}