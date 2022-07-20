package com.example.addiction2dcumpose.States

import com.example.addiction2dcumpose.dataClasses.MangaData


sealed class MangaResultState {
    object Progress: MangaResultState()
    object Error: MangaResultState()
    class Success(val mangaData: MangaData): MangaResultState()
}
