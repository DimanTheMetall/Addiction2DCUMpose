package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

class MangaListReceive(
    @SerializedName("data")
    val mangaData: List<MangaData>,
    @SerializedName("pagination")
    val pagination: Pagination
)
