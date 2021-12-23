package com.websocketsclient.api.outdoor.gateway.model.guildmembers

import com.google.gson.annotations.SerializedName

data class Member (
    @SerializedName("user") val user: GuildUser,
    @SerializedName("roles") val roles: List<String>,
    @SerializedName("premium_since") val premiumSince: String?,
    @SerializedName("pending") val pending: Boolean,
    @SerializedName("nick") val nick: String?,
    @SerializedName("mute") val mute: Boolean,
    @SerializedName("joined_at") val joinedAt: String,
    @SerializedName("hoisted_role") val hoistedRole: String?,
    @SerializedName("deaf") val deaf: Boolean,
    @SerializedName("communication_disabled_until") val communicationDisabledUntil: String,
    @SerializedName("avatar") val avatar: String,
)
