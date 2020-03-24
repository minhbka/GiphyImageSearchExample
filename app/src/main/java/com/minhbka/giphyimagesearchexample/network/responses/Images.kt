package com.minhbka.giphyimagesearchexample.network.responses


import com.google.gson.annotations.SerializedName

data class Images(
    val downsized: ImageObject,
    @SerializedName("downsized_large")
    val downsizedLarge: ImageObject,
    @SerializedName("downsized_medium")
    val downsizedMedium: ImageObject,
    @SerializedName("downsized_small")
    val downsizedSmall: ImageObject,
    @SerializedName("downsized_still")
    val downsizedStill: ImageObject,
    @SerializedName("fixed_height")
    val fixedHeight: ImageObject,
    @SerializedName("fixed_height_downsampled")
    val fixedHeightDownsampled: ImageObject,
    @SerializedName("fixed_height_small")
    val fixedHeightSmall: ImageObject,
    @SerializedName("fixed_height_small_still")
    val fixedHeightSmallStill: ImageObject,
    @SerializedName("fixed_height_still")
    val fixedHeightStill: ImageObject,
    @SerializedName("fixed_width")
    val fixedWidth: ImageObject,
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: ImageObject,
    @SerializedName("fixed_width_small")
    val fixedWidthSmall: ImageObject,
    @SerializedName("fixed_width_small_still")
    val fixedWidthSmallStill: ImageObject,
    @SerializedName("fixed_width_still")
    val fixedWidthStill: ImageObject,
    val original: ImageObject

)