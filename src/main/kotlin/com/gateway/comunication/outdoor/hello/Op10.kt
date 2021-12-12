package com.gateway.comunication.outdoor.hello

import com.gateway.Constant.HEARTBEAT_OK
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import java.util.logging.Logger

data class Op10(
    @SerializedName("op") val op: Int,
    @SerializedName("d") val d: Heartbeat,
    @SerializedName("s") val s: Int?,
    @SerializedName("t") val t: Int?,
) {
    companion object {
        suspend fun DefaultClientWebSocketSession.receiveOp10Heartbeat(log: Logger): Op10? =
            (incoming.receive() as? Frame.Text)?.readText()?.let { op10HeartbeatResponse ->
                val heartbeat = Gson().fromJson(op10HeartbeatResponse, Op10::class.java)
                heartbeat?.also {
                    log.info("$HEARTBEAT_OK --> ${it.d.heartbeatInterval}")
                }
            }
    }
}