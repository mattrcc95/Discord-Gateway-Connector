package com.websocketsclient.api.outdoor.gateway.model.guildmembers

import com.google.gson.annotations.SerializedName

data class ChunkData(
    @SerializedName("members") val members: List<Member>,
    @SerializedName("guild_id") val guildId: String,
    @SerializedName("chunk_index") val chunkIndex: Int,
    @SerializedName("chunk_count") val chunkCount: Int,
)
