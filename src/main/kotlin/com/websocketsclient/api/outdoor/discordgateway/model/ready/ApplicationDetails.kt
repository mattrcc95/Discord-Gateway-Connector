package com.websocketsclient.api.outdoor.discordgateway.model.ready

import com.google.gson.annotations.SerializedName

data class ApplicationDetails(
    @SerializedName("id") val applicationId: String,
    @SerializedName("flags") val flags: Int,
)
