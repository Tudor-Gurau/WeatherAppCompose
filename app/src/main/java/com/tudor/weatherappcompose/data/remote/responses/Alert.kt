package com.tudor.weatherappcompose.data.remote.responses


import com.google.gson.annotations.SerializedName

data class Alert(
    @SerializedName("description")
    val description: String,
    @SerializedName("ends")
    val ends: String,
    @SerializedName("endsEpoch")
    val endsEpoch: Int,
    @SerializedName("event")
    val event: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("onset")
    val onset: String,
    @SerializedName("onsetEpoch")
    val onsetEpoch: Int
)