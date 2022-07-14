package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

class Genre(
    @SerializedName("mal_id")
    val malId:Long,
    @SerializedName("name")
    val name: String
)
