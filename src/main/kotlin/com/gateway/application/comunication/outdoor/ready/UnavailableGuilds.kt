package com.gateway.application.comunication.outdoor.ready

import com.google.gson.annotations.SerializedName

data class UnavailableGuilds(
    @SerializedName("id") val guildId: String,
    @SerializedName("unavailable") val unavailable: Boolean,
)
