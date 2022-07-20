package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

class MangaItemReceive(
    @SerializedName("mal_id")
    val id: Long,
    @SerializedName("name")
    val name: String?
)


