package com.gateway.comunication.indoor.ready

import com.gateway.Constant
import com.gateway.comunication.indoor.GatewayIndoor
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import java.util.logging.Logger

data class Ready(
    @SerializedName("v") val v: String = "",
    @SerializedName("user") val user: UserProperties,
    @SerializedName("guilds") val unavailbaleGuidls: List<UnavailableGuilds> = emptyList(),
    @SerializedName("session_id") val sessionId: String = "",
    @SerializedName("shard") val shard: List<Int>? = null,
    @SerializedName("application") val application: ApplicationDetails
): GatewayIndoor {
    companion object {
        suspend fun DefaultClientWebSocketSession.receiveReady(log: Logger): Ready? =
            (incoming.receive() as? Frame.Text)?.readText()?.let { readyResponse ->
                Gson().fromJson(readyResponse, Ready::class.java).also {
                    log.info("${Constant.READY_OK} --> ${Gson().toJson(it)}")
                }
            }

    }
}