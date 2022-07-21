package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

class Pagination(
    @SerializedName("last_visible_page")
    val listVisiblePage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
)
