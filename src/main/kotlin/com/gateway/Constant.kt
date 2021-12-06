package com.gateway

object Constant {
    const val GATEWAY_CONNECTOR = "gateway connector client"
    const val GATEWAY_URL = "wss://gateway.discord.gg/?v=9&encoding=json"
    const val GATEWAY_INITIALIZATION_FAILED = "no heartbeat from gateway "
    const val HEARTBEAT_OK = "heartbeat received at "
    const val PING_OK = "ping ok "
    const val PONG_OK = "pong ok "
    const val COROUTINE_JOB_DEAD = "coroutine job canceled, restarting... "
    const val WEBSOCKETS_CONNECTION_LOST = "gateway connection lost, restarting... "

}