package com.gateway.comunication.indoor.ready

import com.gateway.Constant
import com.gateway.comunication.indoor.GatewayIndoor
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import java.util.logging.Logger

data class Ready(
    @SerializedName("t") val t: String,
    @SerializedName("s") val s: Int,
    @SerializedName("op") val op: Int,
    @SerializedName("d") val attributes: ReadyAttributes
) : GatewayIndoor {
    companion object {
        suspend fun DefaultClientWebSocketSession.receiveReady(log: Logger): Ready? =
            (incoming.receive() as? Frame.Text)?.readText()?.let { readyResponse ->
                Gson().fromJson(readyResponse, Ready::class.java).also {
                    log.info("${Constant.READY_OK} --> ${Gson().toJson(it)}")
                }
            }
    }
}