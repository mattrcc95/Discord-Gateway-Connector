package com.gateway.comunication.outdoor.guildmembers

import com.google.gson.annotations.SerializedName

data class GuildMemberChunk(
    @SerializedName("t") val t: String,
    @SerializedName("s") val s: Int,
    @SerializedName("op") val op: Int,
    @SerializedName("d") val data: ChunkData,
)