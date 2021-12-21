package com.gateway.comunication.outdoor

import com.gateway.comunication.outdoor.hello.Heartbeat
import com.gateway.comunication.outdoor.guildmembers.GuildMemberChunk
import com.google.gson.annotations.SerializedName

sealed class OutdoorData

data class Op10(
    @SerializedName("op") val op: Int,
    @SerializedName("d") val d: Heartbeat,
    @SerializedName("s") val s: Int?,
    @SerializedName("t") val t: Int?,
) : OutdoorData()

data class GuildMemberChunkWithQueryPrefix(
    val chunk: GuildMemberChunk,
    val queryPrefix: String,
    val totalChunksPerPrefix: Int?,
) : OutdoorData()