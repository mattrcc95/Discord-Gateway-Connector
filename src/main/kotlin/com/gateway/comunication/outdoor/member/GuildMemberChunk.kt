package com.gateway.comunication.outdoor.member

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class GuildMemberChunk(
    @SerializedName("t") val t: String,
    @SerializedName("s") val s: Int,
    @SerializedName("op") val op: Int,
    @SerializedName("d") val data: ChunkData,
) {
    companion object {
        fun parseGuildUsers(response: String): List<GuildUser> =
            Gson()
                .fromJson(response, GuildMemberChunk::class.java)
                .data
                .members
                .filter { it.roles.isEmpty() && it.hoistedRole.isBlank() }
                .map { it.user }
    }
}