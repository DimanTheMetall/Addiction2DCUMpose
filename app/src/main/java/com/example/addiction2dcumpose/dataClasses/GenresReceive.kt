package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

data class GenresReceive(
    @SerializedName("data")
    val genres: List<Genre>)
