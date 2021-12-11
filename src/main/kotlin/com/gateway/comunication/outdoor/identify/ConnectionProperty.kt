package com.gateway.comunication.outdoor.identify

import com.google.gson.annotations.SerializedName

const val osDollar = "$" + "os"
const val browserDollar = "$" + "browser"
const val deviceDollar = "$" + "device"

data class ConnectionProperty(
    @SerializedName(osDollar) val os: String = "Windows",
    @SerializedName(browserDollar) val browser: String = "my library",
    @SerializedName(deviceDollar) val device: String = "my library",
)
