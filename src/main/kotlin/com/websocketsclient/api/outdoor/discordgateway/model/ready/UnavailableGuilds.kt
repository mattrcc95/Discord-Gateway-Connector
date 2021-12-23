package com.websocketsclient.api.outdoor.discordgateway.model.ready

import com.google.gson.annotations.SerializedName

data class UnavailableGuilds(
    @SerializedName("id") val guildId: String,
    @SerializedName("unavailable") val unavailable: Boolean,
)
