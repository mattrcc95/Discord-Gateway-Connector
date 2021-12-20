package com.gateway.connection

import com.gateway.Constant
import com.gateway.Constant.GATEWAY_URL
import com.gateway.comunication.indoor.guildrequest.Op8
import com.gateway.comunication.indoor.identify.Op2
import com.gateway.comunication.inoutdoor.Op1
import com.gateway.comunication.outdoor.hello.Op10.Companion.receiveOp10Heartbeat
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.delay
import java.util.logging.Logger


object SingletonConnection {
    var interactions: Int = 0

    suspend fun holdWebsocketsConnection(client: HttpClient, log: Logger) {
        client.webSocket(urlString = GATEWAY_URL) {
            this.receiveOp10Heartbeat(log)?.let { gatewayResponse ->
                while (true) {
//                    delay(gatewayResponse.d.heartbeatInterval)
                    delay(1000L)
                    sendToGateway(log)
                    receiveFromGateway(log)
                }
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger): Unit =
        (incoming.receive() as? Frame.Text)?.readText()?.let { response ->
            log.info("${Constant.PONG_OK} --> $response")
        } ?: log.info("response received was null")

    private suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger): Unit =
        when (interactions) {
            1 -> {
                val op2Identification = Gson().toJson(Op2())
                send(op2Identification)
                interactions++
                log.info("sent: $op2Identification")
            }
            2 -> {
                val op8GuildRequest = Gson().toJson(Op8())
                send(op8GuildRequest)
                interactions++
                log.info("sent: $op8GuildRequest")
            }
            else -> {
                val op1 = Gson().toJson(Op1(d = ++interactions))
                send(op1)
                log.info("sent: $op1")
            }
        }

}
