package com.gateway

object Constant {
    const val GATEWAY_CONNECTOR = "gateway connector bot client"
    const val GATEWAY_URL = "wss://gateway.discord.gg/?v=9&encoding=json"
    const val GATEWAY_INITIALIZATION_FAILED = "no heartbeat from gateway"
    const val HEARTBEAT_OK = "heartbeat received"
    const val OP11_OK = "pong op11 ok"
    const val READY_OK = "ready event correctly received from gateway"
    const val COROUTINE_JOB_DEAD = "coroutine job canceled, restarting..."
    const val WEBSOCKETS_CONNECTION_LOST = "gateway connection lost, restarting..."
}