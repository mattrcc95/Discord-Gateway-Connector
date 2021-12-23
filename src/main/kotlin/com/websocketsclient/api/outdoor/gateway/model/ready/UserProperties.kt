package com.websocketsclient.api.outdoor.gateway.model.ready

import com.google.gson.annotations.SerializedName

data class UserProperties(
    @SerializedName("verified") val verified: Boolean,
    @SerializedName("username") val username: String,
    @SerializedName("mfa_enabled") val mfaEnabled: Boolean,
    @SerializedName("id") val id: String,
    @SerializedName("flags") val flags: Int?,
    @SerializedName("email") val email: String?,
    @SerializedName("discriminator") val discriminator: String?,
    @SerializedName("bot") val bot: Boolean?,
    @SerializedName("avatar") val avatar: String?
)
