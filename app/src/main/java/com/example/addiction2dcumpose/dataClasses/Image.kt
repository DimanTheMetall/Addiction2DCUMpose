package com.example.addiction2dcumpose.dataClasses

import com.google.gson.annotations.SerializedName

class Images(
    @SerializedName("jpg")
    val jpg: JPGImage
)


class JPGImage(
    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("small_image_url")
    val smallImageUrl: String,

    @SerializedName("large_image_url")
    val largeImageUrl: String
)