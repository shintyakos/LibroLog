package com.example.liberolog.api

import com.google.gson.annotations.SerializedName

data class GoogleBookSearchResponse(
    @SerializedName("items")
    val items: List<Item>?,
)

data class Item(
    @SerializedName("id")
    val id: String?,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo?,
    @SerializedName("totalItems")
    val totalItems: Int,
)

data class VolumeInfo(
    @SerializedName("title")
    val title: String?,
    @SerializedName("authors")
    val authors: List<String>?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("publishedDate")
    val publishedDate: String?,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?,
)

data class ImageLinks(
    @SerializedName("smallThumbnail")
    val smallThumbnail: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
)
