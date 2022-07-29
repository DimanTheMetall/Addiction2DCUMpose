package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("mal_id")
    val id: Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("count")
    val count: Long
)
