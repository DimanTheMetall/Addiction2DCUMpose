package com.example.addiction2dcumpose.dataClasses


sealed class MangaResult {
    object Progress: MangaResult()
    object Error: MangaResult()
    class Success(val mangaData: MangaData): MangaResult()
}
