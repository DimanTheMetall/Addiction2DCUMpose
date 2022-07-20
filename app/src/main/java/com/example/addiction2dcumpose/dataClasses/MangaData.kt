package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

data class MangaData(
    @SerializedName("mal_id")
    val malId: Long,
    @SerializedName("images")
    val images: Images?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("chapters")
    val chapters: Int?,
    @SerializedName("volumes")
    val volumes: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("genres")
    val genres: List<MangaItemReceive>?,
    @SerializedName("authors")
    val authors: List<MangaItemReceive>?
)
