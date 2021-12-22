package com

import com.gateway.application.Constant.GATEWAY_CONNECTOR
import com.gateway.application.connection.SingletonConnection
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.logging.Logger


@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val log = Logger.getLogger(GATEWAY_CONNECTOR)
    val client = HttpClient(CIO) {
        install(WebSockets)
    }

    coroutineScope {
        launch(Dispatchers.Default) {
            SingletonConnection.holdWebsocketsConnection(client, log)
        }

    }

}

//private suspend fun stayAlive(client: HttpClient, log: Logger) {
//    try {
//        SingletonConnection.holdWebsocketsConnection(client, log)
//    } catch (ex: CancellationException) {
//        restart(log, client, COROUTINE_JOB_DEAD)
//    } catch (ex: ClosedReceiveChannelException) {
//        restart(log, client, WEBSOCKETS_CONNECTION_LOST)
//    }
//}
//
//private suspend fun restart(log: Logger, client: HttpClient, message: String) {
//    log.info(message)
//    SingletonConnection.interactions = 0
//    stayAlive(client, log)
//}
