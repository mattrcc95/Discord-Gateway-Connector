package com.gateway.connection

import com.gateway.Constant
import com.gateway.comunication.indoor.Op11.Companion.receiveOp11
import com.gateway.comunication.indoor.hello.Op10.Companion.receiveOp10Heartbeat
import com.gateway.comunication.inoutdoor.Op1
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.delay
import java.util.logging.Logger


object ConnectionHandler {
    private var interactions: Int = 0

    suspend fun handleWebsocketsConnection(client: HttpClient, log: Logger) {
        client.webSocket(urlString = Constant.GATEWAY_URL) {
            this.receiveOp10Heartbeat(log)?.let { gatewayResponse ->
                while (true) {
                    delay(5000L)
                    val op1 = Gson().toJson(Op1(d = ++interactions))
                    this.send(op1)
                    log.info(
                        "${Constant.OP1_OK} --> $op1  " +
                                Thread.currentThread().name +
                                " stack: ${Thread.currentThread().stackTrace.size}"
                    )
                    this.receiveOp11(log)
                }
            }
        }
        client.close()
    }

}