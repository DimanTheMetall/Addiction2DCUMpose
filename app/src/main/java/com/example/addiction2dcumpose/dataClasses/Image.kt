package com.example.addiction2dcumpose.dataClasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Images(
    @SerializedName("jpg")
    val jpg: JPGImage
):Parcelable

@Parcelize
class JPGImage(
    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("small_image_url")
    val smallImageUrl: String,

    @SerializedName("large_image_url")
    val largeImageUrl: String
):Parcelable