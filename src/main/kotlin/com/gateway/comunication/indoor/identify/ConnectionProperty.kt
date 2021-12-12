package com.gateway.comunication.indoor.identify

import com.google.gson.annotations.SerializedName

const val osName = "$" + "os"
const val browserName = "$" + "browser"
const val deviceName = "$" + "device"

data class ConnectionProperty(
    @SerializedName(osName) val os: String = "Windows",
    @SerializedName(browserName) val browser: String = "my library",
    @SerializedName(deviceName) val device: String = "my library",
)
