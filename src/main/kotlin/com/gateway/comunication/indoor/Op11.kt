package com.gateway.comunication.indoor

import com.gateway.Constant.OP11_OK
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import java.util.logging.Logger

data class Op11(
    @SerializedName("op") val op: Int = 11
): GatewayIndoor {
    companion object {
        suspend fun DefaultClientWebSocketSession.receiveOp11(log: Logger): Op11? =
            (incoming.receive() as? Frame.Text)?.readText()?.let { op11Response ->
                Gson().fromJson(op11Response, Op11::class.java).also {
                    log.info("$OP11_OK --> ${it.op}")
                }
            }
    }
}
