package com.gateway.comunication.indoor

import com.gateway.Constant
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import java.time.ZonedDateTime
import java.util.logging.Logger

data class Op11(
    @SerializedName("op") val op: Int = 0
) {
    companion object{
         suspend fun DefaultClientWebSocketSession.receiveOp11(log: Logger): Op11? =
            (incoming.receive() as? Frame.Text)?.readText()?.let { op11Response ->
                Gson().fromJson(op11Response, Op11::class.java).also {
                    log.info(
                        "${Constant.OP11_OK}${ZonedDateTime.now()} " + "--> ${it.op}  + " +
                                Thread.currentThread().name +
                                " stack: ${Thread.currentThread().stackTrace.size}"
                    )
                }
            }
    }
}