package com.httpclient.api.outdoor.discordapi.model.dmchannel

import com.google.gson.annotations.SerializedName

data class DMChannel (
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: Int,
    @SerializedName("last_message_id") val lastMessageId: String?,
    @SerializedName("recipients") val recipients: List<Recipient>
)