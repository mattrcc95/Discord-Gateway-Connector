package com.gateway.connection

import com.gateway.Constant.GATEWAY_URL
import com.gateway.Constant.OP1_OK
import com.gateway.comunication.indoor.GatewayIndoor
import com.gateway.comunication.indoor.hello.Op10.Companion.receiveOp10Heartbeat
import com.gateway.comunication.inoutdoor.Op1
import com.gateway.comunication.outdoor.identify.Op2
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
                    delay(5000L)

                    if (interactions != 1) {
                        val op1 = Gson().toJson(Op1(d = ++interactions))
                        send(op1)
                        log.info("$OP1_OK --> $op1")
                    } else {
                        val op2Identification = Gson().toJson(Op2())
                        send(op2Identification)
                    }

                    receiveFromGateway(log)

                }
            }
        }
    }

    /*
    make superclass GatewayIndoor and use this
     */
    private suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger): GatewayIndoor? =
        (incoming.receive() as? Frame.Text)?.readText()?.let { gatewayResponse ->
            Gson().fromJson(gatewayResponse, GatewayIndoor::class.java)
//                .also {
//                log.info("${Constant.READY_OK} --> ${Gson().toJson(it)}")
//            }
    }


}
