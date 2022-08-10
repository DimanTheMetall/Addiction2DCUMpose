package com.example.addiction2dcumpose.dataClasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class MangaItemReceive(
    @SerializedName("mal_id")
    val id: Long,
    @SerializedName("name")
    val name: String
): Parcelable


