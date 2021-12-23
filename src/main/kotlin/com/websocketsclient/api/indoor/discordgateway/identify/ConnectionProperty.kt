package com.websocketsclient.api.indoor.discordgateway.identify

import com.google.gson.annotations.SerializedName

const val osName = "$" + "os"
const val browserName = "$" + "browser"
const val deviceName = "$" + "device"

data class ConnectionProperty(
    @SerializedName(osName) val os: String = "Windows",
    @SerializedName(browserName) val browser: String = "",
    @SerializedName(deviceName) val device: String = "",
)
