package com.tudor.weatherappcompose.data.remote.responses


import com.google.gson.annotations.SerializedName

data class Stations(
    @SerializedName("D5621")
    val d5621: D5621,
    @SerializedName("EGLC")
    val eGLC: EGLC,
    @SerializedName("EGLL")
    val eGLL: EGLL,
    @SerializedName("EGWU")
    val eGWU: EGWU,
    @SerializedName("F6665")
    val f6665: F6665
)