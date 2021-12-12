package com.gateway.connection

import com.gateway.Constant.GATEWAY_URL
import com.gateway.comunication.outdoor.GatewayOutdoor
import com.gateway.comunication.outdoor.Op11.Companion.receiveOp11
import com.gateway.comunication.outdoor.hello.Op10.Companion.receiveOp10Heartbeat
import com.gateway.comunication.outdoor.ready.Ready.Companion.receiveReady
import com.gateway.comunication.inoutdoor.Op1
import com.gateway.comunication.indoor.identify.Op2
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
                    delay(gatewayResponse.d.heartbeatInterval)
                    sendToGateway(log)
                    receiveFromGateway(log)
                }
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger): GatewayOutdoor? =
        if (interactions != 2)
            receiveOp11(log)
        else receiveReady(log)

    private suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger) {
        if (interactions != 1) {
            val op1 = Gson().toJson(Op1(d = ++interactions))
            send(op1)
            log.info("sent: $op1")
        } else {
            val op2Identification = Gson().toJson(Op2())
            send(op2Identification)
            interactions++
            log.info("sent: $op2Identification")
        }
    }

}
