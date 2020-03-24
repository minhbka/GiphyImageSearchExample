package com.minhbka.giphyimagesearchexample.network.responses


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("banner_image")
    val bannerImage: String,
    @SerializedName("banner_url")
    val bannerUrl: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("profile_url")
    val profileUrl: String,
    val username: String
)