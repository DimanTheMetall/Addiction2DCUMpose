package com.example.addiction2dcumpose.dataClasses

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val authors: List<MangaItemReceive>?,
    @SerializedName("themes")
    val themes: List<MangaItemReceive>?,
    @SerializedName("serializations")
    val serializations: List<MangaItemReceive>?
) : Parcelable {
    companion object MangaDataArgument : NavType<MangaData>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): MangaData? {
            return bundle.getParcelable(key)
        }

        override fun parseValue(value: String): MangaData {
            return Gson().fromJson(value, MangaData::class.java)
        }

        override fun put(bundle: Bundle, key: String, value: MangaData) {
            bundle.putParcelable(key, value)
        }
    }
}

