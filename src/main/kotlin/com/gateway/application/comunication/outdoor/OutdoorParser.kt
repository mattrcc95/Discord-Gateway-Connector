package com.gateway.application.comunication.outdoor

import com.gateway.application.Constant.NOT_PARSABLE
import com.google.gson.Gson
import java.util.logging.Logger

object OutdoorParser {

    fun String.toGuildMemberChunk(): GuildMemberChunk = Gson().fromJson(this, GuildMemberChunk::class.java)

    fun String.toOp11OrNothing(log: Logger): OutdoorData = try {
        Gson().fromJson(this, Op11::class.java)
    } catch (ex: Exception) {
        NotParsableYet.also {
            log.info("$NOT_PARSABLE $this")
        }
    }

    fun String.toHeartbeat(): Op10 = Gson().fromJson(this, Op10::class.java)
}
