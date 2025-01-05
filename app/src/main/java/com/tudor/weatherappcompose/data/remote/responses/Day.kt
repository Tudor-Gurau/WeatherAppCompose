package com.tudor.weatherappcompose.data.remote.responses


import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("cloudcover")
    val cloudcover: Double,
    @SerializedName("conditions")
    val conditions: String,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("datetimeEpoch")
    val datetimeEpoch: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("dew")
    val dew: Double,
    @SerializedName("feelslike")
    val feelslike: Double,
    @SerializedName("feelslikemax")
    val feelslikemax: Double,
    @SerializedName("feelslikemin")
    val feelslikemin: Double,
    @SerializedName("hours")
    val hours: List<Hour>,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("moonphase")
    val moonphase: Double,
    @SerializedName("precip")
    val precip: Double,
    @SerializedName("precipcover")
    val precipcover: Double,
    @SerializedName("precipprob")
    val precipprob: Double,
    @SerializedName("preciptype")
    val preciptype: List<String>,
    @SerializedName("pressure")
    val pressure: Double,
    @SerializedName("severerisk")
    val severerisk: Double,
    @SerializedName("snow")
    val snow: Double,
    @SerializedName("snowdepth")
    val snowdepth: Double,
    @SerializedName("solarenergy")
    val solarenergy: Double,
    @SerializedName("solarradiation")
    val solarradiation: Double,
    @SerializedName("source")
    val source: String,
    @SerializedName("stations")
    val stations: List<String>,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunriseEpoch")
    val sunriseEpoch: Int,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("sunsetEpoch")
    val sunsetEpoch: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("tempmax")
    val tempmax: Double,
    @SerializedName("tempmin")
    val tempmin: Double,
    @SerializedName("uvindex")
    val uvindex: Double,
    @SerializedName("visibility")
    val visibility: Double,
    @SerializedName("winddir")
    val winddir: Double,
    @SerializedName("windgust")
    val windgust: Double,
    @SerializedName("windspeed")
    val windspeed: Double
)